import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		Calculator c = new Calculator();
		c.setResizable(false);
		c.setPreferredSize(new Dimension(500,600)); //need to use this instead of setSize
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.pack();
        c.setVisible(true);
	}

}
