package com.cibertec.shoesformen_api.controller;

import com.cibertec.shoesformen_api.exception.EntidadNotFoundException;
import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import com.cibertec.shoesformen_api.repository.DistritoRepository;
import com.cibertec.shoesformen_api.repository.EstadoRepository;
import com.cibertec.shoesformen_api.service.EmpleadoService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    private DistritoRepository distritoRepo;
    @Autowired
    private EstadoRepository estadoRepo;

//    LISTA NORMAL
//    @GetMapping() // al entrar a la ruta principal "/empleados" se activa el metodo GET sin ruta especificada.
//    public ResponseEntity<List<Empleado>> getAll() {
//        return ResponseEntity.ok(empleadoServ.listar());
//    }

    @GetMapping() // LISTAR CON PAGINACION
    public ResponseEntity<List<Empleado>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size,
            @RequestParam(defaultValue = "nombre") String sort)
    {
        List<Empleado> lista = empleadoServ.listar(page, size, sort);
        return new ResponseEntity<List<Empleado>>(lista, HttpStatus.OK);
    }

    @PostMapping // post por defecto
    public ResponseEntity<Empleado> newEmpleado (@RequestBody EmpleadoDTO dto) { // @Valid -> quitado, porque la validacion lo realiza en el servicio
        Empleado empleado = empleadoServ.buildEmpleado(dto); // -> VALIDA y construye el empleado sin cod_empleado
        empleado.setCodEmpleado(empleadoServ.createNewCodigo()); // -> insertamos el codigo que debe tener
        return new ResponseEntity<>(empleadoServ.guardar(empleado), HttpStatus.CREATED); // -> insertamos el empleado
    }

//    @PutMapping("/{id}") // ACTUALIZACION enviando toda la entidad. su objetivo es actualizar no crear, por eso debe verificar si existe.
//    public ResponseEntity replaceEmpleado(@RequestBody @Valid EmpleadoDTO dto, @PathVariable String id) { // El EmpleadoDTO no deberia venir con ID, y todos los demas campos deberian ser opcionales, es decir solo debe llegar los campos que se quieran actualizar
//        if(empleadoServ.getEmpleadoByCodigo(id).isPresent()){
//            //BeanUtils.copyProperties();
//            Empleado empleado = empleadoServ.buildEmpleado(dto);
//            empleado.setCodEmpleado(id);
//            return new ResponseEntity(empleadoServ.guardar(empleado), HttpStatus.OK);
//        } else {
//            throw new EntidadNotFoundException("EMPLEADO", id);
//        }
//    }

    /** 1. Empleado --> eDTO  : con el id obtenemos el empleado y lo comvertirmos a eDTO
     *  2. eDTO <-- DTO(cliente) : seteamos solo los valores existentes del DTO(cliente) al eDTO
     *  3. eDTO --> Empleado : eDTO validamos los datos y lo convertimos a Empleado para poder guardar.
     *  4. save(Empleado) : con el Empleado actualizado guardamos(update) en la BD. */
    @PutMapping("/{id}") // ACTUALIZACION enviando solo los campos que se van actualizar.
    public ResponseEntity replaceEmpleado(@RequestBody EmpleadoDTO dto, @PathVariable String id) { // la validacion se realiza en el servicio
        Optional<Empleado> optional = empleadoServ.getEmpleadoByCodigo(id);
        if(optional.isPresent()){
            EmpleadoDTO eDTO = empleadoServ.buildEmpleadoDTO(optional.get());

            if (dto.getCodDistrito() != null) eDTO.setCodDistrito(dto.getCodDistrito());
            if (dto.getCodEstado() != null) eDTO.setCodEstado(dto.getCodEstado());
            if (dto.getNombre() != null) eDTO.setNombre(dto.getNombre());
            if (dto.getApellidos() != null) eDTO.setApellidos(dto.getApellidos());
            if (dto.getDni() != null) eDTO.setDni(dto.getDni());
            if (dto.getDireccion() != null) eDTO.setDireccion(dto.getDireccion());
            if (dto.getTelefono() != null) eDTO.setTelefono(dto.getTelefono());
            if (dto.getEmail() != null) eDTO.setEmail(dto.getEmail());
            if (dto.getUsuario() != null) eDTO.setUsuario(dto.getUsuario());
            if (dto.getContrasena() != null) eDTO.setContrasena(dto.getContrasena());

            Empleado empleado = empleadoServ.buildEmpleado(eDTO);
            empleado.setCodEmpleado(id);
            return new ResponseEntity(empleadoServ.guardar(empleado), HttpStatus.OK);
        } else {
            throw new EntidadNotFoundException("EMPLEADO", id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        if(empleadoServ.getEmpleadoByCodigo(id).isPresent() ){
            empleadoServ.eliminarByCodigo(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new EntidadNotFoundException("EMPLEADO",id + " - NO ELIMINADO");
        }
    }

    @GetMapping(value = "/rpt_EXCEL", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<Resource> reporteEmpleadoEXCEL(HttpServletResponse response) throws JRException, IOException {
        empleadoServ.exportarReporte("excel", response);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/rpt_PDF", produces = MediaType.APPLICATION_PDF_VALUE)
    private ResponseEntity<Void> reporteEmpleadoPDF(HttpServletResponse response) throws JRException,IOException{
        response.setHeader("","");
        empleadoServ.exportarReporte("pdf", response);
        return ResponseEntity.ok().build();
    }

    /* Configurar la respuesta HTTP
    *  ResponseEntity: representa la respuesta completa HTTP: status code, headers y body.
    *  @ResponseBody: por defecto en un @Controller devuelve un HTML, pero con esta anotacion permite devolver directamente el resultado del metodo como respuesta HTTP.
    *  @ResponseStatus: permite personalizar el status de la respuesta en caso de ERROR
    * */

}
