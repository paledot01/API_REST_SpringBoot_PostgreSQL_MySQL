package com.cibertec.shoesformen_api.controller;

import com.cibertec.shoesformen_api.exception.EmpleadoNotFoundException;
import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import com.cibertec.shoesformen_api.repository.DistritoRepository;
import com.cibertec.shoesformen_api.repository.EmpleadoRepository;
import com.cibertec.shoesformen_api.repository.EstadoRepository;
import com.cibertec.shoesformen_api.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    /*
    * 200 -> OK
    * 204 -> OK pero sin contenido
    * */

    @Autowired
    private EmpleadoService empleadoServ;
    @Autowired
    private EmpleadoRepository empleadoRepo;

    @Autowired
    private DistritoRepository distritoRepo;
    @Autowired
    private EstadoRepository estadoRepo;

    @GetMapping() // al entrar a la ruta principal "/empleados" se activa el metodo GET sin ruta especificada.
    public ResponseEntity<List<Empleado>> getAll() throws EmpleadoNotFoundException {
        return ResponseEntity.ok(empleadoServ.listar());
    }


    @PostMapping // post por defecto
    public ResponseEntity<Empleado> newEmpleado (@RequestBody @Valid EmpleadoDTO dto){ /** sda  **/
        return new ResponseEntity<>(empleadoServ.guardar(dto), HttpStatus.CREATED);
    }

//    @PutMapping("/{id}") // ACTUALIZACION, su objetivo es actualizar no crear, por eso debe verificar si existe.
//    public ResponseEntity replaceEmpleado(@RequestBody Empleado newEmpleado, @PathVariable String id){ // imaginate que el id no coincida con el de empleado que envia. Ademas el empleado no deberia venir con ID, ¿porque?
//        return empleadoServ.getEmpleadoByCodigo(id)
//                .map(empleado ->{
//                    empleado.setDistrito(newEmpleado.getDistrito());
//                    empleado.setEstado(newEmpleado.getEstado());
//                    empleado.setNombre(newEmpleado.getNombre());
//                    empleado.setApellidos(newEmpleado.getApellidos());
//                    empleado.setDni(newEmpleado.getDni());
//                    empleado.setDireccion(newEmpleado.getDireccion());
//                    empleado.setTelefono(newEmpleado.getTelefono());
//                    empleado.setEmail(newEmpleado.getEmail());
//                    empleado.setUsuario(newEmpleado.getUsuario());
//                    empleado.setContrasena(newEmpleado.getContrasena());
//                    return new ResponseEntity(empleadoServ.guardar(empleado), HttpStatus.OK);
//                }).orElseGet(() -> {
//                    return new ResponseEntity("Empleado no encontrado", HttpStatus.NOT_FOUND);
//                });
//    }




    /* Configurar la respuesta HTTP
    *  ResponseEntity: representa la respuesta completa HTTP: status code, headers y body.
    *  @ResponseBody: por defecto en un @Controller devuelve un HTML, pero con esta anotacion permite devolver directamente el resultado del metodo como respuesta HTTP.
    *  @ResponseStatus: permite personalizar el status de la respuesta en caso de ERROR
    * */


}
