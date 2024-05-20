package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Association;
import model.Team;
import model.Teams;

public class ErrorController extends Controller<Team> {
    @FXML
    private Button okBtn;

    @FXML
    private Text errorTxt;

    @FXML
    public void initialize() {
        String error = "";
        for (String e : Association.validator.errors()) {
            error += e;
        }
        errorTxt.setText(error);
        Association.validator.clear();
    }
    private Team getTeam() {
        return model;
    }

    @FXML
    public void close() {
        stage.close();
    }
}