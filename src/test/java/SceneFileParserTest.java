import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import raytracer.Scene;
import raytracer.parsing.SceneFileParser;
import raytracer.parsing.SceneParseException;

public class SceneFileParserTest {

    @Test
    void parseMinimalScene() {
        String src = "size 2 2\n" +
                     "camera 0 0 0 0 0 -1 0 1 0 60\n";

        SceneFileParser p = new SceneFileParser();
        Scene s = p.parse(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)), "test");

        assertEquals(2, s.getWidth());
        assertEquals(2, s.getHeight());
        assertNotNull(s.getCamera());
    }

    @Test
    void missingSizeFails() {
        String src = "camera 0 0 0 0 0 -1 0 1 0 60\n";
        SceneFileParser p = new SceneFileParser();
        assertThrows(SceneParseException.class, () -> p.parse(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)), "test"));
    }

    @Test
    void invalidSphereRadiusFails() {
        String src = "size 1 1\n" +
                     "camera 0 0 0 0 0 -1 0 1 0 60\n" +
                     "sphere 0 0 -5 -1\n"; // negative radius

        SceneFileParser p = new SceneFileParser();
        assertThrows(SceneParseException.class, () -> p.parse(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)), "test"));
    }
}
