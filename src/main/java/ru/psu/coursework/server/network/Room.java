package ru.psu.coursework.server.network;

import ru.psu.coursework.additional.logic.MoveCalculator;
import ru.psu.coursework.additional.logic.MoveValidator;
import ru.psu.coursework.additional.logic.WinConditionChecker;
import ru.psu.coursework.additional.logic.TypeOfWin;
import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;
import ru.psu.coursework.additional.models.Board;
import ru.psu.coursework.additional.models.Cell;
import ru.psu.coursework.additional.models.Dices;

public class Room {

    private final String roomCode;
    private final PlayerConnection white;
    private PlayerConnection black;
    private final Board board;
    private final Dices dices;
    private int currentTurnColor;
    private boolean isGameStarted = false;
    private boolean whiteHeadMoveDone = false;
    private boolean blackHeadMoveDone = false;
    private boolean isWhiteFirstMove = true;
    private boolean isBlackFirstMove = true;
    private int headPiecesTaken = 0;

    public Room(String roomCode, PlayerConnection creator) {
        this.roomCode = roomCode;
        this.white = creator;
        this.board = new Board();
        this.dices = new Dices();
    }

    public void addOpponent(PlayerConnection opponent) {
        this.black = opponent;
    }

    public boolean isFull() {
        return black != null;
    }

    public void start() {
        this.isGameStarted = true;
        this.currentTurnColor = Cell.WHITE;

        //this.dices.roll();

        Object[] whiteData = new Object[] { Cell.WHITE, dices, board };
        white.send(new Message(Commands.GAME_START, whiteData));

        Object[] blackData = new Object[] { Cell.BLACK, dices, board };
        black.send(new Message(Commands.GAME_START, blackData));

        System.out.println("Match started!!!");
    }

    public synchronized void operatePlayersMove(PlayerConnection player, int from, int to) {
        if (!isGameStarted) {
            player.send(new Message(Commands.ERROR, "The match hasn't started yet!"));

            return;
        }

        if (currentTurnColor == Cell.WHITE && player != white) {
            player.send(new Message(Commands.GAME_START, "It's not your move now!"));

            return;
        }

        if (currentTurnColor == Cell.BLACK && player != black) {
            player.send(new Message(Commands.GAME_START, "It's not your move now!"));

            return;
        }

        int diceValue = (to - from + 24) % 24;

        if (!dices.getAvailableValues().contains(diceValue)) {
            player.send(new Message(Commands.ERROR, "You don't have a dice with value " + diceValue + "!"));
            return;
        }

        //доступное значение

        MoveValidator validator = new MoveValidator();

        boolean currentHeadMoveDone = (currentTurnColor == Cell.WHITE) ? whiteHeadMoveDone : blackHeadMoveDone;
        boolean currentFirstMove = (currentTurnColor == Cell.WHITE) ? isWhiteFirstMove : isBlackFirstMove;
        boolean isDouble = dices.getDiceOne() == dices.getDiceTwo();

        boolean canMove = validator.canMove(
                board, from, to, currentTurnColor, currentHeadMoveDone,
                diceValue, currentFirstMove, isDouble, headPiecesTaken);

        if (!canMove) {
            player.send(new Message(Commands.ERROR, "This move is impossible!"));

            return;
        }

        validator.move(board, from, to);
        dices.takeDice(diceValue);

        boolean isFromHead = (currentTurnColor == Cell.WHITE && from == 0) || (currentTurnColor == Cell.BLACK && from == 12);
        if (isFromHead) {
            if (currentTurnColor == Cell.WHITE)
                whiteHeadMoveDone = true;
            else
                blackHeadMoveDone = true;

            headPiecesTaken++;
        }

        WinConditionChecker win  = new WinConditionChecker();

        if (win.isGameOver(board)) {
            endMatch(win);

            return;
        }

        //
        if (dices.getAvailableValues().isEmpty()) {
            System.out.println("SERVER: Player is out of dice. Pass the turn!");
            changeTurn();
            return;
        }


        MoveCalculator calculator = new MoveCalculator();

        boolean isRolled = dices.isRolled() && calculator.hasAnyValidMoves(
                board, currentTurnColor, currentHeadMoveDone, currentFirstMove, isDouble, headPiecesTaken, dices.getAvailableValues()
        );

        if (!isRolled) {
            System.out.println("SERVER: Nowhere to go!");
            changeTurn();
        }
        else {
            System.out.println("SERVER: There are available moves!");
            broadcastState();
        }


    }

    private void endMatch(WinConditionChecker win) {
        isGameStarted = false;

        int winner = win.getWinnerColor(board);
        TypeOfWin typeOfWin = win.checkWin(board, winner);

        String winnerName = (winner == Cell.WHITE) ? white.getUsername() : black.getUsername();
        String endMessage = "Game over! The player " + winnerName + " has won!!!";

        white.send(new Message(Commands.ERROR, endMessage));
        black.send(new Message(Commands.ERROR, endMessage));

    }

    private void changeTurn() {
        if (currentTurnColor == Cell.WHITE) {
            isWhiteFirstMove = false;
            currentTurnColor = Cell.BLACK;
        } else {
            isBlackFirstMove = false;
            currentTurnColor = Cell.WHITE;
        }

        whiteHeadMoveDone = false;
        blackHeadMoveDone = false;
        headPiecesTaken = 0;

        //this.dices = new Dices();

        dices.clear();

        //dices.roll();

        broadcastState();
    }

    private void broadcastState() {
        Object[] state = new Object[] { board, dices, currentTurnColor };
        Message message = new Message(Commands.UPDATE_BOARD, state);

//        white.send(message);
//        black.send(message);

        if (white != null) {
            white.resetSocketCache();
            white.send(message);
        }
        if (black != null) {
            black.resetSocketCache();
            black.send(message);
        }

        System.out.println("The game state has been sent successfully");
    }

    public PlayerConnection getWhitePlayer() { return white; }
    public PlayerConnection getBlackPlayer() { return black; }
    public Board getBoard() { return board; }
    public Dices getDices() { return dices; }
    public int getCurrentTurnColor() { return currentTurnColor; }


}
