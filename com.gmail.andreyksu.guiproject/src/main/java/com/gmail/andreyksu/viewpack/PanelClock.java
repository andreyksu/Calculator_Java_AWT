package com.gmail.andreyksu.viewpack;

import java.awt.*;

import com.gmail.andreyksu.controlpack.ICalcController;

public class PanelClock extends Panel {

    ICalcController calcController;

    int seconds, minut, hour;

    boolean init = false;

    public PanelClock(ICalcController calcController) {
        this.calcController = calcController;
        setPreferredSize(new Dimension(330, 255));

    }

    public void setSeconds(int sec, int min, int h) {
        seconds = sec;
        minut = min;
        hour = h;
        init = true;
        repaint();
    }

    public void paint(Graphics g) {
        int R1 = 89, R2 = 96;
        int dx = 160, dy = 120;
        int[] xy1, xy2;
        int[] xySec, xyMin, xyHour;
        if (init) {
            for (int i = 0; i < 12; i++) {
                xy1 = getXY(R1, i, 12, dx, dy, 0);
                xy2 = getXY(R2, i, 12, dx, dy, 0);
                g.drawLine(xy1[0], xy1[1], xy2[0], xy2[1]);
            }

            xySec = getXY(R2 + 3, seconds, 60, dx, dy, -Math.PI / 2);
            g.drawLine(dx, dy, xySec[0], xySec[1]);

            xyMin = getXY(R2 - 15, minut, 60, dx, dy, -Math.PI / 2);
            g.drawLine(dx, dy, xyMin[0], xyMin[1]);

            xyHour = getXY(R2 - 25, hour, 12, dx, dy, -Math.PI / 2);
            g.drawLine(dx, dy, xyHour[0], xyHour[1]);

            g.drawString(hour + " : " + minut + " : " + seconds, 125, 235);
        }

    }

    private int[] getXY(int R, int k, int d, int x, int y, double rad) {
        int[] coords = {0, 0};
        coords[0] = (int) (R * (Math.cos((double) (k * 2 * Math.PI) / d + rad)))
                + x;
        coords[1] = (int) (R * (Math.sin((double) (k * 2 * Math.PI) / d + rad)))
                + y;
        return coords;
    }

}