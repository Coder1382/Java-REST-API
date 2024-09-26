package rest;

import com.google.gson.Gson;
import rest.dao.*;
import rest.dto.FruitDto;
import rest.dto.SellersDto;
import rest.dto.SuppliersDto;
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
        if (res.next()) exist = 1;
        assertEquals(0, exist);
        exist = 0;
        DatabaseManager.DropTable("suppliers");
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "suppliers", new String[]{"TABLE"});
        if (res.next()) exist = 1;
        assertEquals(0, exist);
        exist = 0;
        DatabaseManager.DropTable("sellers");
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "sellers", new String[]{"TABLE"});
        if (res.next()) exist = 1;
        assertEquals(0, exist);
        exist = 0;
        DatabaseManager.CreateFruitTable();
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "fruit", new String[]{"TABLE"});
        if (res.next()) exist = 1;
        assertEquals(1, exist);
        exist = 0;
        DatabaseManager.CreateSuppliersTable();
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "suppliers", new String[]{"TABLE"});
        if (res.next()) exist = 1;
        assertEquals(1, exist);
        exist = 0;
        DatabaseManager.CreateSellersTable();
        tables = connect.getMetaData();
        res = tables.getTables(null, null, "sellers", new String[]{"TABLE"});
        if (res.next()) exist = 1;
        assertEquals(1, exist);
        postgres.stop();
        connect.close();
    }

    @Test
    public void TestDao() throws ServletException, IOException, SQLException {
        FruitDao fruitDao = new FruitDao();
        SellersDao sellersDao = new SellersDao();
        SuppliersDao suppliersDao = new SuppliersDao();
        fruitDao.find(1);
        fruitDao.find(-1);
        fruitDao.find(0);
        sellersDao.find(1);
        sellersDao.find(-1);
        sellersDao.find(0);
        suppliersDao.find(1);
        suppliersDao.find(-1);
        suppliersDao.find(0);
        fruitDao.save("tomato", 7);
        sellersDao.save("akim", "big");
        suppliersDao.save("new");
        fruitDao.update(1, 8);
        sellersDao.update(1, "mango");
        fruitDao.delete(1);
        sellersDao.delete(1);
        suppliersDao.delete(1);
    }

    @Test
    public void TestService() throws ServletException, IOException, SQLException {
        FruitService fruitService = new FruitService();
        SellersService sellersService = new SellersService();
        SuppliersService suppliersService = new SuppliersService();
        fruitService.find(2);
        fruitService.find(-1);
        fruitService.find(0);
        sellersService.find(2);
        sellersService.find(-1);
        sellersService.find(0);
        suppliersService.find(2);
        suppliersService.find(-1);
        suppliersService.find(0);
        fruitService.save(new FruitDto("carrot", 7));
        sellersService.save(new SellersDto("afanasiy", "big"));
        suppliersService.save(new SuppliersDto("old"));
        fruitService.update(new FruitDto(2, 8));
        sellersService.update(new SellersDto(2, "tomato"));
        fruitService.delete(new FruitDto(2));
        sellersService.delete(new SellersDto(2));
        suppliersService.delete(new SuppliersDto(2));
    }

    @Test
    public void TestServlet() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        PrintWriter pw = mock(PrintWriter.class);
        FruitServlet fruitServlet = new FruitServlet();
        SellersServlet sellersServlet = new SellersServlet();
        SuppliersServlet suppliersServlet = new SuppliersServlet();
        req.setAttribute("/myREST/fruit", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(res.getWriter()).thenReturn(pw);
        fruitServlet.doGet(req, res);
        req.setAttribute("/myREST/fruit/all", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruit/all");
        fruitServlet.doGet(req, res);
        req.setAttribute("/myREST/fruit/1", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruit/1");
        fruitServlet.doGet(req, res);
        req.setAttribute("/myREST/fruit/q", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruit/q");
        fruitServlet.doGet(req, res);
        req.setAttribute("/myREST/fruit/all/1", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruit/all/1");
        fruitServlet.doGet(req, res);
        req.setAttribute("/myREST/sellers", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(res.getWriter()).thenReturn(pw);
        sellersServlet.doGet(req, res);
        req.setAttribute("/myREST/sellers/all", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/sellers/all");
        sellersServlet.doGet(req, res);
        req.setAttribute("/myREST/fruit/1", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/sellers/1");
        sellersServlet.doGet(req, res);
        req.setAttribute("/myREST/fruit/q", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/sellers/q");
        sellersServlet.doGet(req, res);
        req.setAttribute("/myREST/fruit/all/1", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/sellers/all/1");
        sellersServlet.doGet(req, res);
        req.setAttribute("/myREST/suppliers", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(res.getWriter()).thenReturn(pw);
        suppliersServlet.doGet(req, res);
        req.setAttribute("/myREST/suppliers/all", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/suppliers/all");
        suppliersServlet.doGet(req, res);
        req.setAttribute("/myREST/suppliers/1", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/suppliers/1");
        suppliersServlet.doGet(req, res);
        req.setAttribute("/myREST/fruit/q", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/suppliers/q");
        suppliersServlet.doGet(req, res);
        req.setAttribute("/myREST/fruit/all/1", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/suppliers/all/1");
        suppliersServlet.doGet(req, res);
        FruitDto fruitDto = new FruitDto("carrot", 2);
        FruitService fruitService = mock(FruitService.class);
        Gson jsn = mock(Gson.class);
        long id = 1;
        req.setAttribute("/myREST/fruit", req.getRequestURI());
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        when(fruitService.save(fruitDto)).thenReturn(id);
        when(res.getWriter()).thenReturn(pw);
        //fruitServlet.doPost(req,res);

    }

    @Test
    public void TestEntities() throws SQLException {
        Fruit fruit = new Fruit();
        fruit = new Fruit("name");
        assertEquals(fruit.getName(), "name");
        fruit = new Fruit("tomato", 1);
        assertEquals(fruit.getPrice(), 1);
        assertEquals(fruit.toString(), "id: 0, name: tomato, price: 1");
        fruit = new Fruit(1, "tomato", 1);
        assertEquals(fruit.getId(), 1);
        Seller seller = new Seller();
        seller = new Seller("name", "comp");
        assertEquals(seller.getId(), 0);
        List<Fruit> flist = new ArrayList<>();
        Supplier supply = new Supplier();
        seller = new Seller(1, "name", supply);
        seller = new Seller(1, "name", supply, flist);
        assertEquals(seller.getName(), "name");
        seller = new Seller("name", supply);
        seller = new Seller("name", "sup");
        assertEquals(seller.getSupplier(), "sup");
        seller = new Seller(1, "mango");
        Supplier supplier = new Supplier();
        assertEquals(supplier.toString(), "id: 0, name: ");
        supplier = new Supplier("comp");
        assertEquals(supplier.getName(), "comp");
        supplier = new Supplier(1, "comp");
        assertEquals(supplier.getId(), 1);
        assertEquals(supplier.toString(), "id: 1, name: comp");
    }

    @Test
    public void TestDto() throws SQLException {
        FruitDto fruit = new FruitDto();
        fruit = new FruitDto("name");
        assertEquals(fruit.getName(), "name");
        fruit = new FruitDto("tomato", 1);
        assertEquals(fruit.getPrice(), 1);
        fruit = new FruitDto(1, "tomato", 1);
        assertEquals(fruit.getId(), 1);
        fruit = new FruitDto(1, 5);
        SellersDto seller = new SellersDto();
        seller = new SellersDto(1);
        seller = new SellersDto("name", "comp");
        assertEquals(seller.getId(), 0);
        List<FruitDto> flist = new ArrayList<>();
        SuppliersDto supply = new SuppliersDto();
        seller = new SellersDto(1, "name", supply);
        seller = new SellersDto(1, "name", supply, flist);
        assertEquals(seller.getName(), "name");
        seller = new SellersDto(1, "mango");
        seller = new SellersDto("name", "sup");
        assertEquals(seller.getSupplier(), "sup");
        seller = new SellersDto("name", supply);
        seller = new SellersDto("name", supply, flist);
        SuppliersDto supplier = new SuppliersDto();
        supplier = new SuppliersDto("comp");
        assertEquals(supplier.getName(), "comp");
        supplier = new SuppliersDto(1, "comp");
        assertEquals(supplier.getId(), 1);
    }

    @Test
    public void TestBase() throws SQLException {
        DatabaseManager.TruncateTable("fruit");
        DatabaseManager.TruncateTable("sellers");
        DatabaseManager.TruncateTable("suppliers");
        Connection connect = DatabaseConnector.connector();
        connect.createStatement().executeUpdate("INSERT INTO suppliers(name) VALUES('big')");
        connect.createStatement().executeUpdate("INSERT INTO suppliers(name) VALUES('small')");
        connect.createStatement().executeUpdate("INSERT INTO suppliers(name) VALUES('middle')");
        connect.createStatement().executeUpdate("INSERT INTO sellers(name, supplier) VALUES('ignat', 'big')");
        connect.createStatement().executeUpdate("INSERT INTO sellers(name, supplier) VALUES('petr', 'small')");
        connect.createStatement().executeUpdate("INSERT INTO sellers(name, supplier) VALUES('fedor', 'middle')");
        connect.createStatement().executeUpdate("INSERT INTO fruit(name, price) VALUES('mango', 10)");
        connect.createStatement().executeUpdate("INSERT INTO fruit(name, price) VALUES('apple', 5)");
        connect.createStatement().executeUpdate("INSERT INTO fruit(name, price) VALUES('avocado', 15)");
        connect.close();
    }
}
