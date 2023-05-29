package org.example;

import org.apache.commons.text.translate.UnicodeUnescaper;
import org.example.engine.RomanConverter;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the decimal number which you want to convert into roman number: ");
        int decimalNumber = scanner.nextInt();
        System.out.println(decimalNumber + " -> " + RomanConverter.convertToRomanNumber(decimalNumber));

        System.out.println();

        System.out.print("Enter the roman number which you want to convert into decimal number: ");
        String romanNumber = new UnicodeUnescaper().translate(scanner.next()).toUpperCase(Locale.ROOT);
        System.out.println(romanNumber + " -> " + RomanConverter.convertToDecimalNumber(romanNumber));
    }
}