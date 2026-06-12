package com.cajatrujillo.siacredit.controller;

import com.cajatrujillo.siacredit.model.Prospecto;
import com.cajatrujillo.siacredit.service.EvaluacionService;
import com.cajatrujillo.siacredit.repository.ProspectoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    private final EvaluacionService mariam;
    private final ProspectoRepository repo;

    public WebController(EvaluacionService mariam, ProspectoRepository repo) {
        this.mariam = mariam;
        this.repo = repo;
    }

    @GetMapping("/") public String cliente() { return "index"; }

    @PostMapping("/solicitar")
public String solicitar(
        @RequestParam("dni") String dni, 
        @RequestParam("whatsapp") String whatsapp, 
        @RequestParam("monto") Double monto,
        @RequestParam("cuotas") Integer cuotas,
        @RequestParam("recibo") MultipartFile recibo, Model model) {
    
    Prospecto res = mariam.agenteMariamProcesar(dni, whatsapp, monto, cuotas, recibo); 
    model.addAttribute("p", res);
    return "index";
}

    @GetMapping("/login") public String login() { return "login"; }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("usuario") String user, @RequestParam("password") String pass, HttpSession session, Model model) {
        if(("maria".equals(user) || "juan".equals(user) || "admin".equals(user)) && "123456".equals(pass)) {
            session.setAttribute("asesorLogueado", user.toUpperCase()); 
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }

    @GetMapping("/dashboard")
    public String admin(HttpSession session, Model model) {
        String asesor = (String) session.getAttribute("asesorLogueado");
        if(asesor == null) return "redirect:/login"; 
        model.addAttribute("nombreAsesor", asesor); 
        model.addAttribute("lista", repo.findAll());
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/api/chat-mariam")
    @ResponseBody
    public String chatMariamBackend(@RequestParam("mensaje") String mensaje, @RequestParam("dni") String dni) {
        return mariam.consultarMariamChat(mensaje, dni);
    }
}