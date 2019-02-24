package com.agritask.homework.util;

import java.math.BigInteger;
import java.util.Stack;


/*
As we know multiplication is granted a higher precedence than addition, and so the idea
in the code below is to perform multiplication first and add to a stack and then at the end just sum up the elements of the stack.

Execution complexity O(N)
Space O(N)
 */
public class StackEvaluator extends Evaluator {

    @Override
    public BigInteger evaluate(String expression){
        expression = trim(expression);
        boolean wasOperator = true;
        boolean wasMultiplication = false;
        Stack<BigInteger> stack = new Stack();
        for(int i = 0; i < expression.length(); i++){
            char symbol = expression.charAt(i);
            if(Character.isWhitespace(symbol)){
                continue;
            }
            validate(symbol, wasOperator);
            if(symbol == '+'){
                wasOperator = true;
            } else if(symbol == '*'){
                wasOperator = true;
                wasMultiplication = true;
            } else if(Character.isDigit(symbol)){
                wasOperator = false;
                int numberRightIndex = getNumberRightIndex(expression, i);
                BigInteger number = new BigInteger(expression.substring(i, numberRightIndex));
                i = numberRightIndex - 1;
                if(wasMultiplication){
                    BigInteger previousNumber = stack.pop();
                    stack.push(previousNumber.multiply(number));
                } else {
                    stack.push(number);
                }
                wasMultiplication = false;
            }
        }
        return stack.stream().reduce(BigInteger.ZERO, BigInteger::add);
    }
}