package rest.services;

import rest.dao.DatabaseConnector;
import rest.dao.Seller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandlerSellers {
    public static List<Object> getData(String req, long id) throws IOException {
        List<Object> obj= new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.prepareStatement(req)) {
            if(id>0)
                readDB.setLong(1,id);
            ResultSet result = readDB.executeQuery();
                    while (result.next()) {
                        long i = result.getLong("id");
                        String name = result.getString("name");
                        int rating = result.getInt("rating");
                        long supplier_id = result.getLong("supplier_id");
                        PreparedStatement rdb = connect.prepareStatement("SELECT company FROM suppliers WHERE id=?");
                        rdb.setLong(1,supplier_id);
                        ResultSet rs = rdb.executeQuery();
                        while(rs.next()){
                            String company=rs.getString("company");
                            obj.add(new Seller(i, name, rating, supplier_id, company));
                        }
                    }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static List<Object> postData(String str) throws IOException {
        List<Object> obj= new ArrayList<>();
        String[] arr=str.split(",");
        long id=0, index=0;
            int r=0;
            try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                    prepareStatement("INSERT INTO sellers(name,rating,supplier_id) VALUES(?,?,?)")) {
                addToDB.setString(1, arr[1].split(" ")[2]);
                for (int v = 0; v < arr[2].split(" ")[2].length(); ++v)
                    r = r * 10 + (arr[2].split(" ")[2].charAt(v) - 48);
                addToDB.setInt(2, r);
                for (int v = 0; v < arr[3].split(" ")[2].length(); ++v)
                    index = index * 10 + (arr[3].split(" ")[2].charAt(v) - 48);
                addToDB.setLong(3, index);
                addToDB.executeUpdate();
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
        return obj;
    }
    public static List<Object> putData(String str, int col) throws IOException, SQLException {
        List<Object> obj= new ArrayList<>();
        String[] arr=str.split(",");
        long id=0, y=0;
        int x=0;
        for(int v=0; v<(arr[0].split(" "))[1].length(); ++v)
            id=id*10+(arr[0].split(" "))[1].charAt(v)-48;
            Connection connect = DatabaseConnector.connector();
            if (col == 2) {
                String[] s=arr[2].split(" ");
                for(int v=0; v<s[2].length(); ++v)
                    x=x*10+s[2].charAt(v)-48;
                try (PreparedStatement updateInDB = connect.prepareStatement("UPDATE sellers SET rating=? WHERE id=?")) {
                    updateInDB.setInt(1, x);
                    updateInDB.setLong(2, id);
                    updateInDB.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (col == 3) {
                String[] s=arr[3].split(" ");
                for(int v=0; v<s[2].length(); ++v)
                    y=y*10+s[2].charAt(v)-48;
                try (PreparedStatement updateInDB = connect.prepareStatement("UPDATE sellers SET supplier_id=? WHERE id=?")) {
                    updateInDB.setLong(1, y);
                    updateInDB.setLong(2, id);
                    updateInDB.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (col == 5) {
                String[] s=arr[2].split(" ");
                for(int v=0; v<s[2].length(); ++v)
                    x=x*10+s[2].charAt(v)-48;
                String[] z=arr[3].split(" ");
                for(int v=0; v<z[2].length(); ++v)
                    y=y*10+z[2].charAt(v)-48;
                try (PreparedStatement updateInDB = connect.prepareStatement("UPDATE sellers SET rating=?, supplier_id=? WHERE id=?")) {
                    updateInDB.setInt(1, x);
                    updateInDB.setLong(2, y);
                    updateInDB.setLong(3, id);
                    updateInDB.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            PreparedStatement readDB = connect.prepareStatement("SELECT name, rating, supplier_id FROM sellers WHERE id=?");
            readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                String name=result.getString("name");
                int rating = result.getInt("rating");
                long supplier_id = result.getLong("supplier_id");
                obj.add(new Seller(id, name, rating, supplier_id));
            }
            connect.close();
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
                prepareStatement("DELETE FROM sellers WHERE id=?")) {
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM sellers WHERE id=?");
            readDB.setLong(1,id);
            ResultSet rs=readDB.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    int rating = rs.getInt("rating");
                    long supplier_id = rs.getLong("supplier_id");
                    obj.add(new Seller(id, name, rating, supplier_id));
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
