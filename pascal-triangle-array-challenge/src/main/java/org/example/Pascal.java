package org.example;

import org.apache.commons.math.util.MathUtils;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Кожен елемент трикутника Паскалю є Біноміальним коефіцієнтом С(n,k),
 * де n - номер рядка, k - номер елемента у рядку.
 * Формула С(n,k) нам відома, k - це довжина відомого масиву(т.я. нумерація також з 0),
 * а n - співпадає з другим елементом у рядку,
 * тому що С(n,1) = n завжди.
 *
 * 1 та 1,1 не розглядаємо
 * Перевіряємо тільки на те чи закінчений рядок і
 * також це співпадає з умовою розрахунку Біноміального коефіцієнта.
 */
public class Pascal {

    public static long ArrayChallenge() {

        Scanner scanner = new Scanner(System.in);

        String str = scanner.nextLine();

        int[] arr = parseString(str);

        int lineNumber = arr[1];

        int nextElementNumber = arr.length;

        return canCalculateBinomialCoefficient(lineNumber,nextElementNumber)
                ? MathUtils.binomialCoefficient(lineNumber, nextElementNumber)
                : -1;
    }

    private static int[] parseString(String str) {
        String[] stringArr = str
                .substring(1, str.length() - 1)
                .split(",");

        int[] arr = new int[stringArr.length];

        for (int i = 0; i < stringArr.length; i++) {
            arr[i] = Integer.parseInt(stringArr[i]);
        }
        return arr;
    }

    private static boolean canCalculateBinomialCoefficient(int n, int k) {
        return n > k;
    }

}
