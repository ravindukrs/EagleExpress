package Controllers;

import DBConnection.DBHandler;
import MiniModels.AvailableDriverModel;
import MiniModels.ClientListModel;
import TableModel.DriverModel;
import TableModel.VehicleModel;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddTripController implements Initializable {
    @FXML
    private JFXButton add;

    @FXML
    private JFXButton back;

    @FXML
    private JFXDatePicker start_date;

    @FXML
    private JFXDatePicker end_date;

    @FXML
    private JFXComboBox<String> client;

    @FXML
    private JFXComboBox<AvailableDriverModel> driver;

    @FXML
    private JFXCheckBox round_trip;

    @FXML
    private JFXTextField destination;

    @FXML
    private JFXTextField distance;

    @FXML
    private JFXComboBox<VehicleModel> vehicleSelector;

    VehicleModel selectedVehicle;
    @FXML
    void vehiAction(ActionEvent event) {
        selectedVehicle = vehicleSelector.getSelectionModel().getSelectedItem();
    }

    Connection conn = null;
    DBHandler handler;
    ResultSet rs = null;
    PreparedStatement stmt;

    String selectedClient;
    @FXML
    void clientAction(ActionEvent event) {
       selectedClient = client.getValue();
    }

    //ObservableList<VehicleModel> vehiList;

    AvailableDriverModel drivermodel;
    @FXML
    void driverAction(ActionEvent event) {
        drivermodel = driver.getValue();

//        try {
//
//            handler = new DBHandler();
//            conn = handler.getConnection();
//            stmt = conn.prepareStatement("SELECT * FROM VEHICLE WHERE DRIVER=?");
//            stmt.setString(1,drivermodel.getNic());
//            rs = stmt.executeQuery();
//
//
//            while(rs.next()){
//                vehiList.add(new VehicleModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),rs.getString(9),rs.getString(10)));
//            }
//
//
//        }catch(Exception E){
//            System.out.println("Exception in Driver List");
//            System.out.println(E);
//        }finally {
//            try {
//                rs.close();
//                conn.close();
//
//            } catch (SQLException e) {
//                System.out.println(e);
//            }
//
//        }
//
//        vehicleSelector.getItems().clear();
//        for (VehicleModel model:vehiList
//             ) {
//            vehicleSelector.getItems().add(model);
//        }
//
//        vehicleSelector.setConverter(new StringConverter<VehicleModel>() {
//            @Override
//            public String toString(VehicleModel object) {
//                return object.getModel();
//            }
//
//            @Override
//            public VehicleModel fromString(String string) {
//                return vehicleSelector.getSelectionModel().getSelectedItem();
//            }
//        });

    }

    @FXML
    void endDateAction(ActionEvent event) {
        LocalDate startDate = Date.valueOf(start_date.getValue()).toLocalDate();
        LocalDate endDate = Date.valueOf(end_date.getValue()).toLocalDate();
        if(startDate.isAfter(endDate)){
            Tooltip tp = new Tooltip("Invalid Date! Select a date after Start Date!");
            end_date.setTooltip(tp);
            driver.setDisable(true);
        }
        if(startDate.isBefore(endDate)||startDate.isEqual(endDate)){
            //Populate driver
            driver.setDisable(false);
            fillDriverList(Date.valueOf(start_date.getValue()),Date.valueOf(end_date.getValue()));
            driver.getItems().clear();

            for (AvailableDriverModel model:driverList
                 ) {
                driver.getItems().add(model);
            }
        }

    }

    @FXML
    void startDateAction(ActionEvent event) {

       Date startDate = Date.valueOf(start_date.getValue());
       if(startDate!=null){
           end_date.setDisable(false);
       }
       if(startDate==null){
            end_date.setDisable(true);
       }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vehicleSelector.setVisible(false);
        end_date.setDisable(true);
        driver.setDisable(true);

        //Populate clients
        client.getItems().clear();
        fillClientList();

        for (String c: clients
             ) {
            client.getItems().add(c);
        }

    }

    @FXML
    void addClick(ActionEvent event) {

        try{

        LocalDate startDate = start_date.getValue();
        Date startDateSQL = Date.valueOf(startDate);
        LocalDate endDate = end_date.getValue();
        Date endDateSQL = Date.valueOf(endDate);
        String selectedDriver = drivermodel.getNic();
        String clientSelected = selectedClient;
        String dest = destination.getText();
        int clientId = 0;
        boolean returnTrip = round_trip.isSelected();
        //int vehicleId = Integer.parseInt(selectedVehicle.getId());
        String returnVal = "No";
        if(returnTrip==true){
            returnVal = "Yes";
        }
        float dist;
        String status = null;

        if((LocalDate.now().isBefore(endDate)&&LocalDate.now().isAfter(startDate))||LocalDate.now().equals(startDate)||LocalDate.now().equals(endDate)){
            status="In Progress";
        }
        if(LocalDate.now().isBefore(startDate)){
            status="Pending";
        }
        if(LocalDate.now().isAfter(endDate)){
            status="Completed";
        }
        try {
            dist = Float.parseFloat(distance.getText());
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incorrect value in Distance");
            alert.show();
            return;
        }

        if(endDate.isBefore(startDate)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incorrect Start Date or End Date Selected. Please try again");
            alert.show();
        }else if(startDate==null||endDate==null||selectedDriver==null||clientSelected==null||dest.length()<2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid data entered.");
            alert.show();
            return;
        }else {

            try {
                PreparedStatement stmt;
                handler = new DBHandler();
                conn = handler.getConnection();
                String query = "SELECT ID FROM CLIENT WHERE COMPANY_NAME=?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, clientSelected);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    clientId = Integer.parseInt(rs.getString(1));
                }

                String query2 = "INSERT INTO TRIP (CLIENT_ID, DRIVER_NIC, DESTINATION, ROUND_TRIP, DISTANCE, START_DATE, END_DATE, STATUS) VALUES(?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(query2);
                stmt.setInt(1, clientId);
                stmt.setString(2, selectedDriver);
                stmt.setString(3, dest);
                stmt.setString(4, returnVal);
                stmt.setFloat(5, dist);
                stmt.setDate(6, startDateSQL);
                stmt.setDate(7, endDateSQL);
                stmt.setString(8, status);
                //stmt.setInt(9, vehicleId);

                stmt.executeUpdate();


            } catch (Exception E) {
                System.out.println("Exception");
                System.out.println(E);
            } finally {
                try {
                    rs.close();
                    conn.close();

                } catch (SQLException e) {
                    System.out.println(e);
                }

            }

            DashboardController.getInstance().createPage("/FXML/Trip.fxml");
        }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid Details Entered!");
            alert.show();
        }

    }

    @FXML
    void backClick(ActionEvent event) {
        DashboardController.getInstance().createPage("/FXML/Trip.fxml");
    }

    ObservableList<AvailableDriverModel> driverList;


    void fillDriverList(Date startDate, Date endDate){
        PreparedStatement stmt;
        try {
            handler = new DBHandler();
            conn = handler.getConnection();
            String query = "SELECT DISTINCT NIC, F_NAME, L_NAME FROM DRIVER LEFT OUTER JOIN TRIP ON DRIVER.NIC = TRIP.DRIVER_NIC WHERE START_DATE > ? OR END_DATE < ? OR START_DATE IS NULL";
            stmt = conn.prepareStatement(query);
            stmt.setDate(1,endDate);
            stmt.setDate(2,startDate);

            rs = stmt.executeQuery();

            driverList = FXCollections.observableArrayList();

            while(rs.next()){

                driverList.add(new AvailableDriverModel(rs.getString(1),rs.getString(2),rs.getString(3)));

            }

        }catch(Exception E){
            System.out.println("Exception");
        }finally{
            try{
                rs.close();
                conn.close();
                
            }catch(SQLException e){
                System.out.println(e);
            }

        }
    }

    ArrayList<String> clients = new ArrayList<>();
    void fillClientList(){

        try {

            handler = new DBHandler();
            conn = handler.getConnection();
            rs = conn.createStatement().executeQuery("SELECT ID, COMPANY_NAME FROM CLIENT");

            ClientListModel listModel;
            while(rs.next()==true){
                listModel = new ClientListModel(rs.getString(1),rs.getString(2));
                clients.add(listModel.toString());
            }

        }catch(Exception E){
            System.out.println("Exception in Client List");
            System.out.println(E);
        }finally{
            try{
                rs.close();
                conn.close();

            }catch(SQLException e){
                System.out.println(e);
            }

        }
    }
}


