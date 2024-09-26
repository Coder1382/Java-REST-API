package rest.servlets;

import com.google.gson.Gson;
import rest.dto.SuppliersDto;
import rest.services.SuppliersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class SuppliersServlet extends HttpServlet {
    private final SuppliersService suppliersService = new SuppliersService();
    private final Gson jsn = new Gson();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getRequestURI().split("/")[2].equals("suppliers")) {
            PrintWriter pw = res.getWriter();
            pw.write("saved under id: " + suppliersService.save(jsn.fromJson(req.getReader(), SuppliersDto.class)));
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
                    return;
                }
            PrintWriter pw = res.getWriter();
            suppliersService.find(id).forEach(e -> {
                pw.write(jsn.toJson(e) + "\n\n");
            });
            pw.close();
        } else return;
    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getRequestURI().split("/")[2].equals("suppliers")) {
            suppliersService.delete(jsn.fromJson(req.getReader(), SuppliersDto.class));
            PrintWriter pw = res.getWriter();
            pw.write("deleted");
            pw.close();
        }
    }
}
