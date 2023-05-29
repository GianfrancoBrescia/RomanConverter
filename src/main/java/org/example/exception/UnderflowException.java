package org.example.exception;

public class UnderflowException extends RuntimeException {

    public UnderflowException() {
        super("The Input Value was Zero or Negative");
    }
}