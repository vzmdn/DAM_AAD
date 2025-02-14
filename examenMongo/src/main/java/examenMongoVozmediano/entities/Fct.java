package examenMongoVozmediano.entities;

public class Fct {
	private String Empresa;
	private int horas;
	
	
	
	public Fct(String empresa, int horas) {
		super();
		Empresa = empresa;
		this.horas = horas;
	}

	public Fct() {
		super();
	}
	
	public String getEmpresa() {
		return Empresa;
	}
	public void setEmpresa(String empresa) {
		Empresa = empresa;
	}
	public int getHoras() {
		return horas;
	}
	public void setHoras(int horas) {
		this.horas = horas;
	}

	@Override
	public String toString() {
		return "Fct [Empresa=" + Empresa + ", horas=" + horas + "]";
	}
	
	
}
