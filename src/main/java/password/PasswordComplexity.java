package password;

import static java.lang.Character.*;

public enum PasswordComplexity {;

    public static int calculatePasswordComplexity(final String password) {
        int numLowerCase = 0, numUpperCase = 0, numNumbers = 0, numSymbols = 0;
        for (final char c : password.toCharArray()) {
            if (isLowerCase(c)) numLowerCase++;
            else if (isUpperCase(c)) numUpperCase++;
            else if (isDigit(c)) numNumbers++;
            else numSymbols++;
        }
        return calculatePasswordComplexity(numLowerCase, numUpperCase, numNumbers, numSymbols);
    }

    private static final double
            LOG_LOWERCASE_SPACE = Math.log(26),
            LOG_UPPERCASE_SPACE = Math.log(26),
            LOG_NUMBERS_SPACE = Math.log(10),
            LOG_SYMBOLS_SPACE = Math.log(30);

    private static int calculatePasswordComplexity(final int numLowerCase, final int numUpperCase,
                                                   final int numNumbers, final int numSymbols) {
        return (int) Math.ceil(
            (LOG_LOWERCASE_SPACE * numLowerCase)
          + (LOG_UPPERCASE_SPACE * numUpperCase)
          + (LOG_NUMBERS_SPACE * numNumbers)
          + (LOG_SYMBOLS_SPACE * numSymbols));
    }


}
