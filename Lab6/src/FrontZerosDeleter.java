import java.util.StringTokenizer;

public class FrontZerosDeleter {
    private static class DeleterFromString {
        String numbers = new String("123456789");
        boolean isPreviousDelimiterPoint = false;

        private String deleteZeroesFromToken(String curToken) {
            if (curToken.length() == 1) {
                isPreviousDelimiterPoint = (curToken.charAt(0) == '.' ? true : false);
                return curToken;
            }

            if (isPreviousDelimiterPoint || (curToken.charAt(0) != '0')) {
                return curToken;
            }

            int i = 0;
            while(i < curToken.length() && (curToken.charAt(i) == '0')) {
                i++;
            }

            if (i == curToken.length()) {
                return "0";
            } else {
                return curToken.substring(i);
            }
        }

        private String deleteZerosFromString(String curString) {
            StringBuilder clearString = new StringBuilder();
            StringTokenizer tokenizer = new StringTokenizer(curString,
                    " .,;:[]{}()!?\\/abcdefghijklmnopqrstuvwxyzABCDEFHIJKLMOPQRSTUVWXYZ", true);

            while(tokenizer.hasMoreTokens()) {
                clearString.append(deleteZeroesFromToken(tokenizer.nextToken()));
            }

            return clearString.toString();
        }
    };

    public static void main(String[] args) {
        DeleterFromString deleter = new DeleterFromString();

        System.out.println(args[0]);

        for (int i = 0; i < args.length; ++i) {
            args[i] = deleter.deleteZerosFromString(args[i]);

            System.out.println(args[i]);
        }
    }
}
