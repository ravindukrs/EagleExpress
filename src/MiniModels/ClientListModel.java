package MiniModels;

import javafx.beans.property.SimpleStringProperty;

public class ClientListModel {


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

    private SimpleStringProperty id;
    private SimpleStringProperty comp_name;

    public ClientListModel(String id, String comp_name) {
        this.id = new SimpleStringProperty(id);
        this.comp_name = new SimpleStringProperty(comp_name);

    }

    public String toString(){
        return this.getComp_name();
    }
}
