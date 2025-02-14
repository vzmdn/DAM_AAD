import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import nba.entity.Players;
import nba.entity.Teams;

public class QueryPlayers {
	enum TypeOfStat{
		POINTS("pointsPerMatch"),
		ASSISTANCES("assistancesPerMatch"),
		BLOCKS("blocksPerMatch"),
		REBOUNDS("reboundsPerMatch");
		
		public String stat;

		TypeOfStat(String stat) {
			this.stat = stat;
		}
	}
	
	public static void showPlayer(Players p) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			p = session.merge(p);
			System.out.printf("%-20s%-5s%-12s%,12d$%n", 
				    p.getName(), p.getPosition(), p.getTeams().getName(), p.getSalary()
				);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}
	public static Players[] getAllPlayers() {
		List<Players> l = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<Players> q = session.createQuery("from Players p", Players.class);
			l = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return (Players[]) l.toArray(new Players[0]);
	}
	public static Players getHighestPlayer() {
		Players p = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String query = "from Players WHERE height = (SELECT MAX(height) FROM Players)";
			Query<Players> q = session.createQuery(query, Players.class);
			p = q.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return p;
	}
	public static int setSalary(int newSalary, String teamName) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			String query = "UPDATE Players p SET p.salary = :newSalary WHERE p.teams.name = :teamName";
			MutationQuery q = session.createMutationQuery(query);
			q.setParameter("newSalary", newSalary);
			q.setParameter("teamName", teamName);
			int rows = q.executeUpdate();
			return rows;
		}
		catch (Exception e) {
			e.printStackTrace();
			if(tx != null) tx.rollback();
			return -1;
		}
	}
	
	public static int riseSalaryOfMVP(TypeOfStat statType, int prctRise, String teamName) {
	    Transaction tx = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        tx = session.beginTransaction();
	        Teams t = QueryTeams.getTeamByName(teamName);
	        t = session.merge(t);
	        Set<Players> playersSet = t.getPlayerses();
	        Players[] players = playersSet.toArray(new Players[0]);

	        String query = "SELECT p.code FROM Players p "
	                       + "JOIN p.statses s "
	                       + "WHERE s.pointsPerMatch IS NOT NULL AND s.id.season = '07/08' AND p IN :players "
	                       + "GROUP BY p.code ORDER BY MAX(s.pointsPerMatch) DESC";

	        Query<Integer> q = session.createQuery(query, Integer.class);
	        q.setParameterList("players", players);
	        q.setMaxResults(1);

	        Integer playerCode = q.uniqueResult();

	        if (playerCode != null) {
	            Players p = session.get(Players.class, playerCode);
	            int salary = p.getSalary();
	            int newSalary = (int) (salary * (1 + (prctRise / 100.0)));

	            // Update the player's salary
	            String updateQuery = "UPDATE Players p SET p.salary = :newSalary WHERE p.code = :code";
	            MutationQuery mq = session.createMutationQuery(updateQuery);
	            mq.setParameter("newSalary", newSalary);
	            mq.setParameter("code", playerCode);
	            int rows = mq.executeUpdate();

	            tx.commit();
	            return rows;
	        } else {
	            tx.rollback();
	            return 0;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (tx != null) tx.rollback();
	        return -1;
	    }
	}


	public static int deletePlayersOfTeam(String teamName) {
	    Transaction tx = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        tx = session.beginTransaction();

	        String deleteStatsQuery = "DELETE FROM Stats s WHERE s.players.teams.name = :teamName";
	        MutationQuery deleteStats = session.createMutationQuery(deleteStatsQuery);
	        deleteStats.setParameter("teamName", teamName);
	        deleteStats.executeUpdate();


	        String deletePlayersQuery = "DELETE FROM Players p WHERE p.teams.name = :teamName";
	        MutationQuery deletePlayers = session.createMutationQuery(deletePlayersQuery);
	        deletePlayers.setParameter("teamName", teamName);
	        int rows = deletePlayers.executeUpdate();

	        tx.commit();
	        return rows;
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (tx != null) tx.rollback();
	        return -1;
	    }
	}

}
