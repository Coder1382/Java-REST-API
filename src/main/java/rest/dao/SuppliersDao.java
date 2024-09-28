package rest.dao;

import rest.dto.SuppliersDto;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDao {
    public long save(SuppliersDto supplier) {
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

    public long delete(SuppliersDto supplier) {
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

    public List<SuppliersDto> find(long id) throws IOException {
        List<SuppliersDto> suppliersDtos = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement(id > 0 ? "SELECT * FROM suppliers WHERE id=?" : "SELECT * FROM suppliers")) {
            if (id > 0)
                readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                suppliersDtos.add(new SuppliersDto(i, name));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suppliersDtos;
    }
}