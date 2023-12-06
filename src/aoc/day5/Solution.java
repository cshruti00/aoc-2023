package aoc.day5;

import aoc.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {
    private static final String SEED_TO_SOIL = "seed-to-soil";
    private static final String SOIL_TO_FERTILIZER = "soil-to-fertilizer";
    private static final String FERTILIZER_TO_WATER = "fertilizer-to-water";
    private static final String WATER_TO_LIGHT = "water-to-light";
    private static final String LIGHT_TO_TEMPERATURE = "light-to-temperature";
    private static final String TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity";
    private static final String HUMIDITY_TO_LOCATION = "humidity-to-location";

    public static void main(String[] args) {
        String fileLocation = "src/aoc/day5/data.txt";
        List<String> lines = FileReader.readFile(fileLocation);

        List<String> mapNames = List.of(
                SEED_TO_SOIL,
                SOIL_TO_FERTILIZER,
                FERTILIZER_TO_WATER,
                WATER_TO_LIGHT,
                LIGHT_TO_TEMPERATURE,
                TEMPERATURE_TO_HUMIDITY,
                HUMIDITY_TO_LOCATION);

//        part 1
        List<List<String>> details = new ArrayList<>();
        List<String> mapData = new ArrayList<>();
        int i = 0;
        while (i < lines.size()) {
            String line = lines.get(i);
            if (line.isBlank() || i == lines.size() - 1) {
                details.add(mapData);
                mapData = new ArrayList<>();
            } else {
                mapData.add(line);
            }
            i++;
        }

//        System.out.println(details);

        String[] split = details.get(0).get(0).split("\\s");
        List<String> seeds = Arrays.stream(split).collect(Collectors.toList()).subList(1, split.length);

        List<List<List<String>>> maps = IntStream.range(1, details.size()).mapToObj(k -> {
            List<String> ranges = details.get(k).subList(1, details.get(k).size());
            return ranges.stream()
                    .map(range -> Arrays.stream(range.split("\\s")).collect(Collectors.toList()))
                    .collect(Collectors.toList());
        }).collect(Collectors.toList());

        List<Long> all = new ArrayList<>();

        seeds.forEach(seed -> {
            long loc = Long.parseLong(seed);
            for (List<List<String>> ranges : maps) {
                for (List<String> range : ranges) {
                    System.out.println(range);
                    long destination = Long.parseLong(range.get(0));
                    long source = Long.parseLong(range.get(1));
                    long r = Long.parseLong(range.get(2));

                    if (loc >= source && loc <= source + r - 1) {
                        System.out.println("----" + loc);
                        loc = destination + (loc - source);
                        break;
                    }
                }
            }
            System.out.println(loc);
            all.add(loc);
        });

        System.out.println(all.stream().reduce(Math::min));
    }
}
