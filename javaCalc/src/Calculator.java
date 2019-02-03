import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.*;
import java.util.*;

public class Calculator extends JFrame {
	private double savedValue;
	private double displayedValue;
	NumberFormat formatter;
	int decimal;
	char operation = 'o';
	
	/*private JButton b0;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	private JButton b5;
	private JButton b6;
	private JButton b7;
	private JButton b8;
	private JButton b9;*/
	
	private JButton[] digits;
	private JButton dec;
	private JButton neg;
	
	private JButton equal;
	private JButton plus;
	private JButton minus;
	private JButton times;
	private JButton divide;
	private JButton square;
	private JButton exponent;
	private JButton clear;
	private JButton clearentry;
	
	private JTextField display;
	
	// constructor
	
	public Calculator() {
		savedValue = 0;
		displayedValue = 0;
		decimal = 0;
		formatter = new DecimalFormat("########.########");
		
		digits = new JButton[10];
		for(int i = 0; i < 10; i++) {
			digits[i] = new JButton(String.valueOf(i));
			if(i == 0) {
				digits[i].setPreferredSize(new Dimension(145,70));
			}
			else {
				digits[i].setPreferredSize(new Dimension(70,70));
			}
			int number = i;
			digits[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(decimal > 0) {
						displayedValue = displayedValue + (number * Math.pow(10, (-1 * decimal)));
						decimal++;
						updateDisplay();
					}
					else {
						displayedValue = displayedValue * 10 + number;
						updateDisplay();
					}
				}
			});
		}
		
		dec = new JButton(".");
		dec.setPreferredSize(new Dimension(70, 70));
		
		neg = new JButton("+/-");
		neg.setPreferredSize(new Dimension(70, 70));
		
		equal = new JButton("=");
		equal.setPreferredSize(new Dimension(70, 145));
		
		plus = new JButton("+");
		plus.setPreferredSize(new Dimension(70, 70));
		
		minus = new JButton("-");
		minus.setPreferredSize(new Dimension(70, 70));
		
		times = new JButton("X");
		times.setPreferredSize(new Dimension(70, 70));
		
		divide = new JButton("/");
		divide.setPreferredSize(new Dimension(70, 70));
		
		square = new JButton("^2");
		square.setPreferredSize(new Dimension(70, 70));
		
		exponent = new JButton("^");
		exponent.setPreferredSize(new Dimension(70, 70));
		
		clear = new JButton("AC/C");
		clear.setPreferredSize(new Dimension(145, 70));
		
		clearentry = new JButton("CE");
		clearentry.setPreferredSize(new Dimension(70, 70));
		
		// Implement non-operational listeners
		
		dec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (decimal == 0) {
					decimal++;
				}
			}
		});
		
		neg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayedValue = displayedValue * -1;
				updateDisplay();
			}
		});
		
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearScreen();
			}
		});
		
		clearentry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(decimal > 0) {
					displayedValue = (int) (displayedValue * Math.pow(10, decimal - 1)) / Math.pow(10, decimal - 1);
					decimal--;
					updateDisplay();
				}
				else {
					displayedValue = (int) (displayedValue / 10);
					updateDisplay();
				}
			}
		});
		
		// Implement operation listeners
		
		times.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				operation = '*';
				saveValue();
			}
		});
		
		divide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				operation = '/';
				saveValue();
			}
		});
		
		minus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				operation = '-';
				saveValue();
			}
		});
		
		plus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				operation = '+';
				saveValue();
			}
		});
		
		exponent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				operation = '^';
				saveValue();
			}
		});
		
		square.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayedValue = displayedValue * displayedValue;
				updateDisplay();
			}
		});
		
		// Equal Listener
		
		equal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double result;
				switch(operation) {
				case '+':
					result = savedValue + displayedValue;
					displayedValue = result;
					updateDisplay();
					break;
				case '-':
					result = savedValue - displayedValue;
					displayedValue = result;
					updateDisplay();
					break;
				case '*':
					result = savedValue * displayedValue;
					displayedValue = result;
					updateDisplay();
					break;
				case '/':
					result =  savedValue / displayedValue;
					displayedValue = result;
					updateDisplay();
					break;
				case '^':
					result = Math.pow(savedValue, displayedValue);
					displayedValue = result;
					updateDisplay();
				}
			}
		});
		
		// First part of buttons

		JPanel buttons1 = new JPanel();
		FlowLayout buttonlayout1 = new FlowLayout();
		buttons1.setPreferredSize(new Dimension(230, 450));
		buttons1.setLayout(buttonlayout1);
		
		buttons1.add(clear);
		buttons1.add(clearentry);
		buttons1.add(exponent);
		buttons1.add(square);
		buttons1.add(neg);
		
		for(int i = 1; i < 10; i++){
			buttons1.add(digits[i]);
		}
		buttons1.add(digits[0]);
		buttons1.add(dec);
		
		JPanel buttons2 = new JPanel();
		FlowLayout buttonlayout2 = new FlowLayout();
		buttons2.setPreferredSize(new Dimension(70, 445));
		buttons2.setLayout(buttonlayout2);
		
		buttons2.add(plus);
		buttons2.add(minus);
		buttons2.add(times);
		buttons2.add(divide);
		buttons2.add(equal);
		
		// Combine buttons
		
		JPanel buttonPanel = new JPanel();
		FlowLayout layout1 = new FlowLayout();
		buttons2.setPreferredSize(new Dimension(70, 450));
		buttons2.setLayout(layout1);
		
		buttonPanel.add(buttons1);
		buttonPanel.add(buttons2);
		
		// TextField
		
		JPanel textPanel = new JPanel();
		display = new JTextField("0", 10);
		display.setEditable(false);
		display.setPreferredSize(new Dimension(100, 70));
		Font bigFont = display.getFont().deriveFont(Font.PLAIN, 40f);
		display.setFont(bigFont);
		display.setHorizontalAlignment(JTextField.RIGHT);
		textPanel.add(display);
		
		// Put both components onto frame
		
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(textPanel);
		p.add(buttonPanel);
		
		add(p);
	}
	
	// Display functions
	
	public void updateDisplay(){
		display.setText(formatter.format(displayedValue));
	}
	
	public void resetDisplay(){
		displayedValue = 0;
		decimal = 0;
		updateDisplay();
	}
	
	public void allCancel(){
		savedValue = 0;
		operation = 'o';
		resetDisplay();
	}
	
	public void clearScreen(){
		if(displayedValue == 0) {
			allCancel();
		}
		else resetDisplay();
	}

	public void saveValue() {
		savedValue = displayedValue;
		clearScreen();
	}

}
