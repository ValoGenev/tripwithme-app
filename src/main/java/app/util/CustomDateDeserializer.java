package app.util;

import app.exception.declarations.common.InvalidDatePatternException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomDateDeserializer
        extends StdDeserializer<LocalDateTime> {


    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(
            JsonParser jsonparser, DeserializationContext context)
            throws IOException {

        String date = jsonparser.getText();

        try {
            return LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDatePatternException("Invalid date pattern. Use this format [yyyy-mm-ddThh:mm]");
        }
    }
}