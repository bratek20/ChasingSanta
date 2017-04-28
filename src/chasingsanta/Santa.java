/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author BRudzki
 */
public class Santa extends MoveActor{
    public static final int SANTA_SIZE = 50, GIFT_COOLDOWN = 75; 
    public Santa(GameBoard board){
        super(SANTA_SIZE, board);
        
        setSpeed(12);
        setSprite(Assets.santa);
        cooldown = new Cooldown(GIFT_COOLDOWN);
    }
    
    public void reinit(GameBoard board){
        super.reinit(board);
        setSprite(Assets.santa);
    }
    
    public void init(){
        destX = x;
        destY = y;
        cooldown.reset();
    }
    
    public void putGift(){
        if(cooldown.isCooling())return;
        
        boolean putted;
        if(flipped) putted = board.putGift(x - SANTA_SIZE,y);
        else putted = board.putGift(x + SANTA_SIZE,y);
        
        if(putted)
            cooldown.start();
    }
}
