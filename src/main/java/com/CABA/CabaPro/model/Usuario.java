
package com.CABA.CabaPro.model;

public class Usuario {
    private Long id;
    private String nombre;
    private String contrasena;
    private String email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
