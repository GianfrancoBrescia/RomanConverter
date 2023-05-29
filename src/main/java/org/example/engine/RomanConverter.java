package org.example.engine;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.example.exception.UnderflowException;

import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.IntStream;

@UtilityClass
public class RomanConverter {

    private final Map<Integer, Integer> romanValueMap = Map.ofEntries(
            Map.entry(3, 1),
            Map.entry(4, 4),
            Map.entry(8, 5),
            Map.entry(9, 9),
            Map.entry(39, 10),
            Map.entry(49, 40),
            Map.entry(89, 50),
            Map.entry(99, 90),
            Map.entry(399, 100),
            Map.entry(499, 400),
            Map.entry(899, 500),
            Map.entry(999, 900),
            Map.entry(3_999, 1_000),
            Map.entry(4_999, 4_000),
            Map.entry(8_999, 5_000),
            Map.entry(9_999, 9_000),
            Map.entry(39_999, 10_000),
            Map.entry(49_999, 40_000),
            Map.entry(89_999, 50_000),
            Map.entry(99_999, 90_000),
            Map.entry(399_999, 100_000),
            Map.entry(499_999, 400_000),
            Map.entry(899_999, 500_000),
            Map.entry(999_999, 900_000)
    );

    private final Map<Integer, String> romanNumberMap = Map.ofEntries(
            Map.entry(1, "I"),
            Map.entry(4, "IV"),
            Map.entry(5, "V"),
            Map.entry(9, "IX"),
            Map.entry(10, "X"),
            Map.entry(40, "XL"),
            Map.entry(50, "L"),
            Map.entry(90, "XC"),
            Map.entry(100, "C"),
            Map.entry(400, "CD"),
            Map.entry(500, "D"),
            Map.entry(900, "CM"),
            Map.entry(1_000, "M"),
            Map.entry(4_000, "I̅V̅"),
            Map.entry(5_000, "V̅"),
            Map.entry(9_000, "I̅X̅"),
            Map.entry(10_000, "X̅"),
            Map.entry(40_000, "X̅L̅"),
            Map.entry(50_000, "L̅"),
            Map.entry(90_000, "X̅C̅"),
            Map.entry(100_000, "C̅"),
            Map.entry(400_000, "C̅D̅"),
            Map.entry(500_000, "D̅"),
            Map.entry(900_000, "C̅M̅"),
            Map.entry(1_000_000, "M̅")
    );

    private final Map<String, Integer> decimalNumberMap = Map.ofEntries(
            Map.entry("I", 1),
            Map.entry("V", 5),
            Map.entry("X", 10),
            Map.entry("L", 50),
            Map.entry("C", 100),
            Map.entry("D", 500),
            Map.entry("M", 1_000),
            Map.entry("I̅V̅", 4_000),
            Map.entry("V̅", 5_000),
            Map.entry("X̅", 10_000),
            Map.entry("L̅", 50_000),
            Map.entry("C̅", 100_000),
            Map.entry("D̅", 500_000),
            Map.entry("M̅", 1_000_000)
    );

    public static String convertToRomanNumber(int decimalNumber) {
        StringBuilder romanNumber = new StringBuilder();
        int subtractNumber = decimalNumber;
        int highestDecimalNumber;

        do {
            highestDecimalNumber = findHighestDecimalNumberIn(subtractNumber);
            subtractNumber -= highestDecimalNumber;
            romanNumber.append(romanNumberMap.get(highestDecimalNumber));
        } while (subtractNumber != 0);

        return romanNumber.toString();
    }

    private int findHighestDecimalNumberIn(int decimalNumber) {
        if (decimalNumber <= 0) throw new UnderflowException();

        NavigableSet<Integer> set = new TreeSet<>(romanValueMap.keySet());
        Integer key = set.ceiling(decimalNumber);

        return romanValueMap.get(key);
    }

    @SuppressWarnings("java:S127")
    public static int convertToDecimalNumber(String str) {
        String romanNumberString = getRomanNumberString(str);
        List<String> splitList = List.of(romanNumberString.split("\\|"));
        int res = 0;

        for (int i = 0; i < splitList.size(); i++) {
            int s1 = decimalNumberMap.getOrDefault(splitList.get(i), -1);

            if (i + 1 < splitList.size()) {
                int s2 = decimalNumberMap.getOrDefault(splitList.get(i + 1), -1);

                if (s1 >= s2) {
                    res = res + s1;
                } else {
                    res = res + s2 - s1;
                    i++;
                }
            } else {
                res = res + s1;
                i++;
            }
        }

        return res;
    }

    private String getRomanNumberString(String str) {
        List<String> splitList = List.of(str.split(StringUtils.EMPTY));
        String overlineChar = "̅";
        return IntStream.range(0, splitList.size())
                .mapToObj(i -> {
                    if (splitList.get(i).equals(overlineChar)) return null;
                    if (splitList.size() > i + 1 && splitList.get(i + 1).equals(overlineChar)) {
                        return splitList.get(i).concat(splitList.get(i + 1));
                    }
                    return splitList.get(i);
                })
                .filter(Objects::nonNull)
                .reduce(
                        StringUtils.EMPTY,
                        (prev, curr) -> prev + (StringUtils.isNotEmpty(prev) && !Objects.equals(prev, "I̅") ? "|" : StringUtils.EMPTY) + curr
                );
    }
}