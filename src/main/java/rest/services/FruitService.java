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
    final FruitDao fdao = new FruitDao();

    public void showData(HttpServletRequest req, long id, HttpServletResponse res) throws IOException {
        List<Object> obj = new ArrayList<>();
        if (id > 0)
            obj = fdao.showData("SELECT * FROM fruit WHERE id=?", id);
        else obj = fdao.showData("SELECT * FROM fruit", -1);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n\n");
            });
            pw.close();
        }
    }

    public void addData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        long id = 0;
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Fruit.class).toString()).split(",");
        int pr = Integer.parseInt(arr[3].split(" ")[2]);
        id = fdao.addData(arr[1].split(" ")[2], arr[2].split(" ")[2], pr);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("successfully added under id: " + id);
            pw.close();
        }
    }

    public void changeData(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Fruit.class).toString()).split(",");
        long id = 0;
        int pr = 0;
        for (int v = 0; v < (arr[0].split(" "))[1].length(); ++v)
            id = id * 10 + (arr[0].split(" "))[1].charAt(v) - 48;
        for (int v = 0; v < (arr[3].split(" "))[2].length(); ++v)
            pr = pr * 10 + (arr[3].split(" "))[2].charAt(v) - 48;
        fdao.changeData(id, pr);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("price successfully changed");
            pw.close();
        }
    }

    public void deleteData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Gson jsn = new Gson();
        String[] arr = (jsn.fromJson(req.getReader(), Fruit.class).toString()).split(",");
        long id = 0;
        for (int v = 0; v < (arr[0].split(" "))[1].length(); ++v)
            id = id * 10 + ((arr[0].split(" "))[1].charAt(v) - 48);
        fdao.deleteData(id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("successfully deleted");
            pw.close();
        }
    }
}
