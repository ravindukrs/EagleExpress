package Controllers;

import DBConnection.DBHandler;
import MiniModels.ClientListModel;
import TableModel.DriverModel;
import TableModel.VehicleModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditVehicleController implements Initializable {

    @FXML
    private JFXComboBox<DriverModel> driverSelector;


    @FXML
    private JFXButton update;

    @FXML
    private JFXButton close;

    @FXML
    private JFXTextField catagory;

    @FXML
    private JFXTextField make;

    @FXML
    private JFXTextField model;

    @FXML
    private JFXDatePicker insurance;

    @FXML
    private JFXDatePicker service;

    @FXML
    private JFXDatePicker registration;

    @FXML
    private JFXTextField cost;

    @FXML
    private Spinner<Integer> capacitySpinner;

    @FXML
    void closeClick(ActionEvent event) {
        close.getScene().getWindow().hide();
    }
    private int SelecteId;
    @FXML
    void updateClick(ActionEvent event) {
        try{
            String cat = catagory.getText();
            String mak = make.getText();
            String mod= model.getText();
            int seating = Integer.parseInt(capacitySpinner.getValue().toString());
            Date ins = Date.valueOf(insurance.getValue());
            Date ser = Date.valueOf(service.getValue());
            Date reg = Date.valueOf(registration.getValue());
            float cos = Float.parseFloat(cost.getText());
            String driver = driverSelector.getValue().getNic();

            String query = "UPDATE VEHICLE SET DRIVER=? ,CATEGORY=?,MAKE=?,MODEL=?,SEATING=?,INSURANCE_EXPIARY=?,NEXT_SERVICE=?,NEXT_REGISTRATION=?,PERKM_COST=? WHERE VEHICLE_ID=?";


            Connection conn = null;
            PreparedStatement stmt;

            try{
                DBHandler handler = new DBHandler();
                conn = handler.getConnection();
                stmt = conn.prepareStatement(query);
                stmt.setString(1,driver.trim());
                stmt.setString(2,cat);
                stmt.setString(3,mak);
                stmt.setString(4,mod);
                stmt.setInt(5,seating);
                stmt.setDate(6,ins);
                stmt.setDate(7,ser);
                stmt.setDate(8,reg);
                stmt.setFloat(9,cos);
                stmt.setInt(10,SelecteId);
                int results = stmt.executeUpdate();
                System.out.println("Result of Update: "+results);

                DashboardController.getInstance().createPage("/FXML/Vehicle.fxml");
                update.getScene().getWindow().hide();

            }catch(SQLException e){
                System.out.println("ERROR OCCURED");
                System.out.println(e);

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Update Failed!");
                alert.setContentText("Invalid Data Entered!");
                alert.show();
            }finally{
                try{
                    conn.close();
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Update Failed!");
            alert.setContentText("Invalid Data Entered!");
            alert.show();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VehicleModel selectedVehicle = VehicleController.nowSelected;
        SelecteId = Integer.parseInt(selectedVehicle.getId());
        catagory.setText(selectedVehicle.getCategory());
        make.setText(selectedVehicle.getMake());
        model.setText(selectedVehicle.getModel());

        SpinnerValueFactory<Integer> capacityLimiter = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,80,1);
        capacitySpinner.setValueFactory(capacityLimiter);
        capacitySpinner.setEditable(true);

        fillDriverList();
        driverSelector.getItems().clear();
        for (DriverModel model:driverList
                ) {
            driverSelector.getItems().add(model);

        }

        driverSelector.setConverter(new StringConverter<DriverModel>() {
            @Override
            public String toString(DriverModel object) {
                return object.getFname();
            }

            @Override
            public DriverModel fromString(String string) {
                return driverSelector.getSelectionModel().getSelectedItem();
            }
        });


    }

    Connection conn = null;
    DBHandler handler;
    ResultSet rs=null;
    ObservableList<DriverModel> driverList = FXCollections.observableArrayList();
    void fillDriverList(){

        try {

            handler = new DBHandler();
            conn = handler.getConnection();
            rs = conn.createStatement().executeQuery("SELECT * FROM DRIVER");


            while(rs.next()){
                driverList.add(new DriverModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }


        }catch(Exception E){
            System.out.println("Exception in Driver List");
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
