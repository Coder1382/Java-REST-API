package rest.servlets;//package servlets;
import com.google.gson.Gson;
import rest.dao.Seller;
import rest.services.RequestHandlerSellers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestManagerSellers extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn=new Gson();
        List<Object> obj=new ArrayList<>();
        String[] uri=req.getRequestURI().split("/");
        if(uri[2].equals("sellers"))
            obj=RequestHandlerSellers.postData(jsn.fromJson(req.getReader(), Seller.class).toString());
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
        if(uri[2].equals("sellers")) {
            if (req.getQueryString() != null) {
                String[] query = req.getQueryString().split("=");
                long id = 0;
                for (int v = 0; v < query[1].length(); ++v)
                    id = id * 10 + (query[1].charAt(v) - 48);
                obj = RequestHandlerSellers.getData("SELECT * FROM sellers WHERE id=?", id);
            } else obj = RequestHandlerSellers.getData("SELECT * FROM sellers", -1);
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
        String[] uri=req.getRequestURI().split("/");
        Gson jsn=new Gson();
        List<Object> obj=new ArrayList<>();
            String s=jsn.fromJson(req.getReader(), Seller.class).toString();
            String r=(s.split("rating: "))[1];
            String sup=(s.split("supplier_id: "))[1];
            if(r.charAt(0)!='0' && sup.charAt(0)=='0') {
                System.out.println(r);
                try {
                    obj = RequestHandlerSellers.putData(s,2);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(r.charAt(0)=='0' && sup.charAt(0)!='0') {
                try {
                    obj = RequestHandlerSellers.putData(s, 3);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(r.charAt(0)!='0' && sup.charAt(0)!='0') {
                try {
                    obj = RequestHandlerSellers.putData(s,5);
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
            obj=RequestHandlerSellers.deleteData(jsn.fromJson(req.getReader(), Seller.class).toString());
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            obj.forEach(e->{
                pw.write(e.toString()+"\n");
            });
            pw.close();
        }
    }
}
