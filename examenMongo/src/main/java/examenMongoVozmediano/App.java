package examenMongoVozmediano;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.Arrays;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import examenMongoVozmediano.entities.Alumnos;
import examenMongoVozmediano.entities.Fct;

public class App {
	
private static MongoClient client;
	
	private static MongoDatabase db;
	
	private static MongoCollection<Alumnos> alumnosCol;
	
	//private static MongoCollection<Fct> fctCol;
	

	public static void main(String[] args) {
		
		// 2 Conecta y borra colección alumnos
		init();
		alumnosCol.drop();
		
		// 3 Inserta alumnos
		introducirAlumnosDemo();
		
		// 4 Búsquedas
		System.out.println("///////////////////////////");
		busquedaA();
		System.out.println("///////////////////////////");
		busquedaB();
		System.out.println("///////////////////////////");
		busquedaC();
		
		// 5. Modificaciones
		modificacionA();
		modificacionB();
		modificacionC();
		
		// 6.
		eliminar();
		
		close();
		
	}
	public static void eliminar() {
		Bson filter = exists("fct");
		alumnosCol.deleteMany(nor(filter));
	}
	
	public static void modificacionC() {
		System.out.println("\n\n\n\n\n");
		Bson filter = nin("modulos", "FCT");
		Bson update = unset("fct");
		
		
		alumnosCol.updateMany(filter, update);
		
		
	}
	
	public static void modificacionB() {
		Bson filter = gt("fct.horas", 250);
		Bson update = push("modulos", "Proyecto");
		alumnosCol.updateMany(filter, update);
	}
	
	public static void modificacionA() {
		Bson filter = eq("grupo", "7J");
		Bson update = inc("fct.horas", 50);
		alumnosCol.updateMany(filter, update);
	}
	
	public static void busquedaC() {
		System.out.println("alumnos con menos horas de fct\n");
		Bson sort = new Document("fct.horas", 1);
		Alumnos a = alumnosCol.find().sort(sort).first();
		System.out.println(a);
	}
	
	public static void busquedaB() {
		System.out.println("alumnos con menos de 250h en fct\n");
		Bson filter = lt("fct.horas", 250);
		FindIterable<Alumnos> alumnos = alumnosCol.find(filter);
		alumnos.forEach(a -> System.out.println(a+"\n"));
		
	}
	
	public static void busquedaA() {
		System.out.println("alumnos matriculados en ADA con apellido García\n");
		Bson filter = eq("modulos", "ADA");
		Bson filter2 = regex("apellidos","García");
		FindIterable<Alumnos> alumnos = alumnosCol.find(and(filter, filter2));
		alumnos.forEach(a -> System.out.println(a+"\n"));
		
		
	}
	
	public static void introducirAlumnosDemo() {
		Alumnos a1 = new Alumnos();
		a1.setNombre("Pepe");
		a1.setApellidos("García Andreu");
		a1.setGrupo("7K");
		String[] modulos = {"ADA", "DI", "SGE", "PSP", "FCT"};
		a1.setModulos(Arrays.asList(modulos));
		Fct fct = new Fct("Software", 254);
		a1.setFct(fct);
		
		Alumnos a2 = new Alumnos();
		a2.setNombre("Pablo");
		a2.setApellidos("López Vázquez");
		a2.setGrupo("7K");
		String[] modulos2 = {"DI", "SGE", "PSP", "FCT"};
		a2.setModulos(Arrays.asList(modulos2));
		fct = new Fct("Apps SL", 120);
		a2.setFct(fct);
		
		Alumnos a3 = new Alumnos();
		a3.setNombre("María");
		a3.setApellidos("Barreres García");
		a3.setGrupo("7J");
		String[] modulos3 = {"ADA", "DI", "SGE", "PSP", "FCT"};
		a3.setModulos(Arrays.asList(modulos3));
		fct = new Fct("Startup SL", 300);
		a3.setFct(fct);
		
		Alumnos a4 = new Alumnos();
		a4.setNombre("Ramón");
		a4.setApellidos("Perez García");
		a4.setGrupo("7J");
		String[] modulos4 = {"ADA", "DI", "SGE", "PSP"};
		a4.setModulos(Arrays.asList(modulos4));
		fct = new Fct("Currantes SA", 50);
		a4.setFct(fct);
		
		
		alumnosCol.insertOne(a1);
		alumnosCol.insertOne(a2);
		alumnosCol.insertOne(a3);
		alumnosCol.insertOne(a4);
	}
	
	public static void init() {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		client = MongoClients.create();
		db = client.getDatabase("abastos");
		db = db.withCodecRegistry(pojoCodecRegistry);
		alumnosCol = db.getCollection("alumnos", Alumnos.class);
	}
	
	public static void close() {
		client.close();
	}

}
