package app.util;

import app.model.City;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.data.geo.Distance;

import java.io.IOException;

public class CustomCityDeserializer extends StdSerializer<City> {

    public CustomCityDeserializer() {
        super(City.class);
    }


    @Override
    public void serialize(City city, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        System.out.println(city);
    }
}