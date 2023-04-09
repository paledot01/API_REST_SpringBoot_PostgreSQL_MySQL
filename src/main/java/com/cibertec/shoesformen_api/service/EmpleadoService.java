package com.cibertec.shoesformen_api.service;

import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.mapping.PropertyReferenceException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmpleadoService {

    // IllegalArgumentException : salta cuando un ID es null, pero en este caso no es necesario colocarlo porque
    //                            @Valid y EntidadNotFoundException ya controlan de manera indirecta que esta
    //                            excepcion no pueda ocurrir. Antes saltarian primero cualquiera de estas dos.
    public List<Empleado> listar(Integer page, Integer size, String sort) throws PropertyReferenceException; // cuando sort (propiedad) es invalido
    public void eliminarByCodigo(String codigo) throws IllegalArgumentException;
    public Empleado guardar(Empleado empleado) throws IllegalArgumentException;
    public Optional<Empleado> getEmpleadoByCodigo(String codigo) throws IllegalArgumentException;
    public EmpleadoDTO buildEmpleadoDTO (Empleado emp);
    public Empleado buildEmpleado(EmpleadoDTO dto) throws IllegalArgumentException;
    public String createNewCodigo();
    public void exportarReporte(String tipo, HttpServletResponse response) throws JRException, IOException;

}
