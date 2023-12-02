package aoc.day2;

import java.util.List;

public class Game {
    private final int gameNumber;
    private final List<GameSet> gameSets;

    public int getGameNumber() {
        return gameNumber;
    }

    public List<GameSet> getGameSets() {
        return gameSets;
    }

    public static class GameSet {
        private int green;
        private int red;
        private int blue;

        public GameSet() {
            this.green = 0;
            this.red = 0;
            this.blue = 0;
        }

        public GameSet setGreen(int count) {
            this.green = count;
            return this;
        };

        public GameSet setRed(int count) {
            this.red = count;
            return this;
        };

        public GameSet setBlue(int count) {
            this.blue = count;
            return this;
        };

        public int getGreen() {
            return green;
        }

        public int getRed() {
            return red;
        }

        public int getBlue() {
            return blue;
        }

        @Override
        public String toString() {
            return "GameSet{" +
                    "green=" + green +
                    ", red=" + red +
                    ", blue=" + blue +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameNumber=" + gameNumber +
                ", gameSets=" + gameSets +
                '}';
    }

    public Game(int gameNumber, List<GameSet> gameSets) {
        this.gameNumber = gameNumber;
        this.gameSets = gameSets;


    }
}
