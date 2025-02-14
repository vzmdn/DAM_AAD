
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import exUnit4.entity.Books;

public class Main {
	public static void main(String[] args) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		if (session != null) {
			System.out.println("Opened Session");
		} else {
			System.out.println("Error opening session");
		}
		System.out.println("END");

		Query<Books> q = session.createQuery("from Books", Books.class);
		List<Books> results = q.getResultList();
		System.out.println("Showing books data: ");

		for (Books result : results) {
			System.out.println(
					result.getId()+ ": "
					+ result.getTitle() + ", by "
					+ (result.getAuthors() != null ? result.getAuthors().getName() : "Anónimo"));
		}

	}

}
