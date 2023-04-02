package com.cibertec.shoesformen_api.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor()
public class EmpleadoDTO {

        // los que no son @NotBlank pueden ser null o vacio.
        @NotBlank(message = "el COD_DISTRITO es obligatorio") // ** no NULL y no vacio
        private String codDistrito;
        @NotBlank(message = "el COD_ESTADO es obligatorio") // **
        private String codEstado;
        @NotBlank(message = "el NOMBRE es obligatorio") // **
        private String nombre;
        @NotBlank(message = "el APELLIDO es obligatorio") // **
        private String apellidos;
        @Pattern(regexp = "^\\d{8}$", message = "DNI invalido")
        private String dni;
        @Size(min=5, message = "la DIRECCION no debe tener un tamaño menor a 5")
        @Size(max=45, message = "la DIRECCION no debe tener un tamaño mayor a 45")
        private String direccion;
        @Pattern(regexp = "^\\d{7}$", message = "numero de TELEFONO invalido")
        private String telefono;
        @Email(message = "EMAIL invalido")
        private String email;
        @NotBlank(message = "el USUARIO es obligatorio") // **
        private String usuario;
        @NotBlank(message = "la CONTRASEÑA es obligatorio") // **
        private String contrasena;
        //private Collection<String> codRoles;

        //
        // no encuentra el estado ni el distrito con le codigo enviado
        // datos, validador, codigo existentes, pero la BD detecta campos que deberia ser unico repetidos.

}
