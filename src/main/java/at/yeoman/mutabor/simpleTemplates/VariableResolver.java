
package at.yeoman.mutabor.simpleTemplates;

import java.util.Map;
import java.util.function.Function;

class VariableResolver
{
    private final Map<String, String> fixedVariables;
    private final Function<String, String> resolvingFunction;

    VariableResolver(Map<String, String> fixedVariables, Function<String, String> resolvingFunction)
    {
        this.fixedVariables = fixedVariables;
        this.resolvingFunction = resolvingFunction;
    }

    String resolveVariable(String name)
    {
        String value = resolveVariableFromMap(name);

        if (value == null)
        {
            value = resolveVariableFromFunction(name);
        }

        if (value == null)
        {
            throw new IllegalArgumentException("Unable to resolve variable of name [" + name + "]");
        }

        return value;
    }

    private String resolveVariableFromMap(String name)
    {
        if (fixedVariables != null)
        {
            return fixedVariables.get(name);
        }
        else
        {
            return null;
        }
    }

    private String resolveVariableFromFunction(String name)
    {
        if (resolvingFunction != null)
        {
            return resolvingFunction.apply(name);
        }
        else
        {
            return null;
        }
    }
}
