package agenda;

import java.io.Serializable;

/**
 *
 * @author 7JDAM
 */
public class Contact implements Serializable {
    String name, surname, telf;

    // No-argument constructor for Jackson
    public Contact() {
    }

    public Contact(String name, String surname, String telf) {
        this.name = name;
        this.surname = surname;
        this.telf = telf;
    }

    // Getters and setters...
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    @Override
    public String toString() {
        return this.name + " " + this.surname + "; " + this.telf;
    }
}
