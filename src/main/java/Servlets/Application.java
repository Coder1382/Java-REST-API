package Servlets;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static Servlets.CRUD.*;
//@WebServlet("/db")
public class Application extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] query=new String[2];
        List<Fruit> fruit=new ArrayList<>();
        long id=-1;
        //System.out.println(res);
        if(req.getQueryString()!=null) {
            query = req.getQueryString().split("=");
            id = 0;
            for (int v = 0; v < query[1].length(); ++v)
                id = id * 10 + (query[1].charAt(v) - 48);
            fruit=CRUD.getData("SELECT * FROM fruit WHERE id=?", id);
        }
        else  fruit=CRUD.getData("SELECT * FROM fruit",id);
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            fruit.forEach(e->{
                pw.write(e.toString()+"\n");
            });
            pw.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn=new Gson();
        Fruit fruit=new Fruit();
        fruit=postData(jsn.fromJson(req.getReader(), Fruit.class).toString());
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            pw.write(fruit.toString());
            pw.close();
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn=new Gson();
        Fruit fruit=new Fruit();
        fruit= putData(jsn.fromJson(req.getReader(), Fruit.class).toString());
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            pw.write(fruit.toString());
            pw.close();
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Gson jsn=new Gson();
        Fruit fruit=new Fruit();
        fruit= deleteData(jsn.fromJson(req.getReader(), Fruit.class).toString());
        PrintWriter pw=res.getWriter();
        if(pw!=null){
            pw.write(fruit.toString());
            pw.close();
        }
    }
}
