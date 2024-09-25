package rest.servlets;

import com.google.gson.Gson;
import rest.model.Fruit;
import rest.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FruitServlet extends HttpServlet {
    private final FruitService fruitService = new FruitService();
    private final Gson jsn = new Gson();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit")) {
            PrintWriter pw = res.getWriter();
            pw.write("successfully added under id: " + fruitService.save(jsn.fromJson(req.getReader(), Fruit.class)));
            pw.close();
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri.length < 5 && uri[2].equals("fruit")) {
            long id = -1;
            if (uri.length == 4 && !uri[3].equals("all"))
                try {
                    id = Long.parseLong(uri[3]);
                } catch (NumberFormatException e) {
                    throw e;
                }
            PrintWriter pw = res.getWriter();
            fruitService.find(id).forEach(e -> {
                pw.write(jsn.toJson(e) + "\n\n");
            });
            pw.close();
        } else throw new RuntimeException("Bad URL");
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit")) {
            try {
                fruitService.update(jsn.fromJson(req.getReader(), Fruit.class));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = res.getWriter();
            pw.write("price successfully changed");
            pw.close();
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn = new Gson();
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit")) {
            fruitService.delete(jsn.fromJson(req.getReader(), Fruit.class));
            PrintWriter pw = res.getWriter();
            pw.write("successfully deleted");
            pw.close();
        }
    }
}
