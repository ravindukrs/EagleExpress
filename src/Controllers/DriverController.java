package Controllers;

import DBConnection.DBHandler;
import TableModel.DriverModel;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DriverController implements Initializable {


    @FXML
    private TableView myTable;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton addNewBtn;

    @FXML
    void addClick(ActionEvent event) {
        DashboardController.getInstance().createPage("/FXML/AddDriver.fxml");
    }

    static DriverModel nowSelected;
    @FXML
    void editClick(ActionEvent event) {
        nowSelected = (DriverModel) myTable.getSelectionModel().getSelectedItem();
        Stage editStage = new Stage();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/EditDriver.FXML"));
            Scene scene = new Scene(root);
            editStage.setScene(scene);
            editStage.show();
            editStage.setResizable(false);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    void deleteClick(ActionEvent event) {
        try {
            DriverModel selectedModel = (DriverModel) myTable.getSelectionModel().getSelectedItem();
            String selectedNIC = selectedModel.getNic();

            DBHandler handler = new DBHandler();
            Connection conn = null;
            try {
                conn = handler.getConnection();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM DRIVER WHERE NIC=?");
                stmt.setString(1, selectedNIC);
                stmt.executeUpdate();
                DashboardController.getInstance().createPage("/FXML/Driver.FXML");
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }

            }
        }catch(Exception e){
            System.out.println("None Selected");
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        renderTable();
    }

    void renderTable(){
        TableColumn nic = new TableColumn("NIC");
        TableColumn fname = new TableColumn("FIRST NAME");
        TableColumn lname = new TableColumn("LAST NAME");
        TableColumn contact = new TableColumn("CONTACT");
        TableColumn payperkm = new TableColumn("PAY PER KM");

        myTable.getColumns().addAll(nic, fname, lname, contact, payperkm);
        Connection conn = null;
        DBHandler handler;

        try {
            handler = new DBHandler();
            conn = handler.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM DRIVER");

            ObservableList<DriverModel> data = FXCollections.observableArrayList();

            while(rs.next()){
                data.add(new DriverModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            nic.setCellValueFactory(new PropertyValueFactory<DriverModel, String>("nic"));
            fname.setCellValueFactory(new PropertyValueFactory<DriverModel, String>("fname"));
            lname.setCellValueFactory(new PropertyValueFactory<DriverModel, String>("lname"));
            contact.setCellValueFactory(new PropertyValueFactory<DriverModel, String>("contact"));
            payperkm.setCellValueFactory(new PropertyValueFactory<DriverModel, String>("payperkm"));

            myTable.setItems(data);


        }catch(Exception E){
            System.out.println("Exception");
        }finally{
            try{
                conn.close();
            }catch(SQLException e){
                System.out.println(e);
            }

        }
    }

}
