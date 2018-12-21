package TableModel;

import javafx.beans.property.SimpleStringProperty;

public class TripModel {

    private SimpleStringProperty trip_id,client_id,driver_nic,destination,round_trip, distance, start_date,end_date,status;
    private SimpleStringProperty driverFname, driverLname, driverContact, driverRate, clientId, clientName, clientRate;


    public TripModel(String trip_id, String client_id, String driver_nic, String destination, String round_trip, String distance, String start_date, String end_date, String status,
    String driverFname, String driverLname, String driverContact, String driverRate, String clientId, String clientName, String clientRate) {
        this.trip_id = new SimpleStringProperty(trip_id);
        this.client_id = new SimpleStringProperty(client_id);
        this.driver_nic = new SimpleStringProperty(driver_nic);
        this.destination = new SimpleStringProperty(destination);
        this.round_trip = new SimpleStringProperty(round_trip);
        this.distance = new SimpleStringProperty(distance);
        this.start_date = new SimpleStringProperty(start_date);
        this.end_date = new SimpleStringProperty(end_date);
        this.status = new SimpleStringProperty(status);
        //this.vehicleId = new SimpleStringProperty(vehicleId);
        //Additional
        this.driverFname = new SimpleStringProperty(driverFname);
        this.driverLname = new SimpleStringProperty(driverLname);
        this.driverContact = new SimpleStringProperty(driverContact);
        this.driverRate = new SimpleStringProperty(driverRate);
        this.clientId = new SimpleStringProperty(clientId);
        this.clientName = new SimpleStringProperty(clientName);
        this.clientRate = new SimpleStringProperty(clientRate);

    }

    public TripModel(String trip_id, String start_date, String end_date){
        this.trip_id = new SimpleStringProperty(trip_id);
        this.start_date = new SimpleStringProperty(start_date);
        this.end_date = new SimpleStringProperty(end_date);
    };

    public String getTrip_id() {
        return trip_id.get();
    }

    public SimpleStringProperty trip_idProperty() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id.set(trip_id);
    }

    public String getClient_id() {
        return client_id.get();
    }

    public SimpleStringProperty client_idProperty() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id.set(client_id);
    }

    public String getDriver_nic() {
        return driver_nic.get();
    }

    public SimpleStringProperty driver_nicProperty() {
        return driver_nic;
    }

    public void setDriver_nic(String driver_nic) {
        this.driver_nic.set(driver_nic);
    }

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public String getRound_trip() {
        return round_trip.get();
    }

    public SimpleStringProperty round_tripProperty() {
        return round_trip;
    }

    public void setRound_trip(String round_trip) {
        this.round_trip.set(round_trip);
    }

    public String getDistance() {
        return distance.get();
    }

    public SimpleStringProperty distanceProperty() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
    }

    public String getStart_date() {
        return start_date.get();
    }

    public SimpleStringProperty start_dateProperty() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date.set(start_date);
    }

    public String getEnd_date() {
        return end_date.get();
    }

    public SimpleStringProperty end_dateProperty() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date.set(end_date);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
    public String getDriverFname() {
        return driverFname.get();
    }public SimpleStringProperty driverFnameProperty() {
        return driverFname;
    }public void setDriverFname(String driverFname) {
        this.driverFname.set(driverFname);
    }public String getDriverLname() {
        return driverLname.get();
    }public SimpleStringProperty driverLnameProperty() {
        return driverLname;
    }public void setDriverLname(String driverLname) {
        this.driverLname.set(driverLname);
    }public String getDriverContact() {
        return driverContact.get();
    }public SimpleStringProperty driverContactProperty() {
        return driverContact;
    }public void setDriverContact(String driverContact) {
        this.driverContact.set(driverContact);
    }public String getDriverRate() {
        return driverRate.get();
    }public SimpleStringProperty driverRateProperty() {
        return driverRate;
    }public void setDriverRate(String driverRate) {
        this.driverRate.set(driverRate);
    }public String getClientId() {
        return clientId.get();
    }public SimpleStringProperty clientIdProperty() {
        return clientId;
    }public void setClientId(String clientId) {
        this.clientId.set(clientId);
    }public String getClientName() {
        return clientName.get();
    }public SimpleStringProperty clientNameProperty() {
        return clientName;
    }public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }public String getClientRate() {
        return clientRate.get();
    }public SimpleStringProperty clientRateProperty() {
        return clientRate;
    }public void setClientRate(String clientRate) {
        this.clientRate.set(clientRate);
    }

}
