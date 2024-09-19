package rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteRequestHandler {
    public static List<Object> deleteData(String str, String table) throws IOException {
        List<Object> obj= new ArrayList<>();
        long id=0;
        String[] arr=str.split(",");
        String[] s=arr[0].split(" ");
        for(int v=0; v<s[1].length(); ++v)
           id=id*10+(s[1].charAt(v)-48);
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM "+table+" WHERE id=?")) {
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM "+table+" WHERE id=?");
            readDB.setLong(1,id);
            ResultSet rs=readDB.executeQuery();
            if(table.equals("fruit")) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    String color = rs.getString("color");
                    int price = rs.getInt("price");
                    obj.add(new Fruit(id, name, color, price));
                }
            }
            else if(table.equals("sellers")) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int rating = rs.getInt("rating");
                    long supplier_id = rs.getLong("supplier_id");
                    obj.add(new Seller(id, name, rating, supplier_id));
                }
            }
            else if(table.equals("suppliers")) {
                while (rs.next()) {
                    String company = rs.getString("company");
                    obj.add(new Supplier(id, company));
                }
            }
            deleteFromDB.setLong(1,id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
