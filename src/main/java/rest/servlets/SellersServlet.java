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
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("sellers"))
            SellersService.postData(req, res);
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
                SellersService.getData(req, id, res);
            } else SellersService.getData(req, -1, res);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        Gson jsn = new Gson();
        if (uri[2].equals("sellers")) {
            String s = jsn.fromJson(req.getReader(), Seller.class).toString();
            String r = (s.split("rating: "))[1];
            String sup = (s.split("supplier_id: "))[1];
            if (r.charAt(0) != '0' && sup.charAt(0) == '0') {
                try {
                    SellersService.putData(s, 2, res);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (r.charAt(0) == '0' && sup.charAt(0) != '0') {
                try {
                    SellersService.putData(s, 3, res);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (r.charAt(0) != '0' && sup.charAt(0) != '0') {
                try {
                    SellersService.putData(s, 5, res);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("sellers"))
            SellersService.deleteData(req, res);
    }
}
