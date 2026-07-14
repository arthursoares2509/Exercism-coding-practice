class SqueakyClean {

    static String clean(String identifier) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;

        for (char c : identifier.toCharArray()) {
            if (c == '-') {
                capitalizeNext = true;
                continue;
            }

            char processed;
            if (c == ' ') {
                processed = '_';
            } else {
                processed = leetToLetter(c);
            }

            if (capitalizeNext) {
                processed = Character.toUpperCase(processed);
                capitalizeNext = false;
            }

            if (Character.isLetter(processed) || processed == '_') {
                result.append(processed);
            }
        }

        return result.toString();
    }

    private static char leetToLetter(char c) {
        switch (c) {
            case '4': return 'a';
            case '3': return 'e';
            case '0': return 'o';
            case '1': return 'l';
            case '7': return 't';
            default: return c;
        }
    }
}