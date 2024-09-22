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
    final SellersService sserv = new SellersService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("sellers"))
            sserv.addData(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("sellers")) {
            if (req.getQueryString() != null) {
                String[] query = req.getQueryString().split("=");
                long id = 0;
                for (int v = 0; v < query[1].length(); ++v)
                    id = id * 10 + (query[1].charAt(v) - 48);
                sserv.showData(req, id, res);
            } else sserv.showData(req, -1, res);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("sellers")) {
            try {
                sserv.changeData(req, res);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("sellers"))
            sserv.deleteData(req, res);
    }
}
