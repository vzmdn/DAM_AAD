package com.vozmediano.mvc.model.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vozmediano.mvc.model.entity.Productos;
import com.vozmediano.mvc.model.exceptions.ProductoNotFoundException;
import com.vozmediano.mvc.model.service.IProductosService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tienda/productos")
public class ProductosRestController {
	
	@Autowired
	private IProductosService productosService;
	
	// Not found Exception
		@ExceptionHandler(ProductoNotFoundException.class)
		@ResponseBody
		public ResponseEntity<Map<String, Object>> handleException(ProductoNotFoundException pnfe) {
			Map<String, Object> response = new HashMap<>();
			response.put("error", "No content");
			response.put("message", pnfe.getMessage());
			response.put("status", "404");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		
	// 1. Devuelve todos los productos
		@GetMapping("")
		public ResponseEntity<?> obtenerProductos(){
			List<Productos> productos = productosService.findAll();
			if(productos.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return new ResponseEntity<List<Productos>>(productos, HttpStatus.OK);
			}
		}
		
	// 2. Devuelve los datos del producto
		// Get one player id
		@GetMapping("/{id}")
		public Productos obtenerProductoPorId(@PathVariable int id) {
			return productosService.findById(id);
		}
		
	// 3. Crea un nuevo producto
		@PostMapping("")
		@ResponseStatus(HttpStatus.CREATED)
		public Productos crearProducto(@RequestBody Productos p) {
			productosService.save(p);
			return p;
		}
		
	// 4. Elimina el producto
		@DeleteMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public ResponseEntity<?> borrarProducto(@PathVariable int id){
			Productos p = productosService.findById(id);
			
				productosService.delete(p);
				return ResponseEntity.noContent().build();
			
		}
		
	//5. Actualiza los datos del producto
		@PutMapping("/{id}")
		@ResponseStatus(HttpStatus.OK)
		public ResponseEntity<?> actualizaProducto(@RequestBody Productos p, @PathVariable int id){
			Productos currentP = null;
			try {
				currentP = productosService.findById(id);
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(currentP == null) {
				return ResponseEntity.noContent().build();
			} else {
				
				String newNombre = p.getNombre();
				if (newNombre == null) newNombre = currentP.getNombre();
				
				Integer newPrecio = p.getPrecio();
				if (newPrecio == null) newPrecio = currentP.getPrecio();
				
				currentP.setNombre(newNombre);
				currentP.setPrecio(newPrecio);
				currentP.setFabricantes(currentP.getFabricantes());
				
				productosService.save(currentP);
				return new ResponseEntity<Productos>(currentP, HttpStatus.CREATED);
			}
		}
		
		
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
