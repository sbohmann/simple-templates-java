
package at.yeoman.mutabor.simpleTemplates;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTemplateEngine
{
    private static final Pattern placeholder = Pattern.compile("@_([a-zA-Z][a-zA-Z0-9]*)\\;");
    private static final Pattern newline = Pattern.compile("\\r?\\n");
    
    private String template;
    private VariableResolver resolver;
    
    private StringBuilder result = new StringBuilder();
    
    private SimpleTemplateEngine(String template, Map<String, String> variables, Function<String, String> resolveVariable)
    {
        this.template = template;
        resolver = new VariableResolver(variables, resolveVariable);
    }
    
    /**
     * Resolves variables from the variables Map
     */
    public static String replaceVariables(String template, Map<String, String> variables)
    {
        return new SimpleTemplateEngine(template, variables, null).replaceVariables();
    }
    
    /**
     * Resolves variables from the resolveVariables function
     */
    public static String replaceVariables(String template, Function<String, String> resolveVariable)
    {
        return new SimpleTemplateEngine(template, null, resolveVariable).replaceVariables();
    }
    
    /**
     * Resolves variables first from the variables Map, then from the resolveVariables function
     */
    public static String replaceVariables(String template, Map<String, String> variables, Function<String, String> resolveVariable)
    {
        return new SimpleTemplateEngine(template, variables, resolveVariable).replaceVariables();
    }
    
    private String replaceVariables()
    {
        processAllLines();
        
        return result.toString();
    }
    
    private void processAllLines()
    {
        boolean first = true;
        for (Line line : LineSplitter.splitText(template))
        {
            if (!first)
            {
                result.append('\n');
            }
            
            processLine(line.fullText, "\n" + line.indentation);
            
            first = false;
        }
    }
    
    private void processLine(String text, String lineBreak)
    {
        Matcher matcher = placeholder.matcher(text);
        
        int index = 0;
        
        while (matcher.find())
        {
            appendNormalizedPlainText(text.substring(index, matcher.start()));
            result.append(resolveVariable(matcher.group(1), lineBreak));
            index = matcher.end();
        }
    
        appendNormalizedPlainText(text.substring(index, text.length()));
    }
    
    private String resolveVariable(String name, String lineBreak)
    {
        String rawValue = resolver.resolveVariable(name);
        return newline.matcher(rawValue).replaceAll(lineBreak);
    }
    
    private void appendNormalizedPlainText(String plainText)
    {
        result.append(plainText.replace("@__", "@_"));
    }
}
