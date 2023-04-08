package com.cibertec.shoesformen_api.controller;

import com.cibertec.shoesformen_api.model.Distrito;
import com.cibertec.shoesformen_api.repository.DistritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/distritos")
public class DistritoController {

    @Autowired
    private DistritoRepository distritoRepo;


    @GetMapping()
    public List<Distrito> all() {
        return distritoRepo.findAll();
    }

    @GetMapping("/list")
    public ResponseEntity<List<Distrito>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "codDistrito") String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Distrito> pagedResult = distritoRepo.findAll(paging);
        List<Distrito> lista;
        if (pagedResult.hasContent()){
            lista = pagedResult.getContent();
        } else {
            lista = new ArrayList<Distrito>();
        }
        return new ResponseEntity<List<Distrito>>(lista, HttpStatus.OK);
    }

}
