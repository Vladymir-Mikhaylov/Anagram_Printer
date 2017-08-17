import com.sun.org.apache.regexp.internal.RE;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Anagram.
 *
 * Realization task of compare to words, if they are anagrams or not.
 *
 * @author Mikhailov Vladimir
 */
public class Anagram {
    public static void main(String[] args) throws IOException {
        /*
            initialization of reader and StringBuilder
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        /*
            Set 2 words for comparing
         */
//        String a = "abcdefabcdef";
//        String b = "acdfgaacdfg";
        System.out.println("write 1st word: ");
        String a = reader.readLine();
        System.out.println("write 2nd word: ");
        String b = reader.readLine();
        /*
            if length of 2 words is different, we can be sure words aren't anagrams. Exit from the program with code '0'
         */
        if (a.length() != b.length()){
            System.out.println("Strings aren't anagrams. Different length");
            System.exit(0);
        }
        /*
            <tt>length</tt> length of the Strings.
         */
        int length = a.length();
        /*
            String result in the begining is the copy of the String "a" (first string) in the beginning.

            When some of characters will be equal, we'll delete them from the result and string "b".
            The last of them that will leave in the result is difference between 2 compare strings.
            String "result" shows to us which symbols we need to change to make our strings anagrams.
         */
        StringBuilder strB = new StringBuilder(b);
        StringBuilder result = new StringBuilder(a);

        for (int i = 0; i < length; i++){
            char k = a.charAt(i);
            int position = strB.toString().indexOf(k);

            if (position != -1){
                strB.deleteCharAt(position);
                result.deleteCharAt(position);
            }
        }
        /*
            In the end we check value "lengthOfTheResult" which represents length of the "result".
            if value more than 0, so, it shows us that compare strings had difference.
            if value == 0 - it will show that all symbols were equal.
         */
        int lengthOfTheResult = result.length();

        System.out.println("*******Result*********");
        if (lengthOfTheResult != 0){
            System.out.println("String aren't anagrams");
            for (int i = 0; i < lengthOfTheResult-1; i++){
                result.insert(1+i*2, ',');
            }
            result.insert(0, '[');
            result.insert(result.length(), ']');
            System.out.println(result);
        }else {
            System.out.println("Strings are anagrams");
        }
    }
}
