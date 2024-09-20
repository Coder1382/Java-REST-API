package rest;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/***
 * The class to ensure the addition, retrieval, updating, and deletion od data to/from a database
 */
public class RequestManager extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn=new Gson();
        List<Object> obj=new ArrayList<>();
        String[] uri=req.getRequestURI().split("/");
        if(uri[2].equals("fruit"))
            obj=PostRequestHandler.postData(jsn.fromJson(req.getReader(), Fruit.class).toString(), "fruit");
        else if(uri[2].equals("sellers"))
            obj=PostRequestHandler.postData(jsn.fromJson(req.getReader(), Fruit.class).toString(), "sellers");
        else if(uri[2].equals("suppliers"))
            obj=PostRequestHandler.postData(jsn.fromJson(req.getReader(), Fruit.class).toString(), "suppliers");
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            obj.forEach(e->{
                pw.write(e.toString()+"\n");
            });
            pw.close();
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Object> obj=new ArrayList<>();
        String[] uri=req.getRequestURI().split("/");
        if(req.getQueryString()!=null) {
            String[] query = req.getQueryString().split("=");
            long id = 0;
            for (int v = 0; v < query[1].length(); ++v)
                id = id * 10 + (query[1].charAt(v) - 48);
            obj= GetRequestHandler.getData("SELECT * FROM "+uri[2]+" WHERE id=?", id, uri[2]);
        }
        else  obj= GetRequestHandler.getData("SELECT * FROM "+uri[2],-1, uri[2]);
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            obj.forEach(e->{
                pw.write(e.toString()+"\n");
            });
            pw.close();
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri=req.getRequestURI().split("/");
        Gson jsn=new Gson();
        List<Object> obj=new ArrayList<>();
        if(uri[2].equals("fruit")) {
            String s=jsn.fromJson(req.getReader(), Fruit.class).toString();
            try {
                obj=PutRequestHandler.putData(s, "fruit", 0);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(uri[2].equals("sellers")) {
            String s=jsn.fromJson(req.getReader(), Seller.class).toString();
            String r=(s.split("rating: "))[1];
            String sup=(s.split("supplier_id: "))[1];
            if(r.charAt(0)!='0' && sup.charAt(0)=='0') {
                System.out.println(r);
                try {
                    obj = PutRequestHandler.putData(s, "sellers", 2);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(r.charAt(0)=='0' && sup.charAt(0)!='0') {
                try {
                    obj = PutRequestHandler.putData(s, "sellers", 3);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(r.charAt(0)!='0' && sup.charAt(0)!='0') {
                try {
                    obj = PutRequestHandler.putData(s, "sellers", 5);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else if(uri[2].equals("suppliers")) {
            String s=jsn.fromJson(req.getReader(), Supplier.class).toString();
            try {
                obj=PutRequestHandler.putData(s, "suppliers", 0);
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri=req.getRequestURI().split("/");
        Gson jsn=new Gson();
        List<Object> obj=new ArrayList<>();
        if(uri[2].equals("fruit"))
            obj=DeleteRequestHandler.deleteData(jsn.fromJson(req.getReader(), Fruit.class).toString(), "fruit");
        if(uri[2].equals("sellers"))
            obj=DeleteRequestHandler.deleteData(jsn.fromJson(req.getReader(), Seller.class).toString(), "sellers");
        if(uri[2].equals("suppliers"))
            obj=DeleteRequestHandler.deleteData(jsn.fromJson(req.getReader(), Supplier.class).toString(), "suppliers");
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            obj.forEach(e->{
                pw.write(e.toString()+"\n");
            });
            pw.close();
        }
    }
}
