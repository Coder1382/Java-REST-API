package rest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitTest {
    @Test
    public void FruitDtoTest() {
        Fruit fruit = new Fruit();
        fruit = new Fruit("name");
        assertEquals(fruit.getName(), "name");
        fruit = new Fruit("tomato", 1);
        assertEquals(fruit.getPrice(), 1);
        fruit = new Fruit(1, "tomato", 1);
        assertEquals(fruit.getId(), 1);
        fruit = new Fruit("name", 5);
    }
}
