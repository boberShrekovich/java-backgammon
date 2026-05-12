package ru.psu.coursework.additional.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final int COUNT_OF_CELLS = 24;

    private final List<Cell> cells;

    public Board() {
        cells = new ArrayList<>(COUNT_OF_CELLS);

        for (int i = 0; i < COUNT_OF_CELLS; i++) {
            cells.add(new Cell());
        }

        setupPosition();
    }

    public void setupPosition(){

        cells.get(0).setCount(15);
        cells.get(0).setColor(Cell.WHITE);

        cells.get(12).setCount(15);
        cells.get(12).setColor(Cell.BLACK);

    }

    public List<Cell> getCells() {
        return cells;
    }

    public Cell getCell(int id){
        if (id < 0 || id >= 24) {
            return null;
        }

        return cells.get(id);
    }

    public void movePieces(int from, int to) {
        Cell fromCell = cells.get(from);
        Cell toCell = cells.get(to);

        int moverColor = fromCell.getColor();

        fromCell.removePiece();
        toCell.addPiece(moverColor);


    }
}
