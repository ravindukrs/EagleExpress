package Controllers;

import DBConnection.DBHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;




public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    private Connection conn;
    private DBHandler handler;
    private PreparedStatement pst;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = new DBHandler();
        username.setStyle("-fx-text-inner-color:#a0a2ab;");
        password.setStyle("-fx-text-inner-color:#a0a2ab;");
    }


    @FXML
    public void logon() {

        conn = handler.getConnection();
        String query1 = "SELECT * FROM USER WHERE USERNAME=? AND PASSWORD=?";

        try{
            pst = conn.prepareStatement(query1);
            pst.setString(1,username.getText());
            pst.setString(2,password.getText());
            ResultSet rs = pst.executeQuery();

            //Validate resultset
            int count = 0;
            while(rs.next()){
                count++;
            }
            if(count==1){
                login.getScene().getWindow().hide();
                Stage dashboard = new Stage();
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("/FXML/Dashboard.FXML"));
                    Scene scene = new Scene(root);
                    dashboard.setScene(scene);
                    dashboard.show();
                    dashboard.setResizable(false);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Login Failed!");
                alert.setContentText("Invalid Username & Password Combination!");
                alert.show();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                handler.closeConnection();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}
