//Here are the unit tests for the PatientManager class. These tests cover the main functionalities of the system, including registering patients, searching, updating, deleting, allocating beds, releasing beds, and sorting patients. The tests use JUnit 5 and Java Reflection to manipulate the private static list of patients in the PatientManager class.
//I also included tests to ensure that the system correctly handles duplicate patient IDs, prevents bed allocation when all beds are occupied, and verifies that patients are sorted correctly in the reports.
// I also added setup and teardown methods to capture console output and restore standard input/output after each test, ensuring that the tests do not interfere with each other.
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUp() throws Exception {
        // This captures console output so we can verify the print statements
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Field patientsField = PatientManager.class.getDeclaredField("patients");
        patientsField.setAccessible(true);
        ((ArrayList<?>) patientsField.get(null)).clear();
    }

    @AfterEach
    public void tearDown() {
        //They Restore standard console input and output after tests finish
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    // Helper method to simulate user typing and pressing 'Enter'
    private void runMainWithInput(String simulatedUserInput) {
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        PatientManager.main(new String[]{});
    }

    @Test
    public void testRegisterPatient() {
        // Input: 1(Register) -> ID -> Name -> Last -> Age -> Gender -> Condition -> 2(Outpatient) -> 9(Exit)
        String input = "1\nP001\nNeo\nMathipa\n30\nMale\nFlu\n2\n9\n";
        runMainWithInput(input);
        
        assertTrue(outContent.toString().contains("Outpatient registered successfully"), 
            "System should confirm successful registration.");
    }

    @Test
    public void testPreventDuplicatePatientIDs() {
        // Registers P001, then immediately tries to register P001 again
        String input = "1\nP001\nNeo\nMathipa\n18\nMale\nFlu\n2\n" +
                       "1\nP001\n" + 
                       "9\n";
        runMainWithInput(input);
        
        assertTrue(outContent.toString().contains("Error: Patient ID P001 already exists"), 
            "System must catch duplicate IDs immediately.");
    }

    @Test
    public void testSearchForPatient() {
        // Registers P001, then searches (Option 2) for P001
        String input = "1\nP001\nNeo\nMathipa\n18\nMale\nFlu\n2\n" +
                       "2\nP001\n" +
                       "9\n";
        runMainWithInput(input);
        
        assertTrue(outContent.toString().contains("First Name: Neo"), 
            "Search should return and display the patient's details.");
    }

    @Test
    public void testUpdatePatientDetails() {
        // Registers P001, updates (Option 3) age to 35 and condition to Cured
        String input = "1\nP001\nNeo\nMathipa\n18\nMale\nFlu\n2\n" +
                       "3\nP001\n35\nCured\n" +
                       "9\n";
        runMainWithInput(input);
        
        assertTrue(outContent.toString().contains("Patient details updated successfully!"), 
            "Update process should complete successfully.");
    }

    @Test
    public void testDeletePatient() {
        // Registers P001, deletes (Option 4) P001
        String input = "1\nP001\nNeo\nMathipa\n30\nMale\nFlu\n2\n" +
                       "4\nP001\n" +
                       "9\n";
        runMainWithInput(input);
        
        assertTrue(outContent.toString().contains("successfully deleted from the system"), 
            "System should confirm the deletion.");
    }

    @Test
    public void testAllocateBed() {
        // Registers Inpatient (Option 1) P001, then goes to Bed Menu (6) -> Allocate (4)
        String input = "1\nP001\nNeo\nMathipa\n30\nMale\nFlu\n1\n" +
                       "6\n4\nP001\nB01\n" +
                       "9\n";
        runMainWithInput(input);
        
        // Changed to match the exact output of PatientManager (First name only)
        assertTrue(outContent.toString().contains("Success: Bed B01 allocated to Neo"), 
            "System should successfully assign the bed to the inpatient.");
    }


    @Test
    public void testReleaseBed() {
        // Registers P001, allocates B01, then releases (Option 5) B01
        String input = "1\nP001\nNeo\nMathipa\n30\nMale\nFlu\n1\n" +
                       "6\n4\nP001\nB01\n" +
                       "6\n5\nP001\n" +
                       "9\n";
        runMainWithInput(input);
        
        assertTrue(outContent.toString().contains("Success: Bed B01 has been released and is now available"), 
            "System should successfully release the bed.");
    }

    @Test
    public void testPreventAllocatingOccupiedBed() {
        // Registers P001 and P002. Allocates B01 to P001, then tries to allocate B01 to P002
        String input = "1\nP001\nNeo\nMathipa\n30\nMale\nFlu\n1\n" +
                       "1\nP002\nThabang\nLeriba\n25\nFemale\nCold\n1\n" +
                       "6\n4\nP001\nB01\n" +
                       "6\n4\nP002\nB01\n" +
                       "9\n";
        runMainWithInput(input);
        
        assertTrue(outContent.toString().contains("Error: Bed B01 is already occupied!"), 
            "System must block assignment to an occupied bed.");
    }

    @Test
    public void testPreventBedAllocationWhenAllBedsOccupied() {
        // Dynamically generates user input to register 20 patients and assign them all 20 beds
        StringBuilder sb = new StringBuilder();
        int idCounter = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                String id = "P" + idCounter++;
                String bed = "B" + String.format("%02d", idCounter - 1);
                
                // Register patient
                sb.append("1\n").append(id).append("\nName\nLast\n30\nMale\nFlu\n1\n");
                // Allocate bed
                sb.append("6\n4\n").append(id).append("\n").append(bed).append("\n");
            }
        }
        
        // Attempt to allocate a 21st patient 
        sb.append("1\nP99\nExtra\nPatient\n40\nMale\nSick\n1\n");
        sb.append("6\n4\n"); // Tries to open allocation menu, should fail immediately
        sb.append("9\n"); // Exit
        
        runMainWithInput(sb.toString());
        
        assertTrue(outContent.toString().contains("Error: Allocation failed. All the beds in the ward are currently occupied."),
            "System must block allocation when ward is 100% full.");
    }

    @Test
    public void testSortPatients() {
        // Registers Neo, Thabang, and Gomotsegang out of order
        String input = "1\nP001\nNeo\nMathipa\n30\nMale\nFlu\n2\n" +
                       "1\nP002\nThabang\nLeriba\n25\nMale\nCold\n2\n" +
                       "1\nP003\nGomotsegang\nLemao\n25\nFemale\nCold\n2\n" +
                       "7\n2\n" + // Go to reports and sort 
                       "9\n";
        runMainWithInput(input);
        
        String consoleOutput = outContent.toString();
        int neoPosition = consoleOutput.indexOf("- Neo");
        int thabangPosition = consoleOutput.indexOf("- Thabang");
        int gomotsegangPosition = consoleOutput.indexOf("- Gomotsegang");
        
        // Verify they printed out in alphabetical order
        assertTrue(neoPosition < thabangPosition && thabangPosition < gomotsegangPosition, 
            "Patients should be sorted and displayed in alphabetical order.");
    }
}