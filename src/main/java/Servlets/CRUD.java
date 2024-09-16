package Servlets;
import com.fasterxml.jackson.core.JsonProcessingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
public class CRUD {
    private static final String addItem = "INSERT INTO fruit(name,color,price) VALUES(?,?,?)";
    private static final String updateItem = "UPDATE fruit SET price=? WHERE id=?";
    private static final String deleteItem="DELETE FROM fruit WHERE id=?";
    public static List<Fruit> getData(String req, long id) throws IOException {
        List<Fruit> fruit = new ArrayList<>();
        try (Connection connect = Manager.connector(); PreparedStatement readDB = connect.prepareStatement(req)) {
            if(id<0) {
                ResultSet result = readDB.executeQuery();
                while (result.next()) {
                    long i=result.getLong("id");
                    String name = result.getString("name");
                    String color = result.getString("color");
                    int price = result.getInt("price");
                    fruit.add(new Fruit(i, name, color, price));
                }
            }
            else{
                readDB.setLong(1,id);
                ResultSet rs = readDB.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    String color = rs.getString("color");
                    int price = rs.getInt("price");
                    fruit.add(new Fruit(id, name, color, price));
                }
           }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fruit;
    }
    public static Fruit postData(String str) throws IOException {
        Fruit fruit=new Fruit();
        String[] arr=str.split(",");
        for (int i = 1; i < arr.length; ++i) {
            String[] s=arr[i].split(" ");
            if (i == 1)
                fruit.setName(s[2]);
            else if (i == 2)
                fruit.setColor(s[2]);
            else if (i == 3) {
                int price=0;
                for(int v=0; v<s[2].length(); ++v)
                    price=price*10+(s[2].charAt(v)-48);
                fruit.setPrice(price);
            }
        }
        try (Connection connect = Manager.connector(); PreparedStatement addToDB = connect.prepareStatement(addItem)) {
            try {
                addToDB.setString(1, fruit.getName());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                addToDB.setString(2, fruit.getColor());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                addToDB.setInt(3, fruit.getPrice());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                addToDB.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            PreparedStatement readDB = connect.prepareStatement("SELECT id FROM fruit WHERE name=? and color=? and price=?");
            readDB.setString(1,fruit.getName());
            readDB.setString(2,fruit.getColor());
            readDB.setInt(3,fruit.getPrice());
            ResultSet rs=readDB.executeQuery();
            while (rs.next()) {
                fruit.setId(rs.getLong("id"));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fruit;
    }
    public static Fruit putData(String str) throws IOException {
        Fruit fruit = new Fruit();
        long id=0;
        int price=0;
        String[] arr=str.split(",");
        for (int i = 0; i < arr.length; ++i) {
            String[] s=arr[i].split(" ");
            if (i == 0){
                for(int v=0; v<s[1].length(); ++v)
                    id=id*10+(s[1].charAt(v)-48);
            }
            else if (i == 3) {
                price=0;
                for(int v=0; v<s[2].length(); ++v)
                    price=price*10+(s[2].charAt(v)-48);
            }
        }
        try (Connection connect = Manager.connector(); PreparedStatement updateInDB = connect.prepareStatement(updateItem)) {
            updateInDB.setInt(1, price);
            updateInDB.setLong(2,id);
            updateInDB.executeUpdate();
            PreparedStatement readDB = connect.prepareStatement("SELECT name, color, price FROM fruit WHERE id=?");
            readDB.setLong(1,id);
            ResultSet rs=readDB.executeQuery();
            while(rs.next()){
                fruit.setName(rs.getString("name"));
                fruit.setColor(rs.getString("color"));
                fruit.setPrice(rs.getInt("price"));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        fruit.setId(id);
        return fruit;
    }
    public static Fruit deleteData(String str) throws IOException {
        Fruit fruit=new Fruit();
        long id=0;
        String[] arr=str.split(",");
        for (int i = 0; i < arr.length; ++i) {
            String[] s=arr[i].split(" ");
            if (i == 0){
                for(int v=0; v<s[1].length(); ++v)
                    id=id*10+(s[1].charAt(v)-48);
            }
        }
        try (Connection connect = Manager.connector(); PreparedStatement deleteFromDB = connect.prepareStatement(deleteItem)) {
            PreparedStatement readDB = connect.prepareStatement("SELECT name, color, price FROM fruit WHERE id=?");
            readDB.setLong(1,id);
            ResultSet rs=readDB.executeQuery();
            while(rs.next()){
                fruit.setName(rs.getString("name"));
                fruit.setColor(rs.getString("color"));
                fruit.setPrice(rs.getInt("price"));
            }
            deleteFromDB.setLong(1,id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        fruit.setId(id);
        return fruit;
    }
}