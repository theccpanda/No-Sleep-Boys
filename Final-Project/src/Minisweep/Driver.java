package Minisweep;

import java.awt.*;
import javax.swing.*;

public class Driver extends JFrame {
    public Driver() { 	 
         add(new Field());
         setLocationRelativeTo(null);
         setResizable(false);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         pack();
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var window = new Driver();
            window.setVisible(true);
        });
    }
}