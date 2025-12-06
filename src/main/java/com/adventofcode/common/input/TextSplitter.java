package com.adventofcode.common.input;

import com.google.common.base.Splitter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class TextSplitter {
    private static final String DOT = "\\.+";
    private static final String COMMA = "\\,+";
    private static final String SEMICOLON = "\\;+";
    private static final String SPACE = "\\h+";
    private static final String NEWLINE = "\\n+";
    private static final String WHITESPACE = "\\s+";

    public static Stream<String> on(String separator, String content) {
        return Splitter.on(separator).trimResults().omitEmptyStrings().splitToStream(content);
    }

    public static Stream<String> onPattern(String pattern, String content) {
        return Splitter.onPattern(pattern).trimResults().omitEmptyStrings().splitToStream(content);
    }

    public static Stream<String> onDot(String content) {
        return onPattern(DOT, content);
    }

    public static Stream<String> onComma(String content) {
        return onPattern(COMMA, content);
    }

    public static Stream<String> onSemicolon(String context) {
        return onPattern(SEMICOLON, context);
    }

    public static Stream<String> onSpace(String content) {
        return onPattern(SPACE, content);
    }

    public static Stream<String> onNewLine(String content) {
        return onPattern(NEWLINE, content);
    }

    public static Stream<String> onWhitespace(String content) {
        return onPattern(WHITESPACE, content);
    }
}
