import java.util.Scanner;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import nba.entity.Matches;
import nba.entity.Players;
import nba.entity.Stats;
import nba.entity.StatsId;
import nba.entity.Teams;

public class Main {
	static Scanner tecNum = new Scanner(System.in);
	static Scanner tecStr = new Scanner(System.in);
	static Scanner tecChar = new Scanner(System.in);

	public static void main(String[] args) {

		String[] opt = { "Show a team by ID", "Show a player by ID", "Show the players in existing team",
				"Create new team", "Create new player with new team associated",
				"Create new player with existing team associated", "Delete a player", "Delete a team",
				"Set salary of all the players of a team",
				"Rise salary for those players of a team who maxed stat in season 07/08" };

		int n;
		do {
			n = Menu.menu(opt, true);
			switch (n) {
			case 1:
				showTeam();
				break;
			case 2:
				showPlayer();
				break;
			case 3:
				showPlayersInTeam();
				break;
			case 4:
				createTeam(newTeam());
				break;
			case 5:
				createPlayerAndTeam();
				break;
			case 6:
				createPlayerInExistingTeam();
				break;
			case 7:
				deletePlayer(findPlayer());
				break;
			case 8:
				deleteTeam();
				break;
			case 9:
				setSalaryOfTeam();
				break;
			case 10:
				riseSalaryOfTeamBestPlayers();
				break;
			case 11:
				System.out.println("bye");
			default:
			}
		} while (n != opt.length + 1);
	}

	private static void riseSalaryOfTeamBestPlayers() {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();

			Teams t = session.merge(findTeam());
			double maxStat = 0;
			Players bestPlayer = null;
			int pickStat = pickStat();
			if (pickStat == 5)
				return;
			for (Players p : t.getPlayerses()) {
				p = session.merge(p);
				for (Stats s : p.getStatses()) {
					StatsId statsId = s.getId();
					if (statsId.getSeason().equals("07/08")) {
						switch (pickStat) {
						case 1: {
							if (s.getReboundPerMatch() > maxStat) {
								bestPlayer = p;
								maxStat = s.getReboundPerMatch();
							}
							break;
						}
						case 2: {
							if (s.getAssistancesPerMatch() > maxStat) {
								bestPlayer = p;
								maxStat = s.getAssistancesPerMatch();
							}
							break;
						}
						case 3: {
							if (s.getBlocksPerMatch() > maxStat) {
								bestPlayer = p;
								maxStat = s.getBlocksPerMatch();
							}
							break;
						}
						case 4: {
							if (s.getReboundPerMatch() > maxStat) {
								bestPlayer = p;
								maxStat = s.getReboundPerMatch();
							}
							break;
						}
						}

					}

				}
			}
			if (bestPlayer == null) {
				throw new IllegalArgumentException("No player found with matching stats in the 07/08 season.");
			}

			int salary = bestPlayer.getSalary();
			int newSalary = (int) (salary * (1 + askInt("how much % increase?") / 100.0));
			bestPlayer.setSalary(newSalary);
			tx.commit();
			System.out.println(bestPlayer.getName() + " salary was updated");
			System.out.println(salary + " --> " + newSalary);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

	}

	private static int pickStat() {
		String opt[] = { "Points per match", "Assistances per match", "Blocks per match", "Rebounds per match" };
		System.out.println("which stat do you want to apply the bonus for?");
		return Menu.menu(opt, true);
	}

	private static void setSalaryOfTeam() {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			{
				Teams t = session.merge(findTeam());
				int salary = askInt("new salary");
				int n = 0;
				for (Players p : t.getPlayerses()) {
					p.setSalary(salary);
					n++;
				}
				tx.commit();
				System.out.println(n + " salaries were updated");
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

	}

	private static void deleteTeam() {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			
			Teams t = findTeam();
			if (t==null) return;
			t = session.merge(t);
			Set<Players> players = t.getPlayerses();

			if (players.size() > 0) {

				System.out.println(players.size() + " players found. Their stats, matches and info will be deleted");
				if (askYesNo("confirm action")) {
					for (Matches m : t.getMatchesesForLocalTeam())
						session.remove(m);
					Set<Matches> matchesVisitor = t.getMatchesesForVisitorTeam();
					for (Matches m : matchesVisitor)
						session.remove(m);
					for (Players p : players) {
						deletePlayer(p);
					}
				} else {
					session.close();
					return;
				}
			}
			session.remove(t);
			tx.commit();
			System.out.println(t.getName() + " sucessfully deleted");

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
	}

	private static void deletePlayer(Players p) {
		if (p == null) return;

		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			{
				p = session.merge(p);
				for (Stats s : p.getStatses())
					session.remove(s);
				session.remove(p);
				tx.commit();
				System.out.println(p.getName() + " sucessfully deleted");
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
	}

	private static void createPlayerInExistingTeam() {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			{
				Players p = newPlayer();
				Teams t = findTeam();
				if (t != null) {
					p.setTeams(t);
					session.persist(p);
					tx.commit();
				}
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

	}

	private static void createPlayerAndTeam() {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			{
				Players p = newPlayer();
				Teams t = newTeam();
				p.setTeams(t);
				session.persist(t);
				session.persist(p);
				tx.commit();
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

	}

	private static Players newPlayer() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Players p = new Players();
			p.setName(askString("player name"));
			int id = -1;
			do
				id = askInt("player id");
			while (session.get(Players.class, id) != null);
			p.setCode(id);
			p.setTeams(null);
			return p;
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void createTeam(Teams t) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			session.persist(t);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	private static Teams newTeam() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Teams t = new Teams();
			String teamName = "";
			do
				teamName = askString("new team name");
			while (session.get(Teams.class, teamName) != null);
			String teamCity = askString("team city");
			t.setName(teamName);
			t.setCity(teamCity);
			return t;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;

	}

	private static void showPlayersInTeam() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Teams t = session.merge(findTeam());
			Set<Players> players = t.getPlayerses();
			System.out.println(t.getName() + " players:");
			for (Players p : players)
				System.out.println(p);
			System.out.println(players.size() + " players found");
		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

	private static void showPlayer() {
		Players p = findPlayer();
		if (p != null)
			System.out.println(p);
	}

	private static Players findPlayer() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Players p = null;
		do {
			int playerID = askInt("player ID");
			p = session.get(Players.class, playerID);
			if (p == null)
				notFound(playerID);
			else {
				session.close();
				return p;
			}
		} while (askYesNo("retry?"));
		session.close();
		return p;
	}

	private static void showTeam() {
		Teams t = findTeam();
		if (t != null)
			System.out.println(t);

	}

	private static Teams findTeam() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Teams t = null;
		do {
			String teamName = askString("team name");
			t = session.get(Teams.class, teamName);
			if (t == null) {
				notFound(teamName);
			} else {
				session.close();
				return t;
			}
		} while (askYesNo("retry?"));
		session.close();
		return t;
	}

	private static boolean askYesNo(String str) {
		System.out.print(str + " (y/n): ");
		char charRes = ' ';
		try {
			charRes = tecChar.next().charAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charRes == 'y';
	}

	private static String askString(String str) {
		System.out.print("Introduce " + str + ": ");
		String strRes = "";
		try {
			strRes = tecStr.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}

	private static int askInt(String str) {
		System.out.print("Introduce " + str + ": ");
		int n = -1;
		try {
			n = tecNum.nextInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

	private static void notFound(String str) {
		System.out.println(str + " not found in the database");

	}

	private static void notFound(int n) {
		System.out.println(n + " not found in the database");

	}
}
/*
 * private static List<Teams> getAllTeams() { Session session =
 * HibernateUtil.getSessionFactory().openSession(); return
 * session.createQuery("FROM Teams", Teams.class).list(); }
 * 
 */

/*
 * private static Teams getTeam(String teamName) { List<Teams> teams =
 * getAllTeams(); for (Teams team : teams) { if
 * (team.getName().equalsIgnoreCase(teamName)) return team; } return null; }
 */

/*
 * private static void showTeams() { Session session =
 * HibernateUtil.getSessionFactory().openSession(); List<Teams> teams =
 * session.createQuery("FROM Teams", Teams.class).list();
 * System.out.println("These are the available Teams"); for (Teams team : teams)
 * System.out.print(team.getName() + " "); System.out.println(teams.size()); }
 */
/*
 * private static int getMaxID() { Session session =
 * HibernateUtil.getSessionFactory().openSession(); List<Players> players =
 * session.createQuery("FROM Players", Players.class).list(); return
 * players.size() + 1; }
 */

/*
 * private static void showPlayersIDs() { Session session =
 * HibernateUtil.getSessionFactory().openSession(); List<Players> players =
 * session.createQuery("FROM Players", Players.class).list();
 * System.out.println("These are the available IDs"); for (Players player :
 * players) System.out.print(player.getCode() + " "); }
 */