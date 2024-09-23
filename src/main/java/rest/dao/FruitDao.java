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
    public List<Object> showData(String req, long id) throws IOException {
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
                obj.add(new Fruit(i, name, color, price));
            }
            connect.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public long addData(String n, String col, int pr) {
        long id = 0;
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO fruit(name,color,price) VALUES(?,?,?)")) {
            addToDB.setString(1, n);
            addToDB.setString(2, col);
            addToDB.setInt(3, pr);
            addToDB.executeUpdate();
            PreparedStatement readDB = connect.prepareStatement("SELECT id FROM fruit WHERE name=? and color=? and price=?");
            readDB.setString(1, n);
            readDB.setString(2, col);
            readDB.setInt(3, pr);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                id = result.getLong("id");
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void changeData(long id, int pr) {
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement updateInDB = connect.
                prepareStatement("UPDATE fruit SET price=? WHERE id=?")) {
            updateInDB.setInt(1, pr);
            updateInDB.setLong(2, id);
            updateInDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteData(long id) {
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM fruit WHERE id=?")) {
            deleteFromDB.setLong(1, id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
