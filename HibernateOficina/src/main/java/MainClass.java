
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import oficina.entity.Departamentos;
import oficina.entity.Empleados;


public class MainClass {
	public static void main(String[] args) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		if (session != null) {
			System.out.println("Opened Session");
		} else {
			System.out.println("Error opening session");
		}
		System.out.println("END");
		/*
		Query<Empleados> q = session.createQuery("from Empleados", Empleados.class);
		List<Empleados> results = q.getResultList();
		System.out.println("Datos de empleados: ");

		for (Empleados result : results) {
			System.out.println(
					result.getApellido() + ", " + result.getOficio()
					+ "\nnúmero de empleado: " + result.getEmpNo()
					+ "\ntrabaja en " + result.getDir()
					+ "desde " + result.getFechaAlta()
					+ "\nsueldo: " + result.getSalario()
					+ "\ncomision: " + result.getComision()
					+ "\nnº departamento: " + result.getDepartamentos().getDeptNo()
					+"\n"
					);
		}
		*/
		Query<Departamentos> d = session.createQuery("from Departamentos",Departamentos.class);
		Query<Empleados> e = session.createQuery("from Empleados",Empleados.class);
		
		List<Departamentos> results = d.getResultList();
		List<Empleados> resultsE = e.getResultList();
		
		for(Departamentos result : results) {
			System.out.println(
					"Departamento nº " + result.getDeptNo()
					+ ", " + result.getDnombre()
					+ " en " + result.getLoc()
					);
			for(Empleados resultE : resultsE) {
				if(result.getDeptNo() == resultE.getDepartamentos().getDeptNo()) {
					System.out.println(
				    resultE.getApellido() + ", " + resultE.getOficio()
					+ "\nnúmero de empleado: " + resultE.getEmpNo()
					+ "\ntrabaja en " + resultE.getDir()
					+ "desde " + resultE.getFechaAlta()
					+ "\nsueldo: " + resultE.getSalario()
					+ "\ncomision: " + resultE.getComision()+"\n");
					
				}
			}
		System.out.println();
		}

	}

}
