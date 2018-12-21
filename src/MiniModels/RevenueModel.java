package MiniModels;

import javafx.beans.property.SimpleStringProperty;

public class RevenueModel {

    public String getDistance() {
        return distance.get();
    }

    public SimpleStringProperty distanceProperty() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
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

    private SimpleStringProperty distance;
    private SimpleStringProperty std_rate;

    public RevenueModel(String distance, String std_rate) {
        this.distance = new SimpleStringProperty(distance);
        this.std_rate = new SimpleStringProperty(std_rate);

    }

}
