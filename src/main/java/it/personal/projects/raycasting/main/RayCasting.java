package it.personal.projects.raycasting.main;


import it.personal.projects.raycasting.MyKeyListener;
import it.personal.projects.raycasting.MyPanel;
import it.personal.projects.raycasting.constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class RayCasting
{
    public static void main( String[] args ) {
        JFrame frame = new JFrame("My first JFrame");

        frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyPanel pane = new MyPanel();
        frame.add(pane);
        frame.setVisible(true);

        frame.addKeyListener(new MyKeyListener(pane));
    }
}
