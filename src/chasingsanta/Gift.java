/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

/**
 *
 * @author BRudzki
 */
public class Gift extends Actor{
    public final static int GIFT_SIZE = 20;
    private boolean taken = false;
    public Gift(int x, int y, GameBoard board){
        super(GIFT_SIZE, board);
        this.x = x; this.y = y;
        
        setSprite(Assets.gift);
    }
    
    public void reinit(GameBoard board){
        super.reinit(board);
        setSprite(Assets.gift);
    }
    
    public boolean isTaken(){
        return taken;
    }
    public void take(){
        taken = true;
    }
    @Override
    public void update() {
    }
    
}
