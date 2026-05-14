package ru.psu.coursework.additional.logic;

public class Mars implements TypeOfWin {
    public String getName() {
        return "Mars";
    }

    public int getScorePoints() {
        return 2;
    }

    public String getDescription(String player) {
        return "Player " + player + " smashed the opponent!!!!!!";
    }
}
