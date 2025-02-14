import java.sql.*;
import java.util.Scanner;
public class Main {
	static Scanner tec = new Scanner(System.in);
	static boolean sqlite = false;
	static boolean hsqldb = false;
	public static void main(String[] args) {
		int opt = 0;
		while(opt != 3) {
			opt = menu();
			}
	}
	static int menu() {
		System.out.println("1. SQLite Database\n2. HSQLDB Database\n3. Exit");
		int opt = tec.nextInt();
		switch(opt) {
		case 1:
			sqlite = true;
			String rutaSqlite = "./db/sqlite/sqlite";
			String sqliteURL = "jdbc:sqlite:" + rutaSqlite;
			mostrarDatos(sqliteURL);
		break;
		case 2:
			hsqldb = true;			
			String rutaHsqldb = "./db/hsqldb/hsqldb";
			String hsqldbURL = "jdbc:hsqldb:file:"+ rutaHsqldb;
			mostrarDatos(hsqldbURL);
		break;
		case 3: return 3;
		}
		return 0;
	}
	
	static void mostrarDatos(String url) {
		try (Connection con = DriverManager.getConnection(url)) {
			DatabaseMetaData dmd = con.getMetaData();
			Statement stt = con.createStatement();
			
			String query = "DELETE FROM teachers WHERE name = 'Luz'";
			int rows = stt.executeUpdate(query);
			System.out.println(rows + " filas fueron eliminadas");
			query = "SELECT * FROM teachers WHERE name = 'Luz'";
			ResultSet rs = stt.executeQuery(query);
			JDBCHelper.showResultSet(rs);
			
//			System.out.println("----------------------------------");
//			System.out.println("DATABASE INFORMATION");
//			System.out.println("----------------------------------");
//			System.out.println("Name: " + dmd.getDatabaseProductName());
//			System.out.println("Driver: " + dmd.getDriverName());
//			System.out.println("URL: " + dmd.getURL());
//			System.out.println("USER: " + dmd.getUserName());
//			System.out.println("----------------------------------");
//			ResultSet result = dmd.getTables(null, null, null, null);
			//System.out.println("TABLES INFORMATION");
			//JDBCHelper.showResultSet(result);
			/*			
			while (result.next()) {
				String catalogo = result.getString(1);
				String esquema = result.getString(2);
				String tabla = result.getString(3);
				String tipo = result.getString(4);
				//if(tipo.equals("SYSTEM TABLE")) continue;
				if(hsqldb && !esquema.equals("PUBLIC")) continue;
				System.out.println("----------------------------------");
				System.out.print("TABLE NAME: " + tabla + "; " + "Catalog: " + catalogo + "; " + "Schema: " + esquema + "; " + "Type: " + tipo + ";\n");

				ResultSet columnas = dmd.getColumns(null, null, tabla, null);
				System.out.println("*** COLUMNS of TABLE " +  tabla + " ***");
				while (columnas.next()) {
					String nombreCol = columnas.getString("COLUMN_NAME");
					String tipoCol = columnas.getString("TYPE_NAME");
					String nula = columnas.getString("IS_NULLABLE");					
					System.out.println("Column name: " + nombreCol + "; Type: " + tipoCol + "; IsNullable: " + nula);
				}

			}*/
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			System.out.println("\n");
			sqlite = false;
			hsqldb = false;
		}
	}
}
	
