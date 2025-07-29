package it.personal.projects.raycasting.shapes;

import java.awt.*;

public class Line extends Shape{

    Double angle;
    public Line(int x, int y, int width,Double angle, Color color){
        this.x=x;
        this.y=y;
        this.width=width;
        this.angle=angle;
        this.color=color;
    }

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {

        this.angle = angle;

        //normalizzo angolo tra -180 e +180
        if(angle<-Math.PI){
            double tmp = Math.abs(angle+Math.PI);
            this.angle=Math.PI-tmp;
        }
        if(angle>Math.PI){
            double tmp = Math.abs(angle-Math.PI);
            this.angle=-Math.PI+tmp;
        }
    }

    public int getEndX(){
        Double endX = (x + width * Math.cos(angle));
        return endX.intValue();
    }
    public int getEndY(){
        Double endX = (y + width * Math.sin(angle));
        return endX.intValue();
    }
    @Override
    public void setWidth(int width){
        this.width=width;

    }
}
