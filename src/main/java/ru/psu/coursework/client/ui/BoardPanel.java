package ru.psu.coursework.client.ui;

import ru.psu.coursework.additional.models.Board;
import ru.psu.coursework.additional.models.Cell;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BoardPanel extends JPanel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Image bgImage;
    private Image whiteChipImage;
    private Image blackChipImage;

    private Board board;
    private List<Integer> highlightedSlots = new ArrayList<>();

    public BoardPanel(Board board) {
        this.board = board;

        bgImage = new ImageIcon(getClass().getResource("/ru/psu/coursework/other/field.png")).getImage();
        whiteChipImage = new ImageIcon(getClass().getResource("/ru/psu/coursework/other/white1.png")).getImage();
        blackChipImage = new ImageIcon(getClass().getResource("/ru/psu/coursework/other/black1.png")).getImage();

        setPreferredSize(new Dimension(840, 600));
    }

    //подсветка нужных мест
    public void setHighlightedSlots(List<Integer> slots) {
        this.highlightedSlots = slots;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (bgImage != null) {
            g2d.drawImage(bgImage, 0, 0, 840, 600, this);
        }

        if (board == null) return;

        int chipSize = 46;
        int overlapStep = 12;//расстояние наложения
        int centerOffset = (70 - chipSize) / 2;

        for (int i = 0; i < 24; i++) {
            Cell cell = board.getCell(i);
            if (cell == null || cell.isEmpty()) continue;

            Image currentChipImg = (cell.getColor() == Cell.WHITE) ? whiteChipImage : blackChipImage;
            if (currentChipImg == null) continue;

            int baseX = (i <= 11) ? (i * 70) : ((23 - i) * 70);
            int finalX = baseX + centerOffset;

            for (int k = 0; k < cell.getCount(); k++) {
                int finalY;
                if (i <= 11) {
                    finalY = 600 - chipSize - (k * overlapStep);
                } else {
                    finalY = 0 + (k * overlapStep);
                }

                g2d.drawImage(currentChipImg, finalX, finalY, chipSize, chipSize, this);
            }
        }

        if (highlightedSlots != null && !highlightedSlots.isEmpty()) {
            g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(new Color(0, 200, 0, 130));

            for (int cellId : highlightedSlots) {
                if (cellId < 0 || cellId >= 24) continue;

                int baseX = (cellId <= 11) ? (cellId * 70) : ((23 - cellId) * 70);
                int markerSize = 26;
                int x = baseX + (70 - markerSize) / 2;
                int y = (cellId <= 11) ? (600 - markerSize - 15) : (0 + 15);

                //кружок
                g2d.fillOval(x, y, markerSize, markerSize);

                g2d.setColor(new Color(0, 200, 0, 130));
            }
        }
    }

    public void setBoard(Board newBoard) {
        this.board = newBoard;
    }

}
