package lt.esdc.shapes.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.exception.MalformedInputStringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShapeFileReader<T extends Shape> implements ShapeReader<T> {

  private final Path path;
  private final StringParser<T> reader;
  private final Logger logger = LoggerFactory.getLogger(ShapeFileReader.class);

  public ShapeFileReader(Path path, StringParser<T> reader) {
    this.path = path;
    this.reader = reader;
  }

  @Override
  public List<T> readShapes() {

    try (Stream<String> stream = Files.lines(path)) {
      return stream.map(str -> {
        try {
          return reader.parse(str);
        } catch (MalformedInputStringException | IllegalArgumentException e) {
          logger.error("Shape reading failed for input string `{}`", str, e);
          return null;
        }
      }).filter(Objects::nonNull).toList();
    } catch (IOException e) {
      logger.error("Shape reading failed for path `{}`", path, e);
      throw new RuntimeException(e);
    }
  }

}
