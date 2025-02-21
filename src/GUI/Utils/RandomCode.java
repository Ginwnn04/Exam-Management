package GUI.Utils;

import java.util.Random;

public class RandomCode {
    private static final String ALPHA_NUMERIC_STRING = "0123456789";

    public static String generate(int length) {
        String code = "";
        for (int i = 0; i < length; i++) {
            Random random = new Random();
            
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            code += ALPHA_NUMERIC_STRING.charAt(index);
        }

        return code;
    }
}
