package MiniModels;

import javafx.beans.property.SimpleStringProperty;

public class AvailableDriverModel {

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

    private SimpleStringProperty nic;
    private SimpleStringProperty fname;
    private SimpleStringProperty lname;

    public AvailableDriverModel(String nic, String fname, String lname) {
        this.nic = new SimpleStringProperty(nic);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
    }

    public String toString(){
        return this.getFname()+" "+this.getLname();
    }


}
