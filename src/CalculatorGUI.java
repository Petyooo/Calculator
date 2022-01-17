/**
 * Note: The following base code is from the following source:
 * https://classes.engineering.wustl.edu/cse501/labs/Lab1/Lab1.htm
 * File: CalculatorGUI.java
 */

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CalculatorGUI extends JFrame {

    protected boolean calculateAsInt;
    protected Calculator calculator;
    protected BinaryOperator operator;
    protected BinaryOperator getBinaryOperator() { return this.operator;}

    protected JTextField display, expressionDisplay, memoryDisplay;

    //The gui now keeps track of a unary operator, in addition to a binary operator
    protected UnaryOperator curUnaryOperator;
    protected UnaryOperator getCurUnaryOperator() { return this.curUnaryOperator;}
    protected void setCurUnaryOperator(UnaryOperator uOp) {this.curUnaryOperator = uOp;}

    public CalculatorGUI () {
        super("My Java Calculator");
        this.calculator = new Calculator();

        initGUI();
    }

    private void initGUI () {
        JPanel buttonPanel = new JPanel();
        JPanel typeOptionPanel = new JPanel();
        JPanel displayPanel = new JPanel();
        setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(9, 5));
        displayPanel.setLayout(new GridLayout(2, 2));

        Expression expression = new ExpressionBuilder("pi+sqrt(5)").build();
        this.calculateAsInt = false;
        this.expressionDisplay = new JTextField(20);
        this.display = new JTextField(20);
        this.memoryDisplay = new JTextField(5);
        //expressionDisplay.setText("Testing 123");
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
        JButton leftParenthesis = new JButton("(");
        JButton rightParenthesis = new JButton(")");

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
        JButton naturalLog = new JButton("log"); //ln did not work with ExpressionBuilder
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
        leftParenthesis.addActionListener(new expressionValueActionListener("("));
        rightParenthesis.addActionListener(new expressionValueActionListener(")"));

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
                setCurUnaryOperator(null);
                setExpressionValue("");
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
        //squareRoot.addActionListener(new expressionValueActionListener("sqrt("));

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
                pushValue("e");
            }
        });

        pi.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                pushValue("pi");
            }
        });

        // this method is critical in the new version
        // as the displayed expression can be parsed by analyzing
        // whether an '=' is present in it
        equals.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ae) {
                String operandTwo = getDisplayedValue();
                if(!getExpressionValue().contains("=")) {
                    if (operator == null && curUnaryOperator == null) {
                        if (operandTwo.equals("pi")) {
                            setDisplayedValue(calculator.pi());
                        } else if (operandTwo.equals("e")) {
                            setDisplayedValue(calculator.e());
                        } else if (operandTwo.equals(null)) {
                            return;
                        }
                    }

                    pushToExpression(operandTwo);
                    Expression expression = new ExpressionBuilder(getExpressionValue()).build();
                    double res = expression.evaluate();
                    pushToExpression("=");
                    setDisplayedValue(res);
                    popOperator();
                    setCurUnaryOperator(null);
                    //pushToExpression(operandTwo);
                } else {

                }
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

        // it worked out really well that this is not a UnaryOperator!!
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
        buttonPanel.add(leftParenthesis);
        buttonPanel.add(rightParenthesis);
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


        displayPanel.setBorder(BorderFactory.createEtchedBorder());
        displayPanel.add(expressionDisplay);
        displayPanel.add(display);

        add(displayPanel, BorderLayout.NORTH);
        //add(display, BorderLayout.NORTH);
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

    protected String getExpressionValue () {
        return this.expressionDisplay.getText();
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

    protected void setExpressionValue (String value) {
        this.expressionDisplay.setText(value);
    }

    protected void setMemoryDisplay (String value) {
        this.memoryDisplay.setText(value);
    }

    protected void pushValue (String value) {

        String newText = getDisplayedValue();

        if(getExpressionValue().contains("=")) {
            newText = "";
            setExpressionValue("");
        }

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
        if (this.curUnaryOperator == null) {
            this.display.setText(newText);
        } else { // here we push the value directly to the expression display!
            // otherwise what will happen? check it out
            this.display.setText("");
            pushToExpression(value);
        }
    }

    protected void pushToExpression (String value) {
        String newText = getExpressionValue();

        /* Prevent leading zeros. */
        if (newText == null || newText.contains("=")) { // if newText has an equal then we need to restart the expression!!!
            newText = "";
            setExpressionValue("");
        }

        newText += value;
        this.expressionDisplay.setText(newText);
    }

    protected void changeSign () {
        if(this.curUnaryOperator == null) {
            double value = getDisplayedDouble();
            value = calculator.negate(value);
            setDisplayedValue(value);
        }
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
//            if(getBinaryOperator() == null && getCurUnaryOperator() == null) { //this is the condition after you use equals
//                setExpressionValue("");
//                pushToExpression(getDisplayedValue()+this.operator.getSymbol());
//                setDisplayedValue(0);
//            } else if (getBinaryOperator() == null) {
//                pushToExpression(getDisplayedValue()+this.operator.getSymbol());
//                setDisplayedValue(0);
//            } else {
            String value = getDisplayedValue();
            if (getExpressionValue().contains("=")) {
                setExpressionValue("");
            }
            operator.setOperand(value);
            pushToExpression(value);
            pushToExpression(this.operator.getSymbol());
            pushOperator(operator);
//            }
        }
    }

    class UnaryOperatorListener implements ActionListener {

        protected UnaryOperator operator;

        public UnaryOperatorListener (UnaryOperator operator) {
            this.operator = operator;
        }

        public void actionPerformed (ActionEvent ae) {
            // I needed to add the getBinaryOperator method because it was getting confused with the "opertor" variable
            // within this class and the BinaryOperator in calcGUI even though they have different types
            if(curUnaryOperator == null && getBinaryOperator() == null) {
                String curRes = getDisplayedValue();

                // mathematical expression parser is here
                setExpressionValue(this.operator.getSymbol() + getDisplayedValue() + ")");
                Expression expression = new ExpressionBuilder(getExpressionValue()).build();
                double result = expression.evaluate();
                pushToExpression("="); //necessary so that the binaryOperator can recognize '=' in expressions
                //in order to refresh the expression display field.
                setDisplayedValue(result);
            } else {
                setCurUnaryOperator(this.operator);
                pushToExpression(this.operator.getSymbol());
            }

//            String value = getDisplayedValue();
//            operator.setOperand(value);
//            String result = operator.compute();
//            setDisplayedValue(result);
        }
    }

    abstract class UnaryOperator {

        protected String operand;
        protected String symbol;

        //they all had default constructors that were not explicitly shown before
        public UnaryOperator(String symbol) {
            this.symbol = symbol;
        }

        public abstract String compute ();

        public void setOperand (String operand) {	this.operand = operand; }

        //protected void setSymbol (String symbol) {	this.symbol = symbol; }

        public String getSymbol() {return symbol; }
    }

    abstract class NumericUnaryOperator extends UnaryOperator {

        NumericUnaryOperator (String symbol) {
            super(symbol);
        }

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

        BooleanUnaryOperator (String symbol) {
            super(symbol);
        }

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

        BinaryOperator (String symbol) {
            super(symbol);
        }

        protected String operandTwo;

        public void setOperandTwo (String operand) {	this.operandTwo = operand; }

    }

    abstract class BooleanBinaryOperator extends BinaryOperator {
        BooleanBinaryOperator (String symbol) {
            super(symbol);
        }

        public String compute () {
            boolean booleanOperand = false;
            boolean booleanOperandTwo = false;
            try {
                booleanOperand = Boolean.parseBoolean(operand);
                booleanOperandTwo = Boolean.parseBoolean(operandTwo);
            }
            catch (Exception e) {} // Do nothing. Leave with initial values.
            return compute(booleanOperand, booleanOperandTwo);
        }

        public abstract String compute (boolean operandOne, boolean operandTwo);
    }

    class NotOperator extends BooleanUnaryOperator {
        NotOperator () {
            super(" ! ");
        }

        public String compute (boolean operand) {
            return calculator.not(operand) + "";
        }
    }

    class AndOperator extends BooleanBinaryOperator {
        AndOperator() {
            super(" && ");
        }

        public String compute (boolean operandOne, boolean operandTwo) {
            return calculator.and(operandOne, operandTwo) + "";
        }
    }

    class OrOperator extends BooleanBinaryOperator {
        OrOperator() {
            super(" || ");
        }
        public String compute (boolean operandOne, boolean operandTwo) {
            return calculator.or(operandOne, operandTwo) + "";
        }
    }

    class NandOperator extends BooleanBinaryOperator {
        NandOperator() {
            super(" nand ");
        }

        public String compute (boolean operandOne, boolean operandTwo) {
            return calculator.nand(operandOne, operandTwo) + "";
        }
    }

    class NorOperator extends BooleanBinaryOperator {

        NorOperator() {
            super(" nor ");
        }

        public String compute (boolean operandOne, boolean operandTwo) {
            return calculator.nor(operandOne, operandTwo) + "";
        }
    }

    class XorOperator extends BooleanBinaryOperator {

        XorOperator () {
            super(" xor ");
        }
        public String compute (boolean operandOne, boolean operandTwo) {
            return calculator.xor(operandOne, operandTwo) + "";
        }
    }

    abstract class NumericBinaryOperator extends BinaryOperator {

        NumericBinaryOperator (String symbol) {
            super(symbol);
        }

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
        SquareRootOperator () {
            //super((String.valueOf('\u221A')));
            super("sqrt(");
        }
        public String compute (double operand) {
            return calculator.sqrt(operand) + "";
        }
    }

    class NaturalLogOperator extends NumericUnaryOperator {
        NaturalLogOperator () {
            super("log(");
        }
        public String compute (double operand) {
            return calculator.ln(operand) + "";
        }
    }

    class CosineOperator extends NumericUnaryOperator {
        CosineOperator () {
            super("cos(");
        }

        public String compute (double operand) {
            return calculator.cos(operand) + "";
        }
    }

    class SineOperator extends NumericUnaryOperator {
        SineOperator () {
            super("sin(");
        }

        public String compute (double operand) {
            return calculator.sin(operand) + "";
        }
    }

    class TangentOperator extends NumericUnaryOperator {
        TangentOperator () {
            super("tan(");
        }

        public String compute (double operand) {
            return calculator.tan(operand) + "";
        }
    }

    class PlusOperator extends NumericBinaryOperator {
        PlusOperator () {
            super("+");
        }

        public String compute (double operand, double operandTwo) {
            if (calculateAsInt) {
                int sum = calculator.plus((int)operand, (int)operandTwo);	// Cast operands to ints to invoke plus(int, int)
                return sum + "";
            }
            return calculator.plus(operand, operandTwo) + "";
        }
    }

    class MinusOperator extends NumericBinaryOperator {
        MinusOperator () {
            super("-");
        }

        public String compute (double operand, double operandTwo) {
            if (calculateAsInt) {
                int difference = calculator.minus((int)operand, (int)operandTwo);
                return difference + "";
            }
            return calculator.minus(operand, operandTwo) + "";
        }
    }

    class DivideOperator extends NumericBinaryOperator {
        DivideOperator () {
            super("/");
        }

        public String compute (double operandOne, double operandTwo) {
            if (calculateAsInt) {
                int difference = calculator.divide((int)operandOne, (int)operandTwo);
                return difference + "";
            }
            return calculator.divide(operandOne, operandTwo) + "";
        }
    }

    class MultiplyOperator extends NumericBinaryOperator {
        MultiplyOperator () {
            super("*");
        }

        public String compute (double operandOne, double operandTwo) {
            if (calculateAsInt) {
                int product = calculator.multiply((int)operandOne, (int)operandTwo);
                return product + "";
            }
            return calculator.multiply(operandOne, operandTwo) + "";
        }
    }

    class ModulusOperator extends NumericBinaryOperator {
        ModulusOperator () {
            super("%");
        }

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

    class expressionValueActionListener implements ActionListener {
        protected String value;

        public expressionValueActionListener (String value) {
            this.value = value;
        }

        public void actionPerformed (ActionEvent event) {
            pushToExpression(this.value);
        }
    }
}
