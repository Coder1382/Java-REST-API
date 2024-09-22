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
    final SellersDao sdao = new SellersDao();

    public void showData(HttpServletRequest req, long id, HttpServletResponse res) throws IOException {
        List<Object> obj = new ArrayList<>();
        if (id > 0)
            obj = sdao.showData("SELECT * FROM sellers WHERE id=?", id);
        else obj = sdao.showData("SELECT * FROM sellers", -1);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }

    public void addData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<Object> obj = new ArrayList<>();
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Seller.class).toString()).split(",");
        long id = 0, index = 0;
        for (int v = 0; v < arr[2].split(" ")[2].length(); ++v)
            index = index * 10 + (arr[2].split(" ")[2].charAt(v) - 48);
        obj = sdao.addData(arr[1].split(" ")[2], index);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }

    public void changeData(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Seller.class).toString()).split(",");
        long id = 0;
        for (int v = 0; v < (arr[0].split(" "))[1].length(); ++v)
            id = id * 10 + (arr[0].split(" "))[1].charAt(v) - 48;
        System.out.println((arr[0].split(" "))[1]);
        int signal = sdao.changeData(id, (arr[1].split(" "))[2]);
        PrintWriter pw = res.getWriter();
        if (pw != null && signal == 0) {
            pw.write("fruit successfully added");
            pw.close();
        }
    }

    public void deleteData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<Object> obj = new ArrayList<>();
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Seller.class).toString()).split(",");
        long id = 0;
        for (int v = 0; v < (arr[0].split(" "))[1].length(); ++v)
            id = id * 10 + ((arr[0].split(" "))[1].charAt(v) - 48);
        obj = sdao.deleteData(id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }
}
