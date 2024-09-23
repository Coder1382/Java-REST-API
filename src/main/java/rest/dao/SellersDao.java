package rest.dao;

import rest.model.Fruit;
import rest.model.Seller;
import rest.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellersDao {
    public long addData(String n, long index) {
        System.out.println(n);
        System.out.println(index);
        long id = 0;
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
                id = result.getLong("id");
            }
            PreparedStatement wSup = connect.prepareStatement("UPDATE suppliers SET clients=array_append(clients,?) WHERE id=?");
            wSup.setString(1, n);
            wSup.setLong(2, index);
            wSup.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
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
                PreparedStatement rdb = connect.prepareStatement("SELECT name FROM suppliers WHERE id=?");
                rdb.setLong(1, supplier_id);
                ResultSet rs = rdb.executeQuery();
                while (rs.next()) {
                    String company = rs.getString("name");
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

    public void changeData(long id, String f) throws SQLException {
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM sellers WHERE id=? and ?=ANY(fruits)")) {
            readDB.setLong(1, id);
            readDB.setString(2, f);
            ResultSet resset = readDB.executeQuery();
            int i = 0;
            while (resset.next()) {
                ++i;
            }
            if (i == 0) {
                PreparedStatement updateInDB = connect.prepareStatement("UPDATE sellers SET fruits=array_append(fruits,?) WHERE id=?");
                updateInDB.setString(1, f);
                updateInDB.setLong(2, id);
                updateInDB.executeUpdate();
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteData(long id) {
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT name, supplier_id FROM sellers WHERE id=?")) {
            long i = 0;
            String name = "";
            readDB.setLong(1, id);
            ResultSet rs = readDB.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                i = rs.getLong("supplier_id");
            }
            PreparedStatement deleteFromDB = connect.prepareStatement("DELETE FROM sellers WHERE id=?");
            deleteFromDB.setLong(1, id);
            deleteFromDB.executeUpdate();
            PreparedStatement del = connect.prepareStatement("UPDATE suppliers SET clients=ARRAY_REMOVE(clients, ?) WHERE id=?");
            del.setString(1, name);
            del.setLong(2, i);
            del.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
