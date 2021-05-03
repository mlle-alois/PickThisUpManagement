package Requete;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Body {
    public HashMap<String, String> map;
    public ObjectMapper objectMapper = new ObjectMapper();

    private static String baseURL = "http://localhost:3000/";
    private static String limit = "limit";
    private static String offset = "offset";


    public Body() {
        map = new HashMap<>();
    }

    public void addValueToBody(String attribute, String value) {
        this.map.put(attribute, value);
    }

    public String getStringAsJSon() throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(this.map);
    }

    public String getUrlWithParametersInMap(String route) {
        String result;
        result = baseURL + route + getEndUrlFromMap();
        return result;
    }

    private String getEndUrlFromMap() {
        String result = "/";
        int i = 0;
        for (Map.Entry me : map.entrySet()) {
            if (i == 0) {
                // result += me.getValue();
                result += firstLine(me);
                i++;
                continue;
            }
            result += "&" + me.getKey() + "=" + me.getValue();
        }
        ;
        return result;
    }


    private boolean isGetAllOptions() {
        String firstKey = map.keySet().iterator().next();
        return firstKey.equals(limit) || firstKey.equals(offset);
    }

    private String firstLine(Map.Entry me) {
        if (isGetAllOptions())
            return "?" + me.getKey() + "=" + me.getValue();
        return (String) me.getValue();
    }
}


