package com.agritask.homework.util;

import com.agritask.homework.enums.Operation;
import com.agritask.homework.pojos.Pair;

import java.math.BigInteger;

public abstract class Evaluator {

    protected static final String WRONG_EXPRESSION_FORMAT = "Wrong expression format. An expression may contain only numbers, + , * and white spaces";
    protected static final String WRONG_OPERATORS_BETWEEN_NUMBERS = "Expression format exception, there must be + or * operator between numbers!";
    protected static final String WRONG_ORDER_OF_OPERATORS = "Wrong order of arithmetic operators, please check your expression!";
    protected static final String EXPRESSION_MUST_END_WITH_NUMBER = "Expression format exception, an expression must end with a number !";
    protected  static final String NULL_OR_EMPTY_EXPRESSION = "Input cannot be null or empty, please provide a valid expression!";

    public abstract BigInteger evaluate(String expression);

    protected String trim(String expression){
        if(expression == null || expression.isEmpty()){
            throw new IllegalArgumentException(NULL_OR_EMPTY_EXPRESSION);
        }
        String trimmedExpression = expression.trim();
        if(trimmedExpression.length() > 0 && !Character.isDigit((trimmedExpression.charAt(trimmedExpression.length() - 1)))){
            throw new IllegalArgumentException(EXPRESSION_MUST_END_WITH_NUMBER);
        }
        return  trimmedExpression;
    }

    protected void validate(char symbol, boolean wasOperator){
        if((symbol == '*' || symbol == '+') && wasOperator) {
            throw new IllegalArgumentException(WRONG_ORDER_OF_OPERATORS);
        }
        if(symbol != '*' && symbol != '+'  && !Character.isDigit(symbol)){
            throw new IllegalArgumentException("Wrong expression format, the expression must contain only digits " +
                    "and + and * symbols");
        }
        if(Character.isDigit(symbol) && !wasOperator){
            throw new IllegalArgumentException("Expression format exception, there must be + or * operator between numbers!");
        }
    }

    protected int getNumberRightIndex(String expression, int i) {
        int rightIndex = i;
        for(;rightIndex < expression.length() && Character.isDigit(expression.charAt(rightIndex)); rightIndex++);
        return rightIndex;
    }

    protected int getNextNumberLeftIndex(String expression, int i){
        int index = i;
        for(; index < expression.length(); index++){
            char symbol = expression.charAt(index);
            if(Character.isWhitespace(symbol)){
                continue;
            } else if(Character.isDigit(symbol)){
                break;
            } else {
                throw new IllegalArgumentException(WRONG_EXPRESSION_FORMAT);
            }
        }
        return index;
    }

    protected Pair getNextNumberPosition(String expression, int index){
        int nextNumberLeftIndex = getNextNumberLeftIndex(expression, index);
        int nextNumberRightIndex = getNumberRightIndex(expression, nextNumberLeftIndex);
        return new Pair(nextNumberLeftIndex, nextNumberRightIndex);
    }

    protected int getNextOperationIndex(String expression, int index){
        int i = index;
        for(; i < expression.length(); i++){
            char symbol = expression.charAt(i);
            if(Character.isWhitespace(symbol)){
                continue;
            } else if(symbol == '+' || symbol == '*'){
                break;
            } else {
                throw new IllegalArgumentException(WRONG_EXPRESSION_FORMAT);
            }
        }
        return i;
    }

    protected Operation getSymbolOperation(char symbol){
        return symbol == '+' ? Operation.ADDITION : Operation.MULTIPLICATION;
    }

}
