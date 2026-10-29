package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
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
        runApp(inventory,scanner);

        // Close the scanner
        scanner.close();
    }

    public static void runApp(ArrayList<Product> inventory,Scanner scanner) {
        boolean appRunning = true;
        while (appRunning) {
            // Open the menu
            openMenu();
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("a")) {
                // Read all the items in the inventory
                listInventory(inventory);
            } else if (response.equalsIgnoreCase("b")) {
                // Add to the inventory
                addToInventory(inventory,scanner);
            } else if (response.equalsIgnoreCase("c")) {
                // Exit the app
                System.out.println("-> Exiting the app...");
                appRunning = false;
            } else {
                // Error, invalid input
                System.out.println("Error: Invalid input");
            }
        }
    }

    public static void listInventory(ArrayList<Product> inventory) {
        System.out.println("\n[INVENTORY PRODUCT INFORMATION]");
        // Sort the products in inventory by name
        inventory.sort(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER));
        // Display the items in the inventory
        for (Product product : inventory) {
            System.out.printf(
                    "id: %d, name: %s, price: $%.2f\n",
                    product.getId(),product.getName(),product.getPrice()
            );
        }
        System.out.println();
    }

    public static void addToInventory(ArrayList<Product> inventory,Scanner scanner) {
        // Prompt the user for the info needed to create a Product
        System.out.println("\n[ADD ITEM TO INVENTORY]");

        System.out.println("-> Enter item id (ex. 4221:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("-> Enter item name:");
        String name = scanner.nextLine();

        System.out.println("-> Enter item price:");
        double price = scanner.nextDouble();
        scanner.nextLine();

        // Create the product & add to inventory
        Product product = new Product(id,name,price);
        inventory.add(product);

        // Append product information to the .csv file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.csv",true))) {
            String line = String.valueOf(id) + "|" + name + "|" + String.valueOf(price);
            writer.newLine();
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            // Error reading the file
            System.out.println("Error: Issue reading the file");
        }

        System.out.println("\n-> Created new product! (" + name + ")\n");
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
    }
}