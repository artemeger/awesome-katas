package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class DataMungingImpl {

  public String determineDayWithMaxTemperatureSpread(Path path) throws IOException {
    var dataFile = new WeatherFile(path);
    var entries = readFile(dataFile);
    return getDayWithMaxTemperatureSpread(entries);
  }

  String getDayWithMaxTemperatureSpread(
    final List<HasSpread> tempSpreads
  ) {
    return tempSpreads.stream()
      .max(Comparator.comparing(HasSpread::calculateSpread))
      .map(HasSpread::getId)
      .orElse("0");
  }

  public String determineTeamWithMinGoalSpread(Path path) throws IOException {
    var dataFile = new FootballFile(path);
    var entries = readFile(dataFile);
    return getTeamWithMinGoalSpread(entries);
  }

  String getTeamWithMinGoalSpread(
    final List<HasSpread> tempSpreads
  ) {
    return tempSpreads.stream()
      .min(Comparator.comparing(HasSpread::calculateSpread))
      .map(HasSpread::getId)
      .orElse("No Team");
  }

  private static List<HasSpread> readFile(
    final DataFile dataFile
  ) throws IOException {
    return Files.readAllLines(dataFile.getPath(), StandardCharsets.UTF_8)
      .stream()
      .skip(dataFile.getLinesToSkip())
      .map(dataFile::cleanInputRow)
      .takeWhile(it -> dataFile.untilCondition().apply(it))
      .map(dataFile::toHasSpread)
      .toList()
      ;
  }

  public interface DataFile {
    Path getPath();

    long getLinesToSkip();

    HasSpread toHasSpread(String[] raw);

    String[] cleanInputRow(String input);

    Function<String[], Boolean> untilCondition();
  }

  public record WeatherFile(
    Path path
  ) implements DataFile {

    @Override
    public Path getPath() {
      return path;
    }

    @Override
    public long getLinesToSkip() {
      return 2;
    }

    @Override
    public HasSpread toHasSpread(String[] raw) {
      return new DayTemperatureEntry(
        Integer.valueOf(raw[0]),
        Integer.valueOf(raw[1]),
        Integer.valueOf(raw[2])
      );
    }

    @Override
    public String[] cleanInputRow(String input) {
      return input.trim().replace("*", "").split("\\s+");
    }

    @Override
    public Function<String[], Boolean> untilCondition() {
      return it -> it[0].matches("\\d+");
    }
  }

  public record FootballFile(
    Path path
  ) implements DataFile {

    @Override
    public Path getPath() {
      return path;
    }

    @Override
    public long getLinesToSkip() {
      return 1;
    }

    @Override
    public HasSpread toHasSpread(String[] raw) {
      return new FootballTeamEntry(
        raw[1],
        Integer.valueOf(raw[6]),
        Integer.valueOf(raw[8])
      );
    }

    @Override
    public String[] cleanInputRow(String input) {
      return input.trim().split("\\s+");
    }

    @Override
    public Function<String[], Boolean> untilCondition() {
      return it -> it[0].substring(0, 1).matches("\\d");
    }
  }

  public interface HasSpread {
    Integer calculateSpread();

    String getId();
  }

  public record DayTemperatureEntry(
    Integer day,
    Integer maxTemperature,
    Integer minTemperature
  ) implements HasSpread {

    public Integer calculateSpread() {
      return maxTemperature - minTemperature;
    }

    @Override
    public String getId() {
      return day.toString();
    }
  }

  public record FootballTeamEntry(
    String name,
    Integer goalsFor,
    Integer goalsAgainst
  ) implements HasSpread {

    @Override
    public Integer calculateSpread() {
      return Math.abs(goalsFor - goalsAgainst);
    }

    @Override
    public String getId() {
      return name;
    }
  }
}
