package aoc.day6;

import aoc.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    public static final String FINAL_TIME = "finalTime";
    public static final String RECORD_DISTANCE = "recordDistance";

    public static void main(String[] args) {
        String fileLocation = "src/aoc/day6/data.txt";
        List<String> lines = FileReader.readFile(fileLocation);

        partOne(lines);
        partTwo(lines);
    }

    private static void partOne(List<String> lines) {
        List<String> timeSplit = Arrays.stream(lines.get(0).split("\\s+")).collect(Collectors.toList());
        List<String> times = timeSplit.subList(1, timeSplit.size());

        List<String> distanceSplit = Arrays.stream(lines.get(1).split("\\s+")).collect(Collectors.toList());
        List<String> distances = distanceSplit.subList(1, distanceSplit.size());

        List<Map<String, Integer>> records = IntStream.range(0, distances.size())
                .mapToObj((i) ->
                        Map.of(
                                FINAL_TIME, Integer.parseInt(times.get(i)),
                                RECORD_DISTANCE, Integer.parseInt(distances.get(i))))
                .collect(Collectors.toList());

        int speed = 1;
        List<Integer> winCount = new ArrayList<>();

        for (Map<String, Integer> r : records) {
            List<Integer> distanceCoveredForEachGame = new ArrayList<>();
            for (int timeSpent = 1; timeSpent <= r.get(FINAL_TIME); timeSpent++) {
                distanceCoveredForEachGame.add((r.get(FINAL_TIME) - timeSpent) * (timeSpent * speed));
            }
            List<Integer> winningDistances = distanceCoveredForEachGame.stream()
                    .filter(dist -> dist > r.get(RECORD_DISTANCE)).collect(Collectors.toList());
            System.out.println("=========");
            System.out.println(distanceCoveredForEachGame.size());
            winCount.add(winningDistances.size());
        }
        System.out.println(winCount);
        System.out.println(winCount.stream().reduce((a,b) -> a*b));
    }


    private static void partTwo(List<String> lines) {
        List<String> timeSplit = Arrays.stream(lines.get(0).split("\\s+")).collect(Collectors.toList());
        List<String> times = timeSplit.subList(1,timeSplit.size());
        String correctTime = String.join("", times);

        List<String> distanceSplit = Arrays.stream(lines.get(1).split("\\s+")).collect(Collectors.toList());
        List<String> distances = distanceSplit.subList(1, distanceSplit.size());
        String correctDist = String.join("", distances);


        Map<String, Long> finalRecords = Map.of(
                FINAL_TIME, Long.parseLong(correctTime),
                RECORD_DISTANCE, Long.parseLong(correctDist));

        List<Map<String,Long>> records = List.of(finalRecords);

        int speed = 1;

        for (Map<String, Long> r : records) {
            List<Long> distanceCoveredForEachGame = new ArrayList<>();
            for (int timeSpent = 1; timeSpent <= r.get(FINAL_TIME); timeSpent++) {
                distanceCoveredForEachGame.add((r.get(FINAL_TIME) - timeSpent) * (timeSpent * speed));
            }
            List<Long> winningDistances = distanceCoveredForEachGame.stream()
                    .filter(dist -> dist > r.get(RECORD_DISTANCE)).collect(Collectors.toList());
            System.out.println("=========");
            System.out.println(distanceCoveredForEachGame.size());
            System.out.println(winningDistances.size());
        }
    }

}
