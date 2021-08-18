/*
David Tsibulsky 309444065
Omri Haham 308428226
 */

package il.ac.shenkar.calculator;

import javax.swing.*;

/**
 * Simple calculator app, implementing View-Mode version 1.0 with java.swing
 */
public class SimpleCalculatorApp {
    public static void main(String[] argsp) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MySimpleCalculatorView view = new MySimpleCalculatorView();
                view.start();
            }
        });

    }
}