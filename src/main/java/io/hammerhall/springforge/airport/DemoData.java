package io.hammerhall.springforge.airport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Срез демобазы Postgres Pro — готовые справочник, флот и схемы салонов для задач.
 *
 * <p>15 аэропортов, 10 моделей, 1741 место — из ресурсов {@code demodb/} этого пакета; откуда
 * они и что с ними сделано — {@code demodb/SOURCE.md}.
 *
 * <p>Каждый вызов {@link #airports()}, {@link #fleet()}, {@link #seatMaps()} — <b>новый</b>
 * объект с теми же данными. Это нарочно: задачи проверяют, что служба получает тот
 * справочник, что лежит в контейнере, а не второй такой же.
 */
public final class DemoData {

    private DemoData() {
    }

    /** Новый справочник: 15 аэропортов среза, в порядке среза — от Шереметьева до Окленда. */
    public static AirportDirectory airports() {
        return new ListDirectory(Slice.AIRPORTS);
    }

    /** Справочник из своих аэропортов — в том порядке, в каком переданы. */
    public static AirportDirectory airportsOf(List<Airport> airports) {
        return new ListDirectory(List.copyOf(airports));
    }

    /** Новый флот: 10 моделей демобазы, две из них — грузовые, не в работе. */
    public static Fleet fleet() {
        return new ListFleet(Slice.AIRPLANES);
    }

    /** Флот из своих моделей — в том порядке, в каком переданы. */
    public static Fleet fleetOf(List<Airplane> airplanes) {
        return new ListFleet(List.copyOf(airplanes));
    }

    /** Новые схемы салонов всего флота. */
    public static SeatMaps seatMaps() {
        return new MapSeatMaps(Slice.SEAT_MAPS);
    }

    private record ListDirectory(List<Airport> airports) implements AirportDirectory {
    }

    private record ListFleet(List<Airplane> airplanes) implements Fleet {
    }

    private record MapSeatMaps(Map<String, SeatMap> maps) implements SeatMaps {

        @Override
        public SeatMap of(String airplaneCode) {
            SeatMap map = maps.get(airplaneCode);
            if (map == null) {
                throw new IllegalArgumentException("во флоте нет модели " + airplaneCode);
            }
            return map;
        }
    }

    /** Данные среза — читаются один раз, при первом обращении. */
    private static final class Slice {

        static final List<Airport> AIRPORTS = read("airports.tsv", 8, f -> new Airport(
                f[0], f[1], f[2], f[3], f[4],
                Double.parseDouble(f[5]), Double.parseDouble(f[6]), ZoneId.of(f[7])));

        static final List<Airplane> AIRPLANES = read("airplanes.tsv", 5, f -> new Airplane(
                f[0], f[1], Integer.parseInt(f[2]), Integer.parseInt(f[3]), f[4].equals("t")));

        static final Map<String, SeatMap> SEAT_MAPS = seatMaps();

        private static Map<String, SeatMap> seatMaps() {
            List<String[]> rows = read("seats.tsv", 3, f -> f);
            Map<String, List<Seat>> byModel = rows.stream().collect(Collectors.groupingBy(
                    f -> f[0], LinkedHashMap::new,
                    Collectors.mapping(f -> new Seat(f[1], Fare.of(f[2])), Collectors.toList())));
            return AIRPLANES.stream().collect(Collectors.toMap(
                    Airplane::code,
                    a -> new SeatMap(a.code(), byModel.getOrDefault(a.code(), List.of())),
                    (x, y) -> x, LinkedHashMap::new));
        }

        private static <T> List<T> read(String file, int columns, Function<String[], T> row) {
            String path = "demodb/" + file;
            try (InputStream in = DemoData.class.getResourceAsStream(path)) {
                if (in == null) {
                    throw new IllegalStateException("нет ресурса " + path + " рядом с DemoData");
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                return reader.lines()
                        .filter(line -> !line.isEmpty())
                        .map(line -> line.split("\t", -1))
                        .peek(f -> {
                            if (f.length != columns) {
                                throw new IllegalStateException(path + ": " + f.length
                                        + " столбцов вместо " + columns + " в строке " + String.join(" ", f));
                            }
                        })
                        .map(row)
                        .toList();
            } catch (IOException e) {
                throw new UncheckedIOException(path, e);
            }
        }
    }
}
