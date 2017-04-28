/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author BRudzki
 */
public class GameWindow extends JFrame{
    private GameBoard board;
    public GameWindow(){
        super("ChasingSanta");
        
        setSize(800, 600);
        setLocation(100, 100);
        
        board = new GameBoard();
        add(new GameMenu(board), BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                board.saveGame();
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
