package aoc.day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static aoc.FileReader.readFile;

public class Solution {
    public static final String JOKER = "J";


    private static Map<CardType, CardType> cardUpgradeRule = Map.ofEntries(
            Map.entry(CardType.FIVE,CardType.FIVE),
            Map.entry(CardType.FOUR,CardType.FIVE),
            Map.entry(CardType.FULL_HOUSE,CardType.FIVE),
            Map.entry(CardType.THREE,CardType.FOUR),
            Map.entry(CardType.TWO_PAIR,CardType.FOUR),
            Map.entry(CardType.ONE_PAIR,CardType.THREE),
            Map.entry(CardType.HIGH_CARD,CardType.ONE_PAIR)
    );
    private static Map<String, Long> cardValues = Map.ofEntries(
            Map.entry("A", 13L),
            Map.entry("K", 12L),
            Map.entry("Q", 11L),
            Map.entry("T", 10L),
            Map.entry("9", 9L),
            Map.entry("8", 8L),
            Map.entry("7", 7L),
            Map.entry("6", 6L),
            Map.entry("5", 5L),
            Map.entry("4", 4L),
            Map.entry("3", 3L),
            Map.entry("2", 2L),
            Map.entry("J", 0L)
    );

    public static void main(String[] args) {
        String fileLocation = "src/aoc/day7/data.txt";

        List<String> lines = readFile(fileLocation);

        Map<CardType, List<Hand>> filteredHands = lines.stream().map(line -> {
            String[] split = line.split("\\s");
            String hand = split[0];
            CardType cardType = getHandType(hand);
            return new Hand(
                    hand,
                    Long.parseLong(split[1]),
                    cardType);
        }).collect(Collectors.groupingBy(u -> u.handType));

        //sorting
        filteredHands.forEach((cardType, hands) -> {
            for (int i = 1; i < hands.size(); i++) {
                Hand first = hands.get(i);
                int pointer = i - 1;

                while (pointer >= 0 && hands.get(pointer).isGreaterThan(first)) {
                    hands.set(pointer + 1, hands.get(pointer));
                    pointer--;
                }
                hands.set(pointer + 1, first);
            }
        });


        ArrayList<Hand> reversed = new ArrayList<>();
        if (filteredHands.get(CardType.HIGH_CARD) != null) {
            reversed.addAll(filteredHands.get(CardType.HIGH_CARD));
        }
        if (filteredHands.get(CardType.ONE_PAIR) != null) {
            reversed.addAll(filteredHands.get(CardType.ONE_PAIR));
        }
        if (filteredHands.get(CardType.TWO_PAIR) != null) {
            reversed.addAll(filteredHands.get(CardType.TWO_PAIR));
        }
        if (filteredHands.get(CardType.THREE) != null) {
            reversed.addAll(filteredHands.get(CardType.THREE));
        }
        if (filteredHands.get(CardType.FULL_HOUSE) != null) {
            reversed.addAll(filteredHands.get(CardType.FULL_HOUSE));
        }
        if (filteredHands.get(CardType.FOUR) != null) {
            reversed.addAll(filteredHands.get(CardType.FOUR));
        }
        if (filteredHands.get(CardType.FIVE) != null) {
            reversed.addAll(filteredHands.get(CardType.FIVE));
        }
        System.out.println(filteredHands);

        System.out.println("--------");
        System.out.println(reversed);
        ArrayList<Long> numbers = new ArrayList<>();
        for (int i = 0; i < reversed.size(); i++) {
            numbers.add(reversed.get(i).bidValue * (i + 1L));
        }
        System.out.println(numbers.stream().reduce(Long::sum));
    }


    private static CardType getHandType(String hand) {
        Map<String, Integer> map = new HashMap<>();
        String[] split = hand.split("");
        for (String s : split) {
            map.merge(s, 1, Integer::sum);
        }

        if (map.containsValue(5)) {
            return CardType.FIVE;
        } else if (map.containsValue(4)) {
            if(map.containsKey(JOKER)) {
                return CardType.FIVE;
            }
            return CardType.FOUR;
        } else if (map.values().containsAll(List.of(3, 2))) {
            if(map.containsKey(JOKER)) {
                return CardType.FIVE;
            }
            return CardType.FULL_HOUSE;
        } else if (map.values().containsAll(List.of(3, 1, 1)) && map.values().size() == 3) {
            if(map.containsKey(JOKER)) {
                return CardType.FOUR;
            }
            return CardType.THREE;
        } else if (map.values().containsAll(List.of(2, 2, 1)) && map.values().size() == 3) {
            if(map.containsKey(JOKER) && map.get(JOKER) == 1) {
                return CardType.FULL_HOUSE; // full house
            } else if (map.containsKey(JOKER) && map.get(JOKER) == 2) {
                return CardType.FOUR;
            }
            return CardType.TWO_PAIR;
        } else if (map.values().size() == 4) {
            if(map.containsKey(JOKER)) {
                return CardType.THREE;
            }
            return CardType.ONE_PAIR;
        } else if (map.containsKey(JOKER)) {
            return CardType.ONE_PAIR;
        }
        return CardType.HIGH_CARD;
    }

    private static class Hand {
        private final String hand;
        private final Long bidValue;
        private final CardType handType;

        public Hand(String hand, Long value, CardType handType) {
            this.hand = hand;
            this.bidValue = value;
            this.handType = handType;
        }

        @Override
        public String toString() {
            return "Hand{" +
                    "hand='" + hand + '\'' +
                    ", bidValue=" + bidValue +
                    ", handType=" + handType +
                    '}';
        }

        public boolean isGreaterThan(Hand other) {
            boolean isGreater = false;
            String[] cards = this.hand.split("");
            String[] cards2 = other.hand.split("");
            int i = 0;
            while (i < 5) {
                if (cardValues.get(cards[i]) > (cardValues.get(cards2[i]))) {
                    isGreater = true;
                    break;
                } else if (cardValues.get(cards[i]).equals(cardValues.get(cards2[i]))) {
                    i++;
                } else {
                    break;
                }

            }
            return isGreater;
        }
    }

    private enum CardType {
        FIVE, FOUR, FULL_HOUSE, THREE, TWO_PAIR, ONE_PAIR, HIGH_CARD
    }
}
