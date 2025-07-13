package it.personal.projects.raycasting.shapes;

import java.awt.*;

public abstract class Shape {
    protected int x;
    protected int y;
     int width;
    protected Color color;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }
    public Color getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
