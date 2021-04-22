import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class Body {
    public HashMap<String, String> map;
    public ObjectMapper objectMapper = new ObjectMapper();

    public Body() {
        map = new HashMap<>();
    }
    public void addValueToBody(String attribute, String value){
        this.map.put(attribute,value);
    }

    public String getStringAsJSon() throws JsonProcessingException {
       return  this.objectMapper.writeValueAsString(this.map);
    }
}


