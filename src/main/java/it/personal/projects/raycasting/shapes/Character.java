package it.personal.projects.raycasting.shapes;

import java.awt.*;

public class Character extends Rectangle{
    private Double rotation;
    public Character(int x, int y, int width, int height, Color color, Double rotation){
        super(x,y,width,height,color);
        this.rotation=rotation;
    }

    public Double getRotation() {
        return rotation;
    }

    public void setRotation(Double rotation) {
        this.rotation = rotation;
    }
}
