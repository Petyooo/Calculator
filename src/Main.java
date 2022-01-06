import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Main {
    public static void main (String[] args) {
        CalculatorGUI testGUI = new CalculatorGUI();

        test();
    }

    public static void test() {
        Expression expression = new ExpressionBuilder("0 + 1").build();
        Expression expression1 = new ExpressionBuilder("1+2/3").build();

        double result = expression.evaluate();
        //Assertions.assertEquals(5, result);
        System.out.println(result);
        System.out.println(expression1.evaluate());
    }
}
