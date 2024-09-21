package rest.services;

import com.google.gson.Gson;
import rest.dao.*;
import rest.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComboService {
    public static void getData(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
        String obj = "";
        if (req.getQueryString() != null) {
            String[] query = req.getQueryString().split("=");
            long seller_id = 0;
            for (int v = 0; v < query[1].length(); ++v) {
                seller_id = seller_id * 10 + (query[1].charAt(v) - 48);
            }
            try {
                obj = ComboDao.getData("SELECT fruit_id FROM seller_fruit WHERE seller_id=?", seller_id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                obj = ComboDao.getData("SELECT seller_id FROM seller_fruit", -1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write(obj);
            pw.close();
        }
    }

    public static void postData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Gson jsn = new Gson();
        List<Object> obj = new ArrayList<>();
        String[] arr = (jsn.fromJson(req.getReader(), Seller_fruit.class).toString()).split(",");
        long seller_id = 0, fruit_id = 0;
        for (int v = 0; v < arr[0].split(" ")[1].length(); ++v)
            seller_id = seller_id * 10 + (arr[0].split(" ")[1].charAt(v) - 48);
        for (int v = 0; v < arr[1].split(" ")[2].length(); ++v)
            fruit_id = fruit_id * 10 + (arr[1].split(" ")[2].charAt(v) - 48);
        obj = ComboDao.postData(seller_id, fruit_id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }

    public static void deleteData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        long seller_id = 0, fruit_id = 0;
        List<Object> obj = new ArrayList<>();
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Seller_fruit.class).toString()).split(",");
        for (int v = 0; v < arr[0].split(" ")[1].length(); ++v)
            seller_id = seller_id * 10 + (arr[0].split(" ")[1].charAt(v) - 48);
        for (int v = 0; v < arr[1].split(" ")[2].length(); ++v)
            fruit_id = fruit_id * 10 + (arr[1].split(" ")[2].charAt(v) - 48);
        System.out.println(seller_id);
        System.out.println(fruit_id);
        obj = ComboDao.deleteData(seller_id, fruit_id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }
}
