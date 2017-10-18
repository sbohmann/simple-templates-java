
package at.yeoman.mutabor.simpleTemplates;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class SimpleTemplateEngineTest
{
    @Test
    public void storedExample() throws IOException
    {
        Properties variables = new Properties();
        variables.load(new InputStreamReader(getClass().getResourceAsStream("variables.properties"), StandardCharsets.UTF_8));
    
        String template = readTextResource("template");
    
        String result = SimpleTemplateEngine.replaceVariables(template, name -> toString(variables.get(name)));
        
        String expectedResult = readTextResource("result");
    
        Assert.assertEquals(expectedResult, result);
    }
    
    private String readTextResource(String name) throws IOException
    {
        return new String(getClass().getResourceAsStream(name).readAllBytes(), StandardCharsets.UTF_8);
    }
    
    private String toString(Object object)
    {
        if (object != null)
        {
            return object.toString();
        }
        else
        {
            return null;
        }
    }
}
