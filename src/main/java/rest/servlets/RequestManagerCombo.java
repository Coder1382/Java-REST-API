package rest.servlets;

import com.google.gson.Gson;
import rest.dao.Fruit;
import rest.dao.Seller_fruit;
import rest.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestManagerCombo extends HttpServlet {
    final String table_name = "seller_fruit";
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn = new Gson();
        List<Object> obj = new ArrayList<>();
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals(table_name))
            obj = RequestHandlerCombo.postData(jsn.fromJson(req.getReader(), Seller_fruit.class).toString());
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String obj = "";
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals(table_name)) {
            if (req.getQueryString() != null) {
                String[] query = req.getQueryString().split("=");
                long seller_id = 0;
                for (int v = 0; v < query[1].length(); ++v) {
                    seller_id = seller_id * 10 + (query[1].charAt(v) - 48);
                }
                try {
                    obj = RequestHandlerCombo.getData("SELECT fruit_id FROM seller_fruit WHERE seller_id=?", seller_id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    obj = RequestHandlerCombo.getData("SELECT seller_id FROM seller_fruit", -1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            pw.write(obj);
            pw.close();
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        Gson jsn = new Gson();
        List<Object> obj = new ArrayList<>();
        if (uri[2].equals(table_name))
            obj = RequestHandlerCombo.deleteData(jsn.fromJson(req.getReader(), Seller_fruit.class).toString());
        PrintWriter pw = res.getWriter();
        if (pw != null) {
            obj.forEach(e -> {
                pw.write(e.toString() + "\n");
            });
            pw.close();
        }
    }
}
