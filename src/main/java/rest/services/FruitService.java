package rest.services;

import com.google.gson.Gson;
import rest.dao.DatabaseConnector;
import rest.dao.FruitDao;
import rest.model.Fruit;

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

public class FruitService {
    public static void getData(HttpServletRequest req, long id, HttpServletResponse res) throws IOException {
        List<Object> obj = new ArrayList<>();
        if (id > 0)
            obj = FruitDao.getData("SELECT * FROM fruit WHERE id=?", id);
        else obj = FruitDao.getData("SELECT * FROM fruit", -1);
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
        String[] arr = (jsn.fromJson(req.getReader(), Fruit.class).toString()).split(",");
        int pr = 0;
        for (int v = 0; v < arr[3].split(" ")[2].length(); ++v)
            pr = pr * 10 + (arr[3].split(" ")[2].charAt(v) - 48);
        obj = FruitDao.postData(arr[1].split(" ")[2], arr[2].split(" ")[2], pr);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }

    public static void putData(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
        List<Object> obj = new ArrayList<>();
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Fruit.class).toString()).split(",");
        long id = 0;
        int pr = 0;
        for (int v = 0; v < (arr[0].split(" "))[1].length(); ++v)
            id = id * 10 + (arr[0].split(" "))[1].charAt(v) - 48;
        String[] s = arr[3].split(" ");
        for (int v = 0; v < s[2].length(); ++v)
            pr = pr * 10 + s[2].charAt(v) - 48;
        obj = FruitDao.putData(id, pr);
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
        String[] arr = (jsn.fromJson(req.getReader(), Fruit.class).toString()).split(",");
        long id = 0;
        for (int v = 0; v < (arr[0].split(" "))[1].length(); ++v)
            id = id * 10 + ((arr[0].split(" "))[1].charAt(v) - 48);
        obj = FruitDao.deleteData(id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }
}
