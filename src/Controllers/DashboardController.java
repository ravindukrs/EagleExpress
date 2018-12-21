package Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton tripsBtn;

    @FXML
    private JFXButton vehicleBtn;

    @FXML
    private JFXButton clientsBtn;

    @FXML
    private JFXButton driversBtn;

    @FXML
    private AnchorPane holderPane;

    private static DashboardController instance;

    public DashboardController(){
        instance = this;
    }

    public static DashboardController getInstance() {
        return instance;
    }


    public void createPage(String link){
        try {
            AnchorPane vehicle = FXMLLoader.load(getClass().getResource(link));
            setNode(vehicle);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void setNode(Node node){
        holderPane.getChildren().clear();
        holderPane.getChildren().add(node);

        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    void dashClient(ActionEvent event) {
        createPage("/FXML/Client.fxml");
        changeColour(clientsBtn);
    }

    @FXML
    void dashHome(ActionEvent event) {
        createPage("/FXML/HomePage.fxml");
        changeColour(homeBtn);
    }

    @FXML
    void dashTrips(ActionEvent event) {
        createPage("/FXML/Trip.fxml");
        changeColour(tripsBtn);
    }

    @FXML
    void dashDriver(ActionEvent event) {
        createPage("/FXML/Driver.fxml");
        changeColour(driversBtn);

    }

    @FXML
    void dashVehicle(ActionEvent event) {
        createPage("/FXML/Vehicle.fxml");
        changeColour(vehicleBtn);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createPage("/FXML/HomePage.fxml");
        changeColour(homeBtn);

    }

    @FXML
    void logoutAction(ActionEvent event) {
        homeBtn.getScene().getWindow().hide();
        Stage loginStage = new Stage();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
            loginStage.setTitle("Pelican Tours - Login");
            loginStage.setScene(new Scene(root, 311, 429));
            loginStage.show();
            loginStage.setResizable(false);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void changeColour(JFXButton button){
        homeBtn.setStyle("-fx-text-fill:#b2aaaa");
        tripsBtn.setStyle("-fx-text-fill:#b2aaaa");
        vehicleBtn.setStyle("-fx-text-fill:#b2aaaa");
        clientsBtn.setStyle("-fx-text-fill:#b2aaaa");
        driversBtn.setStyle("-fx-text-fill:#b2aaaa");

        button.setStyle("-fx-text-fill:#2982d0");
    }
}
