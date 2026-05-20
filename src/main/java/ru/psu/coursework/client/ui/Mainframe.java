package ru.psu.coursework.client.ui;

import ru.psu.coursework.additional.models.Board;
import ru.psu.coursework.additional.models.Cell;
import ru.psu.coursework.additional.models.Dices;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Mainframe extends javax.swing.JFrame {

    private javax.swing.JButton jButtonRoll;
    private javax.swing.JLabel jLabelBlack;
    private javax.swing.JLabel jLabelBlackScore;
    private javax.swing.JLabel jLabelDice;
    private javax.swing.JLabel jLabelOneDice;
    private javax.swing.JLabel jLabelTwoDice;
    private javax.swing.JLabel jLabelWhite;
    private javax.swing.JLabel jLabelWhiteScore;
    private javax.swing.JPanel jPanel1;

    private BoardPanel boardPanel;
    private Board currentBoard;
    private int selectedCell = -1;

    public Mainframe() {
        //тестовая доска с начальной расстановкой для проверки отображения
        currentBoard = new Board();

        initComponents();
        initMouseListener();
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();

        boardPanel = new BoardPanel(currentBoard);

        jButtonRoll = new javax.swing.JButton();
        jLabelWhite = new javax.swing.JLabel();
        jLabelBlack = new javax.swing.JLabel();
        jLabelDice = new javax.swing.JLabel();
        jLabelWhiteScore = new javax.swing.JLabel();
        jLabelBlackScore = new javax.swing.JLabel();
        jLabelOneDice = new javax.swing.JLabel();
        jLabelTwoDice = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(128, 110, 99));

        jButtonRoll.setBackground(new java.awt.Color(171, 139, 119));
        jButtonRoll.setFont(new java.awt.Font("SimSun-ExtB", 1, 18));
        jButtonRoll.setForeground(new java.awt.Color(86, 57, 39));
        jButtonRoll.setText("Roll");

        jLabelWhite.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));
        jLabelWhite.setText("White");

        jLabelBlack.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));
        jLabelBlack.setText("Black");

        jLabelDice.setFont(new java.awt.Font("SimSun-ExtB", 1, 18));
        jLabelDice.setText("Dice:");

        jLabelOneDice.setFont(new java.awt.Font("SimSun-ExtB", 1, 24));
        jLabelTwoDice.setFont(new java.awt.Font("SimSun-ExtB", 1, 24));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(204, 204, 204)
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
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jButtonRoll)
                                                        .addComponent(jLabelDice)
                                                        .addComponent(jLabelOneDice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabelTwoDice, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
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
                onSlotSelected(clickedCell);
            }
        });
    }

    //выбор фишки
    private void onSlotSelected(int cellId) {
        if (selectedCell == -1) {
            Cell cell = currentBoard.getCell(cellId);

            if (!cell.isEmpty()) {
                selectedCell = cellId;

                List<Integer> testMoves = new ArrayList<>();
                testMoves.add((cellId + 3) % 24);
                testMoves.add((cellId + 5) % 24);

                boardPanel.setHighlightedSlots(testMoves); // Передаем зеленые маркеры
                boardPanel.repaint(); // Заставляем Swing мгновенно перерисовать экран
            }
        } else {
            System.out.println("Move from cell " + selectedCell + " to cell " + cellId);

            //connector.sendMessage(new Message(Commands.MAKE_MOVE, new int[]{selectedSlot, slotId}));

            selectedCell = -1;
            boardPanel.setHighlightedSlots(new ArrayList<>());
            boardPanel.repaint();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Mainframe().setVisible(true));
    }
}
