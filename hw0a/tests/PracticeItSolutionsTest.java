import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PracticeItSolutionsTest {

    /** Performs a few arbitrary tests to see if the product method is
     * correct */
    @Test
    @Order(0)
    @DisplayName("Test Ex. 1 - Confusing correctness")
    public void testConfusingCorrectness() {
        String expectedPrint  = "I am method 1.\nI am method 1.\nI am method 2.\nI am method 3.\nI am method 1.\nI am method 1.\nI am method 2.\nI am method 1.\nI am method 2.\nI am method 3.\nI am method 1.\n";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ogOutput = System.out;
        System.setOut(new PrintStream(os));
        try {
            PracticeItSolutions.confusing();
            String output = os.toString().replace("\r", ""); // Remove carriage returns for cross-platform compatibility
            Assertions.assertEquals(expectedPrint                                                                                                                                                     , output);
        } finally {
            System.setOut(ogOutput);
        }
    }
}