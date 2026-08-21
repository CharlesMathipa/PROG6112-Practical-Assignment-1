public class Inpatient extends Patient {

    private int wardNumber;
    private String bedNumber;

    public Inpatient(int wardNumber, String bedNumber, String patientID, String firstName, String lastName, int age, String gender, String medicalCondition) {
        super(patientID, firstName, lastName, age, gender, medicalCondition);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    public int getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(int wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    @Override
    public void displayPatientDetails() {
        super.displayPatientDetails();
        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: " + bedNumber);
    }
}
