import java.util.HashMap;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import nba.entity.Players;
import nba.entity.Teams;

public class MainApp {
	public static void main(String[] args) {
		
		testConnection();
		
		String[] opciones = { "Show all teams",
				"Show Team whose name matches a pattern",
				"Get average salary of a Team (by name)", 
				"Show average salary of each Team", 
				"Show all Players",
				"Show the highest Player", 
				"Set salary of all Players of a Team",
				"Rise salary of MVP of a Team in 07/08 season", 
				"Delete ALL Players of a Team" };
		int n;
		do {
			n = Menu.menu(opciones, true);
			switch (n) {
			case 1:
				showAllTeams();
				break;
			case 2:
				showTeamByName();
				break;
			case 3:
				showAverageSalary();
				break;
			case 4:
				showAverageSalaries();
				break;
			case 5:
				showAllPlayers();
				break;
			case 6:
				showHighestPlayer();
				break;
			case 7:
				setSalaries();
				break;
			case 8:
				riseMVPSalary();
				break;
			case 9:
				deleteAllPlayersOfATeam();
				break;
			}
		} while (n != opciones.length + 1);
	}
	

	private static void deleteAllPlayersOfATeam() {
		int n = QueryPlayers.deletePlayersOfTeam(Menu.askString("team name"));
		System.out.println(n + " players deleted");	
		
	}


	private static void riseMVPSalary() {
		String[] stats = {"POINTS","ASSISTANCES","BLOCKS","REBOUNDS"};
		QueryPlayers.TypeOfStat stat = null;
		switch(Menu.menu(stats,true)){
			case 1:
				stat = QueryPlayers.TypeOfStat.POINTS;
				break;
			case 2:
				stat = QueryPlayers.TypeOfStat.ASSISTANCES;
				break;
			case 3:
				stat = QueryPlayers.TypeOfStat.BLOCKS;
				break;
			case 4:
				stat = QueryPlayers.TypeOfStat.REBOUNDS;
				break;
			default:
				break;
		}
		int n = QueryPlayers.riseSalaryOfMVP(stat, Menu.askInt("percentage"), Menu.askString("team name"));
		if(n != -1) System.out.println("salary updated");
	}


	private static void setSalaries() {
		int n = QueryPlayers.setSalary(Menu.askInt("salary"), Menu.askString("team name"));
		System.out.println(n + " salaries updated");		
	}

	private static void showHighestPlayer() {
		Players p = QueryPlayers.getHighestPlayer();
		if(p!= null) QueryPlayers.showPlayer(p);	
	}

	private static void showAllPlayers() {
		Players[] players = QueryPlayers.getAllPlayers();
		for(Players p : players) QueryPlayers.showPlayer(p);
	}

	private static void showAverageSalaries() {
		HashMap<String,Double> salaries = QueryTeams.getAverageSalaryPerTeam();
		if(salaries != null) System.out.println(salaries);		
	}

	private static void showAverageSalary() {
		Double salary = QueryTeams.getAverageSalaryofTeam(Menu.askString("team name"));
		if(salary != -1) System.out.println(salary);		
	}

	private static void showTeamByName() {
		Teams t = QueryTeams.getTeamByName(Menu.askString("team name"));
		if(t!= null) QueryTeams.showTeam(t);		
	}

	private static void showAllTeams() {
		Teams[] t = QueryTeams.getAllTeams();
		for (Teams team : t)
			QueryTeams.showTeam(team);
	}
	
	private static void testConnection() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			System.out.println("connection successfull");
		} catch (HibernateException e) {
			System.out.println("error connecting to the database");
			e.printStackTrace();
		}
		
	}
}