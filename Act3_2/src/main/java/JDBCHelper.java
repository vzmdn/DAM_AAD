
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JDBCHelper {
	public static void showResultSet(ResultSet res) {
		try {
			ResultSetMetaData rsmd = res.getMetaData();
			int nCol = rsmd.getColumnCount();
			String[] colNames = new String[nCol];
			for (int i = 1; i <= nCol; i++) {
				// System.out.print(rsmd.getColumnClassName(i) + "\t");
				colNames[i - 1] = rsmd.getColumnName(i);
			}
			System.out.println();

			while (res.next()) {
				System.out.print("--> ");
				for (int i = 1; i <= nCol; i++) {
					System.out.print(colNames[i - 1] + ":" + res.getString(i) + ";\t");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean containsTable(Connection con, String tableName) {
		try {
			DatabaseMetaData dmd = con.getMetaData();
			ResultSet result = dmd.getTables(null, null, tableName, null);
			if(result.next()) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
