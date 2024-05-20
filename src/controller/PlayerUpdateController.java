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
import model.Players;
import model.Team;
import model.Teams;

public class PlayerUpdateController extends Controller<Teams>{
   
    @FXML 
    private TextField nameTF;

    @FXML 
    private TextField creditTF;

    @FXML 
    private TextField ageTF;

    @FXML 
    private TextField NoTF;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button closeBtn;

    String playerName = ManageTeamController.selected;
    String teamName = TeamsController.teamName;
    Player player = ManageTeamController.selectedPlayer;

    public Teams getTeams(){
        return model;
    }

    @FXML
    public void initialize() {
        nameTF.setText(playerName);
        creditTF.setText(player.getCredit().toString());
        ageTF.setText(player.getAge().toString());
        NoTF.setText(player.getNo().toString());
    }

    @FXML 
    private void add() {
        String newName = nameTF.getText();
        String newCredit = creditTF.getText();
        String newAge = ageTF.getText();
        String newNo = NoTF.getText();
        boolean b = Association.validator.isValid(newName, newCredit, newAge, newNo);
        Association.validator.generateErrors(newName, newCredit, newAge, newNo);
        if (b) {
            Player player = new Player(newName, Double.parseDouble(newCredit), Integer.parseInt(newAge), Integer.parseInt(newNo));
            getTeams().getTeam(teamName).getPlayers().addPlayer(player);
            close();
        } else {
            inputErrors();
        }
    }

    private void inputErrors() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));
            stage.setResizable(false);
            ViewLoader.showStage(getTeams().getTeam(teamName), "/view/error.fxml", "Input Errors", stage);
        } catch (IOException ex) {
            Logger.getLogger(AssociationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML 
    private void update() {
        String newName = nameTF.getText();
        String newCredit = creditTF.getText();
        String newAge = ageTF.getText();
        String newNo = NoTF.getText();
        boolean b = Association.validator.isValid(newName, newCredit, newAge, newNo);
        Association.validator.generateErrors(newName, newCredit, newAge, newNo);
        if (b) {
            int i = 0;
            
            for (Player p : getTeams().getTeam(teamName).getCurrentPlayers()) {
                if (p == player) {
                    break;
                }
                i++;
            }
            getTeams().getTeam(teamName).getCurrentPlayers().set(i, new Player(newName, Double.valueOf(newCredit), Integer.valueOf(newAge), Integer.valueOf(newNo)));
            player.setName(newName);
            player.setCredit(Double.valueOf(newCredit));
            player.setAge(Integer.valueOf(newAge));
            player.setNo(Integer.valueOf(newNo));
            close();
        } else {
            inputErrors();
        }
    }

    @FXML 
    private void close() {
        stage.close();
    }

    
}
