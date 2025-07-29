package it.personal.projects.raycasting;

import it.personal.projects.raycasting.constants.Constants;
import it.personal.projects.raycasting.shapes.Character;
import it.personal.projects.raycasting.shapes.Line;
import it.personal.projects.raycasting.shapes.Rectangle;
import it.personal.projects.raycasting.shapes.Shape;
import it.personal.projects.raycasting.shapes.ShapeDrawing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JPanel {

        private ShapeDrawing canvas;

        public MyPanel() {
            setLayout(new BorderLayout());

            List<Shape> characterShape = new ArrayList<>();

            it.personal.projects.raycasting.shapes.Character character = new it.personal.projects.raycasting.shapes.Character(20, 20, 20, 20, Color.GREEN,0d);
            characterShape.add(character);

            for(Double i = 0.0; i< Constants.FOV; i+=Constants.DELTA_RAY) {
                Double angle = (i * 1.0)+character.getRotation();
                angle = (angle * Math.PI / 180);
                characterShape.add(new Line(((Rectangle)character).getCenterX(), ((Rectangle)character).getCenterY(), 1000,angle,Color.RED));
            }

            List<Shape> fixed = new ArrayList<>();
            Shape s1 = new Rectangle(100, 50, 20, 200, Color.BLUE);
            fixed.add(s1);

            Shape s2 = new Rectangle(30, 230, 70, 20, Color.GRAY);
            fixed.add(s2);

            canvas = new ShapeDrawing(fixed,characterShape,new ArrayList<Shape>());
            add(canvas);
        }

        public void rotateCharacter(Double deltaDegree){
            it.personal.projects.raycasting.shapes.Character character = (it.personal.projects.raycasting.shapes.Character)canvas.getCharacter(0);
            character.setRotation(character.getRotation()+deltaDegree);
            canvas.getCharacters().set(0,character);
            for(Shape s : canvas.getCharacters()){
                if(!(s instanceof it.personal.projects.raycasting.shapes.Character)){
                    ((Line)s).setAngle( ((Line)s).getAngle()+deltaDegree);
                }
            }
            //canvas.repaint();
        }

        public void moveCharacter(int xIncrement,int yIncrement){
            Shape character = canvas.getCharacter(0);
            character.setX(character.getX()+xIncrement);
            character.setY(character.getY()+yIncrement);

            //System.out.println(((Rectangle) character).getX()+" "+((Rectangle) character).getY());
            //System.out.println(((Rectangle) character).getCenterX()+" "+((Rectangle) character).getCenterY());

            for(Shape s : canvas.getCharacters()){
                if(s instanceof Line){
                    s.setX(((Rectangle) character).getCenterX()+xIncrement);
                    s.setY(((Rectangle) character).getCenterY()+yIncrement);
                }
            }

            calculateDistances();

            canvas.repaint();
        }

        private void calculateDistances(){
            Character character = (Character)canvas.getCharacter(0);
            int xc= character.getCenterX();
            int yc = character.getCenterY();
            double playerAngle = character.getRotation();

            List<Double> distances = new ArrayList<>();
            List<Color> colors = new ArrayList<>();
            List<String> sides = new ArrayList<>();
            for(Double i=0.0;i<Constants.FOV;i+=Constants.DELTA_RAY){
                distances.add(Double.MAX_VALUE);
                colors.add(Color.BLACK);
                sides.add("");
            }

            //per ogni raggio calcolo la distanza da ogni muro e memorizzo la distanza minore
            //per ogni muro calcolo la distanza per ognuno dei lati (sopra, sotto, sinistra e destra) e considero la minore delle 4
            int nRay=-1;
            for(Shape l : canvas.getCharacters()) {
                if(!(l instanceof Line)) continue;

                Line line = (Line) l;
                nRay++;

                List<String> verifySides=new ArrayList<>();
                Double angle = line.getAngle();

                //in base all'angolo considero solo alcuni lati dei muri e non altri
                if(angle>=0 && angle<=Math.PI/2){
                    //lato superiore e lato sinistro
                    verifySides.add("sx");
                    verifySides.add("up");
                }else if(angle>=Math.PI/2 && angle<=Math.PI){
                    //lato superiore e lato destro
                    verifySides.add("dx");
                    verifySides.add("up");
                }else if(angle<=0 && angle>=-Math.PI/2){
                    //lato inferiore e lato sinistro
                    verifySides.add("sx");
                    verifySides.add("down");
                }else if(angle<=-Math.PI/2 && angle>=-Math.PI){
                    //lato inferiore e lato destro
                    verifySides.add("dx");
                    verifySides.add("down");
                }



                for (Shape r : canvas.getFixeds()) {
                    Rectangle wall = (Rectangle) r;
                    int x0 = wall.getX();
                    int x1 = wall.getX() + wall.getWidth();

                    int y0 = wall.getY();
                    int y1 = wall.getY() + wall.getHeight();

                    Double distance = Double.MAX_VALUE;
                    String lato="";

                    Long intersectionPoint=null;
                    Double newWidth = 1000d;

                    //lato sinistro del muro
                    if(verifySides.contains("sx") && xc<=x0){
                        intersectionPoint = Math.round(yc+((x0-xc)*Math.tan(angle)));
                        //punto di intersezione tra il raggio e il lato del muro
                       // System.out.println("intersectionPoint: "+intersectionPoint);
                        if(intersectionPoint>=y0 && intersectionPoint<=y1){
                            //il raggio interseca il lato sinistro del muro

                            //distanza tra il character e il lato del muro, passando per il ray
                            Double newdistance = Math.abs(((x0-xc))/Math.cos(angle));
                            if(newdistance<distance){
                                distance=newdistance;

                                //ricalcola la lunghezza del raggio in modo da visualizzarlo correttamente nella mappa
                                newWidth = Math.sqrt((xc-x0)*(xc-x0)+(yc-intersectionPoint)*(yc-intersectionPoint));
                                lato="sinistro";
                            }
                        }
                    }
                    //lato destro del muro
                    if(verifySides.contains("dx") && xc>=x1) {
                        intersectionPoint = Math.round(yc + ((x1 - xc) * Math.tan(angle)));
                        //punto di intersezione tra il raggio e il lato del muro
                        //System.out.println("intersectionPoint: "+intersectionPoint);
                        if (intersectionPoint >= y0 && intersectionPoint <= y1) {
                            //il raggio interseca il lato sinistro del muro

                            //distanza tra il character e il lato del muro, passando per il ray
                            Double newdistance = Math.abs(((x1 - xc)) / Math.cos(angle));
                            if (newdistance < distance) {
                                distance = newdistance;
                                newWidth = Math.sqrt((xc-x1)*(xc-x1)+(yc-intersectionPoint)*(yc-intersectionPoint));
                                lato = "destro";
                            }
                        }
                    }


                    //lato superiore del muro
                    if(verifySides.contains("up") && yc<=y0) {
                        intersectionPoint = Math.round(xc + ((y0 - yc) * Math.tan((Math.PI / 2) - angle)));
                        //punto di intersezione tra il raggio e il lato del muro
                        //System.out.println("intersectionPoint: "+intersectionPoint);
                        if (intersectionPoint >= x0 && intersectionPoint <= x1) {
                            //il raggio interseca il lato superiore del muro

                            //distanza tra il character e il lato del muro, passando per il ray
                            Double newdistance = Math.abs(((y0 - yc)) / Math.cos((Math.PI / 2) - angle));
                            if (newdistance < distance) {
                                distance = newdistance;
                                newWidth = Math.sqrt((xc-intersectionPoint)*(xc-intersectionPoint)+(yc-y0)*(yc-y0));
                                lato = "superiore";
                            }
                        }
                    }
                    //lato inferiore del muro
                    if(verifySides.contains("down") && yc>=y1) {
                        intersectionPoint = Math.round(xc + ((y1 - yc) * Math.tan((Math.PI / 2) - angle)));
                        //punto di intersezione tra il raggio e il lato del muro
                        //System.out.println("intersectionPoint: "+intersectionPoint);
                        if (intersectionPoint >= x0 && intersectionPoint <= x1) {
                            //il raggio interseca il lato inferiore del muro

                            //distanza tra il character e il lato del muro, passando per il ray
                            Double newdistance = Math.abs(((y1 - yc)) / Math.cos((Math.PI / 2) - angle));
                            if (newdistance < distance) {
                                distance = newdistance;
                                newWidth = Math.sqrt((xc-intersectionPoint)*(xc-intersectionPoint)+(yc-y1)*(yc-y1));
                                lato = "inferiore";
                            }
                        }
                    }


                    //correzione per diminuire effetto fisheye
                    double correctedDistance = distance * Math.cos(angle - playerAngle);
                    if(correctedDistance<distances.get(nRay)){
                        line.setWidth(newWidth.intValue());
                        distances.set(nRay,correctedDistance);
                        colors.set(nRay,wall.getColor());
                        sides.set(nRay,lato);
                    }
                }

            }
            drawRayCasting(distances,colors,sides);
        }
        public void drawRayCasting(List<Double> distances,List<Color> colors,List<String> sides){
            int x0=100;
            int y0=0;
            Double width=(Constants.FRAME_WIDTH-x0)/(Constants.FOV/Constants.DELTA_RAY);
            List<Shape> rectangles = new ArrayList<>();
            for(int i=0;i<distances.size();i++){
                Double distance = distances.get(i);
                y0=0;
                Rectangle rect=null;

                if(distance!=Double.MAX_VALUE){
                    if(distance>Constants.FRAME_HEIGHT){
                        distance=Constants.FRAME_HEIGHT*1.0;
                    }
                    Double temp = Math.floor((Constants.FRAME_HEIGHT-distance)/2);
                    y0=temp.intValue();
                    Color color = colors.get(i);
                    String side = sides.get(i);
                    switch(side){
                        case "sinistro":{
                            break;
                        }
                        case "destro":{
                            color=color.darker();
                            break;
                        }
                        case "superiore":{
                            color=color.brighter();
                            break;
                        }
                        case "inferiore":{
                            color=color.darker().darker();
                        }
                    }
                    rect = new Rectangle(x0,y0,width.intValue(),400-(distance.intValue()),color);
                }
                else{
                    rect = new Rectangle(x0,y0,width.intValue(),0,Color.GRAY);
                }

                rectangles.add(rect);
                x0+=width;
            }
            canvas.setRayCasting(rectangles);
            canvas.repaint();
        }
    }