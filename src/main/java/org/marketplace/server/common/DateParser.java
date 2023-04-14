package org.marketplace.server.common;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateParser {
    public static LocalDate parse(String date) throws DateTimeParseException {
        return LocalDate.parse(date);
    }
}
