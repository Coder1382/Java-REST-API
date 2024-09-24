package rest.dao;

import com.fasterxml.jackson.core.json.async.NonBlockingJsonParserBase;
import rest.dao.DatabaseConnector;
import rest.model.Fruit;
import rest.model.Supplier;
import rest.services.FruitService;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDao {
    public long save(String comp) {
        long id = 0;
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO suppliers(name) VALUES(?)")) {
            addToDB.setString(1, comp);
            addToDB.executeUpdate();
            PreparedStatement readDB = connect.prepareStatement("SELECT id FROM suppliers WHERE name=?");
            readDB.setString(1, comp);
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

    public void delete(long id) {
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement udb = connect.
                prepareStatement("DELETE from suppliers WHERE id=?")) {
            udb.setLong(1, id);
            udb.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object> showData(long id) throws IOException {
        List<Object> obj = new ArrayList<>();
        String query = (id > 0 ? "SELECT * FROM suppliers WHERE id=?" : "SELECT * FROM suppliers");
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.prepareStatement(query)) {
            if (id > 0)
                readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                Array cl = result.getArray("clients");
                if (cl != null) {
                    String[] cli = (String[]) cl.getArray();
                    List<String> s = new ArrayList<>();
                    for (int k = 0; k < cli.length; ++k)
                        s.add(cli[k] + ", ");
                    obj.add(new Supplier(i, name, s));
                } else obj.add(new Supplier(i, name));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}