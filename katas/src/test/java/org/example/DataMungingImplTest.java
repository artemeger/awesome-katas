package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class DataMungingImplTest {

  private final DataMungingImpl underTest = new DataMungingImpl();

  @Test
  void testGetDayWithMaxTemperatureSpread() {
    var tempSpreads = Stream.of(
        new DataMungingImpl.DayTemperatureEntry(1, 20, 10),
        new DataMungingImpl.DayTemperatureEntry(2, 20, 19),
        new DataMungingImpl.DayTemperatureEntry(3, 12, 1)
      )
      .map(it -> (DataMungingImpl.HasSpread) it)
      .toList();

    assertThat(underTest.getDayWithMaxTemperatureSpread(tempSpreads), equalTo("3"));
  }

  @Test
  void testDetermineDayWithMinTemperatureSpread() {
    try {
      var result = underTest.determineDayWithMaxTemperatureSpread(Paths.get("src/test/resources/weather.dat"));
      assertThat(result, equalTo("9"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testGetTeamWithMinGoalSpread() {
    var goalSpreads = Stream.of(
        new DataMungingImpl.FootballTeamEntry("A", 20, 10),
        new DataMungingImpl.FootballTeamEntry("B", 20, 19),
        new DataMungingImpl.FootballTeamEntry("C", 11, 1)
      )
      .map(it -> (DataMungingImpl.HasSpread) it)
      .toList();

    assertThat(underTest.getTeamWithMinGoalSpread(goalSpreads), equalTo("B"));
  }

  @Test
  void testDetermineTeamWithMinGoalSpread() {
    try {
      var result = underTest.determineTeamWithMinGoalSpread(Paths.get("src/test/resources/football.dat"));
      assertThat(result, equalTo("Aston_Villa"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}