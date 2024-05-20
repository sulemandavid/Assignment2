package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Association;
import model.Team;
import model.Teams;

public class AddTeamController extends Controller<Teams> {

    @FXML
    private Button addBtn;

    @FXML
    private TextField username;

    public Teams getTeams() {
        return model;
    }
    
    @FXML
    public void initialize() {
        addBtn.setDisable(username.getText().length()>0);
    }

    @FXML
    public void add() {
        String name = username.getText();
        if (getTeams().hasTeam(name)) {
            error();
        }
        else {
            getTeams().addTeam(new Team(name));
            stage.close();
        }
    }

    private void error() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/error.png"));
            Association.validator.addError(username.getText() + " Team already exists");
            ViewLoader.showStage(getTeam(), "/view/error.fxml", "Error!", stage);
        } catch (IOException ex) {
            Logger.getLogger(AddTeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Team getTeam() {
        return model.getTeam(username.getText());
    }
    
}
