package aoc.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        try {
            String fileLocation = "src/aoc/day1/data.txt";
            BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
            List<String> lines = reader.lines().collect(Collectors.toList());

            Optional<Integer> sum = lines.stream()
                    .map(Solution::getFirstAndLastDigitFor)
                    .reduce(Integer::sum);

            System.out.println(sum.get());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//     Approach 2:
//      line contains letters of number and number
//      Two pointers at the start
//      Move right pointer till a word is found in the array of number words
//      once the number is found, put it in array
//      bring left pointer = right pointer
//      repeat the above process to collect all the digits in array list
//      take the first and last digit, concatenate and parse
//      add all the numbers
    private static int getFirstAndLastDigitFor(String line) {
        HashMap<String, String> wordToNumberMap = new HashMap<>();
        wordToNumberMap.put("zero", "0");
        wordToNumberMap.put("one", "1");
        wordToNumberMap.put("two", "2");
        wordToNumberMap.put("three", "3");
        wordToNumberMap.put("four", "4");
        wordToNumberMap.put("five", "5");
        wordToNumberMap.put("six", "6");
        wordToNumberMap.put("seven", "7");
        wordToNumberMap.put("eight", "8");
        wordToNumberMap.put("nine", "9");

        Set<String> values = wordToNumberMap.keySet();
        ArrayList<String> decodedNumber = new ArrayList<>();

        int leftPointer = 0, rightPointer = 1;
        if (isCharacterADigit(line.charAt(leftPointer))) {
            decodedNumber.add(String.valueOf(line.charAt(leftPointer)));
        }
        while (rightPointer < line.length()) {
            if (isCharacterADigit(line.charAt(rightPointer))) {
                decodedNumber.add(String.valueOf(line.charAt(rightPointer)));
                rightPointer++;
                leftPointer = rightPointer;
            } else {
                String substring = line.substring(leftPointer, rightPointer + 1);
                Optional<String> any = values.stream().filter(substring::contains).findFirst();
                if (any.isPresent()) {
                    decodedNumber.add(wordToNumberMap.get(any.get()));
                    leftPointer = rightPointer;
                }
                rightPointer++;
            }
        }

        String finalDigit = decodedNumber.get(0) + decodedNumber.get(decodedNumber.size() - 1);
        return Integer.parseInt(finalDigit);
    }

//    Approach 1:
//      have left and right pointer
//      increment left till you get the first digit and collect the first digit
//      decrement right till you get the last digit and collect the last digit
//      terminate when both the digits are spotted
//      concat both the digits in map and convert to int
//      reduce the int array to add all the number

    private static int getFirstAndLastNumericDigitFor(String line) {
        int leftPointer = 0, rightPointer = line.length() - 1;
        String firstDigit = "", lastDigit = "", finalNumber = "", firstDigitInWords = "", lastDigitInWords = "";
        while (true) {
            firstDigit = isCharacterADigit(line.charAt(leftPointer))
                    ? String.valueOf(line.charAt(leftPointer))
                    : "";
            if (firstDigit.isEmpty()) {
                leftPointer++;
            }

            lastDigit = isCharacterADigit(line.charAt(rightPointer))
                    ? String.valueOf(line.charAt(rightPointer))
                    : "";
            if (lastDigit.isEmpty()) {
                rightPointer--;
            }

            if (!firstDigit.isEmpty() && !lastDigit.isEmpty()) {
                finalNumber = firstDigit.concat(lastDigit);
                break;
            }
        }
        return Integer.parseInt(finalNumber);
    }

    private static boolean isCharacterADigit(char character) {
        return Character.isDigit(character);
    }
}
