// 21. Custom Exception
// Objective: Create and use custom exceptions.

import java.util.Scanner;

class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

public class CustomExceptionDemo {
    public static void checkAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Age must be at least 18.");
        } else {
            System.out.println("Age is valid.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        try {
            checkAge(age);
        } catch (InvalidAgeException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }

        scanner.close();
    }
}
// 22. File Writing
// Objective: Write data to a file.

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileWritingDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string to write to file: ");
        String data = scanner.nextLine();

        // Set 'true' to append, 'false' to overwrite
        boolean appendToFile = false;

        try (FileWriter writer = new FileWriter("output.txt", appendToFile)) {
            writer.write(data);
            writer.write(System.lineSeparator()); // Write a newline after the data
            System.out.println("Data has been written to output.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();  // Show detailed error
        } finally {
            scanner.close();
        }
    }
}

// 23. File Reading
// Objective: Read data from a file.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReadingDemo {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("output.txt"))) {
            String line;
            System.out.println("Contents of output.txt:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();  // Show detailed error
        }
    }
}

// 24. ArrayList Example
// Objective: Use dynamic arrays.

import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListDemo {
    public static void main(String[] args) {
        ArrayList<String> studentNames = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Enter student names (type 'done' to finish):");
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            studentNames.add(input);
        }

        System.out.println("Student names entered:");
        for (String name : studentNames) {
            System.out.println(name);
        }

        scanner.close();
    }
}
// 25. HashMap Example
// Objective: Use key-value pairs.

import java.util.HashMap;
import java.util.Scanner;

public class HashMapDemo {
    public static void main(String[] args) {
        HashMap<Integer, String> studentMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter student ID and name (type -1 to stop):");
        while (true) {
            System.out.print("Enter ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (id == -1) {
                break;
            }
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            studentMap.put(id, name);
        }

        System.out.print("Enter an ID to look up: ");
        int searchId = scanner.nextInt();

        String foundName = studentMap.get(searchId);
        if (foundName != null) {
            System.out.println("Student with ID " + searchId + ": " + foundName);
        } else {
            System.out.println("No student found with ID " + searchId);
        }

        scanner.close();
    }
}
// 26. Thread Creation
// Objective: Implement multithreading.

class MessageThread extends Thread {
    private String message;

    MessageThread(String message) {
        this.message = message;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(message + " - Count: " + i);
            try {
                Thread.sleep(500); // Sleep 500 ms
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        Thread t1 = new MessageThread("Thread 1");
        Thread t2 = new MessageThread("Thread 2");

        t1.start();
        t2.start();
    }
}
// 27. Lambda Expressions
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LambdaDemo {
    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>();
        fruits.add("Banana");
        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("Mango");

        // Print fruits before sorting
        System.out.println("Fruits before sorting:");
        fruits.forEach(System.out::println);

        // Sort using lambda expression (case-insensitive)
        Collections.sort(fruits, (a, b) -> a.compareToIgnoreCase(b));

        // Print fruits after sorting
        System.out.println("\nSorted fruits:");
        fruits.forEach(System.out::println);
    }
}

// 28. Stream API
// Objective: Process collections using streams.

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamAPIDemo {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 8, 13, 2, 10, 7, 4);

        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Even numbers: " + evenNumbers);
    }
}
// 29. Records (Java 16+)
// Objective: Use the record keyword for immutable data structures.

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

record Person(String name, int age) {}

public class RecordDemo {
    public static void main(String[] args) {
        Person p1 = new Person("Alice", 25);
        Person p2 = new Person("Bob", 17);
        Person p3 = new Person("Charlie", 30);

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);

        List<Person> people = new ArrayList<>();
        people.add(p1);
        people.add(p2);
        people.add(p3);

        List<Person> adults = people.stream()
                .filter(person -> person.age() >= 18)
                .collect(Collectors.toList());

        System.out.println("Adults:");
        adults.forEach(System.out::println);
    }
}
// 30. Pattern Matching for switch (Java 21)
// Objective: Simplify conditional logic with pattern matching in enhanced switch expressions.
import java.util.Scanner;

public class PatternMatchingSwitchDemo {

    public static void checkObjectType(Object obj) {
        switch (obj) {
            case Integer i -> System.out.println("It's an Integer: " + i);
            case String s -> System.out.println("It's a String: " + s);
            case Double d -> System.out.println("It's a Double: " + d);
            case null -> System.out.println("It's null");
            default -> System.out.println("Unknown type: " + obj.getClass().getName());
        }
    }

    public static Object parseInput(String input) {
        if (input == null || input.equalsIgnoreCase("null")) {
            return null;
        }
        // Try to parse Integer
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ignored) {}

        // Try to parse Double
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException ignored) {}

        // Return as String if not a number
        return input;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter input (type will be guessed): ");
        String userInput = scanner.nextLine();

        Object parsedObject = parseInput(userInput);
        checkObjectType(parsedObject);

        scanner.close();
    }
}
