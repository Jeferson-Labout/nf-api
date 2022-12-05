package com.jldev.apinf.exception;

public class SistemaException extends RuntimeException{
public SistemaException(String s) {
	super(s);
}

public SistemaException(String s, Throwable  throwable) {
	super(s, throwable);
}
}
