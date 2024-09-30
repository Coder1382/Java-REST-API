package rest.dao;

import rest.dto.FruitDto;
import rest.dto.SellersDto;
import rest.dto.SuppliersDto;
import rest.model.Fruit;
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
        String name = supplier.getName();
        String seller = supplier.getSeller();
        long id = -1;
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM suppliers WHERE name=? and ?=ANY(sellers)")) {
            readDB.setString(1, name);
            readDB.setString(2, seller);
            ResultSet resset = readDB.executeQuery();
            while (resset.next()) {
                id = resset.getLong("id");
            }
            if (id ==-1) {
                PreparedStatement updateInDB = connect.prepareStatement("UPDATE suppliers SET sellers=array_append(sellers,?) WHERE name=?");
                updateInDB.setString(1, seller);
                updateInDB.setString(2, name);
                updateInDB.executeUpdate();
            }
            PreparedStatement rDB = connect.prepareStatement("SELECT id FROM suppliers WHERE name=? and ?=ANY(sellers)");
            rDB.setString(1, name);
            readDB.setString(2, seller);
            ResultSet rs = readDB.executeQuery();
            while (rs.next()) {
                id = rs.getLong("id");
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public List<SuppliersDto> find(long id) throws IOException {
        List<SuppliersDto> suppliersDtos = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement(id > 0 ? "SELECT * FROM suppliers WHERE id=?" : "SELECT * FROM suppliers")) {
            if (id > 0) readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                Array arr = result.getArray("sellers");
                if (arr != null) {
                    String[] sellers = (String[]) arr.getArray();
                    List<SellersDto> sellersList = new ArrayList<>();
                    for (int k = 0; k < sellers.length; ++k) {
                        PreparedStatement rdf = connect.prepareStatement("SELECT id FROM sellers WHERE name=?");
                        rdf.setString(1, sellers[k]);
                        ResultSet rf = rdf.executeQuery();
                        while (rf.next()) {
                            sellersList.add(new SellersDto(rf.getLong("id"), sellers[k]));
                        }
                    }
                    suppliersDtos.add(new SuppliersDto(i, name, sellersList));
                } else suppliersDtos.add(new SuppliersDto(i, name));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suppliersDtos;
    }
}