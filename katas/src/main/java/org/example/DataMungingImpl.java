package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataMungingImpl {

  public Integer determineDayWithMaxTemperatureSpread(Path path) throws IOException {
    var entries = createDayTemperatureEntriesFromInputFile(path);
    return getDayWithMaxTemperatureSpread(entries);
  }

  Integer getDayWithMaxTemperatureSpread(
    final List<DayTemperatureEntry> tempSpreads
  ) {
    return tempSpreads.stream()
      .max(Comparator.comparing(DayTemperatureEntry::calculateSpread))
      .map(it -> it.day)
      .orElse(0);
  }

  private List<DayTemperatureEntry> createDayTemperatureEntriesFromInputFile(
    final Path path
  ) throws IOException {
    var input = Files.readAllLines(path, StandardCharsets.UTF_8)
      .stream()
      .skip(2);
    return input
      .map(DataMungingImpl::cleanInputRow)
      .takeWhile(DataMungingImpl::firstElementIsInteger)
      .map(it -> new DayTemperatureEntry(
        Integer.valueOf(it[0]),
        Double.valueOf(it[1]),
        Double.valueOf(it[2]))
      )
      .collect(Collectors.toList());
  }

  private static String[] cleanInputRow(String it) {
    return it.trim().replace("*", "").split("\\s+");
  }

  private static boolean firstElementIsInteger(String[] it) {
    return it[0].matches("\\d+");
  }

  public record DayTemperatureEntry(
    Integer day,
    Double maxTemperature,
    Double minTemperature
  ) {

    public Double calculateSpread() {
      return maxTemperature - minTemperature;
    }
  }
}
