package aoc.day8;

import aoc.FileReader;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public static void main(String[] args) {
        String fileLocation = "src/aoc/day8/data.txt";
        List<String> lines = FileReader.readFile(fileLocation);

        char[] instructions = lines.get(0).toCharArray();
        HashMap<String, List<String>> map = new HashMap<>();


        for (int i = 2; i < lines.size(); i++) {
            String[] split = lines.get(i).split("\\s");
            String leadNode = split[0];
            String leftNode = split[2].substring(1, 4);
            String rightNode = split[3].substring(0, 3);
            map.put(leadNode, List.of(leftNode, rightNode));
        }
        System.out.println(map);

        //part 1
        int count = getCount(instructions, map);
        System.out.println(count);


        // part 2
        List<String> startNodes = map.keySet().stream().filter(key -> key.endsWith("A")).collect(Collectors.toList());
        List<Long> endNodesCount = startNodes.stream().map(node ->  Long.valueOf(getCount(node, instructions, map))).collect(Collectors.toList());
        System.out.println(endNodesCount);
        System.out.println(findLcm(endNodesCount));
    }

    private static int getCount(char[] instructions, HashMap<String, List<String>> map) {
        String nodeName = "AAA";
        int count = 0;
        int i = 0;
        while (!nodeName.equals("ZZZ")) {
            nodeName = getNextNode(instructions, map, i, nodeName);
            count++;
            i = nextInstruction(instructions, i);

        }
        return count;
    }


    private static int getCount(String startNode, char[] instructions, HashMap<String, List<String>> map) {
        String nodeName = startNode;
        int count = 0;
        int i = 0;
        while (!nodeName.endsWith("Z")) {
            nodeName = getNextNode(instructions, map, i, nodeName);
            count++;
            i = nextInstruction(instructions, i);

        }
        return count;
    }

    private  static Long findLcm(List<Long> numbers) {
        return numbers.stream().reduce(1L,(n, m) -> (n*m)/findGcd(n,m));
    }

    private static long findGcd(long a, long b) {
        if(b == 0) {
            return a;
        } else {
            return findGcd(b,a%b);
        }
    }

    private static int nextInstruction(char[] instructions, int i) {
        if (i == instructions.length - 1) {
            i = 0;
        } else {
            i++;
        }
        return i;
    }

    private static String getNextNode(char[] instructions, HashMap<String, List<String>> map, int i, String nodeName) {
        if (instructions[i] == 'L') {
            nodeName = map.get(nodeName).get(LEFT);
        } else {
            nodeName = map.get(nodeName).get(RIGHT);
        }
        return nodeName;
    }
}
