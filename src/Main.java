import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        String fileName = "D:\\java\\work\\cities.txt";
        List<String> cities = readCitiesFromFile(fileName);

        if (!cities.isEmpty()) {
            List<String> longestChain = findLongestWordChain(cities);
            printWordChain(longestChain);
        } else {
            throw new IllegalArgumentException("The list of cities is empty.");
        }
    }

    private static List<String> readCitiesFromFile(String fileName) {
        List<String> cities = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                cities.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }

    private static List<String> findLongestWordChain(List<String> cities) {
        return cities.stream()
                .map(city -> {
                    List<String> chain = new ArrayList<>();
                    chain.add(city);
                    findNextWord(city, cities, chain);
                    return chain;
                })
                .max(Comparator.comparingInt(List::size))
                .orElse(Collections.emptyList());
    }

    private static void findNextWord(String currentWord, List<String> cities, List<String> chain) {
        char lastChar = Character.toLowerCase(currentWord.charAt(currentWord.length() - 1));

        Optional<String> nextWord = cities.stream()
                .filter(city -> !chain.contains(city))
                .filter(city -> Character.toLowerCase(city.charAt(0)) == lastChar)
                .findFirst();

        nextWord.ifPresent(word -> {
            chain.add(word);
            findNextWord(word, cities, chain);
        });
    }

    private static void printWordChain(List<String> chain) {
        System.out.println(String.join(" ", chain));
    }
}