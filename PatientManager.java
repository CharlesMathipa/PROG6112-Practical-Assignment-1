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
                do{
                    out.println("===Patient Registration===");
                    out.print("Enter Patient ID: ");
                    patientID = scanner.next();
                    out.print("Enter First Name: ");
                    firstName = scanner.next();
                    out.print("Enter Last Name: ");
                LastName = scanner.next();
                out.print("Enter Age: ");
                age = scanner.nextInt();
                out.print("Enter Gender (Male/Female): ");
                gender = scanner.next();
                out.print("Enter Medical Condition: ");
                medicalCondition = scanner.next();
                out.println("Select Category: ");
              while (true) {
                    out.println("1. Inpatient");
                    out.println("2. Outpatient");
                    out.println("3. Emergency");
                    out.print("Enter your choice (1-3): ");
                    int categoryChoice = scanner.nextInt();
                    if (categoryChoice >= 1 && categoryChoice <= 3) {
                        Category category = Category.values()[categoryChoice - 1];
                        out.println("Patient registered successfully!");
                        break;
                    } else {
                        out.println("Invalid choice. Please select a valid category.");
                    }
                }
                } while (false);
                break;
            case 2:
                out.println("===Search for a Patient by ID===");
                out.print("Enter Patient ID to search: ");
                patientID = scanner.next();
                // Implement search logic here
                break;

        }
    } while (choice != 6);
        
        scanner.close(); 

        while ()
}
}