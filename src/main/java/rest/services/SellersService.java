package rest.services;

import com.google.gson.Gson;
import rest.dao.DatabaseConnector;
import rest.dao.FruitDao;
import rest.dao.SellersDao;
import rest.model.Fruit;
import rest.model.Seller;

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

public class SellersService {
    public static void getData(HttpServletRequest req, long id, HttpServletResponse res) throws IOException {
        List<Object> obj = new ArrayList<>();
        if (id > 0)
            obj = SellersDao.getData("SELECT * FROM sellers WHERE id=?", id);
        else obj = SellersDao.getData("SELECT * FROM sellers", -1);
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
        String[] arr = (jsn.fromJson(req.getReader(), Seller.class).toString()).split(",");
        long id = 0, index = 0;
        int r = 0;
        for (int v = 0; v < arr[2].split(" ")[2].length(); ++v)
            r = r * 10 + (arr[2].split(" ")[2].charAt(v) - 48);
        for (int v = 0; v < arr[3].split(" ")[2].length(); ++v)
            index = index * 10 + (arr[3].split(" ")[2].charAt(v) - 48);
        obj = SellersDao.postData(arr[1].split(" ")[2], r, index);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }

    public static void putData(String str, int col, HttpServletResponse res) throws IOException, SQLException {
        List<Object> obj = new ArrayList<>();
        String[] arr = str.split(",");
        long id = 0, y = 0;
        int x = 0;
        for (int v = 0; v < (arr[0].split(" "))[1].length(); ++v)
            id = id * 10 + (arr[0].split(" "))[1].charAt(v) - 48;
        if (col == 2) {
            String[] s = arr[2].split(" ");
            for (int v = 0; v < s[2].length(); ++v)
                x = x * 10 + s[2].charAt(v) - 48;
        } else if (col == 3) {
            String[] s = arr[3].split(" ");
            for (int v = 0; v < s[2].length(); ++v)
                y = y * 10 + s[2].charAt(v) - 48;
        } else if (col == 5) {
            String[] s = arr[2].split(" ");
            for (int v = 0; v < s[2].length(); ++v)
                x = x * 10 + s[2].charAt(v) - 48;
            String[] z = arr[3].split(" ");
            for (int v = 0; v < z[2].length(); ++v)
                y = y * 10 + z[2].charAt(v) - 48;
        }
        obj = SellersDao.putData(id, x, y, col);
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
        String[] arr = (jsn.fromJson(req.getReader(), Seller.class).toString()).split(",");
        long id = 0;
        for (int v = 0; v < (arr[0].split(" "))[1].length(); ++v)
            id = id * 10 + ((arr[0].split(" "))[1].charAt(v) - 48);
        obj = SellersDao.deleteData(id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }
}
