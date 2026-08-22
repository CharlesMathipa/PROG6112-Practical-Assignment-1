public class Emergency extends Patient {
    public Emergency(String patientID, String firstName, String lastName, int age, String gender, String medicalCondition) {
        
        super(patientID, firstName, lastName, age, gender, medicalCondition, PatientCategory.EMERGENCY);
    }
}
