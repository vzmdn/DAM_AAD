
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory instance = null;
    private HibernateUtil() {}
    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new Configuration() .configure().buildSessionFactory();
        }
        return instance;
    }
}
