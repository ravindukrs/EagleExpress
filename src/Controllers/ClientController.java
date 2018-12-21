package Controllers;

import DBConnection.DBHandler;
import TableModel.ClientModel;
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

public class ClientController implements Initializable {


    @FXML
    private TableView myTable;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton addNewBtn;

    @FXML
    void addClick(ActionEvent event) {
        DashboardController.getInstance().createPage("/FXML/AddClient.fxml");
    }

    static ClientModel nowSelected;
    @FXML
    void editClick(ActionEvent event) {
        nowSelected = (ClientModel) myTable.getSelectionModel().getSelectedItem();
        Stage editStage = new Stage();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/EditClient.FXML"));
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
            ClientModel selectedModel = (ClientModel) myTable.getSelectionModel().getSelectedItem();
            String selectedId = selectedModel.getId();

            DBHandler handler = new DBHandler();
            Connection conn = null;
            try {
                conn = handler.getConnection();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM CLIENT WHERE ID=?");
                stmt.setString(1, selectedId);
                stmt.executeUpdate();
                DashboardController.getInstance().createPage("/FXML/Client.FXML");
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
        TableColumn comp_name = new TableColumn("COMPANY NAME");
        TableColumn location = new TableColumn("LOCATION");
        TableColumn email = new TableColumn("EMAIL");
        TableColumn cont_person = new TableColumn("CONTACT PERSON");
        TableColumn contact = new TableColumn("CONTACT");
        TableColumn std_rate = new TableColumn("RATE/KM STD.");

        myTable.getColumns().addAll(comp_name,location,email,cont_person,contact,std_rate);
        Connection conn = null;
        DBHandler handler;

        try {
            handler = new DBHandler();
            conn = handler.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM CLIENT");

            ObservableList<ClientModel> data = FXCollections.observableArrayList();

            while(rs.next()){
                data.add(new ClientModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7)));
            }

            comp_name.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("comp_name"));
            location.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("location"));
            email.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("email"));
            contact.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("contact"));
            cont_person.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("cont_person"));
            contact.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("contact"));
            std_rate.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("std_rate"));

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
