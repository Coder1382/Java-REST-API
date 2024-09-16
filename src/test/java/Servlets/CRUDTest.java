package Servlets;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.apache.sling.api.servlets.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CRUDTest extends SlingAllMethodsServlet {
    private final static String query="id=3";
    private final static String data="id: 3, name: red apple, color: red, price: 15\n";
    private final static String url="http://localhost:8080/myREST/db";
    @Test
    public void getDataTest() throws ServletException, IOException {
        final CRUD crud=mock(CRUD.class);
        List<Fruit> fruit=CRUD.getData("SELECT * FROM fruit WHERE id=?",3);
        final Application app=new Application();
        final HttpServletRequest req=mock(HttpServletRequest.class);
        final HttpServletResponse res=mock(HttpServletResponse.class);
        PrintWriter pw=mock(PrintWriter.class);
        app.doGet(req,res);
        when(req.getQueryString()).thenReturn("id=3");
    }
    @Test
    public void postDataTest() throws ServletException, IOException {
        final CRUD crud=mock(CRUD.class);
        //Gson jsn=new Gson();
        String s="id: 0, name: berry, color: violet, price: 500";
        Fruit fruit=CRUD.postData(s);
        final Application app=new Application();
        final HttpServletRequest req=mock(HttpServletRequest.class);
        final HttpServletResponse res=mock(HttpServletResponse.class);
        PrintWriter pw=mock(PrintWriter.class);
        app.doGet(req,res);
        //when(CRUD.postData(s)).thenReturn(fruit);
    }
    @Test
    public void putDataTest() throws ServletException, IOException {
        final CRUD crud=mock(CRUD.class);
        String s="id: 3, price: 999";
        Fruit fruit=CRUD.putData(s);
        final Application app=new Application();
        final HttpServletRequest req=mock(HttpServletRequest.class);
        final HttpServletResponse res=mock(HttpServletResponse.class);
        PrintWriter pw=mock(PrintWriter.class);
        app.doGet(req,res);
        //when(CRUD.postData(s)).thenReturn(fruit);
    }
    @Test
    public void deleteDataTest() throws ServletException, IOException {
        final CRUD crud=mock(CRUD.class);
        String s="id: 3";
        Fruit fruit=CRUD.deleteData(s);
        final Application app=new Application();
        final HttpServletRequest req=mock(HttpServletRequest.class);
        final HttpServletResponse res=mock(HttpServletResponse.class);
        PrintWriter pw=mock(PrintWriter.class);
        app.doGet(req,res);
        //when(CRUD.postData(s)).thenReturn(fruit);
    }
}
