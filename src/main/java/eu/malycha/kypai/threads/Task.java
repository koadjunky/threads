package eu.malycha.kypai.threads;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;


class Task {

    static final String CHARS = "0123456789+/*-";
    static final int SIZE = 20;

    private final String expression;

    Task(String expression) {
        this.expression = expression;
    }

    String getExpression() {
        return expression;
    }

    // Note: expressions can be incorrect. In such case execute() will throw exception
    static Task create() {
        int length = RandomUtils.nextInt(1, SIZE);
        String exp = RandomStringUtils.random(length, CHARS);
        return new Task(exp);
    }

    String execute() {
        Expression exp = new ExpressionBuilder(this.expression).build();
        double result = exp.evaluate();
        return String.valueOf(result);
    }
}
