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
    private final SuppliersService suppliersService = new SuppliersService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn = new Gson();
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("suppliers"))
            suppliersService.saveData((jsn.fromJson(req.getReader(), Supplier.class).toString()).split(","), res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("suppliers")) {
            if (req.getQueryString() != null) {
                String[] query = req.getQueryString().split("=");
                long id = Long.parseLong(query[1]);
                suppliersService.showData(id, res);
            } else suppliersService.showData(-1, res);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn = new Gson();
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("suppliers"))
            suppliersService.deleteData((jsn.fromJson(req.getReader(), Supplier.class).toString()).split(","), res);
    }
}
