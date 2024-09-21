package rest.servlets;
import com.google.gson.Gson;
import rest.dao.Fruit;
import rest.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestManagerFruit extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn=new Gson();
        List<Object> obj=new ArrayList<>();
        String[] uri=req.getRequestURI().split("/");
        if(uri[2].equals("fruit"))
            obj=RequestHandlerFruit.postData(jsn.fromJson(req.getReader(), Fruit.class).toString());
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            obj.forEach(e->{
                pw.write(e.toString()+"\n");
            });
            pw.close();
        }
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Object> obj=new ArrayList<>();
        String[] uri=req.getRequestURI().split("/");
        if(uri[2].equals("fruit")) {
            if (req.getQueryString() != null) {
                String[] query = req.getQueryString().split("=");
                long id = 0;
                for (int v = 0; v < query[1].length(); ++v)
                    id = id * 10 + (query[1].charAt(v) - 48);
                obj = RequestHandlerFruit.getData("SELECT * FROM fruit WHERE id=?", id);
            } else obj = RequestHandlerFruit.getData("SELECT * FROM fruit", -1);
        }
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            obj.forEach(e->{
                pw.write(e.toString()+"\n");
            });
            pw.close();
        }
    }
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn=new Gson();
        List<Object> obj=new ArrayList<>();
        String[] uri=req.getRequestURI().split("/");
        if(uri[2].equals("fruit")) {
            try {
                obj=RequestHandlerFruit.putData(jsn.fromJson(req.getReader(), Fruit.class).toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            obj.forEach(e->{
                pw.write(e.toString()+"\n");
            });
            pw.close();
        }
    }
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri=req.getRequestURI().split("/");
        Gson jsn=new Gson();
        List<Object> obj=new ArrayList<>();
        if(uri[2].equals("fruit"))
            obj=RequestHandlerFruit.deleteData(jsn.fromJson(req.getReader(), Fruit.class).toString());
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            obj.forEach(e->{
                pw.write(e.toString()+"\n");
            });
            pw.close();
        }
    }
}
