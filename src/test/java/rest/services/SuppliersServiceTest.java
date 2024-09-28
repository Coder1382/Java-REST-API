package rest.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rest.dto.SuppliersDto;

import java.io.IOException;

public class SuppliersServiceTest {
    SuppliersService suppliersService = new SuppliersService();

    @ParameterizedTest
    @ValueSource(longs = {2, -1, 0})
    public void findTest(long id) throws IOException {
        suppliersService.find(id);
    }

    @Test
    public void saveTest() throws IOException {
        suppliersService.save(new SuppliersDto("old"));
    }

    @Test
    public void deleteTest() throws IOException {
        suppliersService.delete(new SuppliersDto(2));
    }
}
