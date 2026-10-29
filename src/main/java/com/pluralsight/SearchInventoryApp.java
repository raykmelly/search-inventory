package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchInventoryApp {
    public static void main(String[] args) {
        // Create a scanner
        Scanner scanner = new Scanner(System.in);

        // Inventory ArrayList of every item inside inventory
        ArrayList<Product> inventory = new ArrayList<Product>();

        // Collect all existing items inside of inventory.csv
        getInventory(inventory,"inventory.csv");

        // Run the inventory search application
        runApp(scanner);

        // Close the scanner
        scanner.close();
    }

    public static void runApp(Scanner scanner) {
        boolean appRunning = true;
        while (appRunning) {
            // Open the menu
            openMenu();
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("a")) {
                // Read all the items in the inventory

            } else if (response.equalsIgnoreCase("b")) {
                // Add to the inventory

            } else if (response.equalsIgnoreCase("c")) {
                // Exit the app
                appRunning = false;
            } else {
                // Error, invalid input
                System.out.println("Error: Invalid input");
            }
        }
    }

    public static void addToInventory(ArrayList<Product> inventory) {

    }

    public static void openMenu() {
        System.out.println("[INVENTORY MENU]");
        System.out.println("-> Choose a option from the list");
        System.out.println("A.) View inventory");
        System.out.println("B.) Add to inventory");
        System.out.println("C.) Exit");
    }

    public static void getInventory(ArrayList<Product> inventory, String fileName) {
        // Read through the csv file and add to inventory
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Loop through every line to create product
            while ((line = reader.readLine()) != null) {
                // Split the .csv file line (id|name|price)
                String[] split = line.split("\\|");

                // Create the product, add to inventory ArrayList
                int id = Integer.parseInt(split[0]);
                String name = split[1];
                double price = Double.parseDouble(split[2]);

                Product product = new Product(id,name,price);
                inventory.add(product);
            }
        } catch (IOException e) {
            // Error reading the file
            System.out.println("Error: file could not be read");
        }


        return inventory;
    }
}