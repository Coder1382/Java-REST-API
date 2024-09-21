package rest.services;

import rest.dao.DatabaseConnector;
import rest.dao.Supplier;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandlerSuppliers {
    public static List<Object> getData(String req, long id) throws IOException {
        List<Object> obj= new ArrayList<>();
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement readDB = connect.prepareStatement(req)) {
            if(id>0)
                readDB.setLong(1,id);
                ResultSet result = readDB.executeQuery();
                    while (result.next()) {
                        long i = result.getLong("id");
                        String company = result.getString("company");
                        obj.add(new Supplier(i, company));
                    }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static List<Object> postData(String str) throws IOException {
        List<Object> obj= new ArrayList<>();
        String[] arr=str.split(",");
        long id=0;
            try (Connection connect = DatabaseConnector.connector(); PreparedStatement addToDB = connect.
                    prepareStatement("INSERT INTO suppliers(company) VALUES(?)")) {
                addToDB.setString(1, arr[1].split(" ")[2]);
                addToDB.executeUpdate();
                PreparedStatement readDB = connect.prepareStatement("SELECT * FROM suppliers WHERE company=?");
                readDB.setString(1, arr[1].split(" ")[2]);
                ResultSet result = readDB.executeQuery();
                while (result.next()) {
                    long i = result.getLong("id");
                    String company = result.getString("company");
                    obj.add(new Supplier(i, company));
                }
                connect.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return obj;
    }
    public static List<Object> putData(String str) throws IOException, SQLException {
        List<Object> obj= new ArrayList<>();
        String[] arr=str.split(",");
        long id=0, y=0;
        int x=0;
        for(int v=0; v<(arr[0].split(" "))[1].length(); ++v)
            id=id*10+(arr[0].split(" "))[1].charAt(v)-48;
            Connection connect = DatabaseConnector.connector();
            connect.close();
        return obj;
    }
    public static List<Object> deleteData(String str) throws IOException {
        List<Object> obj= new ArrayList<>();
        long id=0;
        String[] arr=str.split(",");
        String[] s=arr[0].split(" ");
        for(int v=0; v<s[1].length(); ++v)
            id=id*10+(s[1].charAt(v)-48);
        try (Connection connect = DatabaseConnector.connector(); PreparedStatement deleteFromDB = connect.
                prepareStatement("DELETE FROM suppliers WHERE id=?")) {
            PreparedStatement readDB = connect.prepareStatement("SELECT * FROM suppliers WHERE id=?");
            readDB.setLong(1,id);
            ResultSet rs=readDB.executeQuery();
                while (rs.next()) {
                    String company = rs.getString("company");
                    obj.add(new Supplier(id, company));
                }
            deleteFromDB.setLong(1,id);
            deleteFromDB.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
