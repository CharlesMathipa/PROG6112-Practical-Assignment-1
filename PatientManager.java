import static java.lang.System.out;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class PatientManager {
    private static ArrayList<Patient> patients = new ArrayList<>();
    String [][] HospitalBeds = new String[4][5]; // Assuming a maximum of 100 patients

    public static void main(String[] args) {

        //variable declaration
        String patientID; String firstName; String LastName; String gender; String medicalCondition;
        int age; int choice;

        int bedCounter = 1;
        for (int i = 0; i < HospitalBeds.length; i++) {
            for (int j = 0; j < HospitalBeds[i].length; j++) {
                HospitalBeds[i][j] = "B" + String.format("%02d", bedCounter++);
            }
        }

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
         out.println("7. Display the complete ward layout");
         out.println("Please select an option (1-7): ");
       
         while (!scanner.hasNextInt()){
            out.println("Invalid input. Please enter a number between 1 and 7.");
            scanner.next(); 
        }

        choice = scanner.nextInt();


        switch (choice) {
            case 1:
                out.println("===Patient Registration===");
                out.print("Enter Patient ID: ");
                patientID = scanner.next();

                boolean isDuplicate = false;
                for (Patient patient : patients) {
                    if (patient.getPatientID().equals(patientID)) {
                        isDuplicate = true;
                        break;
                    }
                }

                if (isDuplicate){
                    out.println("Error: Patient ID " + patientID + " already exists. Please use a unique ID.");
                    break;
                }

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
                out.println("1. Inpatient");
                out.println("2. Outpatient");
                out.println("3. Emergency");
                out.print("Enter your choice (1-3): ");
                int categoryChoice = scanner.nextInt();

                if (categoryChoice == 1) {
                    out.print("Enter Ward Number: ");
                    int wardNumber = scanner.nextInt();
                    out.print("Enter Bed Number (e.g., B01): ");
                    String bedNumber = scanner.next();
                    
                    Inpatient newInpatient = new Inpatient(wardNumber, bedNumber, patientID, firstName, LastName, age, gender, medicalCondition);
                    patients.add(newInpatient);
                    out.println("Inpatient registered successfully.");
                    
                } else if (categoryChoice == 2) {
                    Patient newPatient = new Patient(patientID, firstName, LastName, age, gender, medicalCondition, PatientCategory.OUTPATIENT);
                    patients.add(newPatient);
                    out.println("Outpatient registered successfully.");
                    
                } else if (categoryChoice == 3) {
                    Patient newPatient = new Patient(patientID, firstName, LastName, age, gender, medicalCondition, PatientCategory.EMERGENCY);
                    patients.add(newPatient);
                    out.println("Emergency patient registered successfully.");
                    
                } else {
                    out.println("Invalid category selected. Registration failed.");
                }
                break;

            case 2:

                out.println("===Search for a Patient by ID===");
                out.print("Enter Patient ID to search: ");
                patientID = scanner.next();

                boolean patientFound = false;
                for (Patient patient : patients) {
                    if (patient.getPatientID().equals(patientID)) {
                        patient.displayPatientDetails();
                        patientFound = true;
                        break;
                    }
                }
                if (!patientFound) {
                    out.println("Patient not found.");
                }
                break;

            case 3:

                out.println("===Update Patient Details===");
                out.print("Enter Patient ID to update: ");
                patientID = scanner.next();
                break;
            
            case 4:
                out.println("===Delete a Patient===");
                out.print("Enter Patient ID to delete: ");
                patientID = scanner.next();
                break;
            
            case 5:
                out.println("===Display All Registered Patients===");
                break;
            
            case 6:
                out.println("Exiting the application. Goodbye!");
                System.exit(0);
                break;

            case 7:
                out.println("===Complete Ward Layout==="); 
                for (int i = 0; i < HospitalBeds.length; i++) {
                    for (int j = 0; j < HospitalBeds[i].length; j++) {
                        out.print(HospitalBeds[i][j] + " ");
                    }
                    out.println();
                }

        }
    } while (choice != 7);
        
        scanner.close(); 

        
}
}