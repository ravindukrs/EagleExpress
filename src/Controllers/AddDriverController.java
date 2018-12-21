package Controllers;

import DBConnection.DBHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddDriverController implements Initializable {
    @FXML
    private JFXButton add;

    @FXML
    private JFXButton back;

    @FXML
    private JFXTextField nic;

    @FXML
    private JFXTextField lname;

    @FXML
    private JFXTextField fname;

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXTextField payperkm;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void addClick(ActionEvent event) {
        String idNum = nic.getText();
        String firstName = fname.getText();
        String lastName= lname.getText();
        String contactNum= contact.getText();
        float pay = Float.parseFloat(payperkm.getText());

        String query = "INSERT INTO DRIVER(NIC,F_NAME,L_NAME,CONTACT,PAYPERKM)VALUES(?,?,?,?,?)";


        Connection conn = null;
        PreparedStatement stmt;

        try{
            DBHandler handler = new DBHandler();
            conn = handler.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1,idNum);
            stmt.setString(2,firstName);
            stmt.setString(3,lastName);
            stmt.setString(4,contactNum);
            stmt.setFloat(5,pay);
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
            DashboardController.getInstance().createPage("/FXML/Driver.fxml");

        }


    }

    @FXML
    void backClick(ActionEvent event) {
        DashboardController.getInstance().createPage("/FXML/Driver.fxml");
    }
}
