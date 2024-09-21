package rest.dao;

import com.fasterxml.jackson.core.json.async.NonBlockingJsonParserBase;
import rest.dao.DatabaseConnector;
import rest.model.Fruit;
import rest.model.Supplier;
import rest.services.FruitService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDao {
    public static List<Object> postData(String comp) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO suppliers(company) VALUES(?)")) {
            addToDB.setString(1, comp);
            addToDB.executeUpdate();
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM suppliers WHERE company=?");
            readDB.setString(1, comp);
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
        return obj;
    }

    public static List<Object> deleteData(long id) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM suppliers WHERE id=?")) {
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM suppliers WHERE id=?");
            readDB.setLong(1, id);
            ResultSet rs = readDB.executeQuery();
            while (rs.next()) {
                String company = rs.getString("company");
                obj.add(new Supplier(id, company));
            }
            deleteFromDB.setLong(1, id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static List<Object> getData(String req, long id) throws IOException {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.prepareStatement(req)) {
            if (id > 0)
                readDB.setLong(1, id);
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
        return obj;
    }
}
