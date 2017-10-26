
package at.yeoman.mutabor.simpleTemplates;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LineSplitterTest
{
    @Test
    public void emptyString()
    {
        List<Line> result = LineSplitter.splitText("");
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void whitespaceOnly()
    {
        List<Line> result = LineSplitter.splitText(" \t ");
        Assert.assertEquals(1, result.size());
        Line line = result.get(0);
        Assert.assertEquals(line.indentation, line.fullText);
    }

    @Test
    public void simpleName()
    {
        List<Line> result = LineSplitter.splitText("name");
        Assert.assertEquals(1, result.size());
        Line line = result.get(0);
        Assert.assertEquals("", line.indentation);
        Assert.assertEquals("name", line.fullText);
    }

    @Test
    public void indentedSimpleName()
    {
        List<Line> result = LineSplitter.splitText(" \t name");
        Assert.assertEquals(1, result.size());
        Line line = result.get(0);
        Assert.assertEquals(" \t ", line.indentation);
        Assert.assertEquals(" \t name", line.fullText);
    }

    @Test
    public void trailingEmptyLine()
    {
        List<Line> result = LineSplitter.splitText(" \t \n\n");
        Assert.assertEquals(2, result.size());
        Line first = result.get(0);
        Line second = result.get(1);
        Assert.assertEquals(first.indentation, first.fullText);
        Assert.assertEquals("", second.indentation);
        Assert.assertEquals("", second.fullText);
    }

    @Test
    public void trailingEmptyLineWithCompoundNewline()
    {
        List<Line> result = LineSplitter.splitText(" \t \r\n\r\n");
        Assert.assertEquals(2, result.size());
        Line first = result.get(0);
        Line second = result.get(1);
        Assert.assertEquals(first.indentation, first.fullText);
        Assert.assertEquals("", second.indentation);
        Assert.assertEquals("", second.fullText);
    }

    @Test
    public void twoLines()
    {
        List<Line> result = LineSplitter.splitText("first\n second");
        Assert.assertEquals(2, result.size());
        Line first = result.get(0);
        Line second = result.get(1);
        Assert.assertEquals("first", first.fullText);
        Assert.assertEquals("", first.indentation);
        Assert.assertEquals(" second", second.fullText);
        Assert.assertEquals(" ", second.indentation);
    }

    @Test
    public void twoLinesWithCompoundNewline()
    {
        List<Line> result = LineSplitter.splitText("first\r\n second");
        Assert.assertEquals(2, result.size());
        Line first = result.get(0);
        Line second = result.get(1);
        Assert.assertEquals("first", first.fullText);
        Assert.assertEquals("", first.indentation);
        Assert.assertEquals(" second", second.fullText);
        Assert.assertEquals(" ", second.indentation);
    }

    @Test
    public void revertedTwoLines()
    {
        List<Line> result = LineSplitter.splitText(" first\nsecond");
        Assert.assertEquals(2, result.size());
        Line first = result.get(0);
        Line second = result.get(1);
        Assert.assertEquals(" first", first.fullText);
        Assert.assertEquals(" ", first.indentation);
        Assert.assertEquals("second", second.fullText);
        Assert.assertEquals("", second.indentation);
    }

    @Test
    public void revertedTwoLinesWithCompoundNewline()
    {
        List<Line> result = LineSplitter.splitText(" first\r\nsecond");
        Assert.assertEquals(2, result.size());
        Line first = result.get(0);
        Line second = result.get(1);
        Assert.assertEquals(" first", first.fullText);
        Assert.assertEquals(" ", first.indentation);
        Assert.assertEquals("second", second.fullText);
        Assert.assertEquals("", second.indentation);
    }
}
