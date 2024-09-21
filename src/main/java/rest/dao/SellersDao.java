package rest.dao;

import rest.model.Fruit;
import rest.model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellersDao {
    public static List<Object> postData(String n, int r, long index) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO sellers(name,rating,supplier_id) VALUES(?,?,?)")) {
            addToDB.setString(1, n);
            addToDB.setInt(2, r);
            addToDB.setLong(3, index);
            addToDB.executeUpdate();
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM sellers WHERE name=? and rating=? and supplier_id=?");
            readDB.setString(1, n);
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

    public static List<Object> getData(String req, long id) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.prepareStatement(req)) {
            if (id > 0)
                readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                int rating = result.getInt("rating");
                long supplier_id = result.getLong("supplier_id");
                PreparedStatement rdb = connect.prepareStatement("SELECT company FROM suppliers WHERE id=?");
                rdb.setLong(1, supplier_id);
                ResultSet rs = rdb.executeQuery();
                while (rs.next()) {
                    String company = rs.getString("company");
                    obj.add(new Seller(i, name, rating, supplier_id, company));
                }
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static List<Object> putData(long id, int x, long y, int col) throws SQLException {
        List<Object> obj = new ArrayList<>();
        Connection connect = DatabaseConnector.connector();
        if (col == 2) {
            try (PreparedStatement updateInDB = connect.prepareStatement("UPDATE sellers SET rating=? WHERE id=?")) {
                updateInDB.setInt(1, x);
                updateInDB.setLong(2, id);
                updateInDB.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (col == 3) {
            try (PreparedStatement updateInDB = connect.prepareStatement("UPDATE sellers SET supplier_id=? WHERE id=?")) {
                updateInDB.setLong(1, y);
                updateInDB.setLong(2, id);
                updateInDB.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (col == 5) {
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
            String name = result.getString("name");
            int rating = result.getInt("rating");
            long supplier_id = result.getLong("supplier_id");
            obj.add(new Seller(id, name, rating, supplier_id));
        }
        connect.close();
        return obj;
    }

    public static List<Object> deleteData(long id) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM sellers WHERE id=?")) {
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM sellers WHERE id=?");
            readDB.setLong(1, id);
            ResultSet rs = readDB.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int rating = rs.getInt("rating");
                long index = rs.getLong("supplier_id");
                obj.add(new Seller(id, name, rating, index));
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
