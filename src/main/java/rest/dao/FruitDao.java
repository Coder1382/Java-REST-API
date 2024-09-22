package rest.dao;

import com.fasterxml.jackson.core.json.async.NonBlockingJsonParserBase;
import rest.dao.DatabaseConnector;
import rest.model.Fruit;
import rest.model.Seller;
import rest.services.FruitService;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FruitDao {
    public static List<Object> getData(String req, long id) throws IOException {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.prepareStatement(req)) {
            if (id > 0)
                readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                String color = result.getString("color");
                int price = result.getInt("price");
                Array sel = result.getArray("sel");
                if (sel != null) {
                    String[] sell = (String[]) sel.getArray();
                    List<String> s = new ArrayList<>();
                    for (int k = 0; k < sell.length; ++k)
                        s.add(sell[k] + ", ");
                    obj.add(new Fruit(i, name, color, price, s));
                } else obj.add(new Fruit(i, name, color, price));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static List<Object> postData(String n, String col, int pr) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO fruit(name,color,price) VALUES(?,?,?)")) {
            addToDB.setString(1, n);
            addToDB.setString(2, col);
            addToDB.setInt(3, pr);
            addToDB.executeUpdate();
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM fruit WHERE name=? and color=? and price=?");
            readDB.setString(1, n);
            readDB.setString(2, col);
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

    public static List<Object> putData(long id, int pr) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement updateInDB = connect.
                prepareStatement("UPDATE fruit SET price=? WHERE id=?")) {
            updateInDB.setInt(1, pr);
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

    public static List<Object> deleteData(long id) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM fruit WHERE id=?")) {
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM fruit WHERE id=?");
            readDB.setLong(1, id);
            ResultSet rs = readDB.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String color = rs.getString("color");
                int price = rs.getInt("price");
                obj.add(new Fruit(id, name, color, price));
            }
            deleteFromDB.setLong(1, id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
