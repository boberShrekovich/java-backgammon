package ru.psu.coursework.additional.logic;

import ru.psu.coursework.additional.models.*;

public class MoveValidator {

    public MoveValidator() {

    }


    public boolean canMove(Board board, int from, int to, int playersColor, boolean headMoveDone, int diceValue,
                           boolean isFirstGameMove, boolean isDouble, int headPiecesTaken) {
        if (from < 0 || to < 0 || from >= 24 || to >= 24) return false;

        if (!isDiceValueMatching(from, to, diceValue, playersColor)) return false;

        Cell fromCell = board.getCell(from);
        Cell toCell = board.getCell(to);

        if (fromCell.isEmpty() || fromCell.getColor() != playersColor) return false;

        if (!toCell.isEmpty() && toCell.getColor() != playersColor) return false;

        if (isHeadPosition(from, playersColor) && headMoveDone) {
            if (isFirstGameMove){
                boolean isBlockDouble = (diceValue == 3 || diceValue == 4 || diceValue == 6);

                if (isDouble && isBlockDouble) {
                    if (headPiecesTaken < 2) return true;
                }
            }

            return false;
        }


        return true;
    }

    public void move(Board board, int from, int to) {
        board.movePieces(from, to);
    }

    private boolean isHeadPosition(int position, int playersColor) {
        if (playersColor == Cell.WHITE && position == 0) return true;
        if (playersColor == Cell.BLACK && position == 12) return true;

        return false;
    }

    private boolean isDiceValueMatching(int from, int to, int diceValue, int playersColor) {
        int distance;

        if (playersColor == Cell.WHITE)
            distance = to - from;

         else if (playersColor == Cell.BLACK) {
            if (to >= from)
                distance = to - from;
            else
                distance = (24 - from) + to;

        } else
            return false;

        return distance == diceValue;
    }

    private boolean isBlock(Board board, int from, int to, int playersColor) {
        Cell fromCell = board.getCell(from);
        Cell toCell = board.getCell(to);

        fromCell.removePiece();
        toCell.addPiece(playersColor);

        boolean hasSixBlock = false;
        int blockStartID = -1;

        for (int i = 0; i < 24; i++) {
            int continuousPieces = 0;
            for (int j = 0; j < 6; j++) {
                int checkID = (i + j) % 24;
                if (board.getCell(checkID).getColor() == playersColor)
                    continuousPieces++;
                else
                    break;
            }

            if (continuousPieces == 6) {
                hasSixBlock = true;
                blockStartID = i;
                break;
            }
        }

        boolean isMoveLegal = true;

        if (hasSixBlock) {
            int opponentColor = (playersColor == Cell.WHITE) ? Cell.BLACK : Cell.WHITE;

            boolean opponentAhead = isOpponentAheadOfBlock(board, blockStartID, playersColor, opponentColor);

            if (!opponentAhead)
                isMoveLegal = false;
        }

        toCell.removePiece();
        fromCell.addPiece(playersColor);

        return isMoveLegal;

    }

    private boolean isOpponentAheadOfBlock(Board board, int blockStartID, int playersColor, int opponentColor) {
        //проверка ячеек по кругу начиная со следующей после блока
        for (int i = 6; i < 24; i++) {
            int checkID = (blockStartID + i) % 24;
            Cell cell = board.getCell(checkID);

            //если найдена шашка перед блоком
            if (cell.getColor() == opponentColor && cell.getCount() > 0) {
                //если шашка в своем доме
                if (opponentColor == Cell.WHITE && (checkID >= 18 && checkID <= 23))
                    continue;

                if (opponentColor == Cell.BLACK && (checkID >= 6 && checkID <= 11))
                    continue;

                return true;
            }
        }

        return false;
    }


    private boolean areAllPiecesOnHouse(Board board, int playersColor) {
        if (playersColor == Cell.WHITE) {
            for (int i = 0; i < 17; i++) {
                Cell cell = board.getCell(i);
                if (cell.getColor() == Cell.WHITE && cell.getCount() > 0)
                    return false;
            }
        } else if (playersColor == Cell.BLACK) {
            for (int i = 0; i < 24; i++) {
                if (i >= 6 && i <= 11)
                    continue;

                Cell cell = board.getCell(i);
                if (cell.getColor() == Cell.BLACK && cell.getCount() > 0)
                    return false;
            }
        } else
            return false;

        return true;
    }

    public boolean canRemoveFromBoard(Board board, int playersColor, int from, int diceValue) {
        if (!areAllPiecesOnHouse(board, playersColor)) return false;

        Cell cell = board.getCell(from);
        if (cell.isEmpty() || cell.getColor() != playersColor) return false;

        int position;
        if (playersColor == Cell.WHITE)
            position = 24 - from;
        else
            position = 12 - from;

        if (position == diceValue) return true;

        if (diceValue > position) {
            if (playersColor == Cell.WHITE) {
                for (int i = 18; i < from; i++) {
                    if (board.getCell(i).getColor() == Cell.WHITE && board.getCell(i).getCount() > 0)
                        return false;
                }

            } else {
                for (int i = 6; i < from; i++) {
                    if (board.getCell(i).getColor() == Cell.WHITE && board.getCell(i).getCount() > 0)
                        return false;
                }
            }

            return true;
        }

        return false;
    }

}
