package ru.psu.coursework.client.ui;

import ru.psu.coursework.additional.logic.MoveCalculator;
import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;
import ru.psu.coursework.additional.models.Board;
import ru.psu.coursework.additional.models.Cell;
import ru.psu.coursework.additional.models.Dices;
import ru.psu.coursework.client.network.ClientConnection;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mainframe extends javax.swing.JPanel {

    private javax.swing.JButton jButtonRoll;
    private javax.swing.JButton jButtonBearOff;
    private javax.swing.JLabel jLabelBlack;
    private javax.swing.JLabel jLabelBlackScore;
    private javax.swing.JLabel jLabelDice;
    private javax.swing.JLabel jLabelOneDice;
    private javax.swing.JLabel jLabelTwoDice;
    private javax.swing.JLabel jLabelWhite;
    private javax.swing.JLabel jLabelWhiteScore;
    private javax.swing.JPanel jPanel1;

    private final MatchFrame matchFrame;
    private final ClientConnection connection;
    private final int myColor;
    private BoardPanel boardPanel;
    private Board currentBoard;
    private Dices dices;
    private int selectedCell = -1;

    public Mainframe(MatchFrame matchFrame, ClientConnection connection, int myColor, Board initialBoard, Dices initialDices) {
        this.matchFrame = matchFrame;
        this.connection = connection;
        this.myColor = myColor;
        this.currentBoard = initialBoard;
        this.dices = initialDices;

        initComponents();
        initMouseListener();

        initGameActions();

        updateDicesUI(initialDices);

        jButtonRoll.setEnabled(myColor == ru.psu.coursework.additional.models.Cell.WHITE);
    }

//    public Mainframe() {
//        //тестовая доска с начальной расстановкой для проверки отображения
//        currentBoard = new Board();
//
//        initComponents();
//        initMouseListener();
//    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        boardPanel = new BoardPanel(currentBoard);

        jButtonRoll = new javax.swing.JButton();
        jButtonBearOff = new javax.swing.JButton();
        jLabelWhite = new javax.swing.JLabel();
        jLabelBlack = new javax.swing.JLabel();
        jLabelDice = new javax.swing.JLabel();
        jLabelWhiteScore = new javax.swing.JLabel();
        jLabelBlackScore = new javax.swing.JLabel();
        jLabelOneDice = new javax.swing.JLabel();
        jLabelTwoDice = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());
        jPanel1.setBackground(new java.awt.Color(128, 110, 99));

        jButtonRoll.setBackground(new java.awt.Color(171, 139, 119));
        jButtonRoll.setFont(new java.awt.Font("SimSun-ExtB", 1, 18));
        jButtonRoll.setForeground(new java.awt.Color(86, 57, 39));
        jButtonRoll.setText("Roll");

        jButtonBearOff.setBackground(new java.awt.Color(171, 139, 119));
        jButtonBearOff.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));
        jButtonBearOff.setForeground(new java.awt.Color(86, 57, 39));
        jButtonBearOff.setText("Bear off");
        jButtonBearOff.setEnabled(false);

        jLabelWhite.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));
        jLabelWhite.setText("White");
        jLabelBlack.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));
        jLabelBlack.setText("Black");

        jLabelDice.setFont(new java.awt.Font("SimSun-ExtB", 1, 18));
        jLabelDice.setText("Dice:");

        jLabelWhiteScore.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));
        jLabelBlackScore.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));

        jLabelOneDice.setFont(new java.awt.Font("SimSun-ExtB", 1, 24));
        jLabelTwoDice.setFont(new java.awt.Font("SimSun-ExtB", 1, 24));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(jButtonBearOff) // Кнопка Bear off теперь на своем месте
                                .addGap(39, 39, 39)
                                .addComponent(jButtonRoll)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelWhite)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabelWhiteScore, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelBlack)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabelBlackScore, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(78, 78, 78)
                                .addComponent(jLabelDice)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelOneDice, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelTwoDice, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(165, 165, 165))
        );

        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jButtonRoll)
                                                .addComponent(jButtonBearOff, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabelDice)
                                                .addComponent(jLabelOneDice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabelTwoDice, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabelWhite)
                                                        .addComponent(jLabelBlack))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabelWhiteScore, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)
                                                        .addComponent(jLabelBlackScore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(9, 9, 9))
        );
        add(jPanel1, java.awt.BorderLayout.CENTER);
    }

    //обработка кликов по доске
    private void initMouseListener() {
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int column = mouseX / 70;
                if (column < 0 || column > 11) return;

                int clickedCell;
                if (mouseY > 300) {
                    clickedCell = column;
                } else {
                    clickedCell = 23 - column;
                }

                System.out.println("Cell selection: " + clickedCell);
                try {
                    onSlotSelected(clickedCell);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    //выбор фишки
    private void onSlotSelected(int cellId) throws IOException {
        if (dices == null || !dices.isRolled()) {
            JOptionPane.showMessageDialog(this,
                    "You must roll the dice!!!",
                    "The move is blocked", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedCell == -1) {
            Cell cell = currentBoard.getCell(cellId);

            if (cell != null || !cell.isEmpty()) {
                if (cell.getColor() != myColor) return;

                selectedCell = cellId;

                //List<Integer> possibleMoves

                MoveCalculator calculator = new MoveCalculator();
                List<Integer> validMoves = calculator.getPossibleMoves(
                        currentBoard, cellId, myColor,
                        false, false, dices.isDouble, 0, dices.getAvailableValues()
                );

                if (validMoves.contains(-1)) {
                    jButtonBearOff.setEnabled(true);
                    System.out.println("CLIENT: This piece can be discarded behind the board");
                } else {
                    jButtonBearOff.setEnabled(false);
                }

//                List<Integer> testMoves = new ArrayList<>();
//                testMoves.add((cellId + 3) % 24);
//                testMoves.add((cellId + 5) % 24);

                boardPanel.setHighlightedSlots(validMoves);
                boardPanel.repaint();
            }
        } else {
            System.out.println("Move from cell " + selectedCell + " to cell " + cellId);

            sendMoveToServer(selectedCell, cellId);
        }
    }

    private void sendMoveToServer(int from, int to) {
        int[] moveCoords = new int[]{from, to};
        try {
            connection.sendMessage(new Message(Commands.MAKE_MOVE, moveCoords));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        selectedCell = -1;
        jButtonBearOff.setEnabled(false);
        boardPanel.setHighlightedSlots(new ArrayList<>());
        boardPanel.repaint();
    }

//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(() -> new Mainframe().setVisible(true));
//    }

    //roll
    private void initGameActions() {
        jButtonRoll.addActionListener(e -> {
            System.out.println("Requesting a dice roll from the server...");

            try {
                connection.sendMessage(new Message(Commands.DICE_ROLL, null));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        jButtonBearOff.addActionListener(e -> {
            if (selectedCell != -1) {
                System.out.println("КЛИЕНТ: Игрок выбрал СБРОС фишки из лунки " + selectedCell);

                sendMoveToServer(selectedCell, -1);
            }
        });
    }


    public void refreshGameState(Board newBoard, Dices newDices, int currentTurnColor) {
        this.currentBoard = newBoard;
        this.dices = newDices;

        if (boardPanel != null) {
            boardPanel.setBoard(newBoard);
            boardPanel.repaint();
        }

//        boardPanel.repaint();

        //
        int whitePiecesOnBoard = 0;
        int blackPiecesOnBoard = 0;

        for (int i = 0; i < 24; i++) {
            Cell cell = newBoard.getCell(i);
            if (cell != null && !cell.isEmpty()) {
                if (cell.getColor() == ru.psu.coursework.additional.models.Cell.WHITE) {
                    whitePiecesOnBoard += cell.getCount();
                } else if (cell.getColor() == ru.psu.coursework.additional.models.Cell.BLACK) {
                    blackPiecesOnBoard += cell.getCount();
                }
            }
        }

        int whiteScore = 15 - whitePiecesOnBoard;
        int blackScore = 15 - blackPiecesOnBoard;

        if (jLabelWhiteScore != null)
            jLabelWhiteScore.setText(String.valueOf(whiteScore));

        if (jLabelBlackScore != null)
            jLabelBlackScore.setText(String.valueOf(blackScore));

        //


        if (newDices != null) {
            System.out.println("CLIENT INTERFACE: Cubes arrived from the network: "
                    + newDices.getDiceOne() + " and " + newDices.getDiceTwo());
        }

        updateDicesUI(newDices);

        boolean isMyTurn = (currentTurnColor == myColor);
//        jButtonRoll.setEnabled(isMyTurn && (newDices == null || !newDices.isRolled()));
        if (!isMyTurn || (newDices != null && newDices.getDiceOne() > 0)) {
            jButtonRoll.setEnabled(false);
            System.out.println("CLIENT INTERFACE: Roll button is disabled");
        } else {
            jButtonRoll.setEnabled(true);
            System.out.println("CLIENT INTERFACE: Roll button available for throwing");
        }
    }


    private void updateDicesUI(Dices dices) {
        if (dices != null && dices.getDiceOne() > 0 && dices.getDiceTwo() > 0) {
            if (jLabelOneDice == null || jLabelTwoDice == null) {
                System.err.println("UI ERROR: Cube text labels are null!");
                return;
            }

            jLabelOneDice.setText(String.valueOf(dices.getDiceOne()));
            jLabelTwoDice.setText(String.valueOf(dices.getDiceTwo()));
            System.out.println("The interface displayed dices: " + dices.getDiceOne() + " : " + dices.getDiceTwo());
            //System.out.println("КЛИЕНТ ИНТЕРФЕЙС: Текст лейблов успешно изменен на цифры!");
        } else {
            jLabelOneDice.setText("-");
            jLabelTwoDice.setText("-");
            //System.out.println("КЛИЕНТ ИНТЕРФЕЙС: Кубики пустые, отрисованы прочерки.");
        }
    }

}
