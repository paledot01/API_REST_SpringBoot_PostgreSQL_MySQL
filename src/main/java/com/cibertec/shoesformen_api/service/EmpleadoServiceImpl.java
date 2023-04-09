package com.cibertec.shoesformen_api.service;

import com.cibertec.shoesformen_api.a_empresa.Empresa;
import com.cibertec.shoesformen_api.a_empresa.EmpresaRepository;
import com.cibertec.shoesformen_api.exception.EntidadNotFoundException;
import com.cibertec.shoesformen_api.exception.ValidacionException;
import com.cibertec.shoesformen_api.model.Distrito;
import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.Estado;
import com.cibertec.shoesformen_api.model.Rol;
import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import com.cibertec.shoesformen_api.repository.DistritoRepository;
import com.cibertec.shoesformen_api.repository.EmpleadoRepository;
import com.cibertec.shoesformen_api.repository.EstadoRepository;
import com.cibertec.shoesformen_api.repository.RolRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private EmpresaRepository empresaRepo;
    @Autowired
    private Validator validator;

//    LISTA NORMAL
//    @Override
//    public List<Empleado> listar() {
//        List<Empleado> lista = empleadoRepo.findAll();
//        if(lista.isEmpty()){
//            throw new ListEmptyException("EMPLEADO");
//        }
//        return lista;
//    }

    @Override // LISTAR CON PAGINACION
    public List<Empleado> listar(Integer page, Integer size, String sort) throws PropertyReferenceException {
        Pageable paging = PageRequest.of(page, size, Sort.by(sort));
        Page<Empleado> pagedResult = empleadoRepo.findAll(paging); // -- puede generar un error si no encuentra la propiedad de la Entida
        List<Empleado> lista;
        if (pagedResult.hasContent()){
            return pagedResult.getContent();
        } else {
            return new ArrayList<Empleado>();
        }
    }

    @Override
    public void eliminarByCodigo(String codigo) throws IllegalArgumentException{
        empleadoRepo.deleteById(codigo);
    }

    @Override
    public Empleado guardar(Empleado empleado) throws IllegalArgumentException{
        return empleadoRepo.save(empleado);
    }

    @Override
    public Optional<Empleado> getEmpleadoByCodigo(String codigo) throws IllegalArgumentException {
        return empleadoRepo.findById(codigo);
    }

    @Override
    public EmpleadoDTO buildEmpleadoDTO(Empleado emp) {
        EmpleadoDTO dto = new EmpleadoDTO(
                emp.getDistrito().getCodDistrito(),
                emp.getEstado().getCodEstado(),
                emp.getNombre(),
                emp.getApellidos(),
                emp.getDni(),
                emp.getDireccion(),
                emp.getTelefono(),
                emp.getEmail(),
                emp.getUsuario(),
                emp.getContrasena()
        );
        return dto;
    }

    @Override
    public Empleado buildEmpleado(EmpleadoDTO dto) throws IllegalArgumentException { // valida y crea el empleado pero sin CODIGO DEL EMPLEADO

        // 1. que todos los datos sean validados -> @Valid
        // 1.5. que los ID no sean NULL -> IllegalArgumentException --> NO PASA
        // 2. que los codigos permitan encontrar ah las entidades -> EntidadNotFoundException
        // 3. que los campos en la BD no se repitan -> SQL
        // 4. que Xodo salga correcto -> Controller OK 201.

        Set<ConstraintViolation<EmpleadoDTO>> restricciones = validator.validate(dto);
        if(!restricciones.isEmpty()) throw new ValidacionException(restricciones); // excepcion personalizada

        Optional<Distrito> dis = distritoRepo.findById(dto.getCodDistrito());
        Optional<Estado> est = estadoRepo.findById(dto.getCodEstado());
        Optional<Rol> rol = rolRepo.findById("RL02");
        if(dis.isEmpty()){
            throw new EntidadNotFoundException("Distrito", dto.getCodDistrito());
        } else if (est.isEmpty()) {
            throw new EntidadNotFoundException("Estado", dto.getCodEstado());
        } else if (rol.isEmpty()) {
            throw new EntidadNotFoundException("Rol", "RL02");
        }

        Empleado empleado = new Empleado(
                //this.getUltimoCodigo(),
                dis.get(),
                est.get(),
                dto.getNombre(),
                dto.getApellidos(),
                dto.getDni(),
                dto.getDireccion(),
                dto.getTelefono(),
                dto.getEmail(),
                dto.getUsuario(),
                dto.getContrasena(),
                Arrays.asList(rol.get()));
        return empleado;
    }

    @Override
    public String createNewCodigo() {
        String codigo_nuevo_bd = empleadoRepo.getNuevoCodigo();
        String codigo_nuevo = "EM10001";
        if(codigo_nuevo_bd != null){
            return codigo_nuevo_bd;
        }
        return codigo_nuevo;
    }

    @Override
    public void exportarReporte(String tipo, HttpServletResponse response) throws JRException, IOException {

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleadoRepo.listaPOJO());
        Map<String, Object> parametros = new HashMap<>();
        SimpleDateFormat formato1 = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy 'a las' HH:mm:ss");
        SimpleDateFormat formato2 = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date fecha = new Date();
        String fecha1 = formato1.format(fecha);
        String fecha2 = formato2.format(fecha);

        Empresa empresa = empresaRepo.findById("EP1").orElseThrow(() -> new EntidadNotFoundException("Empresa","EP1"));
        //String imagen = "logo_reporte_01.png";
        //parametros.put("imagen_logo","src/main/resources/static/img/" + imagen);
        parametros.put("imagen_logo",empresa.getImagen());
        parametros.put("nombre_empresa",empresa.getNombre().toUpperCase());
        parametros.put("direccion_empresa","AV. URUGUAY N 000 ");
        parametros.put("distrito_empresa","SAN ISIDRO");
        parametros.put("nombre_empleado","KEVIN B");
        parametros.put("ruc_empresa",empresa.getRuc());
        parametros.put("telefono_empresa",empresa.getTelefono());
        parametros.put("fecha_generacion", fecha1);
        parametros.put("DataEmpleado", dataSource);

        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reporte_jasper/rpt_empleado.jrxml"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parametros, new JREmptyDataSource());

        if(tipo.equalsIgnoreCase("pdf")) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listaEmpleado_" + fecha2 + ".pdf");
            exporter.exportReport();
        }
        else if(tipo.equalsIgnoreCase("excel")) {
            JRXlsxExporter exporter = new JRXlsxExporter(); // la configuracion se hace en el mismo JASPER STUDIO
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); //
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listaEmpleado_" + fecha2 + ".xlsx");
            exporter.exportReport();
        }

    }

}
