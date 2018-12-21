package Controllers;

import DBConnection.DBHandler;
import TableModel.TripModel;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TripController implements Initializable {


    @FXML
    private TableView myTable;

    @FXML
    private JFXButton addNewBtn;

    @FXML
    void addClick(ActionEvent event) {
        DashboardController.getInstance().createPage("/FXML/AddTrip.fxml");
    }
    static VehicleModel nowSelected;


    @FXML
    void deleteClick(ActionEvent event) {
        try {
            TripModel selectedModel = (TripModel) myTable.getSelectionModel().getSelectedItem();
            int selectedId = Integer.parseInt(selectedModel.getTrip_id());

            DBHandler handler = new DBHandler();
            Connection conn = null;
            try {
                conn = handler.getConnection();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM TRIP WHERE TRIP_ID=?");
                stmt.setInt(1, selectedId);
                stmt.executeUpdate();
                DashboardController.getInstance().createPage("/FXML/Trip.FXML");
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
        TableColumn trip_id = new TableColumn("TRIP ID");
        TableColumn client = new TableColumn("CLIENT");
        TableColumn driver = new TableColumn("DRIVER");
        TableColumn destination = new TableColumn("DESTINATION");
        TableColumn roundTrip = new TableColumn("ROUND TRIP?");
        TableColumn distance = new TableColumn("DISTANCE");
        TableColumn startDate = new TableColumn("TRIP START");
        TableColumn endDate = new TableColumn("TRIP END");
        TableColumn status = new TableColumn("STATUS");

        myTable.getColumns().addAll(trip_id, client, driver, destination, roundTrip, distance,startDate,endDate,status);
        Connection conn = null;
        DBHandler handler;

        try {
            handler = new DBHandler();
            conn = handler.getConnection();
            ResultSet rs1 = conn.createStatement().executeQuery("SELECT TRIP_ID, START_DATE, END_DATE FROM TRIP");
            int currentTripId = 0;
            LocalDate currentstartDate = null;
            LocalDate currentendDate = null;
            String currentStatus = null;
            ObservableList<TripModel> editableData = FXCollections.observableArrayList();
            while(rs1.next()){
                editableData.add(new TripModel(rs1.getString(1),rs1.getString(2),rs1.getString(3)));
            }

            String query = "UPDATE TRIP SET STATUS=? WHERE TRIP_ID=?";
            PreparedStatement stmt;
            for (TripModel entry:
                 editableData) {
                currentTripId = Integer.parseInt(entry.getTrip_id());
                currentstartDate = LocalDate.parse(entry.getStart_date());
                currentendDate = LocalDate.parse(entry.getEnd_date());
                if(LocalDate.now().isAfter(currentstartDate)&&LocalDate.now().isBefore(currentendDate)){
                    currentStatus="In Progress";
                }
                if(LocalDate.now().isEqual(currentstartDate)||LocalDate.now().isEqual(currentendDate)){
                    currentStatus="In Progress";
                }
                if(LocalDate.now().isBefore(currentstartDate)){
                    currentStatus="Pending";
                }
                if(LocalDate.now().isAfter(currentendDate)){
                    currentStatus="Completed";
                }

                stmt = conn.prepareStatement(query);
                stmt.setString(1,currentStatus);
                stmt.setInt(2,currentTripId);
                stmt.executeUpdate();
            }




            ResultSet rs = conn.createStatement().executeQuery("SELECT*FROM TRIP INNER JOIN DRIVER ON TRIP.DRIVER_NIC=DRIVER.NIC INNER JOIN CLIENT ON TRIP.CLIENT_ID = CLIENT.ID");

            ObservableList<TripModel> data = FXCollections.observableArrayList();

            while(rs.next()){

                data.add(new TripModel(
                        rs.getString("TRIP_ID"),
                        rs.getString("CLIENT_ID"),
                        rs.getString("DRIVER_NIC"),
                        rs.getString("DESTINATION"),
                        rs.getString("ROUND_TRIP"),
                        rs.getString("DISTANCE"),
                        rs.getString("START_DATE"),
                        rs.getString("END_DATE"),
                        rs.getString("STATUS"),
                        rs.getString("F_NAME"),
                        rs.getString("L_NAME"),
                        rs.getString("CONTACT"),
                        rs.getString("PAYPERKM"),
                        rs.getString("ID"),
                        rs.getString("COMPANY_NAME"),
                        rs.getString("STD_RATE")

                ));
            }


            trip_id.setCellValueFactory(new PropertyValueFactory<TripModel, String>("trip_id"));
            client.setCellValueFactory(new PropertyValueFactory<TripModel, String>("clientName"));
            driver.setCellValueFactory(new PropertyValueFactory<TripModel, String>("driverFname"));
            destination.setCellValueFactory(new PropertyValueFactory<TripModel, String>("destination"));
            roundTrip.setCellValueFactory(new PropertyValueFactory<TripModel, String>("round_trip"));
            distance.setCellValueFactory(new PropertyValueFactory<TripModel, String>("distance"));
            startDate.setCellValueFactory(new PropertyValueFactory<TripModel, String>("start_date"));
            endDate.setCellValueFactory(new PropertyValueFactory<TripModel, String>("end_date"));
            status.setCellValueFactory(new PropertyValueFactory<TripModel, String>("status"));


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
