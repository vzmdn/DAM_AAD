import java.util.Scanner;
public class Menu {
	static Scanner tec = new Scanner(System.in);
	public static int menu(String[] opt, boolean lang) {
		String exitStr = "";
		exitStr = lang ? ". Quit" : ". Salir";
		System.out.println();
		int salirNum = opt.length+1;
		for (int i = 0; i < opt.length; i++) {
			System.out.println((i + 1) + ". " + opt[i]);
		}
		System.out.println(salirNum + exitStr);
		System.out.print("select option: ");
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
}
/**
 * CODIGO PARA EL PROGRAMA CON VUELTA AL MENU
 * int n;
 * do n = Menu.menu(opt);
 * while (n != opt.length + 1);
 * 
 * SIN VUELTA
 * int n = Menu.menu(opt);
 */