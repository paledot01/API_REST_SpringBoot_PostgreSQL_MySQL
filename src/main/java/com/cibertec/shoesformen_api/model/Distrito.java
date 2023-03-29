package com.cibertec.shoesformen_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "distrito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Data
public class Distrito implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cod_distrito")
    private String codDistrito;
    @Column(name = "nombre_distrito")
    private String nombreDistrito;


}
