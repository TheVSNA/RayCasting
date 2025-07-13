package it.personal.projects.raycasting.shapes;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

public class ShapeDrawing extends JComponent {
    List<Shape> fixed;
    List<Shape> character;

    List<Shape> rayCasting;
    public ShapeDrawing(List<Shape> fixed,List<Shape> character,List<Shape> rayCasting){
        this.fixed=fixed;
        this.character=character;
        this.rayCasting=rayCasting;
    }

    public Shape getFixed(int index){
        return fixed.get(index);
    }
    public Shape getCharacter(int index){
        return character.get(index);
    }
    public List<Shape> getFixeds(){return fixed;}
    public List<Shape> getCharacters(){return character;}

    public void setRayCasting(List<Shape> rayCasting){
        this.rayCasting=rayCasting;
    }

    public List<Shape> getRayCasting(){
        return rayCasting;
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        drawShape(g2,fixed);
        drawShape(g2,character);
        drawShape(g2,rayCasting);
    }
    private void drawShape(Graphics2D g2,List<Shape> shapes){
        for(Shape shape : shapes){
            g2.setColor(shape.getColor());

            if(shape instanceof Line){
                Line shapeLine = (Line) shape;
                Line2D lin = new Line2D.Float(shapeLine.getX(), shapeLine.getY(),shapeLine.getEndX(), shapeLine.getEndY());
                g2.draw(lin);
            }else if(shape instanceof Rectangle){
                Rectangle shapeRect = (Rectangle)shape;
                java.awt.Rectangle rect = new java.awt.Rectangle(shapeRect.getX(), shapeRect.getY(), shapeRect.getWidth(), shapeRect.getHeight());
                g2.draw(rect);
                g2.fill(rect);
            }
        }
    }
}