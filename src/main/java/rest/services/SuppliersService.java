package rest.services;

import com.google.gson.Gson;
import rest.dao.DatabaseConnector;
import rest.dao.FruitDao;
import rest.dao.SuppliersDao;
import rest.model.Fruit;
import rest.model.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuppliersService {
    public static void getData(HttpServletRequest req, long id, HttpServletResponse res) throws IOException {
        List<Object> obj = new ArrayList<>();
        if (id > 0)
            obj = SuppliersDao.getData("SELECT * FROM suppliers WHERE id=?", id);
        else obj = SuppliersDao.getData("SELECT * FROM suppliers", -1);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }

    public static void postData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<Object> obj = new ArrayList<>();
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Supplier.class).toString()).split(",");
        System.out.println(arr[0]);
        System.out.println(arr[1]);
        obj = SuppliersDao.postData(arr[1].split(" ")[2]);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }

    public static void deleteData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<Object> obj = new ArrayList<>();
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Supplier.class).toString()).split(",");
        long id = 0;
        for (int v = 0; v < (arr[0].split(" "))[1].length(); ++v)
            id = id * 10 + ((arr[0].split(" "))[1].charAt(v) - 48);
        obj = SuppliersDao.deleteData(id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }
}
