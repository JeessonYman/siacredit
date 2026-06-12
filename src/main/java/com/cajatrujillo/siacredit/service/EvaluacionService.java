package com.cajatrujillo.siacredit.service;

import com.cajatrujillo.siacredit.model.Prospecto;
import com.cajatrujillo.siacredit.repository.ProspectoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.Map;
import java.util.HashMap;

@Service
public class EvaluacionService {

    private final ProspectoRepository repository;
    private final String DOMINIO_COLAB = "https://uninsular-herbert-droopily.ngrok-free.dev";

    public EvaluacionService(ProspectoRepository repository) {
        this.repository = repository;
    }

    public Prospecto agenteMariamProcesar(String dni, String whatsapp, Double monto, Integer cuotas, MultipartFile reciboImagen) {
        Prospecto p = new Prospecto(); 
        p.setDni(dni); 
        p.setWhatsapp(whatsapp);
        p.setMontoPropuesto(monto);
        p.setCuotas(cuotas);
        
        try {
            // SOLUCIÓN AL TIMEOUT (NGROK_3004): Le damos a Java 3 minutos de paciencia
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(180000); 
            requestFactory.setReadTimeout(180000);
            RestTemplate restTemplate = new RestTemplate(requestFactory);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("dni", dni);
            body.add("recibo", new org.springframework.core.io.ByteArrayResource(reciboImagen.getBytes()) {
                @Override public String getFilename() { return reciboImagen.getOriginalFilename(); }
            });
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(DOMINIO_COLAB + "/api/mariam/evaluar", requestEntity, Map.class);
            Map<String, Object> rc = response.getBody();

            if (rc != null) {
                p.setDireccionOcr((String) rc.get("direccion_limpia")); 
                p.setEstado((String) rc.get("estado"));
                p.setRecomendacionMariam((String) rc.get("recomendacion"));
                p.setNombreCompleto((String) rc.get("nombre_completo"));
                p.setEstadoCivil((String) rc.get("estado_civil"));
                p.setFotoUrl((String) rc.get("foto_perfil"));
                p.setDireccionReniec((String) rc.get("direccion_reniec"));
                p.setNombres((String) rc.get("nombres"));
                p.setApellidos((String) rc.get("apellidos"));
                p.setFechaNacimiento((String) rc.get("fecha_nacimiento"));
                p.setDepartamento((String) rc.get("departamento"));
                p.setProvincia((String) rc.get("provincia"));
                p.setDistrito((String) rc.get("distrito"));
                p.setUbigeo((String) rc.get("ubigeo"));
                p.setUrlMaps((String) rc.get("url_maps"));
                p.setPrestamosRecientes((String) rc.get("prestamos_recientes"));

                Object scoreObj = rc.get("score_sbs");
                if(scoreObj != null) p.setScoreSbs(Integer.parseInt(scoreObj.toString()));
            }
        } catch (Exception e) { 
            System.out.println("ERROR MARIAM EVALUACIÓN: " + e.getMessage());
            p.setEstado("ERROR"); 
        }
        return repository.save(p);
    }

    public String consultarMariamChat(String mensaje, String dni) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("contexto_dni", dni);
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(DOMINIO_COLAB + "/api/mariam/consultas", requestEntity, Map.class);
            return (String) response.getBody().get("respuesta");
        } catch (Exception e) { 
            return "MARIAM (Fuera de línea): No pude conectarme al Cerebro en Colab."; 
        }
    }
}