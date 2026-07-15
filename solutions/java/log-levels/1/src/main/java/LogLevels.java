public class LogLevels {

    public static String message(String logLine) {
        int colonIndex = logLine.indexOf(':');
        String rawMessage = logLine.substring(colonIndex + 1);
        return rawMessage.trim();
    }

    public static String logLevel(String logLine) {
        int openBracket = logLine.indexOf('[');
        int closeBracket = logLine.indexOf(']');
        String level = logLine.substring(openBracket + 1, closeBracket);
        return level.toLowerCase();
    }

    public static String reformat(String logLine) {
        return message(logLine) + " (" + logLevel(logLine) + ")";
    }
}