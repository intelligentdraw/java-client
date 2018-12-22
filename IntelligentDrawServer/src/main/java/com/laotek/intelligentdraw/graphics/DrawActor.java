package com.laotek.intelligentdraw.graphics;

import java.awt.Graphics;

public interface DrawActor {

    void drawHead(int x, int y, int width, int height);

    void drawTorso(int x1, int y1, int x2, int y2);

    void drawLeftArm(int x1, int y1, int x2, int y2);

    void drawRightArm(int x1, int y1, int x2, int y2);

    void drawLeftLeg(int x1, int y1, int x2, int y2);

    void drawRightLeg(int x1, int y1, int x2, int y2);

    void drawString(Graphics g2, String currentLine, int x, int y);

    Graphics create2();

    void dispose(Graphics g);
}
