package com.company;


import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Java8DateTimeTests {

    private static final ZoneId CST = ZoneId.of("America/Phoenix");
    private static final ZoneId PST = ZoneId.of("America/Los_Angeles");
    private static final ZoneId EST = ZoneId.of("America/New_York");

    @Test
    public void localTimeExamples() {
        LocalTime localTime = LocalTime.of(8,24,0);
        assertThat(localTime.getHour(), is(8));
        assertThat(localTime.getMinute(), is(24));
    }

    @Test
    public void localDateTimeExamples() {
        LocalDateTime localDateTime = LocalDateTime.of(2016,02,18,10,30,0);
        assertThat(localDateTime.getYear(), is(2016));
        assertThat(localDateTime.getHour(), is(10));
    }

    @Test
    public void periodExamples() {
        Period tenDays = Period.ofDays(10);
        Period periodBetween = Period.between(LocalDate.of(2016,2,01), LocalDate.of(2016,2,11));
        assertThat(tenDays, is(periodBetween));
    }

    @Test
    public void durationExamples() {
        Duration threeMinutes = Duration.ofMinutes(3);
        Duration durationBetween = Duration.between(LocalTime.of(10,1), LocalTime.of(10,4));
        assertThat(threeMinutes, is(durationBetween));
        durationBetween = Duration.between(LocalTime.parse("10:01"), LocalTime.parse("10:04"));
        assertThat(threeMinutes, is(durationBetween));
    }

    @Test
    public void dateTimeFormatterExamples() {
        LocalDate localDate = LocalDate.of(2016,2,18);
        String iso = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        String localISO = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        assertThat(iso, is("20160218"));
        assertThat(localISO, is("2016-02-18"));
        assertThat(localDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US)), is("02/18/2016"));
    }

    @Test
    public void zoneTimeExamples() {
        // 02/18/2016 12:01:00
        LocalDateTime localDateTime = LocalDateTime.of(2016, Month.FEBRUARY, 18, 12, 1, 0);
        ZonedDateTime estDateTime = localDateTime.atZone(this.EST);
        assertThat(estDateTime.getHour(), is(12));

        // Migrate same time to CST
        ZonedDateTime cstDateTime = estDateTime.withZoneSameInstant(this.CST);
        assertThat(cstDateTime.getHour(), is(10));

        // Migrate same time to PST
        ZonedDateTime pstDateTime = estDateTime.withZoneSameInstant(this.PST);
        assertThat(pstDateTime.getHour(), is(9));
    }

    @Test
    public void instantExamples() {
        Instant instant = Instant.now();
        ZonedDateTime zonedDateTime = instant.atZone(this.EST);
        int currentHour = zonedDateTime.getHour();

        // Migrate same time to CST
        ZonedDateTime cstDateTime = zonedDateTime.withZoneSameInstant(this.CST);
        assertThat(cstDateTime.getHour(), is(currentHour - 2));

        // Migrate same time to PST
        ZonedDateTime pstDateTime = zonedDateTime.withZoneSameInstant(this.PST);
        assertThat(pstDateTime.getHour(), is(currentHour - 3));
    }
}
