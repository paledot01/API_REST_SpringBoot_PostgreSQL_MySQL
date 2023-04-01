package com.cibertec.shoesformen_api.service;

import com.cibertec.shoesformen_api.exception.EmpleadoNotFoundException;
import com.cibertec.shoesformen_api.model.Distrito;
import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.Estado;
import com.cibertec.shoesformen_api.model.Rol;
import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import com.cibertec.shoesformen_api.repository.DistritoRepository;
import com.cibertec.shoesformen_api.repository.EmpleadoRepository;
import com.cibertec.shoesformen_api.repository.EstadoRepository;
import com.cibertec.shoesformen_api.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private EmpleadoRepository empleadoRepo;
    @Autowired
    private DistritoRepository distritoRepo;
    @Autowired
    private EstadoRepository estadoRepo;
    @Autowired
    private RolRepository rolRepo;

    //@Override
    //public ResponseEntity<Object> listar() {
    //    List<Empleado> lista = empleadoRepo.findAll();
    //    if(lista.isEmpty()){
    //        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //    }
    //    return new ResponseEntity<>(lista, HttpStatus.OK);
    //}


    @Override
    public List<Empleado> listar() throws EmpleadoNotFoundException {
        List<Empleado> lista = empleadoRepo.findAll();
        if(lista.isEmpty()){
            throw new EmpleadoNotFoundException("lista vacia");
        }
        return lista;
    }

    @Override
    public void eliminar(Empleado empleado) {
        empleadoRepo.delete(empleado);
    }

    @Override
    public void eliminarByCodigo(String codigo) {
        empleadoRepo.deleteById(codigo);
    }

    @Override
    public Empleado guardar(EmpleadoDTO dto) throws IllegalArgumentException{

        // findById cuando el id es null -> IllegalArgumentException [por defecto]
        // findById cuando no retorna nada porque no existe nada con ese ID. OPTIONAL -> XXException
        //  SQL UNIQUE ->

        Distrito dis = distritoRepo.findById(dto.getCodDistrito()).orElseThrow(() -> new SecurityException("Invalido id NULL"));
        Estado est = estadoRepo.findById(dto.getCodEstado()).orElseThrow(() -> new IllegalArgumentException("Invalido id NULL"));
        List<Rol> roles = Arrays.asList(rolRepo.findById("RL02").orElseThrow(() -> new IllegalArgumentException("")));

        Empleado empleado = new Empleado(
                this.getUltimoCodigo(),
                dis,
                est,
                dto.getNombre(),
                dto.getApellidos(),
                dto.getDni(),
                dto.getDireccion(),
                dto.getTelefono(),
                dto.getEmail(),
                dto.getUsuario(),
                dto.getContrasena(),
                roles);
        return empleadoRepo.save(empleado);
    }

    @Override
    public Optional<Empleado> getEmpleadoByCodigo(String codigo) {
        return empleadoRepo.findById(codigo);
    }

    @Override
    public String getUltimoCodigo() {
        String codigo_ultimo = empleadoRepo.getUltimoCodigo();
        String codigo_nuevo = "EM10001";
        if(codigo_ultimo != null){
            return codigo_ultimo;
        }
        return codigo_nuevo;
    }

}
