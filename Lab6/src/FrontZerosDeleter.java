import java.util.StringTokenizer;

public class FrontZerosDeleter {
    private static String deleteZerosFromToken(String curToken) throws NumberFormatException {
        if (curToken.length() == 1) {
            return curToken;
        }

        Double doubleStr = new Double(curToken);

        if ((curToken.charAt(0) != '0')) {
            return curToken;
        }

        int i = 0;
        while(i < curToken.length() && (curToken.charAt(i) == '0')) {
            i++;
        }

        if (i == curToken.length()) {
            return "0";
        } else {
            if (curToken.charAt(i) == '.') {
                return curToken.substring(i - 1);
            }
            return curToken.substring(i);
        }
    }

    private static String deleteZerosFromString(String curString) {
        StringBuilder clearString = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(curString,
                " ,;:[]{}()!?\\/", true);

        while(tokenizer.hasMoreTokens()) {
            clearString.append(deleteZerosFromToken(tokenizer.nextToken()));
        }

        return clearString.toString();
    }

    public static void main(String[] args) {
        try {
            for (int i = 0; i < args.length; ++i) {
                args[i] = deleteZerosFromString(args[i]);

                System.out.println(args[i]);
            }
        }

        catch(NumberFormatException error) {
            System.err.println(error.getMessage());
        }
    }
}