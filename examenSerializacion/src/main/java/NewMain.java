/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class NewMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// No modifiques el código de la función main
		String filePath = "archivo.xml";
		Grupo grupo7J = Grupo.getFakeGroup();

		System.out.println("Grupo de alumnos para serializar:");
		System.out.println(grupo7J.toString() + "\n");

		serializeGroup(grupo7J, filePath);

		Grupo new7J = deserializeGroup(filePath);

		System.out.println("Grupo de alumnos deserializado:");
		System.out.println(new7J.toString() + "\n");

	}

	/**
	 * Serializa un objeto de la clase Grupo en un archivo XML
	 * 
	 * @param grupo7J
	 * @param filePath
	 */
	public static void serializeGroup(Grupo grupo7J, String filePath) {
		XStream flujox = new XStream();
		try {
			flujox.toXML(grupo7J, new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Deserializa un grupo que está en un archivo XML
	 * 
	 * @param filePath
	 * @return
	 */
	public static Grupo deserializeGroup(String filePath) {
		XStream flujox = new XStream();
		File f = new File(filePath);
		Grupo grupo = null;
		flujox.allowTypes(new Class[] { Alumno.class,Grupo.class });
		if (f.exists() && f.length() > 0) {
			try {
				grupo = (Grupo) flujox.fromXML(new FileInputStream(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		return grupo;
	}

}
