package ru.psu.coursework.additional.models;

import java.io.Serializable;

public class Cell implements Serializable {

    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    private int count;
    private int color;


    public Cell() {
        this.count = 0;
        this.color = EMPTY;
    }


    public boolean isEmpty() {
        return count == 0;
    }

    public void addPiece(int pieceColor){
        if (this.color != EMPTY && this.color != pieceColor)
            throw new IllegalArgumentException("Error!!!");

        this.color = pieceColor;
        this.count++;
    }

    public void removePiece(){
        if (count > 0) {
            count--;
            if (count == 0) color = EMPTY;
        }
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


}
