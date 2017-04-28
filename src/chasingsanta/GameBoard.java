/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author BRudzki
 */
public class GameBoard extends JPanel {
    public static final int bWIDTH=800,bHEIGHT=600, CHILD_NUMBER = 7, FPS = 60; 
    private JLabel info;
    
    private Santa santa;
    private LinkedList<Actor> actors;
    private LinkedList<Gift> gifts;  
    
    private boolean pause = true, gameStarted = false, santaCought = false;
    
    public GameBoard() {
        actors = new LinkedList<Actor>();
        gifts = new LinkedList<Gift>();
        santa = new Santa(this);
 
        int delay = 1000/FPS; 
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               if(!GameBoard.this.pause){
                    GameBoard.this.update();
               }
               
               GameBoard.this.repaint();
            }
        };
        new Timer(delay, taskPerformer).start();
        
        info = new JLabel("PAUSE"); info.setVisible(false);
        add(info);
        addKeyListener(new InputHandler(this));
    }
    
    public boolean isStarted(){
        return gameStarted;
    }
    
    public void startGame(){
        actors.clear();
        gifts.clear();
        
        gameStarted = true;
        pause = false;
        santaCought = false;
        
        actors.add(santa);
        
        for(int i=0;i<CHILD_NUMBER;i++){
            actors.add(new Child(this));
            new Thread((Child)actors.getLast()).start();
        }
        
        for(int i=0;i<actors.size();i++){
            boolean goodPos = false;
            Actor actor = actors.get(i);
            while(!goodPos){
                actor.setRandomPos();
                goodPos = true;
                for(int j=0;j<i;j++)
                    if(actors.get(j).collide(actor)){
                        goodPos = false;
                        break;
                    }
            }
        }
        santa.init();
    }
    
    public void saveGame(){
        if(!gameStarted)return;
        
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("data.bin"))) {
            outputStream.writeObject(true);
            outputStream.writeObject(pause);
            outputStream.writeObject(gameStarted);
            outputStream.writeObject(santaCought);
            
            outputStream.writeObject(santa);
            
            outputStream.writeObject(actors.size());
            for(int i=0;i<actors.size();i++)
                outputStream.writeObject(actors.get(i));
            
            outputStream.writeObject(gifts.size());
            for(int i=0;i<gifts.size();i++)
                outputStream.writeObject(gifts.get(i));
        } catch (Exception ex) {
            Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void loadGame(){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("data.bin"))) {
            boolean saved = (Boolean)inputStream.readObject();
            if(!saved){
                startGame();
                return;
            }
            
            pause = (Boolean)inputStream.readObject();
            info.setVisible(pause);
            
            gameStarted = (Boolean)inputStream.readObject();
            santaCought = (Boolean)inputStream.readObject();
            
            santa = (Santa)inputStream.readObject();
            
            int size = (Integer)inputStream.readObject();
            actors.clear();
            for(int i=0;i<size;i++){
                Actor actor = (Actor)inputStream.readObject();
                actor.reinit(this);
                
                actors.add(actor);
                if(actor instanceof Child){
                    new Thread((Child)actor ).start();
                }
            }
            
            size = (Integer)inputStream.readObject();
            gifts.clear();
            for(int i=0;i<size;i++){
                Gift gift = (Gift)inputStream.readObject();
                gift.reinit(this);
                
                gifts.add(gift);
            }
        } catch (Exception ex) {
            Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void endGame(){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("data.bin"))) {
            outputStream.writeObject(false);
        } catch (Exception ex) {
            Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        gameStarted = false;
        pause = false;
        
        actors.clear();
        gifts.clear();
    }
    
    public void update(){
        if(!gameStarted)return;
        
        for(int i=0;i<actors.size();i++)
            actors.get(i).update();
        
        if(santaCought){
            JOptionPane.showMessageDialog(this, "You have been cought!\n          You lost!!!");
            endGame();
            return;
        }
        
        int childWithGifts = 0;
        for(int i=0;i<actors.size();i++)
            if(actors.get(i) instanceof Child){
                Child child = (Child)actors.get(i);
                
                if(child.hasGift() && !child.isMoving) childWithGifts++; // WTF ???
            }
        
        if(childWithGifts == CHILD_NUMBER){
            JOptionPane.showMessageDialog(this, "All chilrden got gifts!\n          You won!!!");
            endGame();
        }
    }
    
    synchronized public boolean collide(Actor actor){
        boolean collision = false;
        for(int i=0;i<actors.size();i++)
            if(actors.get(i) != actor && 
               actors.get(i).collide(actor)){
               collision = true;
               if(actors.get(i) instanceof Santa) santaCought = true;
            }
        
        return collision;
    }
    
    private boolean giftCollide(Actor actor){
        boolean collision = false;
        for(int i=0;i<actors.size();i++){
            Actor act = actors.get(i);
            if(act != actor && 
               act.collide(actor)){
               collision = true;
               if(act instanceof Child && !((Child)act).hasGift()){
                   collision = false;
               }
            }
        }
        return collision;
    }
    
    synchronized public boolean findGift(Child child){
        for(int i=0;i<gifts.size();i++){
            Gift gift = gifts.get(i);
            if(!gift.isTaken() && 
               child.inReach(gift)){
               
               gift.take();
               child.setDestPoint(gift.getX(), gift.getY());
               return true;
            }               
        }
        
        return false;
    }
    public boolean putGift(int x,int y){
        Gift gift = new Gift(x, y, this);
        
        if(!giftCollide(gift)){
            gifts.add(gift);
            return true;
        }
        
        return false;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        requestFocusInWindow();
 
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        for(int i=0;i<actors.size();i++)
            actors.get(i).draw(g, ((float)getWidth())/bWIDTH, ((float)getHeight())/bHEIGHT);
        for(int i=0;i<gifts.size();i++)
            gifts.get(i).draw(g, ((float)getWidth())/bWIDTH, ((float)getHeight())/bHEIGHT);
    }
    
    Santa getSanta(){
        return santa;
    }
    public int getSantaX(){
        return santa.getX();
    }
    public int getSantaY(){
        return santa.getY();
    }
    
    void changePauseState(){
        pause = !pause;
        info.setVisible(pause);
    }
}
