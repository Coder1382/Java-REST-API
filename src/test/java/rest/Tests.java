package rest;

import rest.dao.*;
import rest.services.*;
import rest.servlets.*;
import rest.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

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
        DatabaseManager.DropTable("seller_fruit");
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "seller_fruit", new String[]{"TABLE"});
        if (res.next())
            exist = 1;
        assertEquals(0, exist);
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
        exist = 0;
        DatabaseManager.CreateCombinationTable();
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "seller_fruit", new String[]{"TABLE"});
        if (res.next())
            exist = 1;
        assertEquals(1, exist);
        DatabaseManager.TruncateTable("seller_fruit");
        connect.createStatement().executeUpdate("INSERT INTO suppliers(company) VALUES('x-vendor')");
        connect.createStatement().executeUpdate("INSERT INTO sellers(name, rating, supplier_id) VALUES('ivan', 5, 1)");
        connect.createStatement().executeUpdate("INSERT INTO fruit(name, color, price) VALUES('green apple', 'green', 10)");
        connect.createStatement().executeUpdate("INSERT INTO seller_fruit(seller_id, fruit_id) VALUES(1,1)");
        postgres.stop();
        connect.close();
    }

    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    FruitServlet appF = new FruitServlet();
    SellersServlet appSel = new SellersServlet();
    SuppliersServlet appSup = new SuppliersServlet();
    ComboServlet appFs = new ComboServlet();

    @Test
    public void getDataTest() throws ServletException, IOException, SQLException {
        FruitDao.getData("SELECT * FROM fruit", -1);
        SellersDao.getData("SELECT * FROM sellers", -1);
        SuppliersDao.getData("SELECT * FROM suppliers", -1);
        ComboDao.getData("SELECT seller_id FROM seller_fruit", -1);
        ComboDao.getData("SELECT fruit_id FROM seller_fruit WHERE seller_id=?", 1);
        assertEquals(req.getQueryString(), null);
        FruitDao.getData("SELECT * FROM fruit WHERE id=?", 1);
        SellersDao.getData("SELECT * FROM sellers WHERE id=?", 1);
        SuppliersDao.getData("SELECT * FROM suppliers WHERE id=?", 1);
        req.setAttribute("/myREST/fruit", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        req.setAttribute(null, req.getQueryString());
        when(req.getQueryString()).thenReturn(null);
        appF.doGet(req, res);
        req.setAttribute("/myREST/sellers", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        req.setAttribute(null, req.getQueryString());
        when(req.getQueryString()).thenReturn(null);
        appSel.doGet(req, res);
        req.setAttribute("/myREST/suppliers", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        req.setAttribute(null, req.getQueryString());
        when(req.getQueryString()).thenReturn(null);
        appSup.doGet(req, res);
        req.setAttribute("/myREST/seller_fruit", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/seller_fruit");
        req.setAttribute(null, req.getQueryString());
        when(req.getQueryString()).thenReturn(null);
        appFs.doGet(req, res);
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
        req.setAttribute("/myREST/seller_fruit", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/seller_fruit");
        req.setAttribute("id=1", req.getQueryString());
        when(req.getQueryString()).thenReturn("id=1");
        appFs.doGet(req, res);
    }

    @Test
    public void postDataTest() throws ServletException, IOException, SQLException {
        DatabaseManager.TruncateTable("sellers");
        DatabaseManager.TruncateTable("suppliers");
        DatabaseManager.TruncateTable("fruit");
        DatabaseManager.TruncateTable("seller_fruit");
        FruitDao.postData("mango", "orange", 10);
        FruitDao.postData("apple", "green", 5);
        SuppliersDao.postData("big");
        SuppliersDao.postData("small");
        SellersDao.postData("petr", 3, 1);
        SellersDao.postData("fedor", 5, 2);
        ComboDao.postData(1, 1);
        ComboDao.postData(1, 2);
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
        req.setAttribute("/myREST/_", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/_");
        when(res.getWriter()).thenReturn(pw);
        appFs.doPost(req, res);
    }

    @Test
    public void putDataTest() throws ServletException, IOException, SQLException {
        FruitDao.putData(1, 20);
        SellersDao.putData(1, 8, 1, 2);
        SellersDao.putData(2, 5, 1, 3);
        SellersDao.putData(1, 7, 1, 5);
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
    public void deleteDataTest() throws ServletException, IOException {
        FruitDao.deleteData(1);
        SellersDao.deleteData(1);
        SuppliersDao.deleteData(1);
        ComboDao.deleteData(1, 1);
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
        req.setAttribute("/myREST/_", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/_");
        when(res.getWriter()).thenReturn(pw);
        appFs.doDelete(req, res);
    }

    @Test
    public void TestEntities() {
        Fruit fruit = new Fruit("tomato", "red", 1);
        fruit.setColor("yellow");
        fruit.setName("mango");
        fruit.setPrice(10);
        assertEquals(fruit.getColor(), "yellow");
        assertEquals(fruit.getName(), "mango");
        assertEquals(fruit.getPrice(), 10);
        Fruit f = new Fruit(1, "tomato", "red", 1);
        f.setId(3);
        assertEquals(f.getId(), 3);
        assertEquals(f.toString(), "id: 3, name: tomato, color: red, price: 1");
        Seller seller = new Seller("izi", 2, 1);
        seller.setRating(8);
        seller.setName("vasiliy");
        seller.setSupplier_id(2);
        assertEquals(seller.getRating(), 8);
        assertEquals(seller.getName(), "vasiliy");
        assertEquals(seller.getSupplier_id(), 2);
        Seller sel = new Seller(1, "izi", 2, 1);
        sel.setId(2);
        assertEquals(sel.getId(), 2);
        assertEquals(sel.toString(), "id: 2, name: izi, rating: 2, supplier_id: 1");
        Supplier sup = new Supplier("comp");
        sup.setCompany("company");
        assertEquals(sup.getCompany(), "company");
        Supplier s = new Supplier(1, "comp");
        s.setId(7);
        assertEquals(s.getId(), 7);
        assertEquals(s.toString(), "id: 7, supplier: comp");
        Seller_fruit self = new Seller_fruit(1, 1);
        self.setSeller_id(2);
        self.setFruit_id(2);
        assertEquals(self.getFruit_id(), 2);
        assertEquals(self.getSeller_id(), 2);
        assertEquals(self.toString(), "seller_id: 2, fruit_id: 2");
    }
}
