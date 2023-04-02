package com.cibertec.shoesformen_api.service;

import com.cibertec.shoesformen_api.exception.EmpleadoNotFoundException;
import com.cibertec.shoesformen_api.exception.EntidadNotFoundException;
import com.cibertec.shoesformen_api.exception.ListEmptyException;
import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {

    public List<Empleado> listar() throws ListEmptyException;
    public void eliminar(Empleado empleado);
    public void eliminarByCodigo(String codigo);
    public Empleado guardar(Empleado empleado) throws IllegalArgumentException, EntidadNotFoundException;
    public Optional<Empleado> getEmpleadoByCodigo(String codigo) throws EntidadNotFoundException;
    public String createNewCodigo();
    public Empleado buildEmpleado(EmpleadoDTO dto) throws EntidadNotFoundException;


}
