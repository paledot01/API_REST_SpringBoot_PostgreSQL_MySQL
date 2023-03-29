package com.cibertec.shoesformen_api.controller;

import com.cibertec.shoesformen_api.model.Distrito;
import com.cibertec.shoesformen_api.repository.DistritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/")
public class DistritoController {

    @Autowired
    private DistritoRepository distritoRepo;


    @GetMapping("/distritos")
    public List<Distrito> all() {
        return distritoRepo.findAll();
    }

}
