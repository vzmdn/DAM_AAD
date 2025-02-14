package com.vozmediano.mvc.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vozmediano.mvc.model.dao.IProductosDAO;
import com.vozmediano.mvc.model.entity.Productos;
import com.vozmediano.mvc.model.exceptions.ProductoNotFoundException;

@Service
public class ProductosServiceImpl implements IProductosService{

	@Autowired
	private IProductosDAO productosDAO;
	
	@Override
	public List<Productos> findAll() {
		return (List<Productos>) productosDAO.findAll();
	}

	@Override
	public Productos findById(int id) {
		return productosDAO.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));
	}

	@Override
	public void save(Productos p) {
		productosDAO.save(p);
		
	}

	@Override
	public void delete(Productos p) {
		productosDAO.delete(p);
		
	}

}
