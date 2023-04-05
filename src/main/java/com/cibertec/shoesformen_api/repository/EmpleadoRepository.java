package com.cibertec.shoesformen_api.repository;

import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.pojo.EmpleadoPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

    // INNER JOIN con JPQL
    @Query("SELECT new com.cibertec.shoesformen_api.model.pojo.EmpleadoPOJO("
            + "e.codEmpleado,"
            + "e.distrito.nombreDistrito,"
            + "e.estado.nombreEstado,"
            + "e.nombre,"
            + "e.apellidos,"
            + "e.dni,"
            + "e.direccion,"
            + "e.telefono,"
            + "e.email,"
            + "e.usuario,"
            + "e.contrasena"
            + ") "
            + "FROM Empleado e ORDER BY e.codEmpleado ASC")
    public abstract List<EmpleadoPOJO> listaPOJO();

    // CONSULTA NATIVA
    @Query(value="SELECT CONCAT('EM',CAST(SUBSTRING(MAX(e.cod_empleado),3) AS INT) + 1)  FROM empleado AS e", nativeQuery = true)
    public abstract String getNuevoCodigo();

    // Automatico
    public abstract Empleado findByUsuario(String usuario);

}
