package Assignments;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Assignment3: A Java program that splits Word Based on condition.
 */
public class SplitWords {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String word;
        String newWord;
        System.out.println("Enter a word");
        word = sc.nextLine();

        newWord = splitString(word);

        System.out.println(newWord);
    }

    private static String splitString(String word) {

        StringBuffer newString = new StringBuffer();

        //Regex to match only alphabets and Special Character
        String regex = "^[a-zA-Z~`!@#$%^&*\\-_=+\\.,?\\/|\\\\:;'\"<>\\(\\)\\[\\]\\{\\}]*$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(word);

        if (m.matches()) {

            for (char c : word.toCharArray()) {
                if (Character.isAlphabetic(c)) {
                    newString.append(c);
                } else {
                    newString.append(" ");
                }
            }
            return newString.toString();
        }
        return word;
    }
}
