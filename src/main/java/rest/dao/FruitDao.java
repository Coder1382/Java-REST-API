package rest.dao;

import rest.model.Fruit;
import rest.model.Seller;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FruitDao {
    public Fruit find(long id) throws IOException {
        Fruit fruits = new Fruit();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM fruit WHERE id=?")) {
            readDB.setLong(1, id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                String name = result.getString("name");
                int price = result.getInt("price");
                Array arr = result.getArray("sellers");
                if (arr != null) {
                    String[] sellers = (String[]) arr.getArray();
                    List<Seller> sellersList = new ArrayList<>();
                    for (int k = 0; k < sellers.length; ++k) {
                        PreparedStatement rdf = connect.prepareStatement("SELECT id FROM sellers WHERE name=?");
                        rdf.setString(1, sellers[k]);
                        ResultSet rf = rdf.executeQuery();
                        while (rf.next()) {
                            sellersList.add(new Seller(sellers[k], rf.getLong("id")));
                        }
                    }
                    fruits = new Fruit(id, name, price, sellersList);
                }
                fruits = new Fruit(id, name, price);
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fruits;
    }

    public List<Fruit> find() throws IOException {
        List<Fruit> fruits = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM fruit")) {
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                Array arr = result.getArray("sellers");
                if (arr != null) {
                    String[] sellers = (String[]) arr.getArray();
                    List<Seller> sellersList = new ArrayList<>();
                    for (int k = 0; k < sellers.length; ++k) {
                        PreparedStatement rdf = connect.prepareStatement("SELECT id FROM sellers WHERE name=?");
                        rdf.setString(1, sellers[k]);
                        ResultSet rf = rdf.executeQuery();
                        while (rf.next()) {
                            sellersList.add(new Seller(sellers[k], rf.getLong("id")));
                        }
                    }
                    fruits.add(new Fruit(i, name, price, sellersList));
                } else fruits.add(new Fruit(i, name, price));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fruits;
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
        String seller = fruit.getSeller();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.
                prepareStatement("SELECT * FROM fruit WHERE id=? and ?=ANY(sellers)")) {
            readDB.setLong(1, id);
            readDB.setString(2, seller);
            ResultSet rs = readDB.executeQuery();
            while (rs.next()) {
                return id;
            }
            PreparedStatement updateInDB = connect.prepareStatement("UPDATE fruit SET sellers=array_append(sellers,?) WHERE id=?");
            updateInDB.setString(1, seller);
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
