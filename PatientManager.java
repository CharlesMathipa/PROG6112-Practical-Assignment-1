import static java.lang.System.out;
import java.util.Scanner;

public class PatientManager {
    public static void main(String[] args) {

        //variable declaration
        String patientID; String firstName; String LastName; String gender; String medicalCondition;
        int age;
        enum  Category {Inpatient, Outpatient, Emergency};


        // Registration of a patient
        Scanner scanner = new Scanner(System.in);

        out.println("===MediCare Hospital===");
        out.println("Welcome to the Patient Management System");
        out.println("1. Registration of a patient");
        out.print("2. Search for a patient by ID");
        out.println("3. Update an existing patient's details");
        out.println("4. Delete a patient");
        out.println("5. Display all registered patients");
        out.println("6. Exit Application");
        
}
}