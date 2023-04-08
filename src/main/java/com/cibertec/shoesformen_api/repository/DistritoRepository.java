package com.cibertec.shoesformen_api.repository;

import com.cibertec.shoesformen_api.model.Distrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, String> {
    // Paginacion: extender de "PagingAndSortingRepository" o de "JpaRepository" porque
    //             este ya extiende del primero.
}
