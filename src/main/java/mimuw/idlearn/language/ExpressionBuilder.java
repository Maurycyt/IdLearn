/*
package jsonparsing;

import lang.Liczba;
import lang.Wyrazenie;
import lang.Zmienna;
import lang.operacyjne.Not;
import lang.operacyjne.Operacyjne;
import lang.operacyjne.dwuargumentowe.*;
import exceptions.NieprawidlowyProgram;

import java.util.Stack;

public class ExpressionBuilder {

    public static Wyrazenie newWyrazenie(String expression) throws NieprawidlowyProgram {
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Wyrazenie> values = new Stack<Wyrazenie>();
        // Stack for Operators: 'ops'
        Stack<String> ops = new Stack<String>();
        char x;
        for (int i = 0; i < tokens.length; i++) {
            // i = 34
            x = tokens[i];
            // Current token is a
            // whitespace, skip it
            if (tokens[i] == ' ') {
                continue;
            }

            // Current token is a number,
            // push it to stack for values
            if (isStartOfDouble(tokens[i])) {
                StringBuffer preprocessed_num = new StringBuffer();

                // There may be more than one
                // digits in number
                while (i < tokens.length && isCharOfDouble(tokens[i])) {
                    preprocessed_num.append(tokens[i++]);
                }

                Liczba liczba = new Liczba(Double.parseDouble(preprocessed_num.toString()));
                values.push(liczba);

                // right now the i points to
                // the character next to the digit,
                // since the for loop also increases
                // the i, we would skip one
                // token position; we need to
                // decrease the value of i by 1 to
                // correct the offset.
                i--;
            }


            // Current token is a variable,
            // push it to stack for values
            else if (isStartOfVarName(tokens[i])) {
                StringBuffer var_name = new StringBuffer();

                // There may be more than one
                // digits in number
                while (i < tokens.length && isCharOfVarName(tokens[i])) {
                    var_name.append(tokens[i++]);
                }

                Zmienna zmienna = new Zmienna(var_name.toString());
                values.push(zmienna);

                // right now the i points to
                // the character next to the digit,
                // since the for loop also increases
                // the i, we would skip one
                // token position; we need to
                // decrease the value of i by 1 to
                // correct the offset.
                i--;
            }

            // Current token is an opening brace,
            // push it to 'ops'
            else if (tokens[i] == '(') {
                ops.push("(");
            }

            // Closing brace encountered,
            // solve entire brace
            else if (tokens[i] == ')') {
                while (!ops.peek().equals("(")) {
                    // If there had been a negation,
                    // negate recently evaluated brace
                    if (ops.peek().equals("!")) {
                        values.push(applyNot(values.pop()));
                        ops.pop(); // pops negation
                    }

                    // TODO: ???
                    values.push(applyTwoArgOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            }

            // Current token is an operator.
            else if (isPartOfOperator(tokens[i])) {
                // While top of 'ops' has same
                // or greater precedence to current
                // token, which is an operator.
                // Apply operator on top of 'ops'
                // to top elements in values stack

                String operator = Character.toString(tokens[i]);

                // Creates a two-character string from a
                // two-character operator.
                if (isStartOfTwoCharacterOperator(tokens[i])) {
                    if (isEndOfTwoCharacterOperator(tokens[i + 1])) {
                        // Push current token to 'ops'.
                        operator += tokens[i + 1];
                        // Skips the next character
                        i++;
                    }
                }

                // A special case, because it has only one argument.
                if (operator.equals("!")) {
*/
/*                    while (!ops.empty() && hasPrecedence(operator, ops.peek())) {
                        ops.pop();
                        values.push(applyNot(values.pop()));
                    }*//*

                }

                // Any two-argument operation
                else {
                    while (!ops.empty() && hasPrecedence(operator, ops.peek())) {
                        values.push(applyTwoArgOp(ops.pop(), values.pop(), values.pop()));
                    }
                }

                // Push current token to 'ops'.
                ops.push(operator);
            }
        }

        // Entire expression has been
        // parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty()) {
            values.push(applyTwoArgOp(ops.pop(), values.pop(), values.pop()));
        }

        // Top of 'values' contains
        // result, return it
        return values.pop();
    }

    // Returns true if 'op2' has higher
    // or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(String op1, String op2) throws NieprawidlowyProgram {
        if (op2.equals("(") || op2.equals(")")) {
            return false;
        } else {
            Operacyjne oper1 = Operacyjne.operacyjneFromString(op1);
            Operacyjne oper2 = Operacyjne.operacyjneFromString(op2);
            return oper2.getPriorytet() <= oper1.getPriorytet();
        }
    }

    // A utility method to apply an
    // operator 'op' on operands 'a'
    // and 'b'. Returns the result.

    // Do not change the order of (b,a) !!!
    public static Operacyjne applyTwoArgOp(String op, Wyrazenie b, Wyrazenie a) throws NieprawidlowyProgram {
        switch (op) {
            case "+": return new Plus(a, b);
            case "-": return new Minus(a, b);
            case "*": return new Razy(a, b);
            case "/":
                if (b.obliczWartosc() == 0)
                    throw new NieprawidlowyProgram("Program dzieli przez zero.");
                return new Dzielenie(a, b);

            case "&&": return new And(a, b);
            case "||": return new Or(a, b);

            case "<": return new Mniejsze(a, b);
            case "<=": return new MniejszeRowne(a, b);
            case ">": return new Wieksze(a, b);
            case ">=": return new WiekszeRowne(a, b);
            case "==": return new Rowne(a, b);

            default: throw new NieprawidlowyProgram("Podana operacja arytmetyczna nie istnieje w języku Robson.");
        }
    }

    public static Not applyNot(Wyrazenie a) {
        return new Not(a);
    }


    public static boolean isNumerical(String str) {
        if (str == null) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStartOfVarName(char c) {
        return (Character.isLetter(c) || c == '_');
    }

    public static boolean isCharOfVarName(char c) {
        return (isStartOfVarName(c) || Character.isDigit(c));
    }

    public static boolean isStartOfDouble(char c) {
        return (Character.isDigit(c));
    }

    public static boolean isCharOfDouble(char c) {
        return (isStartOfDouble(c) || c == '.');
    }

    public static boolean isPartOfOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' ||
                c == '&' || c == '|' || c == '<' || c == '>' ||
                c == '=' || c == '!');
    }

    public static boolean isOneCharacterOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '!');
    }

    public static boolean isStartOfTwoCharacterOperator(char c) {
        return (c == '&' || c == '|' || c == '<' || c == '>' || c == '=');
    }

    public static boolean isEndOfTwoCharacterOperator(char c) {
        return (c == '&' || c == '|' || c == '=');
    }

    public static boolean isVarName(String str) {
        if (str == null) {
            return false;
        }

        // Variable names cannot start with a number.
        if (isStartOfVarName(str.charAt(0))) {
            return false;
        }

        if (str.length() > 1) {
            for (int i = 1; i < str.length(); i++) {
                char c = str.charAt(i);

                if (!isCharOfVarName(c)) {
                    return false;
                }
            }
        }
        return true;
    }
}
*/
