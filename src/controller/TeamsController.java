package controller;
import au.edu.uts.ap.javafx.ViewLoader;
import au.edu.uts.ap.javafx.Controller;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.*;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Team;
import model.Teams;
public class TeamsController extends Controller<Teams>{
    @FXML
    private TableView<Team> tableView;

    public static String teamName;

    @FXML
    private Button addBtn;

    @FXML
    private Button manageBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private TableColumn<Team, String> name;

    @FXML
    private TableColumn<Team, Integer> players;

    @FXML
    private TableColumn<Team, Double> credit;

    @FXML
    private TableColumn<Team, Double> age; 

    public static String selected;
    
    public Teams getTeams(){
        return model;
    }
    
    @FXML
    public void initialize() {
        ObservableList<Team> listOfTeams = getTeams().currentTeams();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        players.setCellValueFactory(count -> count.getValue().countPlayerProperty().asObject());
        credit.setCellValueFactory(avgCredit -> avgCredit.getValue().countAvgCreditProperty().asObject());
        age.setCellValueFactory(avgAge -> avgAge.getValue().countAvgAgeProperty().asObject());
        tableView.setItems(listOfTeams);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isItemSelected = (newValue != null);
            addBtn.setDisable(isItemSelected);
            manageBtn.setDisable(!isItemSelected);
            deleteBtn.setDisable(!isItemSelected);
            selected = selectedItem();
        });

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                deselect();
            }
        });
    }

    public String selectedItem() {
        Team selectedTeam = tableView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            teamName = selectedTeam.getName();
            return teamName;
        } else {
            return null;
        }
    }
    

    public void deselect() {
        manageBtn.setDisable(true);
        deleteBtn.setDisable(true);
        addBtn.setDisable(false);
        tableView.getSelectionModel().select(null);
    }

    @FXML
    public void add() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));
            ViewLoader.showStage(getTeams(), "/view/AddTeam.fxml", "Adding New Team", stage);
        } catch (IOException ex) {
            Logger.getLogger(TeamsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void manage() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));
            ViewLoader.showStage(getTeams(), "/view/ManageTeamView.fxml", "Managing Team: " + selected, stage);
            deselect();
        } catch (IOException ex) {
            Logger.getLogger(TeamsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void delete() {
        getTeams().remove(getTeams().getTeam(selectedItem()));
        deselect();
    }
    @FXML
    public void close() {
        stage.close();
    }
}

