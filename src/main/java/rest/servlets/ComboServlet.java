package rest.servlets;

import com.google.gson.Gson;
import rest.dao.ComboDao;
import rest.model.Seller_fruit;
import rest.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComboServlet extends HttpServlet {
    final String table_name = "seller_fruit";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals(table_name))
            ComboService.postData(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String obj = "";
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals(table_name)) {
            try {
                ComboService.getData(req, res);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals(table_name))
            ComboService.deleteData(req, res);
    }
}
