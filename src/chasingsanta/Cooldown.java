/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author BRudzki
 */
public class Cooldown implements Serializable {
    public final static int COOLDOWN_HEIGHT = 5;
    private int currentTime;
    private int cooldownTime;
    private boolean cooling = false;
    public Cooldown(int cooldownTime){
        this.cooldownTime = cooldownTime;
    }
    
    public void start(){
        cooling = true;
        currentTime = 0;
    }
    
    public void reset(){
        cooling = false;
    }
    
    public void update(){
        if(!cooling)return;
        
        currentTime++;
        if(currentTime > cooldownTime)
            cooling = false;
    }
    
    public boolean isCooling(){
        return cooling;
    }
    
    public void draw(Graphics g, float scaleX, float scaleY,  Actor owner){
        if(!cooling)return;
        
        g.setColor(Color.RED);
        g.fillRect(owner.getLeft(scaleX), owner.getTop(scaleY) - getHeight(scaleY),owner.getSizeX(scaleX),getHeight(scaleY));
        
        g.setColor(Color.GREEN);
        float progress = (float) currentTime / cooldownTime;
        g.fillRect(owner.getLeft(scaleX), owner.getTop(scaleY) - getHeight(scaleY),(int)(progress*owner.getSizeX(scaleX)),getHeight(scaleY));
    }
    
    private int getHeight(float scaleY){
        return (int)(COOLDOWN_HEIGHT*scaleY);
    }
}
