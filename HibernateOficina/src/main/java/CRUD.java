
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import oficina.entity.Departamentos;
import oficina.entity.Empleados;

import Utilities.HibernateUtil;
import Utilities.Menu;


public class CRUD {
	static java.util.Scanner tecStr = new java.util.Scanner(System.in);
	static java.util.Scanner tecNum = new java.util.Scanner(System.in);
	static String[] opt = {"introducir departamento","ver departamentos","introducir empleado","ver empleados"};
	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		String res = session != null ? "Opened Session" : "Error opening session";
		System.out.println(res);
		
		int n = 0;
		do{
			n = Menu.menu(opt);
			switch(n) {
			case 1: pedirDatosDepartamento();
			break;
			case 2: verDepartamentos(session);
			break;
			case 3: pedirDatosEmpleado();
			break;
			case 4: verEmpleados(session);
			default:
			}
		} while(n!= opt.length+1);
		
   	
		/*
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			//Departamentos dep = new Departamentos("7");
			Departamentos dep = session.get(Departamentos.class, "1");
			Empleados e = new Empleados();
			e.setApellido("Pascual");
			e.setEmpNo(42534);
			e.setDepartamentos(dep);
			session.persist(e);
			tx.commit();
		}
		catch(Exception e) {
			if(tx!=null) tx.rollback();
			throw e;
		}
		*/
	}
	private static void verEmpleados(Session session) {
	    List<Empleados> resultados = session.createNativeQuery("SELECT * FROM empleados", Empleados.class).list();
	    for(Empleados e : resultados) {
	    	System.out.println(e.getEmpNo()
	    			+ " - "
	    			+ e.getApellido()
	    			+ ", trabaja de "
	    			+ e.getOficio()
	    			+ " en "
	    			+ e.getDepartamentos().getDnombre()
	    			+ " desde "
	    			+ e.getApellido());
	    }

		
	}
	private static void pedirDatosEmpleado() {
		// TODO Auto-generated method stub
		
	}
	private static void verDepartamentos(Session session) {
		List<Departamentos> resultados = session.createNativeQuery("SELECT * FROM departamentos", Departamentos.class).list();
	    for(Departamentos d : resultados) {
	    	System.out.println("id: " 
	    			+ d.getDeptNo()
	    			+ " - "
	    			+ d.getDnombre()
	    			+ " en "
	    			+ d.getLoc());
	    }
		
	}
	private static void pedirDatosDepartamento() {
		// TODO Auto-generated method stub
		
	}
	
}
