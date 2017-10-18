
package at.yeoman.mutabor.simpleTemplates;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class VariableResolverTest
{
    @Test(expected = IllegalArgumentException.class)
    public void unknownVariable()
    {
        new VariableResolver(Collections.emptyMap(), name -> null).resolveVariable("name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownVariableWithNonemptyMap()
    {
        new VariableResolver(Map.of("other", "fromMap"), name -> null).resolveVariable("name");
    }

    @Test
    public void variableFromMap()
    {
        Assert.assertEquals(
            "fromMap",
            new VariableResolver(Map.of("name", "fromMap"), name -> null)
                .resolveVariable("name"));
    }

    @Test
    public void variableFromMapBeforeFunction()
    {
        Assert.assertEquals(
            "fromMap",
            new VariableResolver(Map.of("name", "fromMap"), name -> "fromFunction")
                .resolveVariable("name"));
    }

    @Test
    public void variableFromFunction()
    {
        Assert.assertEquals(
            "fromFunction",
            new VariableResolver(Collections.emptyMap(), name -> "fromFunction")
                .resolveVariable("name"));
    }

    @Test
    public void variableFromFunctionWithNonemptyMap()
    {
        Assert.assertEquals(
            "fromFunction",
            new VariableResolver(Map.of("other", "fromMap"), name -> "fromFunction")
                .resolveVariable("name"));
    }
}
