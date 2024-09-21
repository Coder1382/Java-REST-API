package rest.services;

import rest.dao.DatabaseConnector;
import rest.dao.Seller_fruit;
import rest.dao.Fruit;
import rest.dao.Seller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandlerCombo {
    public static String getData(String req, long id) throws IOException, SQLException {
        String obj="";
        Connection connect = DatabaseConnector.connector();
        try (PreparedStatement readDB = connect.prepareStatement(req)) {
            if(id>0){
                readDB.setLong(1,id);
                ResultSet result = readDB.executeQuery();
                PreparedStatement readSel=connect.prepareStatement("SELECT name FROM sellers WHERE id=?");
                readSel.setLong(1,id);
                ResultSet r=readSel.executeQuery();
                while (r.next()){
                    String n=r.getString("name");
                    obj+="(id="; obj+=id; obj+=")";
                   obj+=n; obj+=": ";
                }
                while (result.next()) {
                        long fruit_id = result.getLong("fruit_id");
                        PreparedStatement readF = connect.prepareStatement("SELECT name FROM fruit WHERE id=?");
                        readF.setLong(1, fruit_id);
                        ResultSet rs = readF.executeQuery();
                        while (rs.next()) {
                            String n=rs.getString("name");
                            obj += n; obj+="(id="; obj+=fruit_id;
                            obj += "), ";
                        }
                }
            }
            else{
                ResultSet result = readDB.executeQuery();
                while(result.next()) {
                    id = result.getLong("seller_id");
                    PreparedStatement readSel = connect.prepareStatement("SELECT name FROM sellers WHERE id=?");
                    readSel.setLong(1, id);
                    ResultSet r = readSel.executeQuery();
                    PreparedStatement readSF=connect.prepareStatement("SELECT fruit_id FROM seller_fruit WHERE seller_id=?");
                    readSF.setLong(1,id);
                    ResultSet reset=readSF.executeQuery();
                    while (r.next()) {
                        String n=r.getString("name");
                        obj+="(id="; obj+=id; obj+=")";
                        obj+=n; obj+=": ";
                    }
                    while (reset.next()) {
                            long fruit_id = reset.getLong("fruit_id");
                            PreparedStatement readF = connect.prepareStatement("SELECT name FROM fruit WHERE id=?");
                            readF.setLong(1, fruit_id);
                            ResultSet rs = readF.executeQuery();
                            while (rs.next()) {
                                String n=rs.getString("name");
                                obj += n; obj+="(id="; obj+=fruit_id;
                                obj += "), ";
                            }
                    }
                    obj+="\n";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connect.close();
        }
        return obj;
    }
    public static List<Object> postData(String str) throws IOException {
        List<Object> obj= new ArrayList<>();
        String[] arr=str.split(",");
        long seller_id=0, fruit_id=0;
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                prepareStatement("INSERT INTO seller_fruit(seller_id, fruit_id) VALUES(?,?)")) {
            for (int v = 0; v < arr[0].split(" ")[1].length(); ++v)
                seller_id = seller_id * 10 + (arr[0].split(" ")[1].charAt(v) - 48);
            for (int v = 0; v < arr[1].split(" ")[2].length(); ++v)
                fruit_id = fruit_id * 10 + (arr[1].split(" ")[2].charAt(v) - 48);
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

    public static List<Object> deleteData(String str) throws IOException {
        List<Object> obj= new ArrayList<>();
        long seller_id=0, fruit_id=0;
        String[] arr=str.split(",");
        for (int v = 0; v < arr[0].split(" ")[1].length(); ++v)
            seller_id = seller_id * 10 + (arr[0].split(" ")[1].charAt(v) - 48);
        for (int v = 0; v < arr[1].split(" ")[2].length(); ++v)
            fruit_id = fruit_id * 10 + (arr[1].split(" ")[2].charAt(v) - 48);
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM seller_fruit WHERE seller_id=? and fruit_id=?")) {
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM seller_fruit WHERE seller_id=? and fruit_id=?");
            readDB.setLong(1,seller_id);
            readDB.setLong(2,fruit_id);
            ResultSet rs=readDB.executeQuery();
            while (rs.next()) {
                long i = rs.getLong("sellet_id");
                long j = rs.getLong("fruit_id");
                int price = rs.getInt("price");
                obj.add(new Seller_fruit(i, j));
            }
            deleteFromDB.setLong(1,seller_id);
            deleteFromDB.setLong(2,fruit_id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
