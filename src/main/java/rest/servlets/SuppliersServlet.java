package rest.servlets;

import com.google.gson.Gson;
import rest.model.Supplier;
import rest.services.SuppliersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppliersServlet extends HttpServlet {
    SuppliersService suserv = new SuppliersService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("suppliers"))
            suserv.addData(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("suppliers")) {
            if (req.getQueryString() != null) {
                String[] query = req.getQueryString().split("=");
                long id = Long.parseLong(query[1]);
                suserv.showData(id, res);
            } else suserv.showData(-1, res);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("suppliers"))
            suserv.deleteData(req, res);
    }
}
