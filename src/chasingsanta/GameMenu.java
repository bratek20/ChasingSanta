/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chasingsanta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author BRudzki
 */
public class GameMenu extends JMenuBar{
    public GameMenu(GameBoard board){
        JMenu menu = new JMenu("Game");
        
        JMenuItem start = new JMenuItem("start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.startGame();
            }
        });
        menu.add(start);
        
        JMenuItem load = new JMenuItem("load");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.loadGame();
            }
        });
        menu.add(load);
        
        add(menu);
    }
}
