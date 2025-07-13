package it.personal.projects.raycasting.shapes;

import java.awt.*;

public class Rectangle extends Shape{
    private int height;

    public Rectangle(int x, int y, int width, int height, Color color){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.color=color;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getCenterX(){
        Double d = Math.floor(width/2.0);
        return x+d.intValue();
    }
    public int getCenterY(){
        Double d = Math.floor(height/2.0);
        return y+d.intValue();
    }
}
