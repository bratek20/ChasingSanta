/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BRudzki
 */
public class Child extends MoveActor implements Runnable{
    public static final int CHILD_SIZE = 40, REACH_GIFT_SIZE = 80,
            CHASE_DIST = 200, WAIT_TIME = 50, START_WAIT_TIME = 1500, REST_COOLDOWN = 50;
    private Square reachGiftSquare;
    private boolean gotGift = false;
    public Child(GameBoard board){
        super(CHILD_SIZE, board);
        this.board = board;
        
        setSprite(Assets.child);
        reachGiftSquare = new Square(REACH_GIFT_SIZE, this);
        cooldown = new Cooldown(REST_COOLDOWN);
    }
    
    public void reinit(GameBoard board){
        super.reinit(board);
        setSprite(Assets.child);
    }
    
    public boolean hasGift(){
        return gotGift;
    }
    
    public boolean inReach(Gift gift){
        return reachGiftSquare.intersects(gift.getCollisionSquare());
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(START_WAIT_TIME);
            while(!gotGift){
                while(cooldown.isCooling()){
                    Thread.sleep(WAIT_TIME);
                }
                
                if(board.findGift(this)){
                    gotGift = true;
                    continue;
                }
                
                chase();
                while(isMoving){
                    Thread.sleep(WAIT_TIME);
                }
                
                cooldown.start();  
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    int euklDist(int x1, int y1, int x2, int y2){
        return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
    }
    
    void chase(){
        int sx = board.getSantaX(); int sy = board.getSantaY();
        int w = GameBoard.bWIDTH; int h = GameBoard.bHEIGHT;
        int posX[] = {sx,sx+w,sx+w,sx-w,sx-w,sx+w,sx-w,sx  ,sx};
        int posY[] = {sy,sy+h,sy-h,sy+h,sy-h,  sy,  sy,sy+h,sy-h};
    
        int ans = 0; int dist = euklDist(x,y,sx,sy);
        for(int i=1;i<posX.length;i++){
            int tmpDist = euklDist(x,y,posX[i],posY[i]);
            if(tmpDist < dist){
                dist = tmpDist;
                ans = i;
            }
        }
        
        if(dist < CHASE_DIST*CHASE_DIST){
            setDestPoint(posX[ans], posY[ans]);
        }
        else{
            int signX = Assets.rand.nextInt(2) == 0 ? -1 : 1;
            int signY = Assets.rand.nextInt(2) == 0 ? -1 : 1;
            float d = Assets.rand.nextFloat();
            if(Assets.rand.nextInt(2) == 0){
                setDestPoint(x+signX*CHASE_DIST, y+(int)(signY*d*CHASE_DIST));
            }
            else{
                setDestPoint(x+(int)(signX*d*CHASE_DIST),y+signY*CHASE_DIST );
            }
        }
    }     
}
