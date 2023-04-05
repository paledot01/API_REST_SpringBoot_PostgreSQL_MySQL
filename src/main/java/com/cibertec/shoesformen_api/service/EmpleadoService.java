package com.cibertec.shoesformen_api.service;

import com.cibertec.shoesformen_api.exception.EntidadNotFoundException;
import com.cibertec.shoesformen_api.exception.ListEmptyException;
import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmpleadoService {

    public List<Empleado> listar() throws ListEmptyException;
    public void eliminarByCodigo(String codigo) throws IllegalArgumentException;
    public Empleado guardar(Empleado empleado) throws IllegalArgumentException, EntidadNotFoundException;
    public Optional<Empleado> getEmpleadoByCodigo(String codigo) throws EntidadNotFoundException;
    public String createNewCodigo();
    public Empleado buildEmpleado(EmpleadoDTO dto) throws EntidadNotFoundException;
    public void exportarReporte(String tipo, HttpServletResponse response) throws JRException, IOException;

}
