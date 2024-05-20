package controller;
import au.edu.uts.ap.javafx.Controller;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Game;
import model.Season;
import model.Team;

public class TeamsRoundController extends Controller<Season>{

    @FXML
    private Label roundText;

    @FXML
    private ListView<Team> listView;

    @FXML
    private Button addButton;

    @FXML
    private TableView<Game> tableView;

    @FXML
    private TableColumn<Game, Integer> game;

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

        ObservableList<Team> listOfTeams =  getSeason().getCurrentTeams();
        game.setCellValueFactory(game -> game.getValue().termProperty().asObject());
        team1.setCellValueFactory(team1 -> team1.getValue().team1());
        team2.setCellValueFactory(team2 -> team2.getValue().team2());
        listView.setItems(listOfTeams);

        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                deselect();
            }
        });

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        listView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<?> change) -> {
            if (listView.getSelectionModel().getSelectedItems().size() % 2 == 0) {
                addButton.setDisable(false);
            } else if (listView.getItems().size() == 0) {
                addButton.setDisable(true);
            } else {
                addButton.setDisable(true);
            }
        });

        tableView.setItems(getSeason().getCurrentSchedule());
    }

    @FXML
    public void add() {
        getSeason().addTeams(listView.getSelectionModel().getSelectedItems());
        listView.setItems(getSeason().getCurrentTeams());
        deselect();
    }
    
    public void deselect() {
        addButton.setDisable(true);
        listView.getSelectionModel().select(null);
    }

    @FXML public void close() {
        stage.close();
    }
}
