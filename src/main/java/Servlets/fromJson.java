package Servlets;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
public class fromJson {
    //private static List<Fruit> fruit=new ArrayList<>();
    public static List<Fruit> convert(String jsn) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        return mapper.readValue(jsn, ArrayList.class);
    }
}
