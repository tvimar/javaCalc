import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.*;

public class Calculator extends JFrame {
	private double _savedValue;
	private double _displayedValue;
	private NumberFormat _formatter;
	private int _decimal;
	private char _operation = 'o';
	
	private JButton[] _digits;
	private JButton _dec;
	private JButton _neg;
	
	private JButton _equal;
	private JButton _plus;
	private JButton _minus;
	private JButton _times;
	private JButton _divide;
	private JButton _square;
	private JButton _exponent;
	private JButton _clear;
	private JButton _clearentry;
	
	private JTextField _display;
	
	// constructor
	
	public Calculator() {
		_savedValue = 0;
		_displayedValue = 0;
		_decimal = 0;
		_formatter = new DecimalFormat("########.########");
		
		// Make Buttons;
		
		makeButtons();
		
		// Implement listeners
		
		implementListeners();
		
		// Make ButtonPanel
		
		JPanel buttonPanel = makeButtonPanel();
		
		// TextField
		
		JPanel textPanel = makeTextPanel();
		
		// Put both panels onto frame
		
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(textPanel);
		p.add(buttonPanel);
		
		add(p);
	}
	
	// Display functions
	
	public void updateDisplay(){
		_display.setText(_formatter.format(_displayedValue));
	}
	
	public void resetDisplay(){
		_displayedValue = 0;
		_decimal = 0;
		updateDisplay();
	}
	
	public void allCancel(){
		_savedValue = 0;
		_operation = 'o';
		resetDisplay();
	}
	
	public void clearScreen(){
		if(_displayedValue == 0) {
			allCancel();
		}
		else resetDisplay();
	}

	public void saveValue() {
		_savedValue = _displayedValue;
		clearScreen();
	}
	
	public void changeOperation(char op) {
		_operation = op;
		saveValue();
	}

	// Initialization Functions
	
	private JButton makeButton(String text, int height, int width) {
		JButton newButton;
		
		newButton = new JButton(text);
		newButton.setPreferredSize(new Dimension(height, width));
		return newButton;
	}
	
	private void makeButtons(){
		_digits = makeDigitButtons();
		makeNonDigitButtons();
	}
	
	private JButton[] makeDigitButtons(){
		JButton[] digits = new JButton[10];
		for(int i = 0; i < 10; i++) {
			if(i == 0) {
				digits[i] = makeButton(String.valueOf(i), 145, 70);
			}
			else {
				digits[i] = makeButton(String.valueOf(i), 70, 70);
			}
			int number = i;
			digits[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(_decimal > 0) {
						_displayedValue = _displayedValue + (number * Math.pow(10, (-1 * _decimal)));
						_decimal++;
						updateDisplay();
					}
					else {
						_displayedValue = _displayedValue * 10 + number;
						updateDisplay();
					}
				}
			});
		}
		return digits;
	}	
	
	private void makeNonDigitButtons() {		
		_dec = makeButton(".", 70, 70);
		_neg = makeButton("+/-", 70, 70);
		_equal = makeButton("=", 70, 145);
		_plus = makeButton("+", 70, 70);
		_minus = makeButton("-", 70, 70);
		_times = makeButton("X", 70, 70);
		_divide = makeButton("/", 70, 70);
		_square = makeButton("^2", 70, 70);
		_exponent = makeButton("^", 70, 70);
		_clear = makeButton("AC/C", 145, 70);
		_clearentry = makeButton("CE", 70, 70);
	}
	
	private void implementListeners() {
		// Implement non-operational listeners
		implementNonOperationListeners();
		
		// Implement operation listeners
		implementOperationListeners();
		
		// Equal Listener
		implementEqualListener();
	}
	
	private void implementNonOperationListeners() {
		_dec.addActionListener(e -> {if(_decimal == 0) _decimal++;});
		
		_neg.addActionListener(e -> {
				_displayedValue = _displayedValue * -1;
				updateDisplay();
			}
		);
		
		_clear.addActionListener(e -> {clearScreen();});
		
		_clearentry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(_decimal > 0) {
					_displayedValue = (int) (_displayedValue * Math.pow(10, _decimal - 1)) / Math.pow(10, _decimal - 1);
					_decimal--;
					updateDisplay();
				}
				else {
					_displayedValue = (int) (_displayedValue / 10);
					updateDisplay();
				}
			}
		});
	}
	
	private void implementOperationListeners() {
		_times.addActionListener( e-> changeOperation('*'));
		_divide.addActionListener( e-> changeOperation('/'));
		_minus.addActionListener( e-> changeOperation('-'));
		_plus.addActionListener( e-> changeOperation('+'));
		_exponent.addActionListener( e-> changeOperation('^'));
		
		_square.addActionListener(e -> {
				_displayedValue = _displayedValue * _displayedValue;
				updateDisplay();
			}
		);
	}
	
	private void implementEqualListener() {
		_equal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double result = 0; // Init'd since was getting an error
				switch(_operation) {
				case '+':
					result = _savedValue + _displayedValue;
					break;
				case '-':
					result = _savedValue - _displayedValue;
					break;
				case '*':
					result = _savedValue * _displayedValue;
					break;
				case '/':
					result =  _savedValue / _displayedValue;
					break;
				case '^':
					result = Math.pow(_savedValue, _displayedValue);
				}
				_displayedValue = result;
				updateDisplay();
			}
		});
	}
	
	private JPanel makeButtonPanel() {
		JPanel buttons1 = makeFirstButtonPanel();
		JPanel buttons2 = makeSecondButtonPanel();
			
		// Combine buttons
		
		JPanel buttonPanel = new JPanel();
		FlowLayout layout1 = new FlowLayout();
		buttons2.setPreferredSize(new Dimension(70, 450));
		buttons2.setLayout(layout1);
		
		buttonPanel.add(buttons1);
		buttonPanel.add(buttons2);
		
		return buttonPanel;
	}
	
	private JPanel makeFirstButtonPanel() {

		JPanel buttons1 = new JPanel();
		FlowLayout buttonlayout1 = new FlowLayout();
		buttons1.setPreferredSize(new Dimension(230, 450));
		buttons1.setLayout(buttonlayout1);
		
		buttons1.add(_clear);
		buttons1.add(_clearentry);
		buttons1.add(_exponent);
		buttons1.add(_square);
		buttons1.add(_neg);
		
		for(int i = 1; i < 10; i++){
			buttons1.add(_digits[i]);
		}
		buttons1.add(_digits[0]);
		buttons1.add(_dec);
		
		return buttons1;
	}
	
	private JPanel makeSecondButtonPanel() {
			
		JPanel buttons2 = new JPanel();
		FlowLayout buttonlayout2 = new FlowLayout();
		buttons2.setPreferredSize(new Dimension(70, 445));
		buttons2.setLayout(buttonlayout2);
		
		buttons2.add(_plus);
		buttons2.add(_minus);
		buttons2.add(_times);
		buttons2.add(_divide);
		buttons2.add(_equal);
		
		return buttons2;
	}
	
	private JPanel makeTextPanel() {
		JPanel textPanel = new JPanel();
		_display = new JTextField("0", 10);
		_display.setEditable(false);
		_display.setPreferredSize(new Dimension(100, 70));
		Font bigFont = _display.getFont().deriveFont(Font.PLAIN, 40f);
		_display.setFont(bigFont);
		_display.setHorizontalAlignment(JTextField.RIGHT);
		textPanel.add(_display);
		return textPanel;
	}
}
