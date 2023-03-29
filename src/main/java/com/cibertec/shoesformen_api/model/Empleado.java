package com.cibertec.shoesformen_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "empleado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Empleado implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cod_empleado")
    private String codEmpleado;
    @ManyToOne
    @JoinColumn(name = "cod_distrito")
    private Distrito distrito;
    @ManyToOne
    @JoinColumn(name = "cod_estado")
    private Estado estado;
    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String telefono;
    private String email;
    private String usuario;
    private String contrasena;

    @ManyToMany(fetch = FetchType.EAGER) //cascade = CascadeType.ALL
    @JoinTable(name = "empleado_rol", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "cod_empleado", referencedColumnName = "cod_empleado"), // el primer cod_empleado hace referencia a la tabla intermedia, el segundo a esta tabla empleado
            inverseJoinColumns = @JoinColumn(name = "cod_rol", referencedColumnName = "cod_rol") // el 2do cod_rol hace referencia al pk de la tabla Rol
    )
    private Collection<Rol> roles; // este campo no esta realmente en la tabla

}
