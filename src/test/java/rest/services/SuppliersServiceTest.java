package rest.services;

import org.junit.jupiter.api.Test;
import rest.dto.SuppliersDto;

import java.io.IOException;

public class SuppliersServiceTest {
    SuppliersService suppliersService = new SuppliersService();

    @Test
    public void FindTest() throws IOException {
        suppliersService.find(2);
        suppliersService.find(-1);
        suppliersService.find(0);
    }

    @Test
    public void SaveTest() throws IOException {
        suppliersService.save(new SuppliersDto("old"));
    }

    @Test
    public void DeleteTest() throws IOException {
        suppliersService.delete(new SuppliersDto(2));
    }
}
