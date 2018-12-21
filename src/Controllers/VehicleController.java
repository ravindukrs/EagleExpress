package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBConnection.DBHandler;
import TableModel.VehicleModel;
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

public class VehicleController implements Initializable {


    @FXML
    private TableView myTable;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton addNewBtn;

    @FXML
    void addClick(ActionEvent event) {
        DashboardController.getInstance().createPage("/FXML/AddVehicle.fxml");
    }
    static VehicleModel nowSelected;
    @FXML
    void editClick(ActionEvent event) {
        nowSelected = (VehicleModel) myTable.getSelectionModel().getSelectedItem();
        Stage editStage = new Stage();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/EditVehicle.FXML"));
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
            VehicleModel selectedModel = (VehicleModel) myTable.getSelectionModel().getSelectedItem();
            int selectedId = Integer.parseInt(selectedModel.getId());

            DBHandler handler = new DBHandler();
            Connection conn = null;
            try {
                conn = handler.getConnection();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM VEHICLE WHERE VEHICLE_ID=?");
                stmt.setInt(1, selectedId);
                stmt.executeUpdate();
                DashboardController.getInstance().createPage("/FXML/Vehicle.FXML");
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
        TableColumn category = new TableColumn("CATEGORY");
        TableColumn make = new TableColumn("MAKE");
        TableColumn model = new TableColumn("MODEL");
        TableColumn seating = new TableColumn("SEATING");
        TableColumn insurance = new TableColumn("INSURANCE RENEW.");
        TableColumn service = new TableColumn("NEXT SERVICE");
        TableColumn registration = new TableColumn("NEXT REG.");
        TableColumn perkm = new TableColumn("COST/KM");

        myTable.getColumns().addAll(category, make, model, seating, insurance, service,registration,perkm);
        Connection conn = null;
        DBHandler handler;

        try {
            handler = new DBHandler();
            conn = handler.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT CATEGORY,MAKE,MODEL,SEATING,INSURANCE_EXPIARY,NEXT_SERVICE,NEXT_REGISTRATION,PERKM_COST,VEHICLE_ID, DRIVER FROM VEHICLE");

            ObservableList<VehicleModel> data = FXCollections.observableArrayList();

            while(rs.next()){
                data.add(new VehicleModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),rs.getString(9),rs.getString(10)));
            }

            category.setCellValueFactory(new PropertyValueFactory<VehicleModel, String>("category"));
            make.setCellValueFactory(new PropertyValueFactory<VehicleModel, String>("make"));
            model.setCellValueFactory(new PropertyValueFactory<VehicleModel, String>("model"));
            seating.setCellValueFactory(new PropertyValueFactory<VehicleModel, String>("seating"));
            insurance.setCellValueFactory(new PropertyValueFactory<VehicleModel, String>("insurance"));
            service.setCellValueFactory(new PropertyValueFactory<VehicleModel, String>("service"));
            registration.setCellValueFactory(new PropertyValueFactory<VehicleModel, String>("registration"));
            perkm.setCellValueFactory(new PropertyValueFactory<VehicleModel, String>("perkm"));


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
