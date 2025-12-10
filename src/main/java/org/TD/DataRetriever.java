package org.TD;

import org.TD.Category;
import org.TD.DBConnection;
import org.TD.Product;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    DBConnection DBConnection = new DBConnection();
    Connection connection = DBConnection.getDBConnection();

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT id, name FROM Product_category";

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                categories.add(
                        new Category(
                                resultSet.getInt("id"),
                                resultSet.getString("name")
                        )
                );
            }

        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }

        return categories;
    }

    public List<Product> getProductList(int page, int size) throws SQLException {
        List<Product> products = new ArrayList<>();

        int offset = (page - 1) * size;

        String query = """
            SELECT id, name, price, creation_datetime, product_category_id
            FROM Product
            ORDER BY id
            LIMIT ? OFFSET ?
        """;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, offset);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                products.add(
                        new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getTimestamp("creation_datetime").toInstant()
                        )
                );
            }

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        }

        return products;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax
    ) throws SQLException {

        List<Product> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT p.id, p.name, p.price, p.creation_datetime, p.product_category_id
            FROM Product p
            INNER JOIN Product_category c ON p.product_category_id = c.id
            WHERE 1=1
        """);

        List<Object> params = new ArrayList<>();

        if (productName != null) {
            sql.append(" AND p.name ILIKE ?");
            params.add("%" + productName + "%");
        }

        if (categoryName != null) {
            sql.append(" AND c.name ILIKE ?");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ?");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ?");
            params.add(Timestamp.from(creationMax));
        }

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            preparedStatement.setObject(i + 1, params.get(i));
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            products.add(
                    new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getTimestamp("creation_datetime").toInstant()
                    )
            );
        }

        resultSet.close();
        preparedStatement.close();

        return products;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax,
            int page,
            int size
    ) throws SQLException {

        List<Product> filtered = getProductsByCriteria(
                productName,
                categoryName,
                creationMin,
                creationMax
        );

        int start = (page - 1) * size;
        int end = Math.min(start + size, filtered.size());

        if (start >= filtered.size()) {
            return new ArrayList<>();
        }

        return filtered.subList(start, end);
    }
}