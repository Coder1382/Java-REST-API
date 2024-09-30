package rest.dao;

import rest.model.Fruit;
import rest.model.Seller;
import rest.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellersDao {
    public long save(Seller seller) {
        String name = seller.getName();
        String supplier = seller.getSupplier();
        long id = 0;
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO sellers(name, supplier) VALUES(?,?)")) {
            addToDB.setString(1, name);
            addToDB.setString(2, supplier);
            addToDB.executeUpdate();
            PreparedStatement readDB = connect.prepareStatement("SELECT id FROM sellers WHERE name=? and supplier=?");
            readDB.setString(1, name);
            readDB.setString(2, supplier);
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

    public Seller find(long id) {
        Seller sellers = new Seller();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM sellers WHERE id=?")) {
            readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                String name = result.getString("name");
                String supplier = result.getString("supplier");
                Array arr = result.getArray("fruits");
                PreparedStatement rdb = connect.prepareStatement("SELECT id FROM suppliers WHERE name=?");
                rdb.setString(1, supplier);
                ResultSet rs = rdb.executeQuery();
                while (rs.next()) {
                    id = rs.getLong("id");
                }
                if (arr != null) {
                    String[] fruits = (String[]) arr.getArray();
                    List<Fruit> fruitList = new ArrayList<>();
                    for (int k = 0; k < fruits.length; ++k) {
                        PreparedStatement rdf = connect.prepareStatement("SELECT id, price FROM fruit WHERE name=?");
                        rdf.setString(1, fruits[k]);
                        ResultSet rf = rdf.executeQuery();
                        while (rf.next()) {
                            fruitList.add(new Fruit(rf.getLong("id"), fruits[k], rf.getInt("price")));
                        }
                    }
                    sellers = new Seller(id, name, new Supplier(id, supplier), fruitList);
                }
                sellers = new Seller(id, name, new Supplier(id, supplier));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sellers;
    }

    public List<Seller> find() {
        List<Seller> sellers = new ArrayList<>();
        long id = 0;
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM sellers")) {
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                String supplier = result.getString("supplier");
                Array arr = result.getArray("fruits");
                PreparedStatement rdb = connect.prepareStatement("SELECT id FROM suppliers WHERE name=?");
                rdb.setString(1, supplier);
                ResultSet rs = rdb.executeQuery();
                while (rs.next()) {
                    id = rs.getLong("id");
                }
                if (arr != null) {
                    String[] fruits = (String[]) arr.getArray();
                    List<Fruit> fruitList = new ArrayList<>();
                    for (int k = 0; k < fruits.length; ++k) {
                        PreparedStatement rdf = connect.prepareStatement("SELECT id, price FROM fruit WHERE name=?");
                        rdf.setString(1, fruits[k]);
                        ResultSet rf = rdf.executeQuery();
                        while (rf.next()) {
                            fruitList.add(new Fruit(rf.getLong("id"), fruits[k], rf.getInt("price")));
                        }
                    }
                    sellers.add(new Seller(i, name, new Supplier(id, supplier), fruitList));
                } else sellers.add(new Seller(i, name, new Supplier(id, supplier)));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sellers;
    }

    public long update(Seller seller) throws SQLException {
        long id = seller.getId();
        String fruit = seller.getFruit();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM sellers WHERE id=? and ?=ANY(fruits)")) {
            readDB.setLong(1, id);
            readDB.setString(2, fruit);
            ResultSet resset = readDB.executeQuery();
            int i = 0;
            while (resset.next()) {
                ++i;
            }
            if (i == 0) {
                PreparedStatement updateInDB = connect.prepareStatement("UPDATE sellers SET fruits=array_append(fruits,?) WHERE id=?");
                updateInDB.setString(1, fruit);
                updateInDB.setLong(2, id);
                updateInDB.executeUpdate();
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public long delete(Seller seller) {
        long id = seller.getId();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect
                .prepareStatement("DELETE FROM sellers WHERE id=?")) {
            deleteFromDB.setLong(1, id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}
