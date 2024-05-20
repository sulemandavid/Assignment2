package controller;
import au.edu.uts.ap.javafx.Controller;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Game;
import model.Season;

public class CurrentRoundTeamsController extends Controller<Season> {

    @FXML
    private Label roundText;

    @FXML
    private TableView<Game> tableView;

    @FXML
    private TableColumn<Game, String> team1;

    @FXML
    private TableColumn<Game, String> team2;
    
    public Season getSeason() {
        return model;
    }

    @FXML
    public void initialize() {
        roundText.setText("Round: " + (getSeason().round() + 1));

        ObservableList<Game> listOfGames =  getSeason().getCurrentSchedule();
        team1.setCellValueFactory(team1 -> team1.getValue().team1());
        team2.setCellValueFactory(team2 -> team2.getValue().team2());
        tableView.setItems(listOfGames);
    }

    @FXML public void close() {
        stage.close();
    }
}







