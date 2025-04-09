package lt.esdc.shapes.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lt.esdc.shapes.entity.Quadrilateral;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ShapeFileReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testValidShapesFile() throws IOException {
        String content = """
            0,0;1,0;1,1;0,1
            0,0;2,0;2,1;0,1
            -1,-1;1,-1;1,1;-1,1""";
        Path file = createTempFile(content);

        ShapeFileReader<Quadrilateral> reader = new ShapeFileReader<>(file,
            new QuadrilateralStringParser());
        List<Quadrilateral> shapes = reader.readShapes();

        assertEquals(3, shapes.size());
    }

    @Test
    void testMixedValidAndInvalidShapes() throws IOException {
        String content = """
            0,0;1,0;1,1;0,1
            invalid_shape
            0,0;2,0;2,1;0,1""";
        Path file = createTempFile(content);

        ShapeFileReader<Quadrilateral> reader = new ShapeFileReader<>(file,
            new QuadrilateralStringParser());
        List<Quadrilateral> shapes = reader.readShapes();

        assertEquals(2, shapes.size());
    }

    @Test
    void testEmptyFile() throws IOException {
        Path file = createTempFile("");

        ShapeFileReader<Quadrilateral> reader = new ShapeFileReader<>(file,
            new QuadrilateralStringParser());
        List<Quadrilateral> shapes = reader.readShapes();

        assertTrue(shapes.isEmpty());
    }

    @Test
    void testNonExistentFile() {
        Path nonExistentFile = tempDir.resolve("non_existent.txt");
        ShapeFileReader<Quadrilateral> reader = new ShapeFileReader<>(nonExistentFile,
            new QuadrilateralStringParser());

        assertThrows(RuntimeException.class, reader::readShapes);
    }

    private Path createTempFile(String content) throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, content);
        return file;
    }
}
