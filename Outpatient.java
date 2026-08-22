public class Outpatient extends Patient {


    public Outpatient(String patientID, String firstName, String lastName, int age, String gender, String medicalCondition) {
        super(patientID, firstName, lastName, age, gender, medicalCondition, PatientCategory.OUTPATIENT);
    }
}
