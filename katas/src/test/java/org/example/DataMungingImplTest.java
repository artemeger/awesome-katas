package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class DataMungingImplTest {

  private final DataMungingImpl underTest = new DataMungingImpl();

  @Test
  void testGetDayWithMaxTemperatureSpread() {
    var tempSpreads = List.of(
      new DataMungingImpl.DayTemperatureEntry(1, 20.0, 10.0),
      new DataMungingImpl.DayTemperatureEntry(2, 20.0, 19.0),
      new DataMungingImpl.DayTemperatureEntry(3, 11.1, 1.0)
    );

    assertThat(underTest.getDayWithMaxTemperatureSpread(tempSpreads), equalTo(3));
  }

  @Test
  void testDetermineDayWithMinTemperatureSpread() {
    try {
      var result = underTest.determineDayWithMaxTemperatureSpread(Paths.get("src/test/resources/weather.dat"));
      assertThat(result, equalTo(9));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}