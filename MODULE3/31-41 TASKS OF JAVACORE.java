// 31. Basic JDBC Connection
// Connect to a local SQLite database and retrieve data from "students" table
import java.sql.*;

public class BasicJDBCConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:students.db"; // SQLite file database

        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite established.");

            String query = "SELECT id, name, age FROM students";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") +
                                       ", Name: " + rs.getString("name") +
                                       ", Age: " + rs.getInt("age"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
// 32. Insert and Update Operations in JDBC
// StudentDAO with insert and update methods using PreparedStatement

import java.sql.*;

public class StudentDAO {
    private final Connection conn;

    public StudentDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertStudent(int id, String name, int age) throws SQLException {
        String sql = "INSERT INTO students (id, name, age) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.executeUpdate();
            System.out.println("Student inserted.");
        }
    }

    public void updateStudentAge(int id, int newAge) throws SQLException {
        String sql = "UPDATE students SET age = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newAge);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student age updated.");
            } else {
                System.out.println("Student not found.");
            }
        }
    }

    // Main for testing
    public static void main(String[] args) {
        String url = "jdbc:sqlite:students.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            StudentDAO dao = new StudentDAO(conn);
            dao.insertStudent(3, "John Doe", 22);
            dao.updateStudentAge(3, 23);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
// 33. Transaction Handling in JDBC
// Simulate money transfer between two accounts

import java.sql.*;

public class TransactionDemo {
    public static void transfer(Connection conn, int fromAcc, int toAcc, double amount) throws SQLException {
        try {
            conn.setAutoCommit(false);

            String debitSql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
            String creditSql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";

            try (PreparedStatement debitStmt = conn.prepareStatement(debitSql);
                 PreparedStatement creditStmt = conn.prepareStatement(creditSql)) {

                debitStmt.setDouble(1, amount);
                debitStmt.setInt(2, fromAcc);
                debitStmt.executeUpdate();

                creditStmt.setDouble(1, amount);
                creditStmt.setInt(2, toAcc);
                creditStmt.executeUpdate();

                conn.commit();
                System.out.println("Transfer successful.");
            }
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Transfer failed, transaction rolled back.");
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:sqlite:bank.db"; // Your database file
        try (Connection conn = DriverManager.getConnection(url)) {
            transfer(conn, 1, 2, 100.0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
// 34. Create and Use Java Modules
// Directory structure example:
// com.utils/module-info.java
// com.utils/Utils.java
// com.greetings/module-info.java
// com.greetings/Greeting.java

// com.utils/module-info.java
module com.utils {
    exports com.utils;
}

// com.utils/Utils.java
package com.utils;

public class Utils {
    public static String getGreeting() {
        return "Hello from Utils!";
    }
}

// com.greetings/module-info.java
module com.greetings {
    requires com.utils;
}

// com.greetings/Greeting.java
package com.greetings;

import com.utils.Utils;

public class Greeting {
    public static void main(String[] args) {
        System.out.println(Utils.getGreeting());
    }
}
// 35. TCP Client-Server Chat
// Server.java

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started, waiting for client...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        String clientMsg, serverMsg;
        while ((clientMsg = in.readLine()) != null) {
            System.out.println("Client: " + clientMsg);
            System.out.print("Server: ");
            serverMsg = userInput.readLine();
            out.println(serverMsg);
            if ("bye".equalsIgnoreCase(serverMsg)) break;
        }

        clientSocket.close();
        serverSocket.close();
    }
}
// Client.java

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        String serverMsg, clientMsg;
        while (true) {
            System.out.print("Client: ");
            clientMsg = userInput.readLine();
            out.println(clientMsg);
            if ("bye".equalsIgnoreCase(clientMsg)) break;
            serverMsg = in.readLine();
            System.out.println("Server: " + serverMsg);
        }

        socket.close();
    }
}
// 36. HTTP Client API (Java 11+)
// Fetch data from GitHub API

import java.net.URI;
import java.net.http.*;

public class HttpClientDemo {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.github.com/"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Body:");
        System.out.println(response.body());
    }
}
// 37. Using javap to Inspect Bytecode
// Create class Example.java:

public class Example {
    public void sayHello() {
        System.out.println("Hello Bytecode");
    }
}
// 38. Decompile a Class File
// Write any simple class, compile it, then use a decompiler tool (e.g., JD-GUI, CFR)
// This is a manual step, no runnable code here.
// 39. Reflection in Java
// Load class dynamically and invoke methods

import java.lang.reflect.Method;

public class ReflectionDemo {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("java.lang.String");

        Method[] methods = clazz.getDeclaredMethods();
        System.out.println("Methods of String class:");
        for (Method m : methods) {
            System.out.println(m.getName() + " - Parameters: " + m.getParameterCount());
        }

        // Invoke method dynamically
        Method lengthMethod = clazz.getMethod("length");
        String str = "Reflection";
        int length = (int) lengthMethod.invoke(str);
        System.out.println("Length of \"" + str + "\": " + length);
    }
}
// 40. Virtual Threads (Java 21)
// Launch 100,000 virtual threads printing a message

public class VirtualThreadsDemo {
    public static void main(String[] args) throws InterruptedException {
        var start = System.currentTimeMillis();

        Thread[] threads = new Thread[100_000];
        for (int i = 0; i < 100_000; i++) {
            threads[i] = Thread.startVirtualThread(() -> {
                // minimal work
                // System.out.println("Hello from virtual thread");
            });
        }
        for (Thread t : threads) {
            t.join();
        }

        var end = System.currentTimeMillis();
        System.out.println("Time taken with 100,000 virtual threads: " + (end - start) + " ms");
    }
}
import java.util.concurrent.*;
import java.util.*;
//41 Executor Service and Callable
public class ExecutorServiceCallableDemo {
    public static void main(String[] args) {
        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Create a list of Callable tasks
        List<Callable<String>> tasks = new ArrayList<>();

        // Add some tasks (each returns a String result)
        tasks.add(() -> {
            Thread.sleep(1000); // simulate work
            return "Task 1 completed";
        });
        tasks.add(() -> {
            Thread.sleep(500);
            return "Task 2 completed";
        });
        tasks.add(() -> {
            Thread.sleep(2000);
            return "Task 3 completed";
        });

        try {
            // Submit all tasks and get a list of Futures
            List<Future<String>> futures = executor.invokeAll(tasks);

            // Loop through futures to get results
            for (Future<String> future : futures) {
                // get() will block until the result is available
                String result = future.get();
                System.out.println(result);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shutdown the executor service
            executor.shutdown();
        }
    }
}
