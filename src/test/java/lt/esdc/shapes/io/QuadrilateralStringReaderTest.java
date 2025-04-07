package lt.esdc.shapes.io;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lt.esdc.shapes.exception.MalformedInputStringException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuadrilateralStringReaderTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "0,0;1,0;1,1;0,1",                    // Square
        "0,0;2,0;2,1;0,1",                    // Rectangle
        "-1,-1;1,-1;1,1;-1,1",                // Square with negative coordinates
        "0.5,0.5;1.5,0.5;1.5,1.5;0.5,1.5",   // Square with decimal coordinates
        "0,0;3,0;2,2;1,2"                     // Irregular quadrilateral
    })
    void testValidQuadrilaterals(String input) {
        QuadrilateralStringReader reader = new QuadrilateralStringReader();
        assertDoesNotThrow(() -> reader.read(input),
            "Expected no exception for valid quadrilateral: " + input);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",                                    // Empty string
        "0,0;1,0;1,1",                        // Only 3 points
        "0,0;1,0;1,1;0,1;1,1",               // 5 points
        "0,0;1,0;1,1:0,1",                    // Wrong separator
        "a,b;c,d;e,f;g,h",                    // Non-numeric coordinates
        "0,0;0,0;0,0;0,0",                    // All points the same
        "0,0;1,1;0,0;1,1",                    // Degenerate quadrilateral
        " 0,0;1,0;1,1;0,1 ",                  // Extra whitespace
        "0.0.0,1;1,0;1,1;0,1",               // Malformed number
        "0,0,1,0,1,1,0,1"                     // Wrong format
    })
    void testInvalidQuadrilaterals(String input) {
        QuadrilateralStringReader reader = new QuadrilateralStringReader();
        assertThrows(MalformedInputStringException.class, () -> reader.read(input),
            "Expected MalformedInputStringException for invalid quadrilateral: " + input);
    }

    @Test
    void testNullInput() {
        QuadrilateralStringReader reader = new QuadrilateralStringReader();
        assertThrows(MalformedInputStringException.class, () -> reader.read(null),
            "Expected MalformedInputStringException for null input");
    }
}
