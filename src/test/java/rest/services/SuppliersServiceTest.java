package rest.services;

import org.junit.jupiter.api.Test;
import rest.dto.SuppliersDto;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuppliersServiceTest {
    SuppliersService suppliersService = new SuppliersService();

    @Test
    public void findTest_1() throws IOException {
        assertEquals((suppliersService.find().get(0)).getName(), "small");
        assertEquals((suppliersService.find().get(0)).getId(), 2);
    }

    @Test
    public void findTest_2() throws IOException {
        assertEquals(suppliersService.find(1).getName(), "big");
        assertEquals(suppliersService.find(1).getId(), 1);
    }

    @Test
    public void saveTest() throws IOException {
        assertEquals(Long.class, ((Long) suppliersService.save(new SuppliersDto("old"))).getClass());
    }

    @Test
    public void updateTest() throws SQLException, IOException {
        assertEquals(suppliersService.update(new SuppliersDto(1, "fedor")), 1);
    }

    @Test
    public void deleteTest() throws IOException {
        suppliersService.delete(new SuppliersDto(2));
    }
}
