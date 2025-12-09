package org.TD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    DBConnection DBConnection = new DBConnection();
    Connection connection = DBConnection.getDBConnection();

    public List<Category> getAllCategories() throws SQLException{
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id , name FROM Product_category";
        Statement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            categories.add(new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("name")));
        }
        resultSet.close();
        statement.close();
        return categories;
    }
}
