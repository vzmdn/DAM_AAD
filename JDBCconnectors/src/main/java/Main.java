import java.sql.*;

public class Main {
	public static void main(String[] args) {
		// D:\7J\Documentos\sqlite\db\Act2.1-SQLite
		String url = "jdbc:sqlite:D:\\7J\\Documentos\\sqlite\\db\\Act2.1-SQLite";

		try (Connection con = DriverManager.getConnection(url)) {
			DatabaseMetaData dmd = con.getMetaData();
			//infoMetadata(dmd);
			infoColumns(dmd);
			
			

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
	static void infoColumns(DatabaseMetaData dmd) {
		try {
			ResultSet result = dmd.getTables(null, null, null, null);
			JDBCHelper.showResultSet(result);
			/*while (result.next()) {
				String catalogo = result.getString(1);
				String esquema = result.getString(2);
				String tabla = result.getString(3);
				String tipo = result.getString(4);
				if(tipo.equals("SYSTEM TABLE")) continue;

	 			System.out.println(catalogo);
				System.out.println(esquema);
				System.out.println(tabla);
				System.out.println(tipo);

				ResultSet columnas = dmd.getColumns(null, null, tabla, null);
				while (columnas.next()) {
					String nombreCol = columnas.getString("COLUMN_NAME");
					String tipoCol = columnas.getString("TYPE_NAME");
					String tamCol = columnas.getString("COLUMN_SIZE");
					String nula = columnas.getString("IS_NULLABLE");

					System.out.println("ColName: " + nombreCol + "; type: " + tipoCol + "; size: " + tamCol
							+ "; nullable: " + nula);
				}

			}*/
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	static void infoMetadata(DatabaseMetaData dmd) throws SQLException {
		System.out.println(dmd.getDatabaseProductName());
		System.out.println(dmd.getDriverName());
		System.out.println(dmd.getDriverVersion());
		System.out.println(dmd.getURL());
		System.out.println(dmd.getUserName());
	}
}
