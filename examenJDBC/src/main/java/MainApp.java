import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp {
	static boolean sqlite = false;
	static boolean hsqldb = false;

	static String rutaSqlite = "./db_examen/sqlite/database";
	static String sqliteURL = "jdbc:sqlite:" + rutaSqlite;

	static String rutaHsqldb = "./db_examen/hsqldb/database";
	static String hsqldbURL = "jdbc:hsqldb:file:" + rutaHsqldb;

	public static void main(String args[]) {
		String[] bdd = { "SQLite", "HSQLDB" };

		int n;
		do {
			n = Menu.menu(bdd, false);

			switch (n) {
			case 1:
				sqlite = true;
				System.out.println("conectando a SQLite\n...");
				elegirOpcion();
				break;

			case 2:
				hsqldb = true;
				System.out.println("conectando a HSQLDB\n...");
				elegirOpcion();
				break;

			case 3:
				System.out.println("bye");

			}
			sqlite = false;
			hsqldb = false;

		} while (n != bdd.length + 1);

	}

	private static void introduce_municipio() {
		String url = "";
		if (sqlite)
			url = sqliteURL;
		if (hsqldb)
			url = hsqldbURL;

		try (Connection con = DriverManager.getConnection(url);) {
			String driver = con.getMetaData().getDriverName();
			System.out.println("driver elegido: " + driver);
			if (driver.contains("SQLite")) {
				System.out.println("esta opción solo está disponible con HSQLDB");
				return;
			}
			
			CallableStatement call = con.prepareCall("call nuevo_municipio(?, ?, ?)");
			call.setString(1,Menu.askString("id municipio"));
			call.setString(2,Menu.askString("nombre"));
			call.setInt(3,Menu.askInt("número de habitantes"));
			call.execute();
			System.out.println("municipio creado");

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			System.out.println();
		}

	}

	private static void introduce_alumno() {
		String url = "";
		if (sqlite)
			url = sqliteURL;
		if (hsqldb)
			url = hsqldbURL;
		String sql = "INSERT INTO alumnos VALUES (?, ?, ?, ?)";
		try (Connection con = DriverManager.getConnection(url); PreparedStatement pstt = con.prepareStatement(sql);) {
			System.out.println("driver elegido: " + con.getMetaData().getDriverName());

			pstt.setInt(1, Menu.askInt("nia"));
			pstt.setString(2, Menu.askString("nombre"));
			pstt.setString(3, Menu.askString("apellidos"));
			pstt.setInt(4, Menu.askInt("id municipio"));
			if (pstt.executeUpdate() == 1)
				System.out.println("alumno creado correctamente");
			else
				System.out.println("error al crear el alumno");

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			System.out.println();
		}

	}

	private static void mostrar_municipios() {
		String url = "";
		if (sqlite)
			url = sqliteURL;
		if (hsqldb)
			url = hsqldbURL;
		try (Connection con = DriverManager.getConnection(url); Statement stt = con.createStatement();) {
			System.out.println("driver elegido: " + con.getMetaData().getDriverName());

			String query = "SELECT * FROM municipios";
			ResultSet rs = stt.executeQuery(query);
			JDBCHelper.showResultSet(rs);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			System.out.println();
		}

	}

	private static void mostrar_alumnos() {
		String url = "";
		if (sqlite)
			url = sqliteURL;
		if (hsqldb)
			url = hsqldbURL;
		try (Connection con = DriverManager.getConnection(url); Statement stt = con.createStatement();) {
			System.out.println("driver elegido: " + con.getMetaData().getDriverName());

			String query = "SELECT nombre,apellidos,id_municipio FROM alumnos";
			ResultSet rs = stt.executeQuery(query);
			JDBCHelper.showResultSet(rs);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			System.out.println();
		}
	}

	private static void elegirOpcion() {
		String[] opt = { "Mostrar un listado de alumnos", "Mostrar un listado de municipios",
				"Introducir un nuevo alumno (PreparedStatement)", "Introducir un nuevo municipio (Procedimiento)" };
		int n2;
		do {
			n2 = Menu.menu(opt, false);
			switch (n2) {
			case 1:
				mostrar_alumnos();
				break;
			case 2:
				mostrar_municipios();
				break;
			case 3:
				introduce_alumno();
				break;
			case 4:
				introduce_municipio();
				break;
			case 5:
				break;
			}
		} while (n2 != opt.length + 1);
	}
}
