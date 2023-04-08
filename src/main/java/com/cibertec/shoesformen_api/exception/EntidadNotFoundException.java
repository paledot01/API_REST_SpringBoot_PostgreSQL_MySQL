package com.cibertec.shoesformen_api.exception;

public class EntidadNotFoundException extends RuntimeException{
    public EntidadNotFoundException(String entidad, String id){
        super("No se encontro " + entidad + " con codigo " + id);
    }
}
