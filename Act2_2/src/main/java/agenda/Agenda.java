package agenda;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import com.thoughtworks.xstream.XStream;

/**
 *
 * @author 7JDAM
 */
public class Agenda {

    enum Mode {
        BINARY, XML, JSON
    };

    static Mode mode;
    static Scanner sc = new Scanner(System.in);
    static ContactList contacts = new ContactList();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        boolean salir = false;
        
        
        // Select mode before getting the file name
        mode = selectMode();
        String fileName = getAgendaFileName();
        
        File f = new File(fileName);
        
        // If the agenda file exists
        if (f.exists()) {
            contacts = readAgenda(f);
            showContacts(contacts.getList());
        }

        // Menu
        do {
            printMenu();
            String opt = sc.nextLine();
            switch (opt) {
                case "1": // Show all contacts
                    showContacts(contacts.getList());
                    break;

                case "2": // Add new contact
                    String name, surname, telf;
                    System.out.print("Name: ");
                    name = sc.nextLine();
                    System.out.print("Surname: ");
                    surname = sc.nextLine();
                    System.out.print("Telf: ");
                    telf = sc.nextLine();
                    contacts.add(new Contact(name, surname, telf));
                    break;

                case "3": // Save and exit
                    System.out.println("Saving all contacts...");
                    System.out.println(contacts);
                    saveAgendaFile(contacts, f);
                    salir = true;
                    break;
                default:
                    System.out.println("Incorrect option");
            }
        } while (!salir);
        System.out.println("Bye, bye!");
    }


    private static Mode selectMode() {
        while (true) {
            System.out.println("Select Mode:\n  1-> Binary\n  2-> XML\n  3-> JSON");
            String opt = sc.nextLine();
            switch (opt) {
                case "1":
                    return Mode.BINARY;
                case "2":
                    return Mode.XML;
                case "3":
                    return Mode.JSON;
                default:
                    System.out.println("Incorrect option");
            }
        }
    }

    private static void saveAgendaFile(ContactList contacts, File f) throws IOException {
        switch (mode) {
            case XML:
                XStream flujox = getConfiguredXStream();
                flujox.toXML(contacts, new FileOutputStream(f));
                break;
            case BINARY:
                try (FileOutputStream fout = new FileOutputStream(f);
                     ObjectOutputStream objOut = new ObjectOutputStream(fout)) {
                    objOut.writeObject(contacts);
                }
                break;
            case JSON:
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(f, contacts);
                System.out.println("JSON file created successfully!");
                break;
        }
    }

    private static String getAgendaFileName() {
        String fileName;
        switch (mode) {
            case XML:
                fileName = "agenda.xml";
                break;
            case JSON:
                fileName = "agenda.json";
                break;
            default:
                fileName = "agenda.bin";
                break;
        }
        return fileName;
    }

    private static ContactList readAgenda(File f) throws IOException, ClassNotFoundException {
        ContactList contacts = null;
        switch (mode) {
            case XML:
                XStream flujox = getConfiguredXStream();
                contacts = (ContactList) flujox.fromXML(f);
                break;
            case BINARY:
                try (FileInputStream fin = new FileInputStream(f);
                     ObjectInputStream objIn = new ObjectInputStream(fin)) {
                    contacts = (ContactList) objIn.readObject();
                }
                break;
            case JSON:
                ObjectMapper objectMapper = new ObjectMapper();
                contacts = objectMapper.readValue(f, ContactList.class);
                break;
        }
        return contacts;
    }

    private static XStream getConfiguredXStream() {
        XStream xstream = new XStream();
        xstream.allowTypes(new Class[]{Contact.class, ContactList.class});
        return xstream;
    }

    static void showContacts(ArrayList<Contact> contacts) {
        System.out.println("");
        System.out.println("There are " + contacts.size() + " contacts");
        int i = 1;
        for (Contact contact : contacts) {
            System.out.println(i + ".- " + contact.toString());
            i++;
        }
    }

    public static void printMenu() {
        System.out.println("");
        System.out.println("1. Show all contacts");
        System.out.println("2. Add new contact");
        System.out.println("3. Save and exit");
        System.out.print("Please, choose an option: ");
    }
}
