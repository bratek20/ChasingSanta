/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;

/**
 *
 * @author BRudzki
 */
abstract public class Actor implements Serializable{
    protected int x,y,size;
    protected boolean flipped = false;
    private Square collisionSquare;
    protected Cooldown cooldown = null;
    
    transient private Image sprite = null;
    transient protected GameBoard board;
    
    public Actor(int size, GameBoard board){
        this.size = size;
        this.board = board;
        
        collisionSquare = new Square(size, this);
    }
    
    public void reinit(GameBoard board){ 
        this.board = board;
    }
    
    public void setRandomPos(){
        x = Assets.rand.nextInt(GameBoard.bWIDTH);
        y = Assets.rand.nextInt(GameBoard.bHEIGHT);
    }
    protected void setSprite(Image sprite){
        this.sprite = sprite;
    }
    
    public boolean collide(Actor actor){
        return collisionSquare.intersects(actor.collisionSquare); // WTF ???
    }
    public Square getCollisionSquare(){
        return collisionSquare;
    }
    
    public void update(){
        if(cooldown != null)
            cooldown.update();
    }
    
    public void draw(Graphics g, float scaleX, float scaleY){
        if(flipped)
           g.drawImage(sprite, getLeft(scaleX)+getSizeX(scaleX), getTop(scaleY), -getSizeX(scaleX), getSizeY(scaleY), null);
        else
            g.drawImage(sprite, getLeft(scaleX), getTop(scaleY), getSizeX(scaleX), getSizeY(scaleY), null);
        
        //g.setColor(Color.red);
        //g.fillRect(getLeft(scaleX), getTop(scaleY), getSizeX(scaleX), getSizeY(scaleY));
        
        if(cooldown != null)
            cooldown.draw(g, scaleX, scaleY, this);
    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
    public int getLeft(float scaleX){
        return (int)(collisionSquare.getLeft()*scaleX);
    }
    public int getTop(float scaleY){
        return (int)(collisionSquare.getTop()*scaleY);
    }
    public int getSizeX(float scaleX){
        return (int)(size*scaleX);
    }
    public int getSizeY(float scaleY){
        return (int)(size*scaleY);
    }
}
