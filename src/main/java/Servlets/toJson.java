package Servlets;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
public class toJson {
    public static String convert(List<Fruit> fruit) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        return mapper.writeValueAsString(fruit);
    }
    public static String convert(Fruit fruit) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        return mapper.writeValueAsString(fruit);
    }
}
