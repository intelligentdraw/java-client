package com.laotek.intelligentdraw.graphics;

import java.awt.Graphics;

public interface DrawUsecase {

    void drawOval(int x1, int y1, int x2, int y2);

    void drawString(Graphics g2, String currentLine, int x, int y);

    Graphics create2();

    void dispose(Graphics g);
}
