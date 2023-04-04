package com.cibertec.shoesformen_api.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor()
public class EmpleadoPOJO {

    private String codEmpleado;
    private String distrito; // --
    private String estado; // --
    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String telefono;
    private String email;
    private String usuario;
    private String contrasena;

}
