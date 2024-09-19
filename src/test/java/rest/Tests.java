package rest;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Tests /*extends SlingAllMethodsServlet*/ {
    @Test
    void TestTables() throws SQLException {
        Connection connect=DatabaseConnector.connector();
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.start();
        int exist=0;
        DatabaseManager.DropTable("fruit");
        DatabaseMetaData tables=connect.getMetaData();
        ResultSet res=tables.getTables(null,null,"fruit",new String[] {"TABLE"});
        if(res.next())
            exist=1;
        assertEquals(0, exist);
        exist=0;
        DatabaseManager.DropTable("suppliers");
        tables=connect.getMetaData();
        res=tables.getTables(null,null,"suppliers",new String[] {"TABLE"});
        if(res.next())
            exist=1;
        assertEquals(0, exist);
        exist=0;
        DatabaseManager.DropTable("sellers");
        tables=connect.getMetaData();
        res=tables.getTables(null,null,"sellers",new String[] {"TABLE"});
        if(res.next())
            exist=1;
        assertEquals(0, exist);
        exist=0;
        DatabaseManager.DropTable("seller_fruit");
        tables=connect.getMetaData();
        res=tables.getTables(null,null,"seller_fruit",new String[] {"TABLE"});
        if(res.next())
            exist=1;
        assertEquals(0, exist);
        exist=0;
        DatabaseManager.CreateFruitTable();
        tables=connect.getMetaData();
        res=tables.getTables(null,null,"fruit",new String[] {"TABLE"});
        if(res.next())
            exist=1;
        assertEquals(1, exist);
        DatabaseManager.TruncateTable("fruit");
        exist=0;
        DatabaseManager.CreateSuppliersTable();
        tables=connect.getMetaData();
        res=tables.getTables(null,null,"suppliers",new String[] {"TABLE"});
        if(res.next())
            exist=1;
        assertEquals(1, exist);
        DatabaseManager.TruncateTable("suppliers");
        exist=0;
        DatabaseManager.CreateSellersTable();
        tables=connect.getMetaData();
        res=tables.getTables(null,null,"sellers",new String[] {"TABLE"});
        if(res.next())
            exist=1;
        assertEquals(1, exist);
        DatabaseManager.TruncateTable("sellers");
        exist=0;
        DatabaseManager.CreateCombinationTable();
        tables=connect.getMetaData();
        res=tables.getTables(null,null,"seller_fruit",new String[] {"TABLE"});
        if(res.next())
            exist=1;
        assertEquals(1, exist);
        DatabaseManager.TruncateTable("seller_fruit");
        connect.createStatement().executeUpdate("INSERT INTO suppliers(company) VALUES('x-vendor')");
        connect.createStatement().executeUpdate("INSERT INTO sellers(name, rating, supplier_id) VALUES('ivan', 5, 1)");
        connect.createStatement().executeUpdate("INSERT INTO fruit(name, color, price) VALUES('green apple', 'green', 10)");
        connect.createStatement().executeUpdate("INSERT INTO seller_fruit(seller_id, fruit_id) VALUES(1,1)");
        postgres.stop();
        connect.close();
    }
    HttpServletRequest req=mock(HttpServletRequest.class);
    HttpServletResponse res=mock(HttpServletResponse.class);
    ResultSet result=mock(ResultSet.class);
    PrintWriter pw=mock(PrintWriter.class);
    RequestManager app=mock(RequestManager.class);
    @Test
    public void getDataTest() throws ServletException, IOException, SQLException {
        GetRequestHandler.getData("SELECT * FROM fruit", -1, "fruit");
        GetRequestHandler.getData("SELECT * FROM sellers", -1, "sellers");
        GetRequestHandler.getData("SELECT * FROM suppliers", -1, "suppliers");
        //GetRequestHandler.getData("select * from seller_fruit", -1, "seller_fruit");
        assertEquals(req.getQueryString(),null);
        when(req.getQueryString()).thenReturn(null);
        GetRequestHandler.getData("SELECT * FROM fruit WHERE id=?", 1, "fruit");
        GetRequestHandler.getData("SELECT * FROM sellers WHERE id=?", 1, "sellers");
        GetRequestHandler.getData("SELECT * FROM suppliers WHERE id=?", 1, "suppliers");
        //GetRequestHandler.getData("select * from seller_fruit where id=?", 1, "seller_fruit");
        //when(req.getRequestURI()).thenReturn("myREST/fruit");
        when(res.getWriter()).thenReturn(pw);
        req.setAttribute("/myREST/fruit", URI.class);
        //app.doGet(req,res);
    }
       @Test
       public void postDataTest() throws ServletException, IOException, SQLException {
           DatabaseManager.TruncateTable("sellers"); DatabaseManager.TruncateTable("suppliers");
           DatabaseManager.TruncateTable("fruit"); DatabaseManager.TruncateTable("seller_fruit");
           PostRequestHandler.postData("id: 0, name: mango, color: orange, price: 10","fruit");
           PostRequestHandler.postData("id: 0, name: apple, color: green, price: 5","fruit");
           PostRequestHandler.postData("id: 0, company: big","suppliers");
           PostRequestHandler.postData("id: 0, company: small","suppliers");
           PostRequestHandler.postData("id: 0, name: petr, rating: 3, supplier_id: 1","sellers");
           PostRequestHandler.postData("id: 0, name: fedor, rating: 5, supplier_id: 2","sellers");
           assertEquals(req.getQueryString(),null);
           when(req.getQueryString()).thenReturn(null);
           when(res.getWriter()).thenReturn(pw);
           //app.doGet(req,res);
       }
    @Test
    public void putDataTest() throws ServletException, IOException, SQLException {
        PutRequestHandler.putData("id: 1, name: null, color: null, price: 20","fruit", 0);
        PutRequestHandler.putData("id: 1, name: null, rating: 8, supplier_id: 0","sellers", 2);
        PutRequestHandler.putData("id: 2, name: null, rating: 0, supplier_id: 1","sellers", 3);
        PutRequestHandler.putData("id: 1, name: null, rating: 7, supplier_id: 2","sellers", 5);
        assertEquals(req.getQueryString(),null);
        when(req.getQueryString()).thenReturn(null);
        when(res.getWriter()).thenReturn(pw);
        //app.doGet(req,res);
    }
    @Test
    public void deleteDataTest() throws ServletException, IOException {

        DeleteRequestHandler.deleteData("id: 1, name: null, color: null, price: 0","fruit");
        DeleteRequestHandler.deleteData("id: 1, name: null, rating: 0, supplier_id: 0","sellers");
        DeleteRequestHandler.deleteData("id: 1, company: null","suppliers");
        assertEquals(req.getQueryString(),null);
        when(req.getQueryString()).thenReturn(null);
        when(res.getWriter()).thenReturn(pw);
        //app.doGet(req,res);

    }
    @Test
    public void TestEntities(){
           Fruit fruit=new Fruit("tomato", "red", 1);
           fruit.setColor("yellow");
           fruit.setName("mango");
           fruit.setPrice(10);
           assertEquals(fruit.getColor(),"yellow");
           assertEquals(fruit.getName(),"mango");
           assertEquals(fruit.getPrice(),10);
        Fruit f=new Fruit(1, "tomato", "red", 1);
        f.setId(3);
        assertEquals(f.getId(), 3);
        assertEquals(f.toString(),"id: 3, name: tomato, color: red, price: 1");
        Seller seller=new Seller("izi", 2, 1);
        seller.setRating(8);
        seller.setName("vasiliy");
        seller.setSupplier_id(2);
        assertEquals(seller.getRating(),8);
        assertEquals(seller.getName(),"vasiliy");
        assertEquals(seller.getSupplier_id(),2);
        Seller sel=new Seller(1, "izi", 2, 1);
        sel.setId(2);
        assertEquals(sel.getId(),2);
        assertEquals(sel.toString(),"id: 2, name: izi, rating: 2, supplier_id: 1");
        Supplier sup=new Supplier("comp");
        sup.setCompany("company");
        assertEquals(sup.getCompany(),"company");
        Supplier s=new Supplier(1, "comp");
        s.setId(7);
        assertEquals(s.getId(),7);
        assertEquals(s.toString(),"id: 7, supplier: comp");
        Seller_fruit self=new Seller_fruit(1,1);
        self.setSeller_id(2);
        self.setFruit_id(2);
        assertEquals(self.getFruit_id(),2);
        assertEquals(self.getSeller_id(),2);
        assertEquals(self.toString(),"seller_id: 2, fruit_id: 2");
    }
}
