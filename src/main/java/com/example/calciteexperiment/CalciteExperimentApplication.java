package com.example.calciteexperiment;

import org.apache.calcite.jdbc.CalciteConnection;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CalciteExperimentApplication {
    public static void main(String[] args) throws URISyntaxException, ClassNotFoundException, SQLException {

        // Connect to Redis Server "redis://localhost:6379"
        RedisConnection rc = new RedisConnection();
        rc.connectToRedis();

        // Load Data("employees", "departments") to Redis Server
        rc.loadDataToRedis();

        // Schema definition file("model-redis.json")
        URL res = CalciteExperimentApplication.class.getClassLoader().getResource("static/model-redis.json");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        // Load Calcite jdbc driver
        Class.forName("org.apache.calcite.jdbc.Driver");

        // Make a calcite connection of which model is "model-redis.json"
        // and Execute queries
        Scanner sc = new Scanner(System.in);
        System.out.println("Please write a query statement.");
        String query = sc.nextLine();
        while(!query.equals("q")){
            try(Connection connection = DriverManager.getConnection("jdbc:calcite:model=" + absolutePath);
                CalciteConnection calciteConnection = (CalciteConnection) connection;
                Statement statement = calciteConnection.createStatement();
                ResultSet rs = statement.executeQuery(query)
            ) {

                // Get the metadata of the result set
                ResultSetMetaData rsmd = rs.getMetaData();
                List<String> columns = new ArrayList<String>(rsmd.getColumnCount());

                // Add the column names of the result set to a list
                System.out.println("\n=== Column names of EMPLOYEES ===\n");
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    // Print column names
                    System.out.println(rsmd.getColumnName(i));
                    columns.add(rsmd.getColumnName(i));
                }

                System.out.println("\n=== Result of the query ===\n");
                while (rs.next()) {
                    for (String col : columns) {
                        System.out.println("" + col + " : " + rs.getString(col));
                    }
                    System.out.println("---------");
                }

                System.out.println("\n\nPlease write a query statement.");
                query = sc.nextLine();
            }catch (SQLException e){
                System.out.println("\n\nPlease correct the query statement.");
                query = sc.nextLine();
                continue;
            }
        }

        MongoConnection mc = new MongoConnection();
        mc.connectToMongo();
    }

}


// === query examples ===
// 1. select * from "employees"
// 2. select * from "departments"
// 3.

