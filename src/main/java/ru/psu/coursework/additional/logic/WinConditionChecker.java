package ru.psu.coursework.additional.logic;

import ru.psu.coursework.additional.models.*;

public class WinConditionChecker {

    public WinConditionChecker() {

    }

    private int countTotalPieces(Board board, int playersColor) {
        int total = 0;

        for (int i = 0; i < 8; i++) {
            Cell cell = board.getCell(i);
            if (cell.getColor() == playersColor) {
                total += cell.getCount();
            }
        }

        return total;
    }

    public boolean isGameOver(Board board) {
        return countTotalPieces(board, Cell.WHITE) == 0 || countTotalPieces(board, Cell.BLACK) == 0;
    }

    public int getWinnerColor(Board board) {
        if (countTotalPieces(board, Cell.WHITE) == 0) return Cell.WHITE;

        if (countTotalPieces(board, Cell.BLACK) == 0) return Cell.BLACK;

        return Cell.EMPTY;
    }


    public TypeOfWin checkWin(Board board, int winnersColor) {
        int losersColor = (winnersColor == Cell.WHITE) ? Cell.BLACK : Cell.WHITE;
        int loserPiecesLeft = countTotalPieces(board, losersColor);

        if (loserPiecesLeft == 15) return new Mars();

        return new Oyn();
    }
}
