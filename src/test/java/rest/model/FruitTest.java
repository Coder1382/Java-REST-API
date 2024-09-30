package rest.model;

import org.junit.jupiter.api.Test;
import rest.dto.FruitDto;
import rest.dto.SellersDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitTest {
    @Test
    public void fruitTest() {
        Fruit fruit = new Fruit();
        assertEquals(fruit.getId(), 0);
        fruit = new Fruit(1);
        assertEquals(fruit.getId(), 1);
        fruit = new Fruit("name");
        assertEquals(fruit.getName(), "name");
        fruit = new Fruit("tomato", 1);
        assertEquals(fruit.getPrice(), 1);
        fruit = new Fruit(1, "tomato", 1);
        assertEquals(fruit.getId(), 1);
        fruit = new Fruit("name", 5);
        assertEquals(fruit.getPrice(), 5);
        fruit = new Fruit(1, 5);
        assertEquals(fruit.getPrice(), 5);
        fruit = new Fruit(1, "ignat");
        assertEquals(fruit.getSeller(), "ignat");
        List<Seller> list=new ArrayList<>();
        list.add(new Seller());
        fruit = new Fruit(1, "fruit", 10, list);
        assertEquals(fruit.getName(), "fruit");
    }
}
