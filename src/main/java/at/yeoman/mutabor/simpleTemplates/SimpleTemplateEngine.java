
package at.yeoman.mutabor.simpleTemplates;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTemplateEngine
{
    private static final Pattern placeholder = Pattern.compile("@_(\\w+)\\;");
    private static final Pattern newline = Pattern.compile("\\r?\\n");

    private String text;
    private VariableResolver resolver;

    private StringBuilder result = new StringBuilder();

    public SimpleTemplateEngine(String text, Map<String, String> variables, Function<String,String> resolveVariable)
    {
        this.text = text;
        resolver = new VariableResolver(variables, resolveVariable);
    }

    public static String replaceVariables(String text, Map<String,String> variables)
    {
        return new SimpleTemplateEngine(text, variables, null).replaceVariables();
    }

    public static String replaceVariables(String text, Function<String,String> resolveVariable)
    {
        return new SimpleTemplateEngine(text, null, resolveVariable).replaceVariables();
    }

    public static String replaceVariables(String text, Map<String,String> variables, Function<String,String> resolveUnknownVariable)
    {
        return new SimpleTemplateEngine(text, variables, resolveUnknownVariable).replaceVariables();
    }

    private String replaceVariables()
    {
        processAllLines();

        return result.toString();
    }

    private void processAllLines()
    {
        for (Line line : LineSplitter.splitText(text))
        {
            processLine(line.fullText, line.indentation);
        }
    }

    private void processLine(String text, String indentation)
    {
        Matcher matcher = placeholder.matcher(text);

        int index = 0;

        while (matcher.find())
        {
            result.append(text.substring(index, matcher.start()));
            result.append(resolveVariable(matcher.group(1), indentation));
            index = matcher.end();
        }

        result.append(text.substring(index, text.length()));
    }

    private String resolveVariable(String name, String indentation)
    {
        String rawValue = resolver.resolveVariable(name);
        return newline.matcher(name).replaceAll(indentation);
    }
}
