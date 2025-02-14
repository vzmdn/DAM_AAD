import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {
	static File depTxt = new File("./files/departments.txt");
	static File teaTxt = new File("./files/teachers.txt");
	public static void main(String args[]) {
		//String ruta = "./database/act3_3";
		String ruta = "C:\\Users\\7J\\eclipse-workspace\\EjemploCallable\\database\\callableDB";
		//File f = new File("database");
		File f = new File("C:\\Users\\7J\\eclipse-workspace\\EjemploCallable\\database");
		System.out.println("Deleting database");
		deleteDB(f);

		String url = "jdbc:hsqldb:" + ruta;

		try (Connection con = DriverManager.getConnection(url); Statement stt = con.createStatement()) {

			System.out.println("Creating and connecting to database");
			String table = "departments";
			String values = "(Dept_num INT NOT NULL PRIMARY KEY," + "Name VARCHAR," + "Office VARCHAR)";
			createTable(con, stt, table, values);			
			ArrayList<String> arrayListValues = readTxt(depTxt);
			fillDepartmentsTable(arrayListValues,con);
			showTable(table,con,stt);
			table = "teachers";
			values = "(Id INT NOT NULL PRIMARY KEY,"
					+ "Name VARCHAR,"
					+ "Surname VARCHAR,"
					+ "Email VARCHAR,"
					+ "Start_date DATE,"
					+ "Dept_num INT,"
					+ "FOREIGN KEY (Dept_num) REFERENCES departments(Dept_num))";
			
			createTable(con, stt, table, values);
			arrayListValues = readTxt(teaTxt);
			fillTeachersTable(arrayListValues,con);
			showTable(table, con, stt);
			
		} catch (Exception e) {
		}

	}
	
	static void fillTeachersTable(ArrayList<String> txtValues, Connection con) {
	    System.out.println("Filling teachers table");
	    String sql = "INSERT INTO teachers VALUES (?, ?, ?, ?, ?, ?)";
	    int filas = 0;
	    try (PreparedStatement pstt = con.prepareStatement(sql)) {
	        for (String txt : txtValues) {
	            String[] txtArray = txt.split(",");
	            for (int i = 0; i < txtArray.length; i++) {
	                txtArray[i] = txtArray[i].trim();
	            }
	            pstt.setInt(1, Integer.parseInt(txtArray[0]));
	            pstt.setString(2, txtArray[1]);
	            pstt.setString(3, txtArray[2]);
	            pstt.setString(4, txtArray[3]);
	            if(!txtArray[4].isEmpty()) pstt.setDate(5, Date.valueOf(txtArray[4]));
	            else pstt.setDate(5, null);
	            pstt.setInt(6, Integer.parseInt(txtArray[5]));
	            filas += pstt.executeUpdate();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	    System.out.println("\t..." + filas + " rows updated");
	}

	
	static void fillDepartmentsTable(ArrayList<String> txtValues, Connection con) {
		System.out.println("Filling departments table");
		String sql = "INSERT INTO departments VALUES (?,?,?)";
		int filas = 0;
		try {
			PreparedStatement pstt = con.prepareStatement(sql);
			for(String txt : txtValues) {
				String[] txtArray = txt.split(",");
				for (int i = 0; i < txtArray.length; i++) {
					txtArray[i] = txtArray[i].trim();
				}
				pstt.setInt(1, Integer.parseInt((txtArray[0])));
				pstt.setString(2, txtArray[1]);
				pstt.setString(3, txtArray[2]);
				filas += pstt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\t..." + filas + " rows updated");
	}
	
	static ArrayList<String> readTxt(File f) {
		ArrayList<String> txtValues = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			while(line != null) {
				txtValues.add(line);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txtValues;
	}
	
	

	static void createTable(Connection con, Statement stt, String table, String values) {
		System.out.println("Creating " + table + " table");
		String sql = "CREATE TABLE IF NOT EXISTS " + table + " " + values;
		try {
			stt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void showTable(String table, Connection con, Statement stt) {
		String sql = "SELECT * FROM " + table;
		System.out.println(sql);
		try {
			JDBCHelper.showResultSet(stt.executeQuery(sql));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void deleteDB(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			
				for (File file : files) {
					deleteDB(file);
				}
			
		}
		f.delete();
	}
}
