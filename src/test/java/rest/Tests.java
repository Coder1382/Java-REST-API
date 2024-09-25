package rest;

import rest.dao.*;
import rest.services.*;
import rest.servlets.*;
import rest.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

public class
Tests {
    @Test
    void TestTables() throws SQLException {
        Connection connect = DatabaseConnector.connector();
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.start();
        int exist = 0;
        DatabaseManager.DropTable("fruit");
        DatabaseMetaData tables = connect.getMetaData();
        ResultSet res = tables.getTables(null, null, "fruit", new String[]{"TABLE"});
        if (res.next())
            exist = 1;
        assertEquals(0, exist);
        exist = 0;
        DatabaseManager.DropTable("suppliers");
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "suppliers", new String[]{"TABLE"});
        if (res.next())
            exist = 1;
        assertEquals(0, exist);
        exist = 0;
        DatabaseManager.DropTable("sellers");
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "sellers", new String[]{"TABLE"});
        if (res.next())
            exist = 1;
        assertEquals(0, exist);
        exist = 0;
        exist = 0;
        DatabaseManager.CreateFruitTable();
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "fruit", new String[]{"TABLE"});
        if (res.next())
            exist = 1;
        assertEquals(1, exist);
        DatabaseManager.TruncateTable("fruit");
        exist = 0;
        DatabaseManager.CreateSuppliersTable();
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "suppliers", new String[]{"TABLE"});
        if (res.next())
            exist = 1;
        assertEquals(1, exist);
        DatabaseManager.TruncateTable("suppliers");
        exist = 0;
        DatabaseManager.CreateSellersTable();
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "sellers", new String[]{"TABLE"});
        if (res.next())
            exist = 1;
        assertEquals(1, exist);
        DatabaseManager.TruncateTable("sellers");
        connect.createStatement().executeUpdate("INSERT INTO suppliers(name) VALUES('x-vendor')");
        connect.createStatement().executeUpdate("INSERT INTO sellers(name, supplier_id) VALUES('ivan', 1)");
        connect.createStatement().executeUpdate("INSERT INTO fruit(name, color, price) VALUES('green apple', 'green', 10)");
        postgres.stop();
        connect.close();
    }

    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    FruitServlet appF = new FruitServlet();
    SellersServlet appSel = new SellersServlet();
    SuppliersServlet appSup = new SuppliersServlet();

    @Test
    public void showTest() throws ServletException, IOException, SQLException {
        FruitDao fdao = new FruitDao();
        FruitService fserv = new FruitService();
        SellersDao sdao = new SellersDao();
        SellersService sserv = new SellersService();
        SuppliersDao sudao = new SuppliersDao();
        SuppliersService suserv = new SuppliersService();
        fdao.find(-1);
        sdao.find(-1);
        sdao.update(1, "mango");
        sdao.find(-1);
        sudao.find(-1);
        assertEquals(req.getQueryString(), null);
        fdao.find(1);
        sdao.find(1);
        sudao.find(1);
        req.setAttribute("/myREST/fruit", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        req.setAttribute(null, req.getQueryString());
        when(req.getQueryString()).thenReturn(null);
        req.setAttribute("/myREST/sellers", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        req.setAttribute(null, req.getQueryString());
        when(req.getQueryString()).thenReturn(null);
        req.setAttribute("/myREST/suppliers", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        req.setAttribute(null, req.getQueryString());
        when(req.getQueryString()).thenReturn(null);
        req.setAttribute("/myREST/fruit", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        req.setAttribute("id=1", req.getQueryString());
        when(req.getQueryString()).thenReturn("id=1");
        when(res.getWriter()).thenReturn(pw);
        appF.doGet(req, res);
        req.setAttribute("/myREST/sellers", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        req.setAttribute("id=1", req.getQueryString());
        when(req.getQueryString()).thenReturn("id=1");
        when(res.getWriter()).thenReturn(pw);
        appSel.doGet(req, res);
        req.setAttribute("/myREST/suppliers", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        req.setAttribute("id=1", req.getQueryString());
        when(req.getQueryString()).thenReturn("id=1");
        when(res.getWriter()).thenReturn(pw);
        appSup.doGet(req, res);
    }

    @Test
    public void saveTest() throws ServletException, IOException, SQLException {
        FruitDao fdao = new FruitDao();
        FruitService fserv = new FruitService();
        SellersDao sdao = new SellersDao();
        SellersService sserv = new SellersService();
        SuppliersDao sudao = new SuppliersDao();
        SuppliersService suserv = new SuppliersService();
        DatabaseManager.TruncateTable("sellers");
        DatabaseManager.TruncateTable("suppliers");
        DatabaseManager.TruncateTable("fruit");
        fserv.find(1);
        fserv.find(-1);
        suserv.find(-1);
        suserv.find(1);
        sserv.find(-1);
        sserv.find(1);
        Fruit fruit = new Fruit( 0, "mango", "orange", 10);
        fserv.save(fruit);
        fdao.save("mango", "orange", 10);
        fdao.save("apple", "green", 5);
        Supplier z = new Supplier(0, "big");
        suserv.save(z);
        Seller u = new Seller(0, "ignat", 1);
        sserv.save(u);
        sudao.save("big");
        sudao.save("small");
        sdao.save("fedor", 2);
        sdao.save("petr", 1);
        assertEquals(req.getQueryString(), null);
        req.setAttribute("/myREST/fruits", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruits");
        when(res.getWriter()).thenReturn(pw);
        appF.doPost(req, res);
        req.setAttribute("/myREST/seller", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/seller");
        when(res.getWriter()).thenReturn(pw);
        appSel.doPost(req, res);
        req.setAttribute("/myREST/supplier", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/supplier");
        when(res.getWriter()).thenReturn(pw);
        appSup.doPost(req, res);
    }

    @Test
    public void updateTest() throws ServletException, IOException, SQLException {
        FruitDao fdao = new FruitDao();
        FruitService fserv = new FruitService();
        SellersDao sdao = new SellersDao();
        SellersService sserv = new SellersService();
        SuppliersDao sudao = new SuppliersDao();
        SuppliersService suserv = new SuppliersService();
        Fruit f = new Fruit(1, "", "", 30);
        fserv.update(f);
        fdao.update(1, 20);
        Seller z = new Seller(1, "mango", 0);
        sserv.update(z);
        fserv.update(f);
        sdao.update(1, "mango");
        sdao.update(2, "apple");
        sdao.update(1, "mango");
        sdao.update(2, "apple");

        assertEquals(req.getQueryString(), null);
        req.setAttribute("/myREST/fruits", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruits");
        when(res.getWriter()).thenReturn(pw);
        appF.doPut(req, res);
        req.setAttribute("/myREST/seller", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/seller");
        when(res.getWriter()).thenReturn(pw);
        appSel.doPut(req, res);
    }

    @Test
    public void deleteDataTest() throws ServletException, IOException, SQLException {
        FruitDao fdao = new FruitDao();
        FruitService fserv = new FruitService();
        SellersDao sdao = new SellersDao();
        SellersService sserv = new SellersService();
        fdao.delete(1);
        sdao.delete(1);
        SuppliersDao sudao = new SuppliersDao();
        SuppliersService suserv = new SuppliersService();
        Fruit f = new Fruit(1, "", "", 0);
        fserv.delete(f);
        Seller s = new Seller(1, "", 0);
        sserv.delete(s);
        Supplier u = new Supplier( 1, "");
        suserv.delete(u);
        sudao.delete(1);
        assertEquals(req.getQueryString(), null);
        req.setAttribute("/myREST/fruits", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruits");
        when(res.getWriter()).thenReturn(pw);
        appF.doDelete(req, res);
        req.setAttribute("/myREST/seller", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/seller");
        when(res.getWriter()).thenReturn(pw);
        appSel.doDelete(req, res);
        req.setAttribute("/myREST/supplier", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/supplier");
        when(res.getWriter()).thenReturn(pw);
        appSup.doDelete(req, res);
    }
    @Test
    public void TestEntities() throws SQLException {
        Fruit fruit = new Fruit("tomato", "red", 1);
        fruit.setColor("yellow");
        fruit.setName("mango");
        fruit.setPrice(10);
        assertEquals(fruit.getColor(), "yellow");
        assertEquals(fruit.getName(), "mango");
        assertEquals(fruit.getPrice(), 10);
        Fruit fr = new Fruit();
        assertEquals(fr.toString(), "id: 0, name: , color: , price: 0");
        Fruit f = new Fruit(1, "tomato", "red", 1);
        assertEquals(f.getId(), 1);
        assertEquals(f.toString(), "id: 1, name: tomato, color: red, price: 1");
        List<Seller> list = new ArrayList<>();
        list.add(new Seller(0,"string",1));
        List<Seller> l = new ArrayList<>();
        Fruit fru = new Fruit(1, "tomato", "red", 1, list);
        fru.toString();
        Fruit frui = new Fruit(1, "tomato", "red", 1, l);
        fruit.toString();
        Seller sels = new Seller();
        assertEquals(sels.toString(), "id: 0, name: ");
        Seller seller = new Seller("izi", 1);
        seller.setName("vasiliy");
        seller.setSupplier_id(2);
        assertEquals(seller.getName(), "vasiliy");
        assertEquals(seller.getSupplier_id(), 2);
        Seller sel = new Seller(1, "izi", 2);
        assertEquals(sel.getId(), 1);
        assertEquals(sel.toString(), "id: 1, name: izi");
        Seller selling = new Seller(1, "izi", 2);
        selling.toString();
        Seller sells = new Seller(1, "izi", 1);
        sells.toString();
        Supplier su = new Supplier();
        assertEquals(su.toString(), "id: 0, name: ");
        Supplier sup = new Supplier("comp");
        sup.setName("name");
        assertEquals(sup.getName(), "name");
        Supplier s = new Supplier(1, "comp");
        assertEquals(s.getId(), 1);
        assertEquals(s.toString(), "id: 1, name: comp");
        Supplier suppl = new Supplier(1, "comp", list);
        suppl.toString();
        Supplier supplying = new Supplier(1, "comp", l);
        supplying.toString();
        DatabaseManager.TruncateTable("sellers");
        DatabaseManager.TruncateTable("suppliers");
        DatabaseManager.TruncateTable("fruit");
    }
}
