package com.cibertec.shoesformen_api.exception;

public class ListEmptyException extends RuntimeException{
    public ListEmptyException(String entidad){
        super("Lista " + entidad + " vacia");
    }
}
