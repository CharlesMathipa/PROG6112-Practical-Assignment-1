public class Inpatient extends Patient {

    int wardNumber;
    String bedNumber;

    public Inpatient(int wardNumber, String bedNumber, String patientID, String firstName, String lastName, int age, String gender, String medicalCondition) {
        super(patientID, firstName, lastName, age, gender, medicalCondition);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }
}
