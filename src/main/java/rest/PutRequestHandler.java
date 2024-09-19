package rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PutRequestHandler {
    public static List<Object> putData(String str, String table, int col) throws IOException, SQLException {
        List<Object> obj= new ArrayList<>();
        String[] arr=str.split(",");
        long id=0, y=0;
        int x=0;
        for(int v=0; v<(arr[0].split(" "))[1].length(); ++v)
            id=id*10+(arr[0].split(" "))[1].charAt(v)-48;
        if(table.equals("fruit")) {
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
        }
        else if(table.equals("sellers")) {
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
        }
        return obj;
    }
}
