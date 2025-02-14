
import java.util.Scanner;

public class Menu {
	public static int menu(String[] opt, boolean lang) {
		Scanner tec = new Scanner(System.in);
		String exitStr = "";
		exitStr = lang ? ". Quit" : ". Salir";
		System.out.println();
		int salirNum = opt.length + 1;
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
		Scanner tec = new Scanner(System.in);
		System.out.println("introduce " + data);
		try {
			str = tec.nextLine();

		} catch (Exception e) {
		}
		return str;

	}

	public static int askInt(String data) {
		int n = 0;
		Scanner tec = new Scanner(System.in);
		System.out.println("introduce " + data);
		try {
			n = tec.nextInt();

		} catch (Exception e) {
		}
		return n;

	}
}
/**
 * CODIGO PARA EL PROGRAMA int n; do n = Menu.menu(opt); while (n != opt.length
 * + 1);
 */
