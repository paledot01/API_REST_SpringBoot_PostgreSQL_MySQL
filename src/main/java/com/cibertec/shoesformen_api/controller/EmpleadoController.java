package com.cibertec.shoesformen_api.controller;

import com.cibertec.shoesformen_api.exception.EmpleadoNotFoundException;
import com.cibertec.shoesformen_api.exception.EntidadNotFoundException;
import com.cibertec.shoesformen_api.exception.ListEmptyException;
import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import com.cibertec.shoesformen_api.repository.DistritoRepository;
import com.cibertec.shoesformen_api.repository.EmpleadoRepository;
import com.cibertec.shoesformen_api.repository.EstadoRepository;
import com.cibertec.shoesformen_api.service.EmpleadoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<List<Empleado>> getAll() throws ListEmptyException{
        return ResponseEntity.ok(empleadoServ.listar());
    }

    @PostMapping // post por defecto
    public ResponseEntity<Empleado> newEmpleado (@RequestBody @Valid EmpleadoDTO dto) throws IllegalArgumentException, EntidadNotFoundException {
        Empleado empleado = empleadoServ.buildEmpleado(dto); // -> construyo el empleado sin cod_empleado
        empleado.setCodEmpleado(empleadoServ.createNewCodigo()); // -> insertamos el codigo que debe tener
        return new ResponseEntity<>(empleadoServ.guardar(empleado), HttpStatus.CREATED); // -> insertamos el empleado
    }

    @PutMapping("/{id}") // ACTUALIZACION, su objetivo es actualizar no crear, por eso debe verificar si existe.
    public ResponseEntity replaceEmpleado(@RequestBody @Valid EmpleadoDTO dto, @PathVariable String id) throws IllegalArgumentException, EntidadNotFoundException{ // imaginate que el id no coincida con el de empleado que envia. Ademas el empleado no deberia venir con ID, ¿porque?
        if(empleadoServ.getEmpleadoByCodigo(id).isPresent() ){
            Empleado empleado = empleadoServ.buildEmpleado(dto);
            empleado.setCodEmpleado(id);
            return new ResponseEntity(empleadoServ.guardar(empleado), HttpStatus.OK);
        } else {
            throw new EntidadNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) throws IllegalArgumentException, EntidadNotFoundException{
        if(empleadoServ.getEmpleadoByCodigo(id).isPresent() ){
            empleadoServ.eliminarByCodigo(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new EntidadNotFoundException(id + " - No eliminado");
        }
    }

    @GetMapping("/rpt_EXCEL")
    private ResponseEntity<Void> reporteEmpleadoEXCEL(HttpServletResponse response) throws JRException, IOException {
        empleadoServ.exportarReporte("excel", response);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/rpt_PDF", produces = MediaType.APPLICATION_PDF_VALUE)
    private ResponseEntity<Void> reporteEmpleadoPDF(HttpServletResponse response) throws JRException,IOException{
        empleadoServ.exportarReporte("pdf", response);
        return ResponseEntity.ok().build();
    }


    /* Configurar la respuesta HTTP
    *  ResponseEntity: representa la respuesta completa HTTP: status code, headers y body.
    *  @ResponseBody: por defecto en un @Controller devuelve un HTML, pero con esta anotacion permite devolver directamente el resultado del metodo como respuesta HTTP.
    *  @ResponseStatus: permite personalizar el status de la respuesta en caso de ERROR
    * */


}
