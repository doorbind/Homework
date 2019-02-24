package com.agritask.homework.tests;

import com.agritask.homework.pojos.Pair;
import com.agritask.homework.util.ConstantSpaceEvaluator;
import com.agritask.homework.util.Evaluator;
import com.agritask.homework.util.StackEvaluator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class EvaluatorTest {

    public static void main(String[] args){
        getSuccessTestCases();
        getFailureTestCases();
        StackEvaluator stackEvaluator = new StackEvaluator();
        System.out.println("Running tests using StackEvaluator...");
        test(stackEvaluator, getSuccessTestCases(), getFailureTestCases());

        ConstantSpaceEvaluator constantSpaceEvaluator = new ConstantSpaceEvaluator();
        System.out.println("Running tests using ConstantSpaceEvaluator...");
        test(constantSpaceEvaluator, getSuccessTestCases(), getFailureTestCases());
    }

    protected  static List<Pair> getSuccessTestCases(){
        List<Pair> testCases = new ArrayList<Pair>();
        testCases.add(new Pair("1 + 1 + 2*2 * 2 + 10+1+2+20+300+1*5*5*10","593"));
        testCases.add(new Pair("  1*2*3 *5 * 6       * 10","1800"));
        testCases.add(new Pair(" 1 + 2+3+4 +5     +6 +10 + 100     +1000 ","1131"));
        testCases.add(new Pair("12345","12345"));
        return testCases;
    }

    protected  static List<String> getFailureTestCases(){
        List<String> testCases = new ArrayList<String>();
        testCases.add("+2 +3 * 10");
        testCases.add("2 + 1000 *");
        testCases.add(null);
        testCases.add("");
        testCases.add("1 + b * 10");
        testCases.add("1b * 10");
        testCases.add("error");
        return testCases;
    }

    private static void test(Evaluator evaluator, List<Pair> successTestCases, List<String> failureTestCases){
        boolean isSuccess = true;
        boolean globalSuccess = true;
        System.out.println("Running success test cases...");
        for(Pair<String, String> pair : successTestCases){
            isSuccess = evaluator.evaluate(pair.getLeft()).equals(new BigInteger(pair.getRight()));
            globalSuccess &= isSuccess;
            System.out.println((isSuccess ? "SUCCESS" : "FAILURE") + " : evaluate(" + pair.getLeft() + ")");
        }
        System.out.println("Success test cases finished with " + (globalSuccess? "SUCCESS" : "FAILURE"));

        globalSuccess = true;
        System.out.println();
        System.out.println("Running failure test cases...");
        for(String failureTestCase : failureTestCases){
            try{
                evaluator.evaluate(failureTestCase);
                globalSuccess &= false;
                System.out.println("FAILURE : " + "evaluate(" + failureTestCase + ")");
            } catch(Exception e){
                globalSuccess &= true;
                System.out.println("SUCCESS : " + "evaluate(" + failureTestCase + ")");
            }
        }
        System.out.println("Failure test cases finished with " + (globalSuccess? "SUCCESS" : "FAILURE"));
    }

}
