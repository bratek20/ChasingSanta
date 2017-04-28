/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

import java.io.Serializable;

/**
 *
 * @author BRudzki
 */
public class Square implements Serializable {
    private int size;
    private Actor owner;
    
    public Square(int size, Actor owner){
        this.size = size;
        this.owner = owner;
    }
    
    public boolean intersects(Square s){
        return contains(s) || s.contains(this);
    }
    
    private boolean contains(Square s){
        return contains(s.getLeft(), s.getTop()) || contains(s.getRight(), s.getTop())
                || contains(s.getLeft(),s.getBottom()) || contains(s.getRight(),s.getBottom());
    }
    
    private boolean contains(float x, float y){
        return getLeft() <= x && x <= getRight()
                && getTop()<= y && y <= getBottom();
    }
    
    public int getLeft(){
        return owner.getX()-size/2;
    }
    public int getRight(){
        return owner.getX()+size/2;
    }
    public int getTop(){
        return owner.getY()-size/2;
    }
    public int getBottom(){
        return owner.getY()+size/2;
    }
}
