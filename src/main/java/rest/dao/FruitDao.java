package rest.dao;

import rest.dto.FruitDto;
import rest.model.Fruit;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FruitDao {
    public List<FruitDto> find(long id) throws IOException {
        List<FruitDto> fruitDtos = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement(id > 0 ? "SELECT * FROM fruit WHERE id=?" : "SELECT * FROM fruit")) {
            if (id > 0) readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                fruitDtos.add(new FruitDto(i, name, price));
            }
            connect.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fruitDtos;
    }

    public long save(Fruit fruit) {
        String name = fruit.getName();
        int pr = fruit.getPrice();
        long id = 0;
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO fruit(name, price) VALUES(?,?)")) {
            addToDB.setString(1, name);
            addToDB.setInt(2, pr);
            addToDB.executeUpdate();
            PreparedStatement readDB = connect.prepareStatement("SELECT id FROM fruit WHERE name=? and price=?");
            readDB.setString(1, name);
            readDB.setInt(2, pr);
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

    public long update(Fruit fruit) {
        long id = fruit.getId();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement updateInDB = connect.
                prepareStatement("UPDATE fruit SET price=? WHERE id=?")) {
            updateInDB.setInt(1, fruit.getPrice());
            updateInDB.setLong(2, id);
            updateInDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public long delete(Fruit fruit) {
        long id = fruit.getId();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM fruit WHERE id=?")) {
            deleteFromDB.setLong(1, id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}
