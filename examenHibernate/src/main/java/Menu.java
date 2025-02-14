
import java.util.Scanner;
public class Menu {
	
	
	/**
	 * CODIGO PARA EL PROGRAMA
	 * int n;
	 * do n = {
	 * 	Menu.menu(opt);
	 * 	switch(){};
	 * while (n != opt.length + 1);
	 */
	
	public static int menu(String[] opt, boolean lang) {
		Scanner tec = new Scanner(System.in);
		String exitStr = "";
		exitStr = lang ? ". Quit" : ". Salir";
		System.out.println();
		int salirNum = opt.length+1;
		for (int i = 0; i < opt.length; i++) {
			System.out.println((i + 1) + ". " + opt[i]);
		}
		System.out.println(salirNum + exitStr);
		int n = 0;
		while (n <= 0 || n > opt.length + 1) {
			try {
				n = tec.nextInt();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return n;
		
	}
	
	
	public static String askString(String data) {
		String str = "";
			System.out.print("introduce " + data + ": ");
			try {
				Scanner tec = new Scanner(System.in);
				str = tec.nextLine();

			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return str;

	}
	public static int askInt(String data) {
		int n = 0;
		System.out.print("introduce " + data + ": ");
		try{
			Scanner tec = new Scanner(System.in);
				n = tec.nextInt();

		}catch (Exception e) {
				e.printStackTrace();
			}
		
		return n;

	}
	
	public static boolean askYesNo(String str) {
		char charRes = ' ';
		System.out.print(str + " (y/n): ");
		try {
			Scanner tec = new Scanner(System.in);
			charRes = tec.next().charAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charRes == 'y';
	}
	
	public static void notFound(String str) {
		System.out.println(str + " not found in the database");

	}

	public static void notFound(int n) {
		System.out.println(n + " not found in the database");

	}
	
}

