package com.cibertec.shoesformen_api.repository;

import com.cibertec.shoesformen_api.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

    // CONSULTA NATIVA
    @Query(value="SELECT CONCAT('EM',CAST(SUBSTRING(MAX(e.cod_empleado),3) AS INT) + 1)  FROM empleado AS e", nativeQuery = true)
    public abstract String getUltimoCodigo();

    // Automatico
    public abstract Empleado findByUsuario(String usuario);

}
