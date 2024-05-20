package model;

import controller.Validator;

public class Association {
    private Teams teams;
    private Season season;

    public Association() {
        this.teams = new Teams();
        this.season = new Season();
    }

    public Teams getTeams() {
        return this.teams;
    }

    public static Validator validator = new Validator();

    public Season getSeason() {
        this.season.currentInit(this.teams.currentTeams());
        return this.season;
    }
}
