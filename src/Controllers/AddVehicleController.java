package Controllers;

import DBConnection.DBHandler;
import TableModel.DriverModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddVehicleController implements Initializable {
    @FXML
    private Spinner capacitySpinner;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton back;

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
    void selectDriver(ActionEvent event) {

    }

    @FXML
    private JFXComboBox<DriverModel> driverSelector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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


    @FXML
    void addClick(ActionEvent event) {
        String cat = catagory.getText();
        String mak = make.getText();
        String mod= model.getText();
        int seating = Integer.parseInt(capacitySpinner.getValue().toString());
        Date ins = Date.valueOf(insurance.getValue());
        Date ser = Date.valueOf(service.getValue());
        Date reg = Date.valueOf(registration.getValue());
        float cos = Float.parseFloat(cost.getText());
        String driverNic = driverSelector.getValue().getNic();


        String query = "INSERT INTO VEHICLE(CATEGORY,MAKE,MODEL,SEATING,INSURANCE_EXPIARY,NEXT_SERVICE,NEXT_REGISTRATION,PERKM_COST, DRIVER)VALUES(?,?,?,?,?,?,?,?,?)";


        Connection conn = null;
        PreparedStatement stmt;

        try{
            DBHandler handler = new DBHandler();
            conn = handler.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1,cat);
            stmt.setString(2,mak);
            stmt.setString(3,mod);
            stmt.setInt(4,seating);
            stmt.setDate(5,ins);
            stmt.setDate(6,ser);
            stmt.setDate(7,reg);
            stmt.setFloat(8,cos);
            stmt.setString(9,driverNic);
            int results = stmt.executeUpdate();
            System.out.println("Result of Update: "+results);

        }catch(SQLException e){
            System.out.println("ERROR OCCURED");
        }finally{
            try{
                conn.close();
            }catch (Exception e){
                System.out.println(e);
            }
            DashboardController.getInstance().createPage("/FXML/Vehicle.fxml");

        }


    }

    @FXML
    void backClick(ActionEvent event) {
        DashboardController.getInstance().createPage("/FXML/Vehicle.fxml");
    }
}
