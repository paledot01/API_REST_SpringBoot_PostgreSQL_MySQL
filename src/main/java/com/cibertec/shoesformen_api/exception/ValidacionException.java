package com.cibertec.shoesformen_api.exception;

import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import jakarta.validation.ConstraintViolation;
import lombok.Data;

import java.util.Set;

@Data
public class ValidacionException extends RuntimeException{
    private Set<ConstraintViolation<EmpleadoDTO>> restricciones; // creado para obtener todas las violaciones a las restricciones
    public ValidacionException(Set<ConstraintViolation<EmpleadoDTO>> restricciones){
        this.restricciones = restricciones;
    }


}
