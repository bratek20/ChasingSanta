/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author BRudzki
 */
public class Assets {
    public static Image santa,child,gift;
    public static Random rand;
    public static void load(){
        rand = new Random();
        try {
            santa = ImageIO.read(new File("textures/santa.jpg"));
            child = ImageIO.read(new File("textures/child.jpg"));
            gift = ImageIO.read(new File("textures/gift.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
