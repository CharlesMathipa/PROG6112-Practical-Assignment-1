import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    @Test
    public void testInpatientCreationAndBedAssignment() {
        // 1. Arrange: Create a new inpatient with an unassigned bed
        Inpatient inpatient = new Inpatient(1, "Unassigned", "P001", "John", "Doe", 30, "Male", "Flu");
        
        // 2. Act & Assert: Check that the initial state is correct
        assertEquals("Unassigned", inpatient.getBedNumber(), "Initial bed should be Unassigned");
        assertEquals(PatientCategory.INPATIENT, inpatient.getCategory(), "Category should be INPATIENT");
        
        // 3. Act: Simulate allocating a bed
        inpatient.setBedNumber("B01");
        
        // 4. Assert: Verify the bed was updated successfully
        assertEquals("B01", inpatient.getBedNumber(), "Bed number should update to B01");
    }
    
    @Test
    public void testOutpatientCategoryAssignment() {
        // 1. Arrange: Create an outpatient (notice we don't pass the category)
        Outpatient outpatient = new Outpatient("P002", "Jane", "Smith", 25, "Female", "Checkup");
        
        // 2. Assert: Verify the constructor automatically applied the correct category
        assertEquals(PatientCategory.OUTPATIENT, outpatient.getCategory(), "Category should be OUTPATIENT automatically");
    }
    
    @Test
    public void testPatientAgeUpdate() {
        // 1. Arrange: Create a generic emergency patient
        Emergency emergency = new Emergency("P003", "Bob", "Jones", 45, "Male", "Trauma");
        
        // 2. Act: Update the age
        emergency.setAge(46);
        
        // 3. Assert: Verify the age was updated
        assertEquals(46, emergency.getAge(), "Age should be updated to 46");
    }
}