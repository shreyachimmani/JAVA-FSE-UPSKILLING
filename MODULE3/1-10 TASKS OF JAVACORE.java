// 1. Hello World Program
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
// 2. Simple Calculator
import java.util.Scanner;
public class SimpleCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        System.out.println("Choose operation: +, -, *, /");
        char operation = scanner.next().charAt(0);
        double result;
        switch (operation) {
            case '+':
                result = num1 + num2;
                System.out.println("Result: " + result);
                break;
            case '-':
                result = num1 - num2;
                System.out.println("Result: " + result);
                break;
            case '*':
                result = num1 * num2;
                System.out.println("Result: " + result);
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                    System.out.println("Result: " + result);
                } else {
                    System.out.println("Error: Division by zero is undefined.");
                }
                break;
            default:
                System.out.println("Invalid operation selected.");
        }
        scanner.close();
    }
}
// 3. Even or Odd Checker
import java.util.Scanner;
public class EvenOddChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an integer: ");
        int number = scanner.nextInt();
        if (number % 2 == 0) {
            System.out.println(number + " is Even.");
        } else {
            System.out.println(number + " is Odd.");
        }
        scanner.close();
    }
}
// 4. Leap Year Checker
// Objective: Apply nested conditional logic.
import java.util.Scanner;
public class LeapYearChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a year: ");
        int year = scanner.nextInt();
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    System.out.println(year + " is a Leap Year.");
                } else {
                    System.out.println(year + " is NOT a Leap Year.");
                }
            } else {
                System.out.println(year + " is a Leap Year.");
            }
        } else {
            System.out.println(year + " is NOT a Leap Year.");
        }
        scanner.close();
    }
}
// 5. Multiplication Table
// Objective: Implement loops.

import java.util.Scanner;

public class MultiplicationTable {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int num = scanner.nextInt();

        System.out.println("Multiplication table of " + num + ":");
        for (int i = 1; i <= 10; i++) {
            System.out.println(num + " x " + i + " = " + (num * i));
        }

        scanner.close();
    }
}
// 6. Data Type Demonstration
// Objective: Understand Java's primitive data types.

public class DataTypeDemo {
    public static void main(String[] args) {
        int i = 100;
        float f = 10.5f;
        double d = 20.99;
        char c = 'A';
        boolean b = true;

        System.out.println("int value: " + i);
        System.out.println("float value: " + f);
        System.out.println("double value: " + d);
        System.out.println("char value: " + c);
        System.out.println("boolean value: " + b);
    }
}
// 7. Type Casting Example
// Objective: Practice type casting between different data types.

public class TypeCastingExample {
    public static void main(String[] args) {
        double doubleValue = 9.78;
        int intValue = (int) doubleValue;  // casting double to int
        System.out.println("Double value: " + doubleValue);
        System.out.println("Double cast to int: " + intValue);

        int anotherInt = 42;
        double castedDouble = (double) anotherInt;  // casting int to double
        System.out.println("Int value: " + anotherInt);
        System.out.println("Int cast to double: " + castedDouble);
    }
}
// 8. Operator Precedence
// Objective: Explore how Java evaluates expressions.

public class OperatorPrecedence {
    public static void main(String[] args) {
        int result = 10 + 5 * 2;  // multiplication before addition
        System.out.println("Result of 10 + 5 * 2 = " + result);
        System.out.println("Explanation: Multiplication (*) has higher precedence than addition (+), so 5 * 2 is evaluated first, then added to 10.");

        int result2 = (10 + 5) * 2;  // parentheses change order
        System.out.println("Result of (10 + 5) * 2 = " + result2);
        System.out.println("Explanation: Parentheses force addition first, then multiplication.");
    }
}
// 9. Grade Calculator
// Objective: Use conditional statements to determine grades.

import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter marks out of 100: ");
        int marks = scanner.nextInt();

        if (marks >= 90 && marks <= 100) {
            System.out.println("Grade: A");
        } else if (marks >= 80 && marks <= 89) {
            System.out.println("Grade: B");
        } else if (marks >= 70 && marks <= 79) {
            System.out.println("Grade: C");
        } else if (marks >= 60 && marks <= 69) {
            System.out.println("Grade: D");
        } else if (marks < 60 && marks >= 0) {
            System.out.println("Grade: F");
        } else {
            System.out.println("Invalid marks entered.");
        }

        scanner.close();
    }
}
// 10. Number Guessing Game
// Objective: Implement loops and conditional logic.

import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Random random = new Random();
        int targetNumber = random.nextInt(100) + 1;  // 1 to 100
        Scanner scanner = new Scanner(System.in);
        int guess = 0;

        System.out.println("Guess the number between 1 and 100.");

        while (guess != targetNumber) {
            System.out.print("Enter your guess: ");
            guess = scanner.nextInt();

            if (guess < targetNumber) {
                System.out.println("Too low! Try again.");
            } else if (guess > targetNumber) {
                System.out.println("Too high! Try again.");
            } else {
                System.out.println("Congratulations! You guessed the correct number: " + targetNumber);
            }
        }

        scanner.close();
    }
}
