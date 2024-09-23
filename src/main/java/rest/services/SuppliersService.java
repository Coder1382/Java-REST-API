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
    private final SuppliersDao suppliersDao = new SuppliersDao();

    public void showData(long id, HttpServletResponse res) throws IOException {
        List<Object> obj = suppliersDao.showData(id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n\n");
            });
            pw.close();
        }
    }

    public void saveData(String[] arr, HttpServletResponse res) throws IOException {
        long id = suppliersDao.saveData(arr[1].split(" ")[2]);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("successfully added under id: " + id);
            pw.close();
        }
    }

    public void deleteData(String[] arr, HttpServletResponse res) throws IOException {
        long id = Long.parseLong((arr[0].split(" "))[1]);
        suppliersDao.deleteData(id);
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write("successfully deleted");
            pw.close();
        }
    }
}
