/*
David Tsibulsky 309444065
Omri Haham 308428226
 */

package il.ac.shenkar.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

/**
 * Simple calculator implementation - supporting the 4 basic operations (Add, Subtract, Multiply, Divide)
 */
public class MySimpleCalculatorView {
    // Init
    private JTextField tf1;
    private JButton bt[];
    private JFrame frame;
    private JPanel panel1, panel2;
    private ActionListener buttonsListener;
    private String buttonSimbol[];
    private String calculatorDisplayText = "0";
    private double prevNumber = 0;
    private String prevAction = "";
    private double result = 0;

    /**
     * Constructor
     */
    public MySimpleCalculatorView() {
        buttonSimbol = new String[]{"7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", ".", "0", "=", "/"};
        frame = new JFrame("My Calculator");
        tf1 = new JTextField(10);
        bt = new JButton[16];
        for (int i = 0; i < 16; i++) {
            bt[i] = new JButton(buttonSimbol[i]);
        }
        panel1 = new JPanel();
        panel2 = new JPanel();
    }

    /**
     * creating the user interface
     */
    public void start() {
        // Window Settings
        panel1.setLayout(new FlowLayout());
        frame.setLayout(new BorderLayout());
        panel1.add(tf1, BorderLayout.CENTER);
        frame.add(panel1, BorderLayout.NORTH);
        panel2.setBackground(Color.LIGHT_GRAY);
        panel2.setLayout(new GridLayout(4, 4));
        for (JButton button : bt) {
            panel2.add(button);
        }
        frame.add(panel2, BorderLayout.CENTER);
        frame.setSize(280, 400);
        frame.setVisible(true);
        Font bigFont = tf1.getFont().deriveFont(Font.PLAIN, 30f);
        tf1.setFont(bigFont);

        // init calculator result
        tf1.setText(String.valueOf(calculatorDisplayText));
        DecimalFormat df = new DecimalFormat("#.####");

        //creating buttons listener
        buttonsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (calculatorDisplayText == "0")
                    calculatorDisplayText = "";

                //  calculatorDisplayText
                String buttonText = ((JButton) e.getSource()).getText();
                switch (buttonText) {
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                        // last pressed operator only
                        if (!(prevAction == "+" || prevAction == "-" || prevAction == "*" || prevAction == "/")) {
                            prevNumber = Double.parseDouble(calculatorDisplayText);
                            calculatorDisplayText = "";
                        }
                        prevAction = buttonText;
                        break;
                    case "=":
                        switch (prevAction) {
                            case "+":
                                result = prevNumber + Double.parseDouble(calculatorDisplayText);
                                break;
                            case "-":
                                result = prevNumber - Double.parseDouble(calculatorDisplayText);
                                break;
                            case "*":
                                result = prevNumber * Double.parseDouble(calculatorDisplayText);
                                break;
                            case "/":
                                result = prevNumber / Double.parseDouble(calculatorDisplayText);
                                break;

                            default:
                                result = Double.parseDouble(calculatorDisplayText);
                        }

                        // convert to string with DecimalFormat (maximum trailing digits applied)
                        String resultToString = df.format(result);

                        // result integer display ( #.x to just #, if needed)
                        if (resultToString.endsWith(".0"))
                            resultToString = resultToString.substring(0, resultToString.length() - 2);

                        // check for 0 division
                        if (resultToString == "Infinity")
                            calculatorDisplayText = "ERROR";
                        else
                            calculatorDisplayText = resultToString;

                        prevAction = buttonText;
                        break;

                    default:
                        if (prevAction == "=") {
                            calculatorDisplayText = "";
                            prevAction = "";
                        }

                        calculatorDisplayText += buttonText;
                }

                //Displaying the last result to the view screen.
                tf1.setText(String.valueOf(calculatorDisplayText));
            }
        };

        //Handling the events
        for (JButton button : bt) {
            button.addActionListener(buttonsListener);
        }

        // Handling window closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }

}
