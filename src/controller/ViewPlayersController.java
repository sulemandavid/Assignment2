package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Player;
import model.Teams;

public class ViewPlayersController extends Controller<Teams> {

    @FXML
    private TableView<Player> tableView;

    @FXML
    private TableColumn<Player, String> team;

    @FXML
    private TableColumn<Player, String> name;

    @FXML
    private TableColumn<Player, Double> credit;

    @FXML
    private TableColumn<Player, Integer> age;

    @FXML
    private TableColumn<Player, Integer> No; 

    @FXML
    private TableColumn<Player, String> level;

    @FXML
    private TextField levelTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField ageLowerTF;

    @FXML
    private TextField ageHigherTF;

    public Teams getTeams(){
        return model;
    }

    @FXML
    public void initialize() {
        ObservableList<Player> listOfPlayers = getTeams().allPlayersList();
        team.setCellValueFactory(new PropertyValueFactory<>("team"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        No.setCellValueFactory(new PropertyValueFactory<>("No"));
        level.setCellValueFactory(new PropertyValueFactory<>("level"));
        tableView.setItems(listOfPlayers);

        ObservableList<Player> filteredList = FXCollections.observableArrayList();
        levelTF.textProperty().addListener((observable, oldValue, newValue) -> filterList(listOfPlayers, filteredList));
        nameTF.textProperty().addListener((observable, oldValue, newValue) -> filterList(listOfPlayers, filteredList));
        ageLowerTF.textProperty().addListener((observable, oldValue, newValue) -> filterList(listOfPlayers, filteredList));
        ageHigherTF.textProperty().addListener((observable, oldValue, newValue) -> filterList(listOfPlayers, filteredList));
    }

    private void filterList(ObservableList<Player> listOfPlayers, ObservableList<Player> filteredList) {
        filteredList.clear();
        String levelInput = levelTF.getText().toLowerCase();
        String nameInput = nameTF.getText().toLowerCase();
        int ageLower = 0;
        int ageHigher = 9999999;

        if (!ageLowerTF.getText().isEmpty()) {
            ageLower = Integer.parseInt(ageLowerTF.getText());
        }
        if (!ageHigherTF.getText().isEmpty()) {
            ageHigher = Integer.parseInt(ageHigherTF.getText());
        }
        
        for (Player player : listOfPlayers) {
            boolean levelFilter = levelInput.isEmpty() || player.getLevel().toLowerCase().contains(levelInput);
            boolean nameFilter = nameInput.isEmpty() || player.getName().toLowerCase().contains(nameInput);
            boolean ageFilter = player.getAge() >= ageLower && player.getAge() <= ageHigher;
            if (levelFilter && nameFilter && ageFilter) {
                filteredList.add(player);
            }
        }
        tableView.setItems(filteredList);
    }

    @FXML
    public void close() {
        stage.close();
    }
}

