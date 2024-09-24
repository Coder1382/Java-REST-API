package rest.servlets;

import com.google.gson.Gson;
import rest.model.Seller;
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
        if (uri[2].equals("suppliers")) {
            PrintWriter pw = res.getWriter();
            pw.write("successfully added under id: " + suppliersService.save(jsn.fromJson(req.getReader(), Supplier.class)));
            pw.close();
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("suppliers")) {
            long id = -1;
            if (req.getQueryString() != null)
                id = Long.parseLong(req.getQueryString().split("=")[1]);
            PrintWriter pw = res.getWriter();
            suppliersService.show(id).forEach(e -> {
                pw.write(e.toString() + "\n\n");
            });
            pw.close();
        }
    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn = new Gson();
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("suppliers")) {
            suppliersService.delete(jsn.fromJson(req.getReader(), Supplier.class));
            PrintWriter pw = res.getWriter();
            pw.write("successfully deleted");
            pw.close();
        }
    }
}
