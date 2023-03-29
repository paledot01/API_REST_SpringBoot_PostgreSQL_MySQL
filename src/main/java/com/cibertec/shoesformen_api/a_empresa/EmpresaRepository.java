package com.cibertec.shoesformen_api.a_empresa;

import com.cibertec.shoesformen_api.a_empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String> {



}
