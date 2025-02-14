import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.vozmediano.entities.Alumnos;
import com.vozmediano.entities.Municipios;

import jakarta.persistence.Tuple;

public class App {
	static int nia;
	public static void main(String[] args) {
		Alumnos a = null;
		int nia = 0;
		
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			// QUERY que obtenga el ID y el Nombre de todos los municipios
			
			Query<Tuple> q = session.createQuery(
					"SELECT M.idMunicipio, M.nombre FROM Municipios M", Tuple.class
					);
			q.list().forEach(t -> System.out.println(t.get(0)+ " - " + t.get(1)));
			
			

			// 1
			String id;
			Municipios m;
			do {
				id = Menu.askString("id municipio");
				m = session.get(Municipios.class, id);
				if (m == null)
					Menu.notFound(id);
			} while (m == null);

			System.out.println(m);

			// 2
			String nombre = Menu.askString("nombre");
			String apellidos = Menu.askString("apellidos");
			nia = Menu.askInt("nia");

			a = new Alumnos(nia, m, nombre, apellidos);

			tx = session.beginTransaction();
			session.persist(a);
			tx.commit();

			// 3
			session.close();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String nuevoNombre = Menu.askString("nombre");
			String nuevoApellido = Menu.askString("apellidos");
			
			a = session.get(Alumnos.class, nia);
			
			a.setNombre(nuevoNombre);
			a.setApellidos(nuevoApellido);
			
			actualizaAlumnos(a);
			
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

	private static Alumnos actualizaAlumnos(Alumnos al) {
		Transaction tx = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			
			//introducir el nuevo alumno
			tx = session.beginTransaction();
			session.merge(al);
			tx.commit();
			
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		
		return null;
	}

}
