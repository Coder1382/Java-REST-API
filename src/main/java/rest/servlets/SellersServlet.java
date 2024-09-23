package rest.servlets;//package servlets;

import com.google.gson.Gson;
import rest.model.Seller;
import rest.services.SellersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellersServlet extends HttpServlet {
    private final SellersService sellersService = new SellersService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn = new Gson();
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("sellers"))
            sellersService.saveData((jsn.fromJson(req.getReader(), Seller.class).toString()).split(","), res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("sellers")) {
            if (req.getQueryString() != null) {
                String[] query = req.getQueryString().split("=");
                long id = Long.parseLong(query[1]);
                sellersService.showData(id, res);
            } else sellersService.showData(-1, res);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn = new Gson();
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("sellers")) {
            try {
                sellersService.changeData((jsn.fromJson(req.getReader(), Seller.class).toString()).split(","), res);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn = new Gson();
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("sellers"))
            sellersService.deleteData((jsn.fromJson(req.getReader(), Seller.class).toString()).split(","), res);
    }
}
