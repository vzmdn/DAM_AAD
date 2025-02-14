import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import nba.entity.Teams;

public class QueryTeams {
	public static void showTeam(Teams t) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			t = session.merge(t);
			System.out.printf("%-16s%-15s%-7s%-12s%-2d players%n", t.getName(), t.getCity(), t.getConference(),
					t.getDivision(), t.getPlayerses().size()
			);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
	}

	public static Teams[] getAllTeams() {
		List<Teams> l = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<Teams> q = session.createQuery("from Teams t", Teams.class);
			l = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return (Teams[]) l.toArray(new Teams[0]);

	}

	public static Teams getTeamByName(String patternName) {
		Teams t = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<Teams> q = session.createQuery("from Teams t WHERE t.name = :teamName", Teams.class);
			q.setParameter("teamName", patternName);
			t = q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			Menu.notFound(patternName);
			return null;
		}
		return t;
	}

	public static double getAverageSalaryofTeam(String depName) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<Double> q = session.createQuery("SELECT AVG(p.salary) FROM Players p WHERE p.teams.name = :teamName",
					Double.class);
			q.setParameter("teamName", depName);
			Double averageSalary = q.uniqueResult();
			if (averageSalary == null) {
				return -1;
			}
			return averageSalary;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static HashMap<String, Double> getAverageSalaryPerTeam() {
	    Teams[] teams = getAllTeams();
	    HashMap<String, Double> salaryMap = new HashMap<>();
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        for (Teams t : teams) {
	            String name = t.getName();	            
	            Query<Double> q = session.createQuery(
	                "SELECT AVG(p.salary) FROM Players p WHERE p.teams.name = :teamName", 
	                Double.class
	            );
	            q.setParameter("teamName", name);
	            Double averageSalary = q.uniqueResult();
	            salaryMap.put(name, averageSalary);
	        }
	        return salaryMap;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
