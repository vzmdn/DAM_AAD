package examenMongoVozmediano.entities;

import java.util.List;

public class Alumnos {
	private String nombre;
	private String apellidos;
	private String grupo;
	private List<String> modulos;
	private Fct fct;
	
	
	public Alumnos() {
		super();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public List<String> getModulos() {
		return modulos;
	}
	public void setModulos(List<String> modulos) {
		this.modulos = modulos;
	}
	public Fct getFct() {
		return fct;
	}
	public void setFct(Fct fct) {
		this.fct = fct;
	}

	@Override
	public String toString() {
		return "Alumnos [nombre=" + nombre + ", apellidos=" + apellidos + ", grupo=" + grupo + ", modulos=" + modulos
				+ ", fct=" + fct + "]";
	}
	
	
	
	
}
