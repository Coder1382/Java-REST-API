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

    public void showData(long id, HttpServletResponse res) throws IOException {
        List<Object> obj = new ArrayList<>();
        if (id > 0)
            obj = sdao.showData("SELECT * FROM sellers WHERE id=?", id);
        else obj = sdao.showData("SELECT * FROM sellers", -1);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n\n");
            });
            pw.close();
        }
    }

    public void addData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Seller.class).toString()).split(",");
        long index = Long.parseLong(arr[2].split(" ")[2]);
        long id = sdao.addData(arr[1].split(" ")[2], index);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("successfully added under id: " + id);
            pw.close();
        }
    }

    public void changeData(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Seller.class).toString()).split(",");
        long id = Long.parseLong((arr[0].split(" "))[1]);
        sdao.changeData(id, (arr[1].split(" "))[2]);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("fruit successfully added");
            pw.close();
        }
    }

    public void deleteData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Seller.class).toString()).split(",");
        long id = Long.parseLong((arr[0].split(" "))[1]);
        sdao.deleteData(id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("successfully deleted");
            pw.close();
        }
    }
}
