package rest.services;

import rest.dao.DatabaseConnector;
import rest.dao.Fruit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandlerFruit {
    public static List<Object> getData(String req, long id) throws IOException {
        List<Object> obj= new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.prepareStatement(req)) {
            if(id>0)
                readDB.setLong(1,id);
                ResultSet result = readDB.executeQuery();
                    while (result.next()) {
                        long i = result.getLong("id");
                        String name = result.getString("name");
                        String color = result.getString("color");
                        int price = result.getInt("price");
                        obj.add(new Fruit(i, name, color, price));
                    }
                /*else if(table.equals("seller_fruit")) {
                    while (result.next()) {
                        long seller_id = result.getLong("seller_id");
                        long fruit_id = result.getLong("fruit_id");
                        obj.add(new Seller_fruit(seller_id, fruit_id));
                    }
                }*/
                /*else if(table.equals("seller_fruit")) {
                    while (result.next()) {
                        long seller_id = result.getLong("seller_id");
                        long fruit_id = result.getLong("fruit_id");
                        obj.add(new Seller_fruit(seller_id, fruit_id));
                    }
                }*/
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static List<Object> postData(String str) throws IOException {
        List<Object> obj= new ArrayList<>();
        String[] arr=str.split(",");
        long id=0;
            int pr=0;
            try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                    prepareStatement("INSERT INTO fruit(name,color,price) VALUES(?,?,?)")) {
                addToDB.setString(1, arr[1].split(" ")[2]);
                addToDB.setString(2, arr[2].split(" ")[2]);
                for (int v = 0; v < arr[3].split(" ")[2].length(); ++v)
                    pr = pr * 10 + (arr[3].split(" ")[2].charAt(v) - 48);
                addToDB.setInt(3, pr);
                addToDB.executeUpdate();
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
        return obj;
    }
    public static List<Object> putData(String str) throws IOException, SQLException {
        List<Object> obj= new ArrayList<>();
        String[] arr=str.split(",");
        long id=0, y=0;
        int x=0;
        for(int v=0; v<(arr[0].split(" "))[1].length(); ++v)
            id=id*10+(arr[0].split(" "))[1].charAt(v)-48;
            String[] s=arr[3].split(" ");
            for(int v=0; v<s[2].length(); ++v)
                x=x*10+s[2].charAt(v)-48;
            try (Connection connect = DatabaseConnector.connector(); PreparedStatement updateInDB = connect.
                    prepareStatement("UPDATE fruit SET price=? WHERE id=?")) {
                updateInDB.setInt(1, x);
                updateInDB.setLong(2, id);
                updateInDB.executeUpdate();
                PreparedStatement readDB = connect.prepareStatement("SELECT name, color, price FROM fruit WHERE id=?");
                readDB.setLong(1, id);
                ResultSet result = readDB.executeQuery();
                while (result.next()) {
                    String name = result.getString("name");
                    String color = result.getString("color");
                    int price = result.getInt("price");
                    obj.add(new Fruit(id, name, color, price));
                }
                connect.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return obj;
    }
    public static List<Object> deleteData(String str) throws IOException {
        List<Object> obj= new ArrayList<>();
        long id=0;
        String[] arr=str.split(",");
        String[] s=arr[0].split(" ");
        for(int v=0; v<s[1].length(); ++v)
            id=id*10+(s[1].charAt(v)-48);
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM fruit WHERE id=?")) {
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM fruit WHERE id=?");
            readDB.setLong(1,id);
            ResultSet rs=readDB.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    String color = rs.getString("color");
                    int price = rs.getInt("price");
                    obj.add(new Fruit(id, name, color, price));
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
