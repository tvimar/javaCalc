import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Init {
	
	public static JButton makeButton(String text, int height, int width) {
		JButton newButton;
		
		newButton = new JButton(text);
		newButton.setPreferredSize(new Dimension(height, width));
		return newButton;
	}
}
