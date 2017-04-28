/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author BRudzki
 */
public class InputHandler extends KeyAdapter{
    private GameBoard board;
    private static int MOVE = 75;
    public InputHandler(GameBoard board){
        this.board = board;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(!board.isStarted())return;
        
        Santa santa = board.getSanta();
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            santa.move(-MOVE,0);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            santa.move(MOVE,0);
        if(e.getKeyCode() == KeyEvent.VK_UP)
            santa.move(0,-MOVE);
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            santa.move(0,MOVE);
        if(e.getKeyCode() == KeyEvent.VK_P)
            board.changePauseState();
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            santa.putGift();
    }
}
