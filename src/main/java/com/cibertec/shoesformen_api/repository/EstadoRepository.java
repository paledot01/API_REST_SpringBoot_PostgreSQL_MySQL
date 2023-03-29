package com.cibertec.shoesformen_api.repository;

import com.cibertec.shoesformen_api.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado,String> {

}
