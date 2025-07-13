package it.personal.projects.raycasting;

import it.personal.projects.raycasting.constants.Constants;
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

            Shape character = new Rectangle(20, 20, 20, 20, Color.GREEN);
            characterShape.add(character);

            for(int i = 0; i< Constants.N_RAYS*Constants.DELTA_REAY; i+=Constants.DELTA_REAY) {
                Double angle = (i * 1.0)+45;
                angle = (angle * Math.PI / 180);
                //System.out.println("Angolo: "+angle);
                characterShape.add(new Line(((Rectangle)character).getCenterX(), ((Rectangle)character).getCenterY(), 1000,angle,Color.RED));
            }

            List<Shape> fixed = new ArrayList<>();
            Shape s1 = new Rectangle(100, 50, 20, 200, Color.BLACK);
            fixed.add(s1);

            Shape s2 = new Rectangle(30, 230, 70, 20, Color.GRAY);
            fixed.add(s2);

            canvas = new ShapeDrawing(fixed,characterShape,new ArrayList<Shape>());
            add(canvas);
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

            canvas.repaint();

            calculateDistances();
        }

        private void calculateDistances(){
            Rectangle character = (Rectangle)canvas.getCharacter(0);
            int xc= character.getCenterX();
            int yc = character.getCenterY();
            boolean excludeCharacter = false;

            List<Double> distances = new ArrayList<>();
            for(int i=0;i<Constants.N_RAYS;i++){
                distances.add(Double.MAX_VALUE);
            }

            //per ogni raggio calcolo la distanza da ogni muro e memorizzo la distanza minore
            //per ogni muro calcolo la distanza per ognuno dei lati (sopra, sotto, sinistra e destra) e considero la minore delle 4
            int nRay=-1;
            for(Shape l : canvas.getCharacters()) {
                if(excludeCharacter) { //il primo elemento è il quadrato che simboleggia il giocatore, gli altri sono i raggi
                    Line line = (Line) l;
                    nRay++;
                    int rnumber=-1;
                    for (Shape r : canvas.getFixeds()) {

                        rnumber++;
                        Rectangle wall = (Rectangle) r;
                        //Rectangle wall = (Rectangle) canvas.getFixeds().get(1);
                        int x0 = wall.getX();
                        int x1 = wall.getX() + wall.getWidth();

                        int y0 = wall.getY();
                        int y1 = wall.getY() + wall.getHeight();

                        Double angle = (line.getAngle());

                        Double distance = Double.MAX_VALUE;
                        String lato="";
                        System.out.println("x0: "+x0+" x1: "+x1+" y0: "+y0+" y1: "+y1);
                        System.out.println("xc: "+xc+" yc: "+yc+" angle: "+angle +" Math.tan(angle): "+Math.tan(angle));

                        //lato sinistro del muro
                        Long intersectionPoint=null;
                        intersectionPoint = Math.round(yc+((x0-xc)*Math.tan(angle)));
                        //punto di intersezione tra il raggio e il lato del muro
                        //System.out.println("intersectionPoint: "+intersectionPoint);
                        if(intersectionPoint>=y0 && intersectionPoint<=y1){
                            //il raggio interseca il lato sinistro del muro

                            //distanza tra il character e il lato del muro, passando per il ray
                            Double newdistance = Math.abs(((x0-xc))/Math.cos(angle));
                            if(newdistance<distance){
                                distance=newdistance;
                                lato="sinistro";
                            }
                            //System.out.println("Distanza dal lato sinistro del rettangolo "+rnumber+": "+newdistance);
                        }

                        //lato destro del muro
                        intersectionPoint = Math.round(yc+((x1-xc)*Math.tan(angle)));
                        //punto di intersezione tra il raggio e il lato del muro
                        //System.out.println("intersectionPoint: "+intersectionPoint);
                        if(intersectionPoint>=y0 && intersectionPoint<=y1){
                            //il raggio interseca il lato sinistro del muro

                            //distanza tra il character e il lato del muro, passando per il ray
                            Double newdistance = Math.abs(((x1-xc))/Math.cos(angle));
                            if(newdistance<distance){
                                distance=newdistance;
                                lato="destro";
                            }
                            //System.out.println("Distanza dal lato destro del rettangolo "+rnumber+": "+newdistance);
                        }





                        //lato superiore del muro
                        intersectionPoint = Math.round(xc+((y0-yc)*Math.tan((Math.PI/2)-angle)));
                        //punto di intersezione tra il raggio e il lato del muro
                        //System.out.println("intersectionPoint: "+intersectionPoint);
                        if(intersectionPoint>=x0 && intersectionPoint<=x1){
                            //il raggio interseca il lato superiore del muro

                            //distanza tra il character e il lato del muro, passando per il ray
                            Double newdistance = Math.abs(((y0-yc))/Math.cos((Math.PI/2)-angle));
                            if(newdistance<distance){
                                distance=newdistance;
                                lato="superiore";
                            }
                            //System.out.println("Distanza dal lato superiore del rettangolo "+rnumber+": "+newdistance);
                        }


                        //lato inferiore del muro
                        intersectionPoint = Math.round(xc+((y1-yc)*Math.tan((Math.PI/2)-angle)));
                        //punto di intersezione tra il raggio e il lato del muro
                        //System.out.println("intersectionPoint: "+intersectionPoint);
                        if(intersectionPoint>=x0 && intersectionPoint<=x1){
                            //il raggio interseca il lato inferiore del muro

                            //distanza tra il character e il lato del muro, passando per il ray
                            Double newdistance = Math.abs(((y1-yc))/Math.cos((Math.PI/2)-angle));
                            if(newdistance<distance){
                                distance=newdistance;
                                lato="inferiore";
                            }
                            //System.out.println("Distanza dal lato inferiore del rettangolo "+rnumber+": "+newdistance);
                        }
                        //System.out.println("Distanza dal rettangolo "+rnumber+": "+distance+" angolo: "+angle+" lato: "+lato);
                        if(distance<distances.get(nRay))
                            distances.set(nRay,distance);

                    }
                }else{
                    excludeCharacter=true;
                }
            }
//            System.out.println("distances: ");
//            for(Double distance : distances){
//                System.out.print(distances+" ");
//            }
//            System.out.println();
            drawRayCasting(distances);
        }
        public void drawRayCasting(List<Double> distances){
            int x0=600;
            int y0=200;
            int width=(Constants.FRAME_WIDTH-x0)/Constants.N_RAYS;
            List<Shape> rectangles = new ArrayList<>();
            for(Double distance : distances){
                Rectangle rect=null;

                if(distance!=Double.MAX_VALUE){
                    //System.out.println("Disegno rettangolo: "+x0+" "+y0+" "+(400-(distance.intValue())));
                    rect = new Rectangle(x0,y0,width,400-(distance.intValue()),Color.BLACK);
                }
                else{
                    //System.out.println("Disegno rettangolo: "+x0+" "+y0+" 0");
                    rect = new Rectangle(x0,y0,width,0,Color.GRAY);
                }

                rectangles.add(rect);
                x0+=width;
            }
            canvas.setRayCasting(rectangles);
            canvas.repaint();
        }
    }