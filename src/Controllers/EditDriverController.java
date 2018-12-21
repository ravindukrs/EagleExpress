package Controllers;

import DBConnection.DBHandler;
import TableModel.DriverModel;
import TableModel.VehicleModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditDriverController implements Initializable {
    @FXML
    private JFXButton update;

    @FXML
    private JFXButton close;

    @FXML
    private JFXTextField lname;

    @FXML
    private JFXTextField fname;

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXTextField payperkm;

    @FXML
    void closeClick(ActionEvent event) {
        close.getScene().getWindow().hide();
    }

    private String SelecteId;
    @FXML
    void updateClick(ActionEvent event) {
        try{
            String firstName = fname.getText();
            String lastName= lname.getText();
            String contactnum= contact.getText();
            float pay= Float.parseFloat(payperkm.getText());


            String query = "UPDATE DRIVER SET F_NAME=?,L_NAME=?,CONTACT=?,PAYPERKM=? WHERE NIC=?";


            Connection conn = null;
            PreparedStatement stmt;

            try{
                DBHandler handler = new DBHandler();
                conn = handler.getConnection();
                stmt = conn.prepareStatement(query);
                stmt.setString(1,firstName);
                stmt.setString(2,lastName);
                stmt.setString(3,contactnum);
                stmt.setFloat(4,pay);
                stmt.setString(5,SelecteId);
                int results = stmt.executeUpdate();
                System.out.println("Result of Update: "+results);

                DashboardController.getInstance().createPage("/FXML/Driver.fxml");
                update.getScene().getWindow().hide();

            }catch(SQLException e){
                System.out.println("ERROR OCCURED");
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
        DriverModel selectedDriver = DriverController.nowSelected;
        SelecteId = selectedDriver.getNic();
        fname.setText(selectedDriver.getFname());
        lname.setText(selectedDriver.getFname());
        contact.setText(selectedDriver.getContact());
        payperkm.setText(selectedDriver.getPayperkm());
    }
}
