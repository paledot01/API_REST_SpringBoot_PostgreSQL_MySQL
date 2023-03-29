package com.cibertec.shoesformen_api.a_empresa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Empresa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String codigo;
    private String ruc;
    private String nombre;
    private String direccion;
    private String distrito;
    private String telefono;
    private String imagen;

}
