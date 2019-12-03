package Minisweep;

import java.awt.*;
import javax.swing.*;

public class Driver extends JFrame{
	private JLabel status;
    public Driver() { 	 
    	 status = new JLabel("");
    	 add(status, BorderLayout.SOUTH);
         add(new Field(status));
         setLocationRelativeTo(null);
         setResizable(false);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         pack();
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Driver window = new Driver();
            window.setVisible(true);
        });
    }
}