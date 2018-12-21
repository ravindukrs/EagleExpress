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

public class AddClientController implements Initializable {
    @FXML
    private Spinner capacitySpinner;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton back;

    @FXML
    private JFXTextField company;

    @FXML
    private JFXTextField location;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField cont_person;

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXTextField std_rate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void addClick(ActionEvent event) {
        String cname = company.getText();
        String eml = email.getText();
        String loc = location.getText();
        String person= cont_person.getText();
        String cont = contact.getText();
        float rate = Float.parseFloat(std_rate.getText());

        String query = " INSERT INTO CLIENT(COMPANY_NAME,LOCATION,EMAIL,CONTACT_PERSON,CONTACT,STD_RATE)VALUES(?,?,?,?,?,?)";


        Connection conn = null;
        PreparedStatement stmt;

        try{
            DBHandler handler = new DBHandler();
            conn = handler.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1,cname);
            stmt.setString(3,eml);
            stmt.setString(2,loc);
            stmt.setString(4,person);
            stmt.setString(5,cont);
            stmt.setFloat(6,rate);
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
            DashboardController.getInstance().createPage("/FXML/Client.fxml");

        }


    }

    @FXML
    void backClick(ActionEvent event) {
        DashboardController.getInstance().createPage("/FXML/Client.fxml");
    }
}
