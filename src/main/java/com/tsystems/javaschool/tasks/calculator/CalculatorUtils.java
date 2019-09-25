package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayDeque;
import java.util.ArrayList;

class CalculatorUtils {

    private static String[] funcs = {"/", "*", "-", "+"};


    static String evaluate(String statement) {
        if (statement == null || statement.isEmpty()) {
            return null;
        }


        char arr[] = statement.toCharArray();
        ArrayList <String> elements = new ArrayList <>();


        StringBuilder stringBuilder = new StringBuilder();


        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ',') {
                return null;
            }
            if (isFunc(arr[i])) {
                if (stringBuilder.length() > 0) {
                    elements.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    elements.add(String.valueOf(arr[i]));
                } else if (i > 0 && arr[i - 1] == ')' && isFunc(arr[i])) {
                    elements.add(String.valueOf(arr[i]));
                } else {
                    return null;
                }
            } else if (isBord(arr[i])) {
                if (stringBuilder.length() > 0) {
                    elements.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    elements.add(String.valueOf(arr[i]));
                } else if ((i == 0 || (i > 0 && isFunc(arr[i - 1])) && arr[i] == '(')) {
                    elements.add(String.valueOf(arr[i]));
                }
            } else {
                if ((i > 0 && arr[i - 1] == '.' || i == 0) && arr[i] == '.') {
                    return null;
                }
                stringBuilder.append(arr[i]);
            }
            if (i == arr.length - 1 && stringBuilder.length() != 0) {
                elements.add(stringBuilder.toString());
            }
        }
        return calc(elements);
    }

    private static String calc(ArrayList <String> elements) {
        ArrayDeque <Integer> open = new ArrayDeque <>();
        ArrayDeque <Integer> close = new ArrayDeque <>();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals("(")) {
                open.push(i);
            } else if (elements.get(i).equals(")")) {
                close.push(i);
            }
        }

        if (open.size() != close.size()) {
            return null;
        }

        while (!open.isEmpty() && !close.isEmpty()) {
            int op = open.pop();
            int cl = close.pop();

            String x = CalculatorUtils.calculate(CalculatorUtils.getFragment(op, cl, elements));

            if (x == null) {
                return null;
            }

            for (int i = cl; i >= op; i--) {
                elements.remove(i);
            }
            elements.add(op, x);
        }


        return CalculatorUtils.calculate(elements);
    }

    private static boolean isFunc(char x) {
        return x == '/' || x == '*' || x == '+' || x == '-';
    }

    private static boolean isBord(char x) {
        return x == '(' || x == ')';
    }

    private static ArrayList getFragment(int from, int to, ArrayList <String> arr) {
        if (from > to) {
            return null;
        }
        ArrayList list = new ArrayList();
        for (int i = from + 1; i < to; i++) {
            list.add(arr.get(i));
        }
        return list;
    }


    private static String calculate(ArrayList <String> statement) {
        if (statement.size() == 1) {
            return statement.get(0);
        }
        for (int i = 0; i < funcs.length; i++) {
            int funcIndex = statement.indexOf(funcs[i]);
            while (funcIndex != -1) {
                statement.add(funcIndex - 1,operation(statement.get(funcIndex - 1),
                        statement.get(funcIndex + 1),
                        statement.get(funcIndex)
                        )
                );

                for (int j = 0; j < 3; j++) {
                    statement.remove(funcIndex);
                }
                funcIndex = statement.indexOf(funcs[i]);
            }
        }
        return statement.get(0);
    }


    private static String operation(String first, String second, String operation) {
        double x = Double.valueOf(first);
        double y = Double.valueOf(second);



        double res = 0.0;

        switch (operation) {
            case "*":
                res = x * y;
                break;
            case "/":
                if(y==0){
                    return null;
                }
                res = x / y;
                break;
            case "-":
                res = x - y;
                break;
            case "+":
                res = x + y;
                break;
        }

        if(res==(int)res){
            return String.valueOf((int) res);
        }

        return String.valueOf(res);





    }


}