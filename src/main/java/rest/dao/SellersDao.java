package rest.dao;

import rest.model.Fruit;
import rest.model.Seller;
import rest.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellersDao {
    public List<Object> addData(String n, long index) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO sellers(name,supplier_id) VALUES(?,?)")) {
            addToDB.setString(1, n);
            addToDB.setLong(2, index);
            addToDB.executeUpdate();
            PreparedStatement readDB = connect.prepareStatement("SELECT id FROM sellers WHERE name=? and supplier_id=?");
            readDB.setString(1, n);
            readDB.setLong(2, index);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                obj.add(new Seller(i, n, index));
            }
            PreparedStatement wSup = connect.prepareStatement("UPDATE suppliers SET clients=array_append(clients,?) WHERE id=?");
            wSup.setString(1, n);
            wSup.setLong(2, index);
            wSup.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public List<Object> showData(String req, long id) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.prepareStatement(req)) {
            if (id > 0)
                readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                long supplier_id = result.getLong("supplier_id");
                PreparedStatement rdb = connect.prepareStatement("SELECT company FROM suppliers WHERE id=?");
                rdb.setLong(1, supplier_id);
                ResultSet rs = rdb.executeQuery();
                while (rs.next()) {
                    String company = rs.getString("company");
                    Array f = result.getArray("fruits");
                    if (f != null) {
                        String[] cli = (String[]) f.getArray();
                        List<String> s = new ArrayList<>();
                        for (int k = 0; k < cli.length; ++k)
                            s.add(cli[k] + ", ");
                        obj.add(new Seller(i, name, supplier_id, company, s));
                    } else obj.add(new Seller(i, name, supplier_id, company));
                }
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public int changeData(long id, String f) throws SQLException {
        int signal = 0;
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement updateInDB = connect.
                prepareStatement("UPDATE sellers SET fruits=array_append(fruits,?) WHERE id=?")) {
            updateInDB.setString(1, f);
            System.out.println(f);
            updateInDB.setLong(2, id);
            updateInDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            signal = 1;
            throw new RuntimeException(e);
        }
        return signal;
    }

    public List<Object> deleteData(long id) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM sellers WHERE id=?")) {
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM sellers WHERE id=?");
            readDB.setLong(1, id);
            ResultSet rs = readDB.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                long index = rs.getLong("supplier_id");
                obj.add(new Seller(id, name, index));
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
