package Controllers;

import DBConnection.DBHandler;
import TableModel.ClientModel;
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
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditClientController implements Initializable {
    @FXML
    private JFXButton update;

    @FXML
    private JFXButton close;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField comp_name;

    @FXML
    private JFXTextField location;

    @FXML
    private JFXTextField contact_person;

    @FXML
    private JFXTextField contact_no;

    @FXML
    private JFXTextField std_rate;

    @FXML
    void closeClick(ActionEvent event) {
        close.getScene().getWindow().hide();
    }
    private int SelecteId;
    @FXML
    void updateClick(ActionEvent event) {
        try{

            String cname = comp_name.getText();
            String eml = email.getText();
            String loc = location.getText();
            String cont_person= contact_person.getText();
            String contact = contact_no.getText();
            float rate = Float.parseFloat(std_rate.getText());

            String query = "UPDATE CLIENT SET COMPANY_NAME=?,LOCATION=?,EMAIL=?,CONTACT_PERSON=?,CONTACT=?,STD_RATE=? WHERE ID=?";


            Connection conn = null;
            PreparedStatement stmt;

            try{
                DBHandler handler = new DBHandler();
                conn = handler.getConnection();
                stmt = conn.prepareStatement(query);
                stmt.setString(1,cname);
                stmt.setString(2,loc);
                stmt.setString(3,eml);
                stmt.setString(4,cont_person);
                stmt.setString(5,contact);
                stmt.setFloat(6,rate);
                stmt.setInt(7,SelecteId);
                int results = stmt.executeUpdate();
                System.out.println("Result of Update: "+results);

                DashboardController.getInstance().createPage("/FXML/Client.fxml");
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
    public void initialize(URL loc, ResourceBundle resources) {
        ClientModel selectedClient = ClientController.nowSelected;
        SelecteId = Integer.parseInt(selectedClient.getId());
        email.setText(selectedClient.getEmail());
        comp_name.setText(selectedClient.getComp_name());
        location.setText(selectedClient.getLocation());
        contact_person.setText(selectedClient.getCont_person());
        contact_no.setText(selectedClient.getContact());
        std_rate.setText(selectedClient.getStd_rate());


    }
}
