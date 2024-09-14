import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StarTriangleTest {
    /** Performs a few arbitrary tests to see if the product method is
     * correct */
    @Test
    @Order(0)
    @DisplayName("Test Ex. 2 - Star Triangle correctness")
    public void testStarTriangleCorrectness() {
        String expectedPrint  = "*\n**\n***\n****\n*****\n";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ogOutput = System.out;
        System.setOut(new PrintStream(os));
        try {
            StarTriangle.main(null);
            String output = os.toString().replace("\r", ""); // Remove carriage returns for cross-platform compatibility
            Assertions.assertEquals(expectedPrint                                                                                                                                              , output);
        } finally {
            System.setOut(ogOutput);
        }
    }
}
