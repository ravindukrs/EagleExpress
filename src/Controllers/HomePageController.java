package Controllers;

import DBConnection.DBHandler;
import MiniModels.RevenueModel;
import TableModel.ClientModel;
import TableModel.TripModel;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

public class HomePageController implements Initializable {


    @FXML
    private Label inProgess;

    @FXML
    private Label endTour;

    @FXML
    private Label startTour;

    @FXML
    private Label dueTours;

    @FXML
    private Label serviceOverdue;

    @FXML
    private Label regOverDue;

    @FXML
    private Label insuranceOverdue;

    @FXML
    private Label revenueText;

    @FXML
    private Label payableDriverSal;

    @FXML
    private Label monthlyProfit;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Connection conn = null;
        DBHandler handler;

        try {
            handler = new DBHandler();
            conn = handler.getConnection();


            ResultSet inProgessCount = conn.createStatement().executeQuery("SELECT COUNT(TRIP_ID) FROM TRIP WHERE STATUS='In Progress'");
            inProgessCount.next();
            inProgess.setText(inProgessCount.getString(1));

            ResultSet startTodayCount = conn.createStatement().executeQuery("SELECT COUNT(TRIP_ID) FROM TRIP WHERE START_DATE=DATE(NOW())");
            startTodayCount.next();
            startTour.setText(startTodayCount.getString(1));

            ResultSet endDayCount = conn.createStatement().executeQuery("SELECT COUNT(TRIP_ID) FROM TRIP WHERE END_DATE=DATE(NOW())");
            endDayCount.next();
            endTour.setText(endDayCount.getString(1));

            ResultSet dueTourCount = conn.createStatement().executeQuery("SELECT COUNT(TRIP_ID) FROM TRIP WHERE MONTH(START_DATE)<=MONTH(NOW())+1 AND STATUS='Pending'");
            dueTourCount.next();
            dueTours.setText(endDayCount.getString(1));

            ResultSet serviceOverdueCount = conn.createStatement().executeQuery("SELECT COUNT(VEHICLE_ID) FROM VEHICLE WHERE MONTH(NEXT_SERVICE)<=MONTH(NOW())+1");
            serviceOverdueCount.next();
            serviceOverdue.setText(serviceOverdueCount.getString(1));

            ResultSet regOverDueCount = conn.createStatement().executeQuery("SELECT COUNT(VEHICLE_ID) FROM VEHICLE WHERE MONTH(NEXT_SERVICE)<=MONTH(NOW())+1");
            regOverDueCount.next();
            regOverDue.setText(serviceOverdueCount.getString(1));

            ResultSet insOverdue = conn.createStatement().executeQuery("SELECT COUNT(VEHICLE_ID) FROM VEHICLE WHERE MONTH(NEXT_SERVICE)<=MONTH(NOW())+1");
            insOverdue.next();
            insuranceOverdue.setText(insOverdue.getString(1));

            //ObservableList<RevenueModel> revenueData = FXCollections.observableArrayList();
//            ResultSet revenue = conn.createStatement().executeQuery("SELECT DISTANCE, STD_RATE FROM TRIP INNER JOIN CLIENT ON CLIENT.ID = TRIP.CLIENT_ID");
//            float sum=10;
//            revenue.next();
//            while(revenue.next()){
//                //revenueData.add(new RevenueModel(revenue.getString(1),revenue.getString(2)));
////                System.out.println(revenue.getString(1));
////                sum+=Float.parseFloat(revenue.getString(1))*Float.parseFloat(revenue.getString(2));
////                revenue = revenue.next();
////            }
//
////            for (RevenueModel model: revenueData
////                 ) {
////                sum+=Float.parseFloat(model.getDistance())*Float.parseFloat(model.getStd_rate());
////            }
//            revenueText.setText(String.valueOf(sum));
//

            //ResultSet tripDataView = conn.createStatement().executeQuery("SELECT FROM TRIP INNER JOIN DRIVER ON TRIP.DRIVER_NIC=DRIVER.NIC INNER JOIN CLIENT ON TRIP.CLIENT_ID = CLIENT.ID");
            //ObservableList<TripModel> tripData = FXCollections.observableArrayList();





        }catch(Exception E){
            System.out.println("Exception");
            System.out.println(E);
        }finally{
            try{
                conn.close();
            }catch(SQLException e){
                System.out.println(e);
            }

        }
    }



}
