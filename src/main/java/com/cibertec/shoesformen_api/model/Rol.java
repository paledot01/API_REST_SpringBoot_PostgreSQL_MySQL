package com.cibertec.shoesformen_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rol implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cod_rol")
    private String codRol;
    @Column(name = "nombre_rol")
    private String nombreRol;

}
