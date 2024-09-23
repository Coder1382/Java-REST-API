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
    private final FruitDao fruitDao = new FruitDao();

    public void showData(long id, HttpServletResponse res) throws IOException {
        List<Object> obj = fruitDao.showData(id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n\n");
            });
            pw.close();
        }
    }

    public void saveData(String[] arr, HttpServletResponse res) throws IOException {
        int pr = Integer.parseInt(arr[3].split(" ")[2]);
        long id = fruitDao.saveData(arr[1].split(" ")[2], arr[2].split(" ")[2], pr);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("successfully added under id: " + id);
            pw.close();
        }
    }

    public void changeData(String[] arr, HttpServletResponse res) throws IOException, SQLException {
        long id = Long.parseLong((arr[0].split(" "))[1]);
        int pr = Integer.parseInt((arr[3].split(" "))[2]);
        fruitDao.changeData(id, pr);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("price successfully changed");
            pw.close();
        }
    }

    public void deleteData(String[] arr, HttpServletResponse res) throws IOException {
        long id = Long.parseLong((arr[0].split(" "))[1]);
        fruitDao.deleteData(id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("successfully deleted");
            pw.close();
        }
    }
}
