package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Record;
import model.Season;

public class RecordController extends Controller<Season>{

    @FXML
    private TableView<Record> tableView;

    @FXML
    private TableColumn<Record, Integer> round;

    @FXML
    private TableColumn<Record, Integer> gameNumber;
    
    @FXML
    private TableColumn<Record, String> winTeam;

    @FXML
    private TableColumn<Record, String> loseTeam;

    public Season getSeason() {
        return model;
    }

    @FXML
    public void initialize() {

        tableView.setItems(getSeason().record());
    }

    @FXML public void close() {
        stage.close();
    }
}







