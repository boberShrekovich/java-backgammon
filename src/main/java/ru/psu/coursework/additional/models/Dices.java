package ru.psu.coursework.additional.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dices implements Serializable {
    private int diceOne;
    private int diceTwo;
    private int diceOneUses;
    private int diceTwoUses;

    private Random generator;

    public Dices() {
        generator = new Random();
    }

    public boolean isDouble = false;

    public void roll(){
        diceOne = generator.nextInt(6) + 1;
        diceTwo = generator.nextInt(6) + 1;

        if (diceOne == diceTwo) {
            diceOneUses = diceTwoUses = 2;
            isDouble = true;
        }
        else
            diceOneUses = diceTwoUses = 1;

    }

    public void takeDice(int number){
        if (diceOneUses > 0 && diceOne == number)
            diceOneUses--;
        else if (diceTwoUses > 0 && diceTwo == number)
            diceTwoUses--;
        else
            throw new IllegalArgumentException("Invalid dice!!!");
    }

    public int takeDiceOne(){
        if (diceOneUses == 0) return 0;

        diceOneUses--;

        return diceOne;
    }

    public int takeDiceTwo(){
        if (diceTwoUses == 0) return 0;

        diceTwoUses--;

        return diceTwo;
    }

    public boolean isRolled(){
        return diceOneUses > 0 || diceTwoUses > 0;
    }

    public int getDiceOne(){
        return diceOne;
    }

    public int getDiceTwo(){
        return diceTwo;
    }

    public List<Integer> getAvailableValues() {
        List<Integer> available= new ArrayList<>();

        for (int i = 0; i < diceOneUses; i++)
            available.add(diceOne);

        for (int i = 0; i < diceTwoUses; i++)
            available.add(diceTwo);

        return available;
    }

    public void clear() {
        this.diceOne = 0;
        this.diceTwo = 0;
        this.diceOneUses = 0;
        this.diceTwoUses = 0;
        this.isDouble = false;
    }
}
