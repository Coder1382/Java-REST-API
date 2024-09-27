package rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitDtoTest {
    @Test
    public void FruitDtoTest() {
        FruitDto fruit = new FruitDto();
        fruit = new FruitDto("name");
        assertEquals(fruit.getName(), "name");
        fruit = new FruitDto("tomato", 1);
        assertEquals(fruit.getPrice(), 1);
        fruit = new FruitDto(1, "tomato", 1);
        assertEquals(fruit.getId(), 1);
        fruit = new FruitDto(1, 5);
    }
}
