package rest;

import rest.dao.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    static final Connection connect = DatabaseConnector.connector();

    public static void CreateFruitTable() throws SQLException {
        connect.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS fruit(id BIGSERIAL PRIMARY KEY, " +
                "name VARCHAR(80) NOT NULL, color VARCHAR(20), price NUMERIC CHECK (price>=0))");
    }

    public static void CreateSellersTable() throws SQLException {
        connect.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS sellers(id BIGSERIAL PRIMARY KEY, " +
                "name VARCHAR(80) NOT NULL, rating INT CHECK (rating>0 AND rating<11), supplier_id BIGINT NOT NULL, " +
                "CONSTRAINT fk FOREIGN KEY(supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE)");
    }

    public static void CreateSuppliersTable() throws SQLException {
        connect.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS suppliers(id BIGSERIAL PRIMARY KEY, " +
                "company VARCHAR(80) NOT NULL)");
    }

    public static void CreateCombinationTable() throws SQLException {
        connect.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS seller_fruit(seller_id BIGINT, " +
                "fruit_id BIGINT, PRIMARY KEY(seller_id, fruit_id), CONSTRAINT fk_seller FOREIGN KEY(seller_id) " +
                "REFERENCES sellers(id) ON DELETE CASCADE, CONSTRAINT fk_fruit FOREIGN KEY(fruit_id) REFERENCES fruit(id) ON DELETE CASCADE)");
    }

    public static void DropTable(String table) throws SQLException {
        connect.createStatement().executeUpdate("DROP TABLE IF EXISTS " + table + " CASCADE");
    }

    public static void TruncateTable(String table) throws SQLException {
        connect.createStatement().executeUpdate("TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE");
    }
}
