package com.example.Painting.Cloning;

import com.example.Painting.Entities.Shape;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class Clone {
//    not here convert to what shape you send
    public static <T> T  makeClone ( T obj, Class<T> c ){
        ObjectMapper objectMapper=new ObjectMapper();
        try {

            return objectMapper.convertValue(objectMapper.writeValueAsString(obj),c);
        }
        catch ( Exception e){
            throw  new RuntimeException("not able to mapper");
        }

    }

}
