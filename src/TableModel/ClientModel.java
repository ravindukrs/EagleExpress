package TableModel;

import javafx.beans.property.SimpleStringProperty;

public class ClientModel {

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getComp_name() {
        return comp_name.get();
    }

    public SimpleStringProperty comp_nameProperty() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name.set(comp_name);
    }

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getCont_person() {
        return cont_person.get();
    }

    public SimpleStringProperty cont_personProperty() {
        return cont_person;
    }

    public void setCont_person(String cont_person) {
        this.cont_person.set(cont_person);
    }

    public String getContact() {
        return contact.get();
    }

    public SimpleStringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public String getStd_rate() {
        return std_rate.get();
    }

    public SimpleStringProperty std_rateProperty() {
        return std_rate;
    }

    public void setStd_rate(String std_rate) {
        this.std_rate.set(std_rate);
    }

    private SimpleStringProperty id;
    private SimpleStringProperty comp_name;
    private SimpleStringProperty location;
    private SimpleStringProperty email;
    private SimpleStringProperty cont_person;
    private SimpleStringProperty contact;
    private SimpleStringProperty std_rate;

    public ClientModel(String id, String comp_name, String location, String email, String cont_person, String contact, String std_rate) {
        this.id = new SimpleStringProperty(id);
        this.comp_name = new SimpleStringProperty(comp_name);
        this.location = new SimpleStringProperty(location);
        this.email = new SimpleStringProperty(email);
        this.cont_person = new SimpleStringProperty(cont_person);
        this.contact = new SimpleStringProperty(contact);
        this.std_rate = new SimpleStringProperty(std_rate);
    }



}
