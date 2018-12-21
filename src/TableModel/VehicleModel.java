package TableModel;

import javafx.beans.property.SimpleStringProperty;

public class VehicleModel {

    private SimpleStringProperty category;
    private SimpleStringProperty make;
    private SimpleStringProperty model;
    private SimpleStringProperty seating;
    private SimpleStringProperty insurance, service, registration;
    private SimpleStringProperty perkm;
    private SimpleStringProperty id;
    private SimpleStringProperty drivernic;

    public String getDrivernic() {
        return drivernic.get();
    }

    public SimpleStringProperty drivernicProperty() {
        return drivernic;
    }

    public void setDrivernic(String drivernic) {
        this.drivernic.set(drivernic);
    }

    public VehicleModel(String category, String make, String model, String seating, String insurance, String service, String registration, String perkm, String id, String driverNIC) {
        this.category = new SimpleStringProperty(category);
        this.make = new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
        this.seating = new SimpleStringProperty(seating);
        this.insurance = new SimpleStringProperty(insurance);
        this.service = new SimpleStringProperty(service);
        this.registration = new SimpleStringProperty(registration);
        this.perkm = new SimpleStringProperty(perkm);
        this.id = new SimpleStringProperty(id);
        this.drivernic = new SimpleStringProperty(driverNIC);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getCategory() {
        return category.get();
    }


    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getMake() {
        return make.get();
    }


    public void setMake(String make) {
        this.make.set(make);
    }

    public String getModel() {
        return model.get();
    }


    public void setModel(String model) {
        this.model.set(model);
    }

    public String getSeating() {
        return seating.get();
    }

    public void setSeating(String seating) {
        this.seating.set(seating);
    }

    public String getInsurance() {
        return insurance.get();
    }

    public void setInsurance(String insurance) {
        this.insurance.set(insurance);
    }

    public String getService() {
        return service.get();
    }

    public void setService(String service) {
        this.service.set(service);
    }

    public String getRegistration() {
        return registration.get();
    }

    public void setRegistration(String registration) {
        this.registration.set(registration);
    }

    public String getPerkm() {
        return perkm.get();
    }

    public void setPerkm(String perkm) {
        this.perkm.set(perkm);
    }

}
