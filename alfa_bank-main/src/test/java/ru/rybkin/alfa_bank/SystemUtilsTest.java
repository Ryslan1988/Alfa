package ru.rybkin.alfa_bank;

import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static org.junit.jupiter.api.Assertions.*;

class SystemUtilsTest {

    @Test
    void historicalDate() {
        Calendar calendar1 = new GregorianCalendar(2021, Calendar.FEBRUARY, 29);
        Calendar calendar2 = new GregorianCalendar(2002, Calendar.NOVEMBER, 12, 23, 11 ,23);
        assertEquals(SystemUtils.historicalDate(calendar1), "2021-03-01.json");
        assertEquals(SystemUtils.historicalDate(calendar2), "2002-11-12.json");

        try {
            Calendar calendar = null;
            SystemUtils.historicalDate(calendar);
            fail("Expected NullPointerException");
        } catch (NullPointerException ex) {
            assertNotEquals("", ex.getMessage());
        }
    }
}