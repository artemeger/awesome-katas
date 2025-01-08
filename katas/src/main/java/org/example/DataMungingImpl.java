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

  public String determineTeamWithMinGoalSpread(Path path) throws IOException {
    var entries = createFootballGoalEntriesFromInputFile(path);
    return getTeamWithMinGoalSpread(entries);
  }

  String getTeamWithMinGoalSpread(
    final List<FootballTeamEntry> tempSpreads
  ) {
    return tempSpreads.stream()
      .min(Comparator.comparing(FootballTeamEntry::calculateSpread))
      .map(it -> it.name)
      .orElse("No Team");
  }

  private List<FootballTeamEntry> createFootballGoalEntriesFromInputFile(
    final Path path
  ) throws IOException {
    var input = Files.readAllLines(path, StandardCharsets.UTF_8)
      .stream()
      .skip(1);
    return input
      .map(DataMungingImpl::cleanFootballInputRow)
      .takeWhile(DataMungingImpl::firstCharOfFirstElementIsInteger)
      .map(it -> new FootballTeamEntry(
        it[1],
        Integer.valueOf(it[6]),
        Integer.valueOf(it[8]))
      )
      .collect(Collectors.toList());
  }

  private static String[] cleanInputRow(String it) {
    return it.trim().replace("*", "").split("\\s+");
  }

  private static String[] cleanFootballInputRow(String it) {
    return it.trim().split("\\s+");
  }

  private static boolean firstElementIsInteger(String[] it) {
    return it[0].matches("\\d+");
  }
  private static boolean firstCharOfFirstElementIsInteger(String[] it) {
    return it[0].substring(0, 1).matches("\\d");
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

  public record FootballTeamEntry(
    String name,
    Integer goalsFor,
    Integer goalsAgainst
  ) {

    public Integer calculateSpread() {
      return Math.abs(goalsFor - goalsAgainst);
    }
  }
}
