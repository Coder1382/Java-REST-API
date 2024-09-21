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
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit"))
            FruitService.postData(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit")) {
            if (req.getQueryString() != null) {
                String[] query = req.getQueryString().split("=");
                long id = 0;
                for (int v = 0; v < query[1].length(); ++v)
                    id = id * 10 + (query[1].charAt(v) - 48);
                FruitService.getData(req, id, res);
            } else FruitService.getData(req, -1, res);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit")) {
            try {
                FruitService.putData(req, res);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("fruit"))
            FruitService.deleteData(req, res);
        PrintWriter pw = res.getWriter();
    }
}
