public class LogLine {
    private final String logLine;

    public LogLine(String logLine) {
        this.logLine = logLine;
    }

    public LogLevel getLogLevel() {
        int openBracket = logLine.indexOf('[');
        int closeBracket = logLine.indexOf(']');
        String levelCode = logLine.substring(openBracket + 1, closeBracket);

        switch (levelCode) {
            case "TRC":
                return LogLevel.TRACE;
            case "DBG":
                return LogLevel.DEBUG;
            case "INF":
                return LogLevel.INFO;
            case "WRN":
                return LogLevel.WARNING;
            case "ERR":
                return LogLevel.ERROR;
            case "FTL":
                return LogLevel.FATAL;
            default:
                return LogLevel.UNKNOWN;
        }
    }

    public String getMessage() {
        int colonIndex = logLine.indexOf(':');
        return logLine.substring(colonIndex + 1).trim();
    }

    public String getOutputForShortLog() {
        return getLogLevel().getEncodedLevel() + ":" + getMessage();
    }
}