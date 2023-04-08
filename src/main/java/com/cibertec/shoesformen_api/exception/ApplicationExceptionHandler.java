package com.cibertec.shoesformen_api.exception;

import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ApplicationExceptionHandler {


    // Cuando la lista que devuelve esta vacia.
    @ExceptionHandler({ListEmptyException.class})
    public ResponseEntity<Objects> handleListEmptyException(ListEmptyException ex) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Cuando el parametro ID es NULL
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public Map<String, String> handleFoundException(IllegalArgumentException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    // Cuando no encuentra la Entidad con el ID proporcionado
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EntidadNotFoundException.class})
    public Map<String, String> handleEntityNotFoundException(EntidadNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorCodeMessage", ex.getMessage());
        return errorMap;
    }

    // Cuando los campos no cumplen las restricciones - VALIDACIONES EN EL CONTROLADOR
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
//        Map<String, String> errorMap = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errorMap.put(error.getField(), error.getDefaultMessage());
//        });
//        return errorMap;
//    }

    // Cuando los campos no cumplen las restricciones - VALIDACIONES EN EL SERVICIO PERSONALIZADA
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidacionException.class)
    public Map<String, String> handleValidArgument(ValidacionException ex) {
        Map<String, String> errorMap = new HashMap<>();
        for (ConstraintViolation<EmpleadoDTO> restriccion : ex.getRestricciones()){
            errorMap.put(restriccion.getPropertyPath().toString(), restriccion.getMessage()); // getPropertyPath : obtiene el campo - getMessage : obtiene el mensaje por defecto
        }
        return errorMap;
    }

    // Error final en la Base de Datos
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({SQLException.class})
    public Map<String, String> handlePSQLException(SQLException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorSQLMessage", ex.getMessage());
        return errorMap;
    }

}
