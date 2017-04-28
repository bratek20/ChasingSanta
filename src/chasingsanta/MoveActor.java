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
abstract public class MoveActor extends Actor{
    protected int destX, destY;
    protected boolean isMoving = false;
    private int speed = 10;
    public MoveActor(int size, GameBoard board){
        super(size, board);
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    private int sign(int x){
        if(x==0)return 0;
        if(x<0)return -1;
        return 1;
    }
    
    private float scale(int up, int down){
        up = Math.abs(up); down = Math.abs(down);
        return ((float)up)/(down+up);
    }
    private int shift(int sign, int d, int dest){
        if(sign < 0)return Math.max(d, dest);
        return Math.min(d, dest);
    }
    public void update(){
        super.update();
        
        if(!isMoving)return;
        
        int dx = destX - x;
        int dy = destY - y;
        if(dx == 0 && dy == 0){
            isMoving = false;
            return;
        }
        
        if(dx<0)flipped = true;
        else flipped = false;
        
        int oldX = x, oldY = y;
        x = shift(dx,x+(int)(scale(dx,dy)*speed*sign(dx)),destX);
        y = shift(dy,y+(int)(scale(dy,dx)*speed*sign(dy)),destY);
        
        fix(x,destX, GameBoard.bWIDTH); 
        fix(y,destY, GameBoard.bHEIGHT);
        
        if(board.collide(this)){
            x = oldX;
            y = oldY;
            isMoving = false;
        }
    }
    
    private int boardMod(int p, int mod){
        p%=mod; p+=mod; p%=mod;
        return p;
    }
    private void fix(int p, int dp, int mod){
        if(boardMod(p,mod) != p && boardMod(dp,mod) != dp){
            if(mod == GameBoard.bWIDTH){
                x = boardMod(p,mod);
                destX = boardMod(dp,mod);
            }
            else{
                y = boardMod(p,mod);
                destY = boardMod(dp,mod);
            }
        }
    }
    public void setDestPoint(int destX, int destY){
        isMoving = true;
        this.destX = destX;
        this.destY = destY;
    }
    public void move(int dx, int dy){
        setDestPoint(x+dx,y+dy);
    }
}
