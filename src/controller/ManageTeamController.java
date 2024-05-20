package controller;
import au.edu.uts.ap.javafx.ViewLoader;
import au.edu.uts.ap.javafx.Controller;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Association;
import model.Player;
import model.Team;
import model.Teams;


public class ManageTeamController extends Controller<Teams>{

    @FXML
    private TableView<Player> tableView;

    @FXML
    private TextField teamTF;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TableColumn<Team, String> name;

    @FXML
    private TableColumn<Team, Double> credit;

    @FXML
    private TableColumn<Team, Integer> age;

    @FXML
    private TableColumn<Team, Integer> No; 

    public static String selected;

    private String teamName;

    public static Player selectedPlayer;

    public Teams getTeams(){
        return model;
    }

    @FXML
    public void initialize() {
        teamName = TeamsController.teamName;
        ObservableList<Player> listOfPlayers = getTeams().getTeam(teamName).getPlayers().getPlayersList();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        No.setCellValueFactory(new PropertyValueFactory<>("No"));
        tableView.setItems(listOfPlayers);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isItemSelected = (newValue != null);
            addBtn.setDisable(isItemSelected);
            updateBtn.setDisable(!isItemSelected);
            deleteBtn.setDisable(!isItemSelected);
            selected = selectedItem();
            selectedPlayer = tableView.getSelectionModel().getSelectedItem();
        });

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                deselect();
            }
        });

        teamTF.setText(teamName);
    }

    public String selectedItem() {
        Player selectedPlayer = tableView.getSelectionModel().getSelectedItem();
        if (selectedPlayer == null) {
            return selected;
        }
        return selectedPlayer.getName();
    }

    public void deselect() {
        updateBtn.setDisable(true);
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
            stage.getIcons().add(new Image("/view/edit.png"));
            ViewLoader.showStage(getTeams(), "/view/PlayerAddView.fxml", "Adding New Player", stage);
        } catch (IOException ex) {
            Logger.getLogger(ManageTeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void update() {
        try {
            String playerName = selectedItem();
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/edit.png"));
            stage.setResizable(false);
            ViewLoader.showStage(getTeams(), "/view/PlayerUpdateView.fxml", "Updating Player: " + playerName, stage);
        } catch (IOException ex) {
            Logger.getLogger(ManageTeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void delete() {
        getTeams().getTeam(teamName).getPlayers().removePlayer(getTeams().getTeam(teamName).getPlayers().getPlayer(selectedItem()));
        deselect();
    }
    @FXML
    public void save() {
        getTeams().getTeam(teamName).setName(teamTF.getText());
        stage.close();
    }
}
