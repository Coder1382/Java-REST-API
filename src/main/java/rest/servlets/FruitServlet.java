package rest.servlets;

import com.google.gson.Gson;
import rest.model.Fruit;
import rest.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FruitServlet extends HttpServlet {
    final FruitService fserv = new FruitService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit"))
            fserv.addData(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit")) {
            if (req.getQueryString() != null) {
                String[] query = req.getQueryString().split("=");
                long id = Long.parseLong(query[1]);
                fserv.showData(id, res);
            } else fserv.showData(-1, res);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit")) {
            try {
                fserv.changeData(req, res);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit"))
            fserv.deleteData(req, res);
        PrintWriter pw = res.getWriter();
    }
}
