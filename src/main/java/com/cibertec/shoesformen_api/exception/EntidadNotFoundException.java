package com.cibertec.shoesformen_api.exception;

public class EntidadNotFoundException extends RuntimeException{
    public EntidadNotFoundException(String id){
        super("No se encontro Entidad con codigo " + id);
    }
}
