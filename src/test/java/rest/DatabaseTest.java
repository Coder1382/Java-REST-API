package rest;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import rest.dao.DatabaseConnector;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {
    @Test
    public void TablesTest() throws SQLException {
        Connection connect = DatabaseConnector.connector();
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.start();
        DatabaseManager.DropTable("fruit");
        DatabaseManager.DropTable("suppliers");
        DatabaseManager.DropTable("sellers");
        DatabaseMetaData tables = connect.getMetaData();
        ResultSet fruit = tables.getTables(null, null, "fruit", new String[]{"TABLE"});
        ResultSet suppliers = tables.getTables(null, null, "suppliers", new String[]{"TABLE"});
        ResultSet sellers = tables.getTables(null, null, "sellers", new String[]{"TABLE"});
        int exist = (fruit.next() ? 1 : (sellers.next() ? 1 : (suppliers.next() ? 1 : 0)));
        assertEquals(0, exist);
        DatabaseManager.CreateFruitTable();
        DatabaseManager.CreateSuppliersTable();
        DatabaseManager.CreateSellersTable();
        tables = connect.getMetaData();
        fruit = tables.getTables(null, null, "fruit", new String[]{"TABLE"});
        sellers = tables.getTables(null, null, "suppliers", new String[]{"TABLE"});
        suppliers = tables.getTables(null, null, "sellers", new String[]{"TABLE"});
        exist = (!fruit.next() ? 1 : (!sellers.next() ? 1 : (!suppliers.next() ? 1 : 0)));
        assertEquals(0, exist);
        postgres.stop();
        connect.close();
    }

    @Test
    public void FillTest() throws SQLException {
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
