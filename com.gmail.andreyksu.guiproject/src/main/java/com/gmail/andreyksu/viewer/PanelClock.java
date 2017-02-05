package com.gmail.andreyksu.viewer;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.StringTokenizer;

import com.gmail.andreyksu.controller.ICalcController;

// TODO: Дать вменяемое наименование переменным, возможо вынести рассчет в
// отдельный класс, здесь лишь отображать/отрисовывать результаты.
/**
 * Класс служит для отрисовки часов каждую секунду. Отрисовка/перерисвока
 * вызываетс каждую секунду из модели. Используется паттерн наблюдатель.
 */
public class PanelClock extends Panel {

    private String strr;

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

    public void paint(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
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

            xyMin = getXY(R2 - 20, minut, 60, dx, dy, -Math.PI / 2);

            BasicStroke minuteline = new BasicStroke(2);
            g.setStroke(minuteline);
            g.draw(new Line2D.Double(dx, dy, xyMin[0], xyMin[1]));

            g.drawLine(dx, dy, xyMin[0], xyMin[1]);

            xyHour = getXY(R2 - 43, hour, 12, dx, dy,
                    -Math.PI / 2 + getOffsetForHours());
            BasicStroke hoursline = new BasicStroke(3);
            g.setStroke(hoursline);
            g.draw(new Line2D.Double(dx, dy, xyHour[0], xyHour[1]));

            g.drawLine(dx, dy, xyHour[0], xyHour[1]);
            strr = String.format("%1$02d : %2$02d : %3$02d", hour, minut,
                    seconds);
            g.drawString(strr, 125, 235);
        }

    }

    private double getOffsetForHours() {
        double offsetHour = 0;
        if (minut < 5)
            offsetHour = 0;
        else if (minut < 30)
            offsetHour = Math.PI / 24;
        else if (minut < 45)
            offsetHour = 2 * Math.PI / 24;
        else if (minut < 61)
            offsetHour = 3 * Math.PI / 24;
        return offsetHour;
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
