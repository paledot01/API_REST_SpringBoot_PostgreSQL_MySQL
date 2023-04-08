package com.cibertec.shoesformen_api.repository;

import com.cibertec.shoesformen_api.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, String> {

}
