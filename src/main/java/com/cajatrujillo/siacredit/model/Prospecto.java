package com.cajatrujillo.siacredit.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prospectos")
public class Prospecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String dni;
    private String whatsapp;
    private String suministro;
    private String direccionOcr;
    private String estado; 
    private boolean alertaReciente; 
    private Double montoPropuesto;
    private Double tasa;
    private Integer cuotas;
    private LocalDateTime fechaRegistro;

    @Column(length = 1000)
    private String recomendacionMariam; 

    private String nombreCompleto;
    private String estadoCivil;
    
    @Column(length = 1000)
    private String fotoUrl;
    
    private String direccionReniec;
    private Integer scoreSbs;

    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String departamento;
    private String provincia;
    private String distrito;
    private String ubigeo;
    
    @Column(length = 500)
    private String urlMaps;
    
    @Column(length = 255)
    private String prestamosRecientes;

    public Prospecto() { this.fechaRegistro = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getWhatsapp() { return whatsapp; }
    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }
    public String getSuministro() { return suministro; }
    public void setSuministro(String suministro) { this.suministro = suministro; }
    public String getDireccionOcr() { return direccionOcr; }
    public void setDireccionOcr(String direccionOcr) { this.direccionOcr = direccionOcr; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public boolean isAlertaReciente() { return alertaReciente; }
    public void setAlertaReciente(boolean alertaReciente) { this.alertaReciente = alertaReciente; }
    public Double getMontoPropuesto() { return montoPropuesto; }
    public void setMontoPropuesto(Double montoPropuesto) { this.montoPropuesto = montoPropuesto; }
    public Double getTasa() { return tasa; }
    public void setTasa(Double tasa) { this.tasa = tasa; }
    public Integer getCuotas() { return cuotas; }
    public void setCuotas(Integer cuotas) { this.cuotas = cuotas; }
    public String getRecomendacionMariam() { return recomendacionMariam; }
    public void setRecomendacionMariam(String recomendacionMariam) { this.recomendacionMariam = recomendacionMariam; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    public String getDireccionReniec() { return direccionReniec; }
    public void setDireccionReniec(String direccionReniec) { this.direccionReniec = direccionReniec; }
    public Integer getScoreSbs() { return scoreSbs; }
    public void setScoreSbs(Integer scoreSbs) { this.scoreSbs = scoreSbs; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }
    public String getUbigeo() { return ubigeo; }
    public void setUbigeo(String ubigeo) { this.ubigeo = ubigeo; }
    public String getUrlMaps() { return urlMaps; }
    public void setUrlMaps(String urlMaps) { this.urlMaps = urlMaps; }
    public String getPrestamosRecientes() { return prestamosRecientes; }
    public void setPrestamosRecientes(String prestamosRecientes) { this.prestamosRecientes = prestamosRecientes; }
}