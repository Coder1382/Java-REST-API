package rest.servlets;

import com.google.gson.Gson;
import rest.dto.SuppliersDto;
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
    private final Gson jsn = new Gson();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("suppliers")) {
            PrintWriter pw = res.getWriter();
            pw.write("successfully added under id: " + suppliersService.save(jsn.fromJson(req.getReader(), SuppliersDto.class)));
            pw.close();
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri.length < 5 && uri[2].equals("suppliers")) {
            long id = -1;
            if (uri.length == 4 && !uri[3].equals("all"))
                try {
                    id = Long.parseLong(uri[3]);
                } catch (NumberFormatException e) {
                    throw e;
                }
            PrintWriter pw = res.getWriter();
            suppliersService.find(id).forEach(e -> {
                pw.write(jsn.toJson(e) + "\n\n");
            });
            pw.close();
        } else throw new RuntimeException("Bad URL");
    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri[2].equals("suppliers")) {
            suppliersService.delete(jsn.fromJson(req.getReader(), SuppliersDto.class));
            PrintWriter pw = res.getWriter();
            pw.write("successfully deleted");
            pw.close();
        }
    }
}
