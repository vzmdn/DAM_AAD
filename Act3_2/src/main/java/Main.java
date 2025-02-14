import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	static java.util.Scanner tec = new java.util.Scanner(System.in);
	static java.util.Scanner str = new java.util.Scanner(System.in);
	public static void main(String args[]) {
		String ruta = "./bd/act3_2";
		String url = "jdbc:hsqldb:" + ruta;
		try (Connection con = DriverManager.getConnection(url); Statement stt = con.createStatement()) {

			String query = "CREATE TABLE IF NOT EXISTS sports ("
					+ "    id INT IDENTITY PRIMARY KEY,"
					+ "    name VARCHAR(50)"
					+ "    );";
			stt.executeUpdate(query);
			query = "CREATE TABLE IF NOT EXISTS players ("
					+ "    id INT IDENTITY PRIMARY KEY,"
					+ "    name VARCHAR(20),"
					+ "    cod_sport INT,"
					+ "    FOREIGN KEY(cod_sport) REFERENCES sports(id)"
					+ ")";
			stt.executeUpdate(query);
			int n = 0;
			do {
				n = menu(n);
				switch (n) {
				case 1:
					newSport(con);
					break;
				case 2:
					newPlayer(con);
					break;
				case 3:
					showPlayers(con);
					break;
				case 4:
					deleteSport(con);
					break;
				default:
					break;
				}
			} while (n != 5);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	static void showSports(Connection con) {
		try {
			DatabaseMetaData dmd = con.getMetaData();
			String query = "SELECT * FROM sports";
			Statement stt = con.createStatement();
			ResultSet rs = stt.executeQuery(query);
			JDBCHelper.showResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void newSport(Connection con) {
		try {
			System.out.println("sport name?");
			String name = str.nextLine();
			String query = "INSERT INTO sports (name) VALUES ('" + name + "')";
			Statement stt = con.createStatement();
			int rows = stt.executeUpdate(query);
			System.out.println(rows + " introduced sports");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	static void newPlayer(Connection con) {
		try {
			System.out.println("player name?");
			String name = str.nextLine();
			System.out.println("what sport do they play?");
			showSports(con);
			int n = tec.nextInt();
			String query = "INSERT INTO players (name,cod_sport) VALUES ('" + name + "'," + n + ")";
			Statement stt = con.createStatement();
			int rows = stt.executeUpdate(query);
			System.out.println(rows + " introduced players");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void showPlayers(Connection con) {
		try {
			String query = "SELECT"
					+ "    players.name,"
					+ "    sports.name"
					+ " FROM "
					+ "    players "
					+ "    LEFT JOIN sports ON players.cod_sport = sports.id;";
			Statement stt = con.createStatement();
			ResultSet rs = stt.executeQuery(query);
			JDBCHelper.showResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void deleteSport(Connection con) {
		try {
			Statement stt = con.createStatement();
			System.out.println("what sport do you want to delete?");
			showSports(con);
			int n = tec.nextInt();
			String query = "DELETE FROM players WHERE cod_sport = " + n;
			stt.executeUpdate(query);
			query = "DELETE FROM sports WHERE id = "+ n;
			stt.executeUpdate(query);
			
			System.out.println("deleted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	static int menu(int n) {
		
		System.out.println("1. introduce new sport\n" + "2. introduce new player\n"
				+ "3. show players and their sports\n" + "4. delete sport and associated players\n" + "5. exit");
		do {
			try {
				n = tec.nextInt();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (n > 5 || n < 1);
		if (n == 5) {
			System.out.println("bye");
			return 5;
		}
		else return n;
	}
}
