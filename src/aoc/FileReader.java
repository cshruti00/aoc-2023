package aoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    public static List<String> readFile(String fileName) {
        try {
            BufferedReader reader  = new BufferedReader(new java.io.FileReader(fileName));
            return reader.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
