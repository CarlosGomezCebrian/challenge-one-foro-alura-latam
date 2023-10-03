package edu.foro.api.infra.errors;

public class IntegrityValidity extends RuntimeException{

    public IntegrityValidity(String s){
        super(s);
    }
}
