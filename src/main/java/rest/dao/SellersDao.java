package rest.dao;

import rest.dto.FruitDto;
import rest.dto.SellersDto;
import rest.dto.SuppliersDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellersDao {
    public long save(String name, String supplier) {
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

    public List<SellersDto> find(long id) {
        List<SellersDto> sellersDtos = new ArrayList<>();
        List<FruitDto> fruitList = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement(id > 0 ? "SELECT * FROM sellers WHERE id=?" : "SELECT * FROM sellers")) {
            if (id > 0) readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                String supplier = result.getString("supplier");
                PreparedStatement rdb = connect.prepareStatement("SELECT id FROM suppliers WHERE name=?");
                rdb.setString(1, supplier);
                ResultSet rs = rdb.executeQuery();
                while (rs.next()) {
                    id = rs.getLong("id");
                }
                Array arr = result.getArray("fruits");
                if (arr != null) {
                    String[] fruits = (String[]) arr.getArray();
                    for (int k = 0; k < fruits.length; ++k) {
                        PreparedStatement rdf = connect.prepareStatement("SELECT id, price FROM fruit WHERE name=?");
                        rdf.setString(1, fruits[k]);
                        ResultSet rf = rdf.executeQuery();
                        while (rf.next()) {
                            fruitList.add(new FruitDto(rf.getLong("id"), fruits[k], rf.getInt("price")));
                        }
                    }
                    sellersDtos.add(new SellersDto(i, name, new SuppliersDto(id, supplier), fruitList));
                } else sellersDtos.add(new SellersDto(i, name, new SuppliersDto(id, supplier)));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sellersDtos;
    }

    public void update(long id, String fruit) throws SQLException {
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
    }

    public void delete(long id) {
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect
                .prepareStatement("DELETE FROM sellers WHERE id=?")) {
            deleteFromDB.setLong(1, id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
