import static java.lang.System.out;
import java.util.Scanner;

public class PatientManager {
    public static void main(String[] args) {

        //variable declaration
        String patientID; String firstName; String LastName; String gender; String medicalCondition;
        int age; int choice;
        enum  Category {Inpatient, Outpatient, Emergency};


        // Registration of a patient
        Scanner scanner = new Scanner(System.in);

        do{
    out.println("===MediCare Hospital===");
        out.println("Welcome to the Patient Management System");
        out.println("1. Registration of a patient");
        out.println("2. Search for a patient by ID");
        out.println("3. Update an existing patient's details");
        out.println("4. Delete a patient");
        out.println("5. Display all registered patients");
        out.println("6. Exit Application");
        out.println("Please select an option (1-6): ");
       
        while (!scanner.hasNextInt()){
            out.println("Invalid input. Please enter a number between 1 and 6.");
            scanner.next(); // Clear the invalid input
        }

        choice = scanner.nextInt();


        switch (choice) {
            case 1:
                out.println("===Patient Registration===");
                out.print("Enter Patient ID: ");
                patientID = scanner.next();
                out.print("Enter First Name: ");
                firstName = scanner.next();
                out.print("Enter Last Name: ");
                LastName = scanner.next();
                out.print("Enter Age: ");
                age = scanner.nextInt();
                out.print("Enter Gender (M/F): ");
                gender = scanner.next();
                out.print("Enter Medical Condition: ");
                medicalCondition = scanner.next();
            case 2:

        }
    }
        
        
}
}