package aoc.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        try {
            String fileLocation = "src/aoc/day4/data.txt";
            BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
            List<String> lines = reader.lines().collect(Collectors.toList());

//            Part 1
            double totalPoints = lines.stream().map(Solution::getPoints).reduce(Double::sum).get();

//            Part 2
            List<Card> cards = IntStream.range(0, lines.size()).mapToObj(i -> getCards(i, lines.get(i))).collect(Collectors.toList());
            List<List<Integer>> winningCards = cards.stream()
                    .map(card -> getCardStructure(cards, card))
                    .map(node -> {
                        ArrayList<Integer> counters = new ArrayList<>();
                        counters.add(1); // for first node
                        return countAllNodes(node, counters);
                    })
                    .collect(Collectors.toList());
            System.out.println(winningCards.stream().flatMap(Collection::stream).reduce(Integer::sum));
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Integer> countAllNodes(TreeNode treeNode, ArrayList<Integer> counters) {
        counters.add(treeNode.getChildNodes().size());
        List<TreeNode> childNodes = treeNode.getChildNodes();
        for (TreeNode childNode : childNodes) {
            countAllNodes(childNode, counters);
        }
        return counters;
    }
    private static TreeNode getCardStructure(List<Card> cards, Card card) {
        TreeNode node = new TreeNode(card.cardNumber);
        for (int i = 0; i < card.countOfWinningCards; i++) {
            int nextCardNumber = card.cardNumber + i + 1;
            Card nextCard = getCard(cards, nextCardNumber).get();
            node.addChild(getCardStructure(cards, nextCard));
        }
        return node;
    }

    private static Optional<Card> getCard(List<Card> cards, int cardNumber) {
        return cards.stream().filter(card -> card.cardNumber == cardNumber).findFirst();
    }

    private static Card getCards(int index, String line) {
        return new Card(index + 1, getMatches(line).size());
    }

    private static double getPoints(String line) {
        List<Integer> matches = getMatches(line);
        System.out.println(matches);
        return matches.isEmpty() ? 0 : Math.pow(2, matches.size() - 1);
    }

    private static List<Integer> getMatches(String line) {
        String[] allNumbers = line.split(":")[1].split("\\|");

        List<Integer> winningNumbers = Arrays.stream(allNumbers[0].trim().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> myNumbers = Arrays.stream(allNumbers[1].trim().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Integer> matches = myNumbers.stream()
                .filter(winningNumbers::contains)
                .collect(Collectors.toList());
        return matches;
    }
}
