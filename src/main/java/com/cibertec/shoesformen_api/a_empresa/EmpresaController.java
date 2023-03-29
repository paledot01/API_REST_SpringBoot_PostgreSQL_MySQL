package com.cibertec.shoesformen_api.a_empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepo;

    @GetMapping("/empresa")
    public List<Empresa> all() {
        return empresaRepo.findAll();
    }

    @PutMapping("/empresa/{id}") // ACTUALIZACION
    public Empresa replaceEmpresa(@RequestBody Empresa newEmpresa, @PathVariable String id){

        return empresaRepo.findById(id) // actualizacion pero si no encuentra con el id lo crea
                .map(empresa -> { // a la empresa encontrada no voy a cambiar su ID
                    empresa.setRuc(newEmpresa.getRuc());
                    empresa.setNombre(newEmpresa.getNombre());
                    empresa.setDireccion(newEmpresa.getDireccion());
                    empresa.setDistrito(newEmpresa.getDistrito());
                    empresa.setTelefono(newEmpresa.getTelefono());
                    empresa.setImagen(newEmpresa.getImagen());
                    return empresaRepo.save(empresa);
                })
                .orElseGet(() -> { // si no lo encuentra lo crea pero esto nunca pasa en este caso
                    return empresaRepo.save(newEmpresa);
                });
    }

}
