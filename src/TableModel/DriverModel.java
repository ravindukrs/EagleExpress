package TableModel;

import javafx.beans.property.SimpleStringProperty;

public class DriverModel {

    public String getNic() {
        return nic.get();
    }

    public SimpleStringProperty nicProperty() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic.set(nic);
    }

    public String getFname() {
        return fname.get();
    }

    public SimpleStringProperty fnameProperty() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname.set(fname);
    }

    public String getLname() {
        return lname.get();
    }

    public SimpleStringProperty lnameProperty() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname.set(lname);
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

    public String getPayperkm() {
        return payperkm.get();
    }

    public SimpleStringProperty payperkmProperty() {
        return payperkm;
    }

    public void setPayperkm(String payperkm) {
        this.payperkm.set(payperkm);
    }

    private SimpleStringProperty nic;
    private SimpleStringProperty fname;
    private SimpleStringProperty lname;
    private SimpleStringProperty contact;
    private SimpleStringProperty payperkm;

    public DriverModel(String nic, String fname, String lname, String contact, String payperkm) {
        this.nic = new SimpleStringProperty(nic);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.contact = new SimpleStringProperty(contact);
        this.payperkm = new SimpleStringProperty(payperkm);
    }


    @Override
    public String toString() {
        return this.fname+" "+this.lname;
    }
}
