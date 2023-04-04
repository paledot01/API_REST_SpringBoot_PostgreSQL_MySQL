package com.cibertec.shoesformen_api.exception;

public class ListEmptyException extends RuntimeException{
    public ListEmptyException(String message){
        super(message);
    }
}
