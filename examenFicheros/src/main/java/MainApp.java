import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainApp {
	static File diccionario = new File("diccionarioSpam.txt");
	static File email = new File("email.txt");
	static HashMap<String, Integer> diccionarioMap = new HashMap<String, Integer>();
	static ArrayList<String> emailList = new ArrayList<String>();

	public static void main(String[] args) {
		diccionarioMap = leerPalabrasSPAM();
		emailList = leerEmail();
		int spamCount = contarPalabrasSPAM();
		String masRepetida = palabraMasRepetida();
		escribirFicheroInforme(spamCount, masRepetida);
	}

	private static void escribirFicheroInforme(int spamCount, String masRepetida) {
		File f = new File("informe.txt");
		BufferedWriter bw = null;
		float porcentaje = ((float) spamCount / emailList.size()) * 100;
		try {
			FileWriter fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			bw.write("El email tiene " + emailList.size() + " palabras, de las cuales " + spamCount
					+ " son palabras SPAM.\n");
			bw.write("Por tanto, el porcentaje de palabras SPAM es: " + porcentaje + "%\n");
			bw.write("La palabra SPAM más repetida es: " + masRepetida + " -> " + diccionarioMap.get(masRepetida)
					+ " veces\n");
			for (String p : diccionarioMap.keySet()) {
				bw.write(p + " -> " + diccionarioMap.get(p) + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static String palabraMasRepetida() {
		int n = 0;
		String masRepetida = "";
		for (String p : diccionarioMap.keySet()) {
			if (diccionarioMap.get(p) > n) {
				masRepetida = p;
				n = diccionarioMap.get(p);
			}
		}
		return masRepetida;
	}

	private static int contarPalabrasSPAM() {
		int count = 0;
		for (String p : emailList)
			if (diccionarioMap.containsKey(p)) {
				diccionarioMap.put(p, diccionarioMap.get(p) + 1);
				count++;
			}
		return count;
	}

	private static ArrayList<String> leerEmail() {
		String linea = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(email));

			while ((linea = br.readLine()) != null) {
				String[] palabras = linea.split(" ");
				for (String p : palabras) {
					p = p.toLowerCase();
					p = p.replace("!", "");
					p = p.replace("¡", "");
					p = p.replace(",", "");
					p = p.replace(".", "");
					p = p.replace(" ", "");
					if (p.length() != 0)
						emailList.add(p);
				}
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return emailList;
	}

	private static HashMap<String, Integer> leerPalabrasSPAM() {

		String linea = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(diccionario));

			while ((linea = br.readLine()) != null) {
				String[] palabras = linea.split(" ");
				for (String p : palabras) {
					p = p.toLowerCase();
					if (!diccionarioMap.containsKey(p))
						diccionarioMap.put(p, 0);

				}
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return diccionarioMap;
	}
}
