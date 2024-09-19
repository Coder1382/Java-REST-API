package rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostRequestHandler {
    public static List<Object> postData(String str, String table) throws IOException {
        List<Object> obj= new ArrayList<>();
        String[] arr=str.split(",");
        long id=0;
        if(table.equals("fruit")) {
            int pr=0;
            try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                    prepareStatement("INSERT INTO fruit(name,color,price) VALUES(?,?,?)")) {
                try {
                    addToDB.setString(1, arr[1].split(" ")[2]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    addToDB.setString(2, arr[2].split(" ")[2]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    for (int v = 0; v < arr[3].split(" ")[2].length(); ++v)
                        pr = pr * 10 + (arr[3].split(" ")[2].charAt(v) - 48);
                    addToDB.setInt(3, pr);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    addToDB.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                PreparedStatement readDB = connect.prepareStatement("SELECT * FROM fruit WHERE name=? and color=? and price=?");
                readDB.setString(1, arr[1].split(" ")[2]);
                readDB.setString(2, arr[2].split(" ")[2]);
                readDB.setInt(3, pr);
                ResultSet result = readDB.executeQuery();
                while (result.next()) {
                    long i = result.getLong("id");
                    String name = result.getString("name");
                    String color = result.getString("color");
                    int price = result.getInt("price");
                    obj.add(new Fruit(i, name, color, price));
                }
                connect.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(table.equals("sellers")) {
            int r=0;
            long index=0;
            try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                    prepareStatement("INSERT INTO sellers(name,rating,supplier_id) VALUES(?,?,?)")) {
                try {
                    addToDB.setString(1, arr[1].split(" ")[2]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(str);
                try {
                    for (int v = 0; v < arr[2].split(" ")[2].length(); ++v)
                        r = r * 10 + (arr[2].split(" ")[2].charAt(v) - 48);
                    addToDB.setInt(2, r);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    for (int v = 0; v < arr[3].split(" ")[2].length(); ++v)
                        index = index * 10 + (arr[3].split(" ")[2].charAt(v) - 48);
                    addToDB.setLong(3, index);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    addToDB.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                PreparedStatement readDB = connect.prepareStatement("SELECT * FROM sellers WHERE name=? and rating=? and supplier_id=?");
                readDB.setString(1, arr[1].split(" ")[2]);
                readDB.setInt(2, r);
                readDB.setLong(3, index);
                ResultSet result = readDB.executeQuery();
                while (result.next()) {
                    long i = result.getLong("id");
                    String name = result.getString("name");
                    int rating = result.getInt("rating");
                    long supplier_id = result.getLong("supplier_id");
                    obj.add(new Seller(i, name, rating, supplier_id));
                }
                connect.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(table.equals("suppliers")) {
            try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                    prepareStatement("INSERT INTO suppliers(company) VALUES(?)")) {
                try {
                    addToDB.setString(1, arr[1].split(" ")[2]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    addToDB.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                PreparedStatement readDB = connect.prepareStatement("SELECT * FROM suppliers WHERE company=?");
                readDB.setString(1, arr[1].split(" ")[2]);
                ResultSet result = readDB.executeQuery();
                while (result.next()) {
                    long i = result.getLong("id");
                    String company = result.getString("company");
                    obj.add(new Supplier(i, company));
                }
                connect.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return obj;
    }
}
