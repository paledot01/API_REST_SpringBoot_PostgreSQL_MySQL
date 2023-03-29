package com.cibertec.shoesformen_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "estado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Estado implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cod_estado")
    private String codEstado;
    @Column(name = "nombre_estado")
    private String nombreEstado;

}
