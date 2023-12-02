package aoc.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        try {
            String fileLocation = "src/aoc/day2/data.txt";
            BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
            List<String> lines = reader.lines().collect(Collectors.toList());

            Optional<Integer> sumOfAllGame = lines.stream()
                    .map(Solution::getGame)
                    .map(Solution::findPossibleGame)
                    .reduce(Integer::sum);

            Optional<Integer> sumOfPowerOfSets = lines.stream()
                    .map(Solution::getGame)
                    .map(Solution::findMinimumCubesForEachGame)
                    .reduce(Integer::sum);

            System.out.println(sumOfAllGame.get());
            System.out.println(sumOfPowerOfSets.get());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Integer findPossibleGame(Game game) {
        int possibleGameNumber = 0;

        Game.GameSet totalSet = new Game.GameSet();
        totalSet.setGreen(13);
        totalSet.setRed(12);
        totalSet.setBlue(14);

        List<Game.GameSet> gameSets = game.getGameSets();
        for(int i = 0; i < gameSets.size(); i++){
            int blueCount = gameSets.get(i).getBlue();
            int redCount = gameSets.get(i).getRed();
            int greenCount = gameSets.get(i).getGreen();
            if(blueCount > totalSet.getBlue() ||
                    redCount > totalSet.getRed() ||
                    greenCount > totalSet.getGreen()){
                possibleGameNumber = 0;
                break;
            }
            possibleGameNumber = game.getGameNumber();
        };
        return possibleGameNumber;
    }


    // collect the max count of three color cubes
    // go through each color and store only the max values
    // multiply the value
    // then add it
    private static Integer findMinimumCubesForEachGame(Game game) {
        int minBlueCount = 0;
        int minGreenCount = 0;
        int minRedCount = 0;

        List<Game.GameSet> gameSets = game.getGameSets();
        for(int i=0;i<gameSets.size();i++) {
            minGreenCount = Math.max(gameSets.get(i).getGreen(), minGreenCount);
            minBlueCount = Math.max(gameSets.get(i).getBlue(), minBlueCount);
            minRedCount = Math.max(gameSets.get(i).getRed(), minRedCount);
        }

        return minGreenCount * minBlueCount * minRedCount;
    }

    private static Game getGame(String line) {
        List<String> gameSplit = Arrays.stream(line.split(":")).collect(Collectors.toList());
        String gameNumber = gameSplit.get(0).split(" ")[1];

        List<Game.GameSet> gameSets = new ArrayList<>();

        Arrays.stream(gameSplit.get(1).split(";")).forEach(sets -> {
            Game.GameSet gameSet = new Game.GameSet();
            Arrays.stream(sets.split(",")).forEach(set -> {
                String[] cubeDetails = set.trim().split(" ");
                String cubeCount = cubeDetails[0];
                String cubeColor = cubeDetails[1];

                switch (cubeColor) {
                    case "red":
                        gameSet.setRed(Integer.parseInt(cubeCount));
                        break;
                    case "blue":
                        gameSet.setBlue(Integer.parseInt(cubeCount));
                        break;
                    default:
                        gameSet.setGreen(Integer.parseInt(cubeCount));
                        break;
                }
            });
            gameSets.add(gameSet);
        });

        return new Game(Integer.parseInt(gameNumber), gameSets);
    }

}
