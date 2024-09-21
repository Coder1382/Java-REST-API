package rest.dao;

import rest.model.Seller_fruit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ComboDao {
    public static List<Object> postData(long seller_id, long fruit_id) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO seller_fruit(seller_id, fruit_id) VALUES(?,?)")) {
            addToDB.setLong(1, seller_id);
            addToDB.setLong(2, fruit_id);
            addToDB.executeUpdate();
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM seller_fruit WHERE seller_id=? and fruit_id=?");
            readDB.setLong(1, seller_id);
            readDB.setLong(2, fruit_id);
            ResultSet result = readDB.executeQuery();
            while (result.next()) {
                long i = result.getLong("seller_id");
                long j = result.getLong("fruit_id");
                obj.add(new Seller_fruit(i, j));
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static List<Object> deleteData(long seller_id, long fruit_id) {
        List<Object> obj = new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM seller_fruit WHERE seller_id=? and fruit_id=?")) {
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM seller_fruit WHERE seller_id=? and fruit_id=?");
            readDB.setLong(1, seller_id);
            readDB.setLong(2, fruit_id);
            ResultSet rs = readDB.executeQuery();
            while (rs.next()) {
                long i = rs.getLong("seller_id");
                long j = rs.getLong("fruit_id");
                obj.add(new Seller_fruit(i, j));
            }
            deleteFromDB.setLong(1, seller_id);
            deleteFromDB.setLong(2, fruit_id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static String getData(String req, long id) throws SQLException {
        String obj = "";
        Connection connect = DatabaseConnector.connector();
        try (PreparedStatement readDB = connect.prepareStatement(req)) {
            if (id > 0) {
                readDB.setLong(1, id);
                ResultSet result = readDB.executeQuery();
                PreparedStatement readSel = connect.prepareStatement("SELECT name FROM sellers WHERE id=?");
                readSel.setLong(1, id);
                ResultSet r = readSel.executeQuery();
                while (r.next()) {
                    String n = r.getString("name");
                    obj += "(id=";
                    obj += id;
                    obj += ")";
                    obj += n;
                    obj += ": ";
                }
                while (result.next()) {
                    long fruit_id = result.getLong("fruit_id");
                    PreparedStatement readF = connect.prepareStatement("SELECT name FROM fruit WHERE id=?");
                    readF.setLong(1, fruit_id);
                    ResultSet rs = readF.executeQuery();
                    while (rs.next()) {
                        String n = rs.getString("name");
                        obj += n;
                        obj += "(id=";
                        obj += fruit_id;
                        obj += "), ";
                    }
                }
            } else {
                List<Long> buf = new ArrayList<>();
                ResultSet result = readDB.executeQuery();
                while (result.next()) {
                    int k = 0;
                    id = result.getLong("seller_id");
                    for (k = 0; k < buf.size(); ++k) {
                        System.out.println(buf.get(k));
                        if (buf.get(k).equals(id))
                            break;
                    }
                    if (k < buf.size()) continue;
                    else buf.add(id);
                    PreparedStatement readSel = connect.prepareStatement("SELECT name FROM sellers WHERE id=?");
                    readSel.setLong(1, id);
                    ResultSet r = readSel.executeQuery();
                    PreparedStatement readSF = connect.prepareStatement("SELECT fruit_id FROM seller_fruit WHERE seller_id=?");
                    readSF.setLong(1, id);
                    ResultSet reset = readSF.executeQuery();
                    while (r.next()) {
                        String n = r.getString("name");
                        obj += "(id=";
                        obj += id;
                        obj += ")";
                        obj += n;
                        obj += ": ";
                    }
                    while (reset.next()) {
                        long fruit_id = reset.getLong("fruit_id");
                        PreparedStatement readF = connect.prepareStatement("SELECT name FROM fruit WHERE id=?");
                        readF.setLong(1, fruit_id);
                        ResultSet rs = readF.executeQuery();
                        while (rs.next()) {
                            String n = rs.getString("name");
                            obj += n;
                            obj += "(id=";
                            obj += fruit_id;
                            obj += "), ";
                        }
                    }
                    obj += "\n";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connect.close();
        }
        return obj;
    }
}
