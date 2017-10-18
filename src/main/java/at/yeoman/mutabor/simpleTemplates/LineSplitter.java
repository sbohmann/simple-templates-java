
package at.yeoman.mutabor.simpleTemplates;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LineSplitter
{
    private static final Pattern newline = Pattern.compile("\\n");
    private static final Pattern initialWhitespace = Pattern.compile("^[ \\t]*");

    static List<Line> splitText(String text)
    {
        String[] rawLines = split(text);

        List<Line> result = new ArrayList<>(rawLines.length);

        for (String rawLine : rawLines)
        {
            result.add(createLine(rawLine));
        }

        return result;
    }

    private static String[] split(String text)
    {
        String normalizedText = normalize(text);

        return newline.split(normalizedText, -1);
    }

    private static String normalize(String text)
    {
        return text
            .replace("\r\n", "\n")
            .replace('\r', '\n');
    }

    private static Line createLine(String rawLine)
    {
        String indentation = getIndentation(rawLine);

        return new Line(rawLine, indentation);
    }

    private static String getIndentation(String rawLine)
    {
        Matcher matcher = initialWhitespace.matcher(rawLine);

        if (!matcher.find())
        {
            throw new RuntimeException("Logical error");
        }

        return matcher.group();
    }
}
