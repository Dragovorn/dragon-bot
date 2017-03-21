import com.vladsch.flexmark.html.HtmlRenderer;
import org.junit.Test;
import com.vladsch.flexmark.parser.Parser;

import static junit.framework.TestCase.assertEquals;

public class TestFlexmark {

    @Test
    public void testMarkdownProcessor() {
        String convert = "**M A R K U P**";

        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        assertEquals("<p><strong>M A R K U P</strong></p>", renderer.render(parser.parse(convert)).trim());
    }
}