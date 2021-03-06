/**
 * Note: The following base code is from the following source:
 * https://classes.engineering.wustl.edu/cse501/labs/Lab1/Lab1.htm
 * File: CalculatorGUI.java
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class CalculatorGUI extends JFrame {

    protected boolean calculateAsInt;
    protected Calculator calculator;
    protected BinaryOperator operator;
    protected JTextField display, memoryDisplay;


    public CalculatorGUI () {
        super("My Java Calculator");
        this.calculator = new Calculator();

        initGUI();
    }

    private void initGUI () {
        JPanel buttonPanel = new JPanel();
        JPanel typeOptionPanel = new JPanel();
        setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(8, 5));

        this.calculateAsInt = false;
        this.display = new JTextField(20);
        this.memoryDisplay = new JTextField(5);

        // display.setEditable(false);

        JButton one = new JButton("1");
        JButton two = new JButton("2");
        JButton three = new JButton("3");
        JButton four = new JButton("4");
        JButton five = new JButton("5");
        JButton six = new JButton("6");
        JButton seven = new JButton("7");
        JButton eight = new JButton("8");
        JButton nine = new JButton("9");
        JButton zero = new JButton("0");
        JButton plusMinus = new JButton("+/-");
        JButton point = new JButton(".");

        JButton memoryClear = new JButton("MC");
        JButton memoryRecall = new JButton("MR");
        JButton memoryStore = new JButton("MS");

        JButton clear = new JButton("C");
        JButton divide = new JButton("/");
        JButton multiply = new JButton("*");
        JButton minus = new JButton("-");
        JButton plus = new JButton("+");
        JButton equals = new JButton("=");
        JButton modulus = new JButton("%");
        JButton squareRoot = new JButton("sqrt");
        JButton toCelcius = new JButton("to C");
        JButton toFarenheit = new JButton("to F");
        JButton pi = new JButton("pi");
        JButton e = new JButton("e");
        JButton naturalLog = new JButton("ln");
        JButton sin = new JButton("sin");
        JButton cos = new JButton("cos");
        JButton tan = new JButton("tan");
        JButton and = new JButton("AND");
        JButton or = new JButton("OR");
        JButton nand = new JButton("NAND");
        JButton nor = new JButton("NOR");
        JButton xor = new JButton("XOR");
        JButton not = new JButton("NOT");
        JButton trueButton = new JButton("true");
        JButton falseButton = new JButton("false");

        JRadioButton intType = new JRadioButton("Integer");
        JRadioButton doubleType = new JRadioButton("Floating Point", true);
        ButtonGroup typeGroup = new ButtonGroup();

        one.addActionListener(new ValueActionListener("1"));
        two.addActionListener(new ValueActionListener("2"));
        three.addActionListener(new ValueActionListener("3"));
        four.addActionListener(new ValueActionListener("4"));
        five.addActionListener(new ValueActionListener("5"));
        six.addActionListener(new ValueActionListener("6"));
        seven.addActionListener(new ValueActionListener("7"));
        eight.addActionListener(new ValueActionListener("8"));
        nine.addActionListener(new ValueActionListener("9"));
        zero.addActionListener(new ValueActionListener("0"));
        point.addActionListener(new ValueActionListener("."));

        memoryClear.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                calculator.memoryClear();
                double storedInMemory = calculator.memoryRecall();
                setMemoryDisplay("" + storedInMemory);
            }
        });

        memoryRecall.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                double storedInMemory = calculator.memoryRecall();
                display.setText("" + storedInMemory);
            }
        });

        memoryStore.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                double displayedDouble = getDisplayedDouble();
                calculator.memoryStore(displayedDouble);
                double storedInMemory = calculator.memoryRecall();
                setMemoryDisplay("" + storedInMemory);
            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                pushOperator(null);
            }
        });

        toCelcius.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                double farenheit = getDisplayedDouble();
                double celcius = calculator.toCelsius(farenheit);
                setDisplayedValue(celcius);
            }
        });

        toFarenheit.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                double celcius = getDisplayedDouble();
                double farenheit = calculator.toFahrenheit(celcius);
                setDisplayedValue(farenheit);
            }
        });

        /* Arithmetic */
        divide.addActionListener(new OperatorListener(new DivideOperator()));
        multiply.addActionListener(new OperatorListener(new MultiplyOperator()));
        minus.addActionListener(new OperatorListener(new MinusOperator()));
        plus.addActionListener(new OperatorListener(new PlusOperator()));
        modulus.addActionListener(new OperatorListener(new ModulusOperator()));
        /* Unary */
        cos.addActionListener(new UnaryOperatorListener(new CosineOperator()));
        sin.addActionListener(new UnaryOperatorListener(new SineOperator()));
        tan.addActionListener(new UnaryOperatorListener(new TangentOperator()));
        squareRoot.addActionListener(new UnaryOperatorListener(new SquareRootOperator()));
        naturalLog.addActionListener(new UnaryOperatorListener(new NaturalLogOperator()));
        /* Boolean */
        and.addActionListener(new OperatorListener(new AndOperator()));
        or.addActionListener(new OperatorListener(new OrOperator()));
        nand.addActionListener(new OperatorListener(new NandOperator()));
        nor.addActionListener(new OperatorListener(new NorOperator()));
        xor.addActionListener(new OperatorListener(new XorOperator()));
        not.addActionListener(new UnaryOperatorListener(new NotOperator()));

        trueButton.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                setDisplayedValue("true");
            }
        });

        falseButton.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                setDisplayedValue("false");
            }
        });

        e.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                double pi = calculator.e();
                setDisplayedValue(pi);
            }
        });

        pi.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                double e = calculator.pi();
                setDisplayedValue(e);
            }
        });

        equals.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                if (operator == null)
                    return;

                String operandTwo = getDisplayedValue();
                operator.setOperandTwo(operandTwo);
                String result = operator.compute();
                setDisplayedValue(result);
                popOperator();
            }
        });

        intType.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                calculateAsInt = true;
            }
        });

        doubleType.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                calculateAsInt = false;
            }
        });

        plusMinus.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                changeSign();
            }
        });

        buttonPanel.add(memoryDisplay);
        buttonPanel.add(memoryClear);
        buttonPanel.add(memoryStore);
        buttonPanel.add(memoryRecall);
        buttonPanel.add(clear);

        buttonPanel.add(one);
        buttonPanel.add(two);
        buttonPanel.add(three);
        buttonPanel.add(plus);
        buttonPanel.add(squareRoot);
        buttonPanel.add(four);
        buttonPanel.add(five);
        buttonPanel.add(six);
        buttonPanel.add(minus);
        buttonPanel.add(naturalLog);
        buttonPanel.add(seven);
        buttonPanel.add(eight);
        buttonPanel.add(nine);
        buttonPanel.add(divide);
        buttonPanel.add(modulus);
        buttonPanel.add(plusMinus);
        buttonPanel.add(zero);
        buttonPanel.add(point);
        buttonPanel.add(multiply);
        buttonPanel.add(equals);
        buttonPanel.add(sin);
        buttonPanel.add(cos);
        buttonPanel.add(tan);
        buttonPanel.add(pi);
        buttonPanel.add(e);
        buttonPanel.add(and);
        buttonPanel.add(or);
        buttonPanel.add(nand);
        buttonPanel.add(nor);
        buttonPanel.add(xor);
        buttonPanel.add(not);
        buttonPanel.add(trueButton);
        buttonPanel.add(falseButton);

        buttonPanel.add(toCelcius);
        buttonPanel.add(toFarenheit);

        typeGroup.add(intType);
        typeGroup.add(doubleType);

        typeOptionPanel.setBorder(new TitledBorder("Calculate As:"));
        typeOptionPanel.add(intType);
        typeOptionPanel.add(doubleType);

        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(typeOptionPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 350);
        setVisible(true);
        setResizable(false);
    }

    protected String getDisplayedValue () {
        return this.display.getText();
    }

    protected boolean getDisplayedBolean () {
        String displayText = getDisplayedValue();
        boolean displayValue;

        if (displayText == null || displayText.length() == 0)
            displayValue = false;
        else if (!displayText.equals("true") && !displayText.equals("false")) {
            double numericValue = Double.parseDouble(displayText);
            /* A number. True if anything but 0. */
            displayValue = (numericValue != 0.0);
        }
        else displayValue = Boolean.parseBoolean(displayText);
        return displayValue;
    }

    protected double getDisplayedDouble () {
        String displayText = getDisplayedValue();
        double displayValue;

        if (displayText == null || displayText.length() == 0)
            displayValue = 0;
        else if (displayText.equals("true"))
            displayValue = 1;
        else if (displayText.equals("false"))
            displayValue = 0;
        else displayValue = Double.parseDouble(displayText);

        return displayValue;
    }

    protected void setDisplayedValue (double value) {
        setDisplayedValue("" + value);
    }

    protected void setDisplayedValue (String value) {
        this.display.setText(value);
    }

    protected void setMemoryDisplay (String value) {
        this.memoryDisplay.setText(value);
    }

    protected void pushValue (String value) {
        String newText = getDisplayedValue();

        /* Prevent leading zeros. */
        if (newText != null && newText.length() > 0) {
            if (newText.charAt(0) == '0' || newText.equals("0.0"))
                newText = "";
            else if (newText.equals("true") || newText.equals("false"))
                newText = "";
        }
        if (value.equals(".") && newText.contains("."))
            return;

        newText += value;
        this.display.setText(newText);
    }

    protected void changeSign () {
        double value = getDisplayedDouble();
        value = calculator.negate(value);
        setDisplayedValue(value);
    }

    protected void popOperator () {
        this.operator = null;
    }

    protected void pushOperator (BinaryOperator operator) {
        if (operator != null && this.operator != null) {
            String operandTwo = getDisplayedValue();
            this.operator.setOperandTwo(operandTwo);
            String result = this.operator.compute();
            operator.setOperand(result);
            setDisplayedValue(result);
        }
        setDisplayedValue("0");
        this.operator = operator;
    }

    class OperatorListener implements ActionListener {

        protected BinaryOperator operator;

        public OperatorListener (BinaryOperator operator) {
            this.operator = operator;
        }

        public void actionPerformed (ActionEvent ae) {
            String value = getDisplayedValue();
            operator.setOperand(value);
            pushOperator(operator);
        }
    }

    class UnaryOperatorListener implements ActionListener {

        protected UnaryOperator operator;

        public UnaryOperatorListener (UnaryOperator operator) {
            this.operator = operator;
        }

        public void actionPerformed (ActionEvent ae) {
            String value = getDisplayedValue();
            operator.setOperand(value);
            String result = operator.compute();
            setDisplayedValue(result);
        }
    }

    abstract class UnaryOperator {

        protected String operand;

        public abstract String compute ();

        public void setOperand (String operand) {	this.operand = operand; }
    }

    abstract class NumericUnaryOperator extends UnaryOperator {
        public String compute () {
            double doubleOperand = 0.0;
            try {
                doubleOperand = Double.parseDouble(this.operand);
            }
            catch (Exception e) {} // Do nothing - leave value at 0.
            return compute(doubleOperand);
        }

        public abstract String compute (double operator);
    }

    abstract class BooleanUnaryOperator extends UnaryOperator {
        public String compute () {
            boolean booleanOperand = false;
            try {
                booleanOperand = Boolean.parseBoolean(this.operand);
            }
            catch (Exception e) {}	// Do nothing - leave value as false
            return compute(booleanOperand);
        }

        public abstract String compute (boolean operator);
    }

    abstract class BinaryOperator extends UnaryOperator {

        protected String operandTwo;

        public BinaryOperator () {
        }

        public void setOperandTwo (String operand) {	this.operandTwo = operand; }

    }

    abstract class BooleanBinaryOperator extends BinaryOperator {
        public String compute () {
            boolean booleanOperand = false;
            boolean booleanOperandTwo = false;
            try {
                booleanOperand = Boolean.parseBoolean(operand);
                booleanOperandTwo = Boolean.parseBoolean(operandTwo);
            }
            catch (Exception e) {} // Do nothing. Leave with intial values.
            return compute(booleanOperand, booleanOperandTwo);
        }

        public abstract String compute (boolean operandOne, boolean operandTwo);
    }

    class NotOperator extends BooleanUnaryOperator {
        public String compute (boolean operand) {
            return calculator.not(operand) + "";
        }
    }

    class AndOperator extends BooleanBinaryOperator {
        public String compute (boolean operandOne, boolean operandTwo) {
            return calculator.and(operandOne, operandTwo) + "";
        }
    }

    class OrOperator extends BooleanBinaryOperator {
        public String compute (boolean operandOne, boolean operandTwo) {
            return calculator.or(operandOne, operandTwo) + "";
        }
    }

    class NandOperator extends BooleanBinaryOperator {
        public String compute (boolean operandOne, boolean operandTwo) {
            return calculator.nand(operandOne, operandTwo) + "";
        }
    }

    class NorOperator extends BooleanBinaryOperator {
        public String compute (boolean operandOne, boolean operandTwo) {
            return calculator.nor(operandOne, operandTwo) + "";
        }
    }

    class XorOperator extends BooleanBinaryOperator {
        public String compute (boolean operandOne, boolean operandTwo) {
            return calculator.xor(operandOne, operandTwo) + "";
        }
    }

    abstract class NumericBinaryOperator extends BinaryOperator {
        public String compute () {
            double doubleOperand = 0.0;
            double doubleOperandTwo = 0.0;
            try {
                doubleOperand = Double.parseDouble(operand);
                doubleOperandTwo = Double.parseDouble(operandTwo);
            }
            catch (Exception e) {} // Do nothing - leave with initial values.
            return compute(doubleOperand, doubleOperandTwo);
        }

        public abstract String compute (double operandOne, double operandTwo);
    }

    class SquareRootOperator extends NumericUnaryOperator {
        public String compute (double operand) {
            return calculator.sqrt(operand) + "";
        }
    }

    class NaturalLogOperator extends NumericUnaryOperator {
        public String compute (double operand) {
            return calculator.ln(operand) + "";
        }
    }

    class CosineOperator extends NumericUnaryOperator {
        public String compute (double operand) {
            return calculator.cos(operand) + "";
        }
    }

    class SineOperator extends NumericUnaryOperator {
        public String compute (double operand) {
            return calculator.sin(operand) + "";
        }
    }

    class TangentOperator extends NumericUnaryOperator {
        public String compute (double operand) {
            return calculator.tan(operand) + "";
        }
    }

    class PlusOperator extends NumericBinaryOperator {
        public String compute (double operand, double operandTwo) {
            if (calculateAsInt) {
                int sum = calculator.plus((int)operand, (int)operandTwo);	// Cast operands to ints to invoke plus(int, int)
                return sum + "";
            }
            return calculator.plus(operand, operandTwo) + "";
        }
    }

    class MinusOperator extends NumericBinaryOperator {
        public String compute (double operand, double operandTwo) {
            if (calculateAsInt) {
                int difference = calculator.minus((int)operand, (int)operandTwo);
                return difference + "";
            }
            return calculator.minus(operand, operandTwo) + "";
        }
    }

    class DivideOperator extends NumericBinaryOperator {
        public String compute (double operandOne, double operandTwo) {
            if (calculateAsInt) {
                int difference = calculator.divide((int)operandOne, (int)operandTwo);
                return difference + "";
            }
            return calculator.divide(operandOne, operandTwo) + "";
        }
    }

    class MultiplyOperator extends NumericBinaryOperator {
        public String compute (double operandOne, double operandTwo) {
            if (calculateAsInt) {
                int product = calculator.multiply((int)operandOne, (int)operandTwo);
                return product + "";
            }
            return calculator.multiply(operandOne, operandTwo) + "";
        }
    }

    class ModulusOperator extends NumericBinaryOperator {
        public String compute (double operandOne, double operandTwo) {
            return calculator.modulus((int)operandOne, (int)operandTwo)  + "";
        }
    }

    class ValueActionListener implements ActionListener {
        protected String value;

        public ValueActionListener (String value) {
            this.value = value;
        }

        public void actionPerformed (ActionEvent event) {
            pushValue(this.value);
        }
    }
}
