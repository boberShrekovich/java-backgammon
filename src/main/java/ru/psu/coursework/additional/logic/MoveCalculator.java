package ru.psu.coursework.additional.logic;

import ru.psu.coursework.additional.models.*;

import java.util.ArrayList;
import java.util.List;

public class MoveCalculator {

    public MoveCalculator() {

    }

    public List<Integer> getPossibleMoves(Board board, int from, int playersColor, boolean headMoveDone,
                                 boolean isFirstGameMove, boolean isDouble, int headPiecesTaken, List<Integer> availableDices) {

        List<Integer> possibleMoves = new ArrayList<Integer>();
        MoveValidator validator = new MoveValidator();


        for (int dice : availableDices){
            int to = (from + dice) % 24;

            if (validator.canMove(board, from, to, playersColor, headMoveDone, dice,
                    isFirstGameMove, isDouble, headPiecesTaken)){
                if (!possibleMoves.contains(to))
                    possibleMoves.add(to);
            }

        }

        //выброс с доски
        for (int dice : availableDices){
            if (validator.canRemoveFromBoard(board, playersColor, from, dice)) {
                if (!possibleMoves.contains(-1))
                    possibleMoves.add(-1); //флаг на выход
            }
        }

        return possibleMoves;

    }

    public List<Integer> getMovablePieces(Board board, int playersColor, boolean headMoveDone, boolean isFirstGameMove,
                                          boolean isDouble, int headPiecesTaken, List<Integer> availableDices) {

        List<Integer> movablePositions = new ArrayList<Integer>();
        MoveValidator validator = new MoveValidator();

        for (int i = 0; i < 24; i++){
            Cell cell = board.getCell(i);

            if (!cell.isEmpty() && cell.getColor() == playersColor) {
                List<Integer> targets = getPossibleMoves(board, i, playersColor, headMoveDone, isFirstGameMove, isDouble, headPiecesTaken, availableDices);

                if (!targets.isEmpty())
                    movablePositions.add(i);
            }
        }

        return movablePositions;
    }

    public boolean hasAnyValidMoves(Board board, int playersColor, boolean headMoveDone, boolean isFirstGameMove,
                                    boolean isDouble, int headPiecesTaken, List<Integer> availableDices) {

        List<Integer> movablePieces = getMovablePieces(board, playersColor, headMoveDone, isFirstGameMove, isDouble, headPiecesTaken, availableDices);

        if (movablePieces.isEmpty()) return false;

        return true;
    }

}
