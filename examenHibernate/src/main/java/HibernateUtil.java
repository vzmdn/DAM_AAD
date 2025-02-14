
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = null;
    private HibernateUtil() {}
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
        	sessionFactory = new Configuration() .configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
