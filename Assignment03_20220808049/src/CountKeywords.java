import java.util.*;
import java.io.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a Java source file: ");
        String filename = input.nextLine();

        File file = new File(filename);
        if (file.exists()) {
            System.out.println("The number of keywords in " + filename
                    + " is " + countKeywords(file));
        }
        else {
            System.out.println("File " + filename + " does not exist");
        }
    }

    public static int countKeywords(File file) throws Exception {
        String[] keywordString = {"abstract", "assert", "boolean",
                "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum",
                "extends", "for", "final", "finally", "float", "goto",
                "if", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private",
                "protected", "public", "return", "short", "static",
                "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile",
                "while", "true", "false", "null"};

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        int count = 0;
        boolean inComment = false;

        Scanner input = new Scanner(file);

        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            StringBuilder word = new StringBuilder();

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                boolean isLetterOrDigit = !Character.isLetterOrDigit(c);

                if (isLetterOrDigit && word.length() > 0) {
                    if (!inComment && keywordSet.contains(word)) {
                        count++;//we make sure before the punctuation
                    }
                    word.delete(0,word.length());
                }

                if (!inComment && c == '/' && i < line.length() - 1) {
                    char nextChar = line.charAt(i + 1);

                    if (nextChar == '/') {
                        break; //skip all line
                    } else if (nextChar == '*') {
                        inComment = true; //we will continue to look
                        i++;
                    }
                } else if (inComment && c == '*' && i < line.length() - 1 && line.charAt(i + 1) == '/') {
                    inComment = false; //we will finish our comment section
                    i++;
                } else if (!isLetterOrDigit) {
                    word.append(c);
                }
            }

            if (word.length() > 0) {
                if (!inComment && keywordSet.contains(word)) {
                    count++;
                }
            }
        }

        return count;
    }

}
