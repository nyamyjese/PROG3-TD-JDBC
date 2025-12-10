package org.TD;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        DataRetriever dataRetriever = new DataRetriever();

        try {
            System.out.println("=== Test getAllCategories() ===");
            List<Category> categories = dataRetriever.getAllCategories();
            for (Category c : categories) {
                System.out.println(c.getId() + " | " + c.getName());
            }

            List<Product> productsPage1 = dataRetriever.getProductList(1, 5);
            for (Product p : productsPage1) {
                System.out.println(
                        p.getId() + " | " +
                        p.getName() + " | " +
                        p.getCreationDatetime() + " | "

                );
            }

            List<Product> filtered = dataRetriever.getProductsByCriteria(
                    "a",
                    null,
                    null,
                    null
            );
            for (Product p : filtered) {
                System.out.println(p.getId() + " - " + p.getName());
            }
            List<Product> filteredPaginated = dataRetriever.getProductsByCriteria(
                    "a",
                    null,
                    null,
                    null,
                    1,   // page
                    3    // size
            );

            for (Product p : filteredPaginated) {
                System.out.println(p.getId() + " | " + p.getName());
            }

        } catch (SQLException e) {
            System.err.println("Error SQL : " + e.getMessage());
        }
    }
}
