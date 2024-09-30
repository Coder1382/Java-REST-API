package rest.dao;

import rest.model.Seller;
import rest.model.Supplier;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDao {
    public long save(Supplier supplier) {
        String comp = supplier.getName();
        long id = 0;
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO suppliers (name) VALUES(?)")) {
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

    public long delete(Supplier supplier) {
        long id = supplier.getId();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement udb = connect.
                prepareStatement("DELETE from suppliers WHERE id=?")) {
            udb.setLong(1, id);
            udb.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public long update(Supplier supplier) throws SQLException {
        String seller = supplier.getName();
        long id = supplier.getId();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM suppliers WHERE id=? and ?=ANY(sellers)")) {
            readDB.setLong(1, id);
            readDB.setString(2, seller);
            ResultSet resset = readDB.executeQuery();
            while (resset.next()) {
                return id;
            }
            PreparedStatement updateInDB = connect.prepareStatement("UPDATE suppliers SET sellers=array_append(sellers,?) WHERE id=?");
            updateInDB.setString(1, seller);
            updateInDB.setLong(2, id);
            updateInDB.executeUpdate();

            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public Supplier find(long id) throws IOException {
        Supplier suppliers = new Supplier();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM suppliers WHERE id=?")) {
            if (id > 0) readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                String name = result.getString("name");
                Array arr = result.getArray("sellers");
                if (arr != null) {
                    String[] sellers = (String[]) arr.getArray();
                    List<Seller> sellersList = new ArrayList<>();
                    for (int k = 0; k < sellers.length; ++k) {
                        PreparedStatement rdf = connect.prepareStatement("SELECT id FROM sellers WHERE name=?");
                        rdf.setString(1, sellers[k]);
                        ResultSet rf = rdf.executeQuery();
                        while (rf.next()) {
                            sellersList.add(new Seller(rf.getLong("id"), sellers[k]));
                        }
                    }
                    suppliers = new Supplier(id, name, sellersList);
                } else suppliers = new Supplier(id, name);
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suppliers;
    }

    public List<Supplier> find() throws IOException {
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM suppliers")) {
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                Array arr = result.getArray("sellers");
                if (arr != null) {
                    String[] sellers = (String[]) arr.getArray();
                    List<Seller> sellersList = new ArrayList<>();
                    for (int k = 0; k < sellers.length; ++k) {
                        PreparedStatement rdf = connect.prepareStatement("SELECT id FROM sellers WHERE name=?");
                        rdf.setString(1, sellers[k]);
                        ResultSet rf = rdf.executeQuery();
                        while (rf.next()) {
                            sellersList.add(new Seller(rf.getLong("id"), sellers[k]));
                        }
                    }
                    suppliers.add(new Supplier(i, name, sellersList));
                } else suppliers.add(new Supplier(i, name));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suppliers;
    }
}