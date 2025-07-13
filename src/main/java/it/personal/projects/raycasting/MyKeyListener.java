package it.personal.projects.raycasting;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
    private MyPanel pane;
    public MyKeyListener(MyPanel pane){
        this.pane=pane;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int xIncrement=0;
        int yIncrement=0;
        switch((""+e.getKeyChar()).toUpperCase()){
            case "W":{
                yIncrement=-1;
                break;
            }
            case "A":{
                xIncrement=-1;
                break;
            }
            case "S":{
                yIncrement=1;
                break;
            }
            case "D":{
                xIncrement=1;
                break;
            }
            default:{break;}
        }
        pane.moveCharacter(xIncrement,yIncrement);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Key pressed!");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("Key released!");
    }
}
