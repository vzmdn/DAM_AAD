package com.vozmediano.mvc.model.exceptions;

public class ProductoNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductoNotFoundException() {
		super();
	}
	
	public ProductoNotFoundException(int id) {
		super("El producto con ID " + id + " no se encuentra en la base de datos");
	}
	
}
