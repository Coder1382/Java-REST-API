package rest.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitDtoTest {
    @Test
    public void fruitDtoTest() {
        FruitDto fruit = new FruitDto();
        assertEquals(fruit.getId(), 0);
        fruit = new FruitDto(1);
        assertEquals(fruit.getId(), 1);
        fruit = new FruitDto("name");
        assertEquals(fruit.getName(), "name");
        fruit = new FruitDto("tomato", 1);
        assertEquals(fruit.getPrice(), 1);
        fruit = new FruitDto(1, "tomato", 1);
        assertEquals(fruit.getId(), 1);
        fruit = new FruitDto(1, 5);
        assertEquals(fruit.getPrice(), 5);
        fruit = new FruitDto(1, "ignat");
        assertEquals(fruit.getSeller(), "ignat");
        List<SellersDto> list = new ArrayList<>();
        list.add(new SellersDto());
        fruit = new FruitDto(1, "fruit", 10, list);
        assertEquals(fruit.getName(), "fruit");
    }
}
