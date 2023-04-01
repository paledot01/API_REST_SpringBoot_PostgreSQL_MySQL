package com.cibertec.shoesformen_api.service;

import com.cibertec.shoesformen_api.exception.EmpleadoNotFoundException;
import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {

    public List<Empleado> listar() throws EmpleadoNotFoundException;
    public void eliminar(Empleado empleado);
    public void eliminarByCodigo(String codigo);
    public Empleado guardar(EmpleadoDTO dto) throws IllegalArgumentException;
    public Optional<Empleado> getEmpleadoByCodigo(String codigo);
    public String getUltimoCodigo();


}
