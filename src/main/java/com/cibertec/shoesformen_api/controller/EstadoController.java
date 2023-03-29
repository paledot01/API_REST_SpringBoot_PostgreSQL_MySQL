package com.cibertec.shoesformen_api.controller;

import com.cibertec.shoesformen_api.model.Estado;
import com.cibertec.shoesformen_api.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepo;


    @GetMapping("/estados")
    public List<Estado> all() {
        return estadoRepo.findAll();
    }

}
