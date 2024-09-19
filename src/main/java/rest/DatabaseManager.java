package rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    static final Connection connect=DatabaseConnector.connector();
    public static void CreateFruitTable() throws SQLException {
        PreparedStatement create= connect.prepareStatement("CREATE TABLE IF NOT EXISTS fruit(id BIGSERIAL PRIMARY KEY, \n"+
                "name VARCHAR(80) NOT NULL, color VARCHAR(20), price NUMERIC CHECK (price>=0)");
    }
    public static void CreateSellersTable() throws SQLException {
        PreparedStatement create= connect.prepareStatement("CREATE TABLE IF NOT EXISTS sellers(id BIGSERIAL PRIMARY KEY, name VARCHAR(80) NOT NULL,\n" +
                "rating INT CHECK (rating>0 AND rating<11), supplier_id BIGINT NOT NULL,\n" +
                "CONSTRAINT fk FOREIGN KEY(supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE");
    }
    public static void CreateSuppliersTable() throws SQLException {
        PreparedStatement create= connect.prepareStatement("CREATE TABLE suppliers(id BIGSERIAL PRIMARY KEY, company VARCHAR(80) NOT NULL");
    }
    public static void CreateCombinationTable() throws SQLException {
        PreparedStatement create= connect.prepareStatement("CREATE TABLE seller_fruit(seller_id BIGINT, fruit_id BIGINT, PRIMARY KEY(seller_id, fruit_id),\n" +
                "CONSTRAINT fk_seller FOREIGN KEY(seller_id) REFERENCES sellers(id) ON DELETE CASCADE,\n" +
                "CONSTRAINT fk_fruit FOREIGN KEY(fruit_id) REFERENCES fruit(id) ON DELETE CASCADE");
    }
    public static void DropTable(String table) throws SQLException {
        PreparedStatement drop= connect.prepareStatement("DROP TABLE IF EXISTS "+table+" CASCADE");
    }
    public static void TruncateTable(String table) throws SQLException {
        PreparedStatement drop= connect.prepareStatement("TRUNCATE TABLE "+table+" CASCADE");
    }
}
