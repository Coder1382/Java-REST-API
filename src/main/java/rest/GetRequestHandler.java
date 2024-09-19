package rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetRequestHandler {
    public static List<Object> getData(String req, long id, String table) throws IOException {
        List<Object> obj= new ArrayList<>();
        System.out.println(req);
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.prepareStatement(req)) {
            if(id<0) {
                ResultSet result = readDB.executeQuery();
                if(table.equals("fruit")) {
                    while (result.next()) {
                        long i = result.getLong("id");
                        String name = result.getString("name");
                        String color = result.getString("color");
                        int price = result.getInt("price");
                        obj.add(new Fruit(i, name, color, price));
                    }
                }
                else if(table.equals("sellers")) {
                    while (result.next()) {
                        long i = result.getLong("id");
                        String name = result.getString("name");
                        int rating = result.getInt("rating");
                        long supplier_id = result.getLong("supplier_id");
                        obj.add(new Seller(i, name, rating, supplier_id));
                    }
                }
                else if(table.equals("suppliers")) {
                    while (result.next()) {
                        long i = result.getLong("id");
                        String company = result.getString("company");
                        obj.add(new Supplier(i, company));
                    }
                }
                /*else if(table.equals("seller_fruit")) {
                    while (result.next()) {
                        long seller_id = result.getLong("seller_id");
                        long fruit_id = result.getLong("fruit_id");
                        obj.add(new Seller_fruit(seller_id, fruit_id));
                    }
                }*/
            }
            else{
                readDB.setLong(1,id);
                ResultSet result = readDB.executeQuery();
                if(table.equals("fruit")) {
                    while (result.next()) {
                        long i = result.getLong("id");
                        String name = result.getString("name");
                        String color = result.getString("color");
                        int price = result.getInt("price");
                        obj.add(new Fruit(i, name, color, price));
                    }
                }
                else if(table.equals("sellers")) {
                    while (result.next()) {
                        long i = result.getLong("id");
                        String name = result.getString("name");
                        int rating = result.getInt("rating");
                        long supplier_id = result.getLong("supplier_id");
                        obj.add(new Seller(i, name, rating, supplier_id));
                    }
                }
                else if(table.equals("suppliers")) {
                    while (result.next()) {
                        long i = result.getLong("id");
                        String company = result.getString("company");
                        obj.add(new Supplier(i, company));
                    }
                }
                /*else if(table.equals("seller_fruit")) {
                    while (result.next()) {
                        long seller_id = result.getLong("seller_id");
                        long fruit_id = result.getLong("fruit_id");
                        obj.add(new Seller_fruit(seller_id, fruit_id));
                    }
                }*/
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
