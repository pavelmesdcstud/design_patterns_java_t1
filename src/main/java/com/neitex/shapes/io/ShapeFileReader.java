package com.neitex.shapes.io;

import com.neitex.shapes.entity.Shape;
import com.neitex.shapes.exception.MalformedInputStringException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShapeFileReader<S extends Shape> implements ShapeReader<S> {

  private final Path path;
  private final StringReader<S> reader;
  private final Logger logger = LoggerFactory.getLogger(ShapeFileReader.class);

  public ShapeFileReader(Path path, StringReader<S> reader) {
    this.path = path;
    this.reader = reader;
  }

  @Override
  public List<S> readShapes() {

    try (Stream<String> stream = Files.lines(path)) {
      return stream.map(str -> {
        try {
          return reader.read(str);
        } catch (MalformedInputStringException e) {
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
