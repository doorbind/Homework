package com.agritask.homework.util;

import com.agritask.homework.enums.Operation;
import com.agritask.homework.pojos.Pair;

import java.math.BigInteger;


/*
  The idea in the code below is to perform operations sequentially, basically we keep going with summation until we
  see there is a multiplication on the right side of the right operand. At that point we keep multiplying until we reach a plus sign
  or the end of the expression and then go back and add the multiplied value to the left operand and assign to the result.

  Pseudocode :

  n1 <- read number
  s1 <- read sign
  n2 <- read number
  s2 <- read sign
  if s1 == '+'
     if s2 == '+'
        n1 += n2
        s1 = s2
     else
        result += n1
        n1 = n2
        s1 = s2
        continue (next iteration)
  else
     n1 *= n2
     s1 = s2

  return n1 == null ? result : result + n1
  Execution complexity O(N)
  Space O(1)
 */
public class ConstantSpaceEvaluator extends Evaluator {

    @Override
    public BigInteger evaluate(String expression){
        expression = trim(expression);
        Pair<Integer, Integer> numberPosition = getNextNumberPosition(expression, 0);
        BigInteger leftOperand = new BigInteger(expression.substring(numberPosition.getLeft(), numberPosition.getRight()));
        BigInteger rightOperand = null;
        BigInteger result = BigInteger.ZERO;
        Operation rightEndOperator;
        boolean synchronizeLeftOperand = true;
        int index = getNextOperationIndex(expression, numberPosition.getRight());
        if(index < expression.length() - 1) {
            Operation leftEndOperator = getSymbolOperation(expression.charAt(index));
            index++;
            for (; index < expression.length(); index++) {
                if (leftEndOperator.equals(Operation.ADDITION)) {
                    numberPosition = getNextNumberPosition(expression, index);
                    rightOperand = new BigInteger(expression.substring(numberPosition.getLeft(), numberPosition.getRight()));
                    index = getNextOperationIndex(expression, numberPosition.getRight());
                    if (index < expression.length()) {
                        rightEndOperator = getSymbolOperation(expression.charAt(index));
                        if (rightEndOperator.equals(Operation.ADDITION)) {
                            leftOperand = leftOperand.add(rightOperand);
                            synchronizeLeftOperand = true;
                        } else {
                            result = result.add(leftOperand);
                            leftOperand = rightOperand;
                            synchronizeLeftOperand = false;
                        }
                        leftEndOperator = rightEndOperator;
                    } else {
                        leftOperand = leftOperand.add(rightOperand);
                        synchronizeLeftOperand = true;
                        break;
                    }
                } else {
                    numberPosition = getNextNumberPosition(expression, index);
                    rightOperand = new BigInteger(expression.substring(numberPosition.getLeft(), numberPosition.getRight()));
                    leftOperand = leftOperand.multiply(rightOperand);
                    synchronizeLeftOperand = true;
                    index = getNextOperationIndex(expression, numberPosition.getRight());
                    if (index < expression.length()) {
                        leftEndOperator = getSymbolOperation(expression.charAt(index));
                    } else {
                        break;
                    }
                }
            }
        }
        return synchronizeLeftOperand ? result.add(leftOperand) : result;
    }
}
