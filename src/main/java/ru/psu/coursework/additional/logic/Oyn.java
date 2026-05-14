package ru.psu.coursework.additional.logic;

public class Oyn implements TypeOfWin {
    public String getName() {
        return "Oyn";
    }

    public int getScorePoints() {
        return 1;
    }

    public String getDescription(String player) {
        return "Player " + player + " won!";
    }
}
