package com.cibertec.shoesformen_api.service;

import com.cibertec.shoesformen_api.exception.EntidadNotFoundException;
import com.cibertec.shoesformen_api.exception.ListEmptyException;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.management.JMRuntimeException;
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


    @Override
    public List<Empleado> listar() throws ListEmptyException {
        List<Empleado> lista = empleadoRepo.findAll();
        if(lista.isEmpty()){
            throw new ListEmptyException("lista Empleado vacio");
        }
        return lista;
    }

    @Override
    public void eliminarByCodigo(String codigo) throws IllegalArgumentException{
        empleadoRepo.deleteById(codigo);
    }

    @Override
    public Empleado guardar(Empleado empleado) throws IllegalArgumentException, EntidadNotFoundException{
        return empleadoRepo.save(empleado);
    }

    @Override
    public Optional<Empleado> getEmpleadoByCodigo(String codigo) throws EntidadNotFoundException {
        return empleadoRepo.findById(codigo);
    }

    @Override
    public String createNewCodigo() {
        String codigo_ultimo = empleadoRepo.getUltimoCodigo();
        String codigo_nuevo = "EM10001";
        if(codigo_ultimo != null){
            return codigo_ultimo;
        }
        return codigo_nuevo;
    }

    @Override
    public Empleado buildEmpleado(EmpleadoDTO dto) throws IllegalArgumentException, EntidadNotFoundException {

        // 1. que todos los datos sean validados -> @Valid
        // 1.5. que los ID no sean NULL -> IllegalArgumentException --> NO PASA
        // 2. que los codigos permitan encontrar ah las entidades -> EntidadNotFoundException
        // 3. que los campos en la BD no se repitan -> SQL
        // 4. que Xodo salga correcto -> Controller OK 201.

        Optional<Distrito> dis = distritoRepo.findById(dto.getCodDistrito());
        Optional<Estado> est = estadoRepo.findById(dto.getCodEstado());
        Optional<Rol> rol = rolRepo.findById("RL02");
        if(dis.isEmpty() || est.isEmpty() || rol.isEmpty()){
            throw new EntidadNotFoundException("Codigos invalidos");
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
    public void exportarReporte(String tipo, HttpServletResponse response) throws JRException, IOException {

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleadoRepo.listaPOJO());
        Map<String, Object> parametros = new HashMap<>();
        SimpleDateFormat formato1 = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy 'a las' HH:mm:ss");
        SimpleDateFormat formato2 = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date fecha = new Date();
        String fecha1 = formato1.format(fecha);
        String fecha2 = formato2.format(fecha);

        String imagen = "logo_reporte_2.png";
        parametros.put("imagen_logo","src/main/resources/static/img/" + imagen);
        parametros.put("nombre_empresa","SHOES FOR MEN");
        parametros.put("direccion_empresa","AV. URUGUAY N 000 ");
        parametros.put("distrito_empresa","SAN ISIDRO");
        parametros.put("nombre_empleado","KEVIN B");
        parametros.put("ruc_empresa","55555555555");
        parametros.put("telefono_empresa","777-7777");
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
            response.setContentType("application/octet-stream");
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=listaEmpleado_" + fecha2 + ".xlsx");
            exporter.exportReport();
        }

    }

}
