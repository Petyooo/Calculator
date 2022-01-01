/**
 * Note: The original code template was from the following source:
 * https://classes.engineering.wustl.edu/cse501/labs/Lab1/Lab1.htm
 * File: Calculator.java
 */
public class Calculator {

    double memoryStore;

    /* This is the constructor for calculator. You will not need to edit this. */
    public Calculator () {
    }

    /* I have completed this method for you for reference. */
    public int plus (int operandOne, int operandTwo) {
        return operandOne + operandTwo;
    }

    /* This is a "Method Skeleton" that returns 0.0 by default. You
       should initially create a method skeleton for all required
       methods so that your program will compile without errors. Then,
       go back and fill in the correct implementation for each
       method. */
    public double plus (double operandOne, double operandTwo) {
        return operandOne + operandTwo;
    }

    // Declare the rest of your methods here.
    public int minus (int operandOne, int operandTwo) {
        return operandOne - operandTwo;
    }

    public double minus (double operandOne, double operandTwo) {
        return operandOne - operandTwo;
    }

    public int multiply (int operandOne, int operandTwo) {
        return operandOne * operandTwo;
    }

    public double multiply (double operandOne, double operandTwo) {
        return operandOne * operandTwo;
    }

    public int divide (int operandOne, int operandTwo) {
        return (int) (operandOne / operandTwo);
    }

    public double divide (double operandOne, double operandTwo) {
        return operandOne / operandTwo;
    }

    public int modulus (int operandOne, int operandTwo) {
        return operandOne % operandTwo;
    }

    public double negate (double operand) {
        return -1 * operand;
    }

    public double sqrt (double operand) {
        return Math.sqrt(operand);
    }

    public double ln (double operand) {
        return Math.log(operand);
    }

    public double sin (double operand) {
        return Math.sin(operand);
    }

    public double cos (double operand) {
        return Math.cos(operand);
    }

    public double tan (double operand) {
        return Math.tan(operand);
    }

    public boolean and (boolean operandOne, boolean operandTwo) {
        return operandOne && operandTwo;
    }

    public boolean or (boolean operandOne, boolean operandTwo) {
        return operandOne || operandTwo;
    }

    public boolean not (boolean operand) {
        return !operand;
    }

    public boolean xor (boolean operandOne, boolean operandTwo) {
        return operandOne ^ operandTwo;
    }

    public boolean nor (boolean operandOne, boolean operandTwo) {
        return !or(operandOne, operandTwo);
    }

    public boolean nand (boolean operandOne, boolean operandTwo) {
        return !and(operandOne, operandTwo);
    }

    public double pi () {
        return Math.PI;
    }

    public double e () {
        return Math.E;
    }

    public double toCelsius (double fahrenheit) {
        return (5.0/9.0) * (fahrenheit - 32);
    }

    public double toFahrenheit (double celsius) {
        return (9.0/5.0) * celsius + 32;
    }

    public void memoryStore (double value) {
        this.memoryStore = value;
    }

    public double memoryRecall () {
        return this.memoryStore;
    }

    public void memoryClear () {
        this.memoryStore = 0;
    }
}
