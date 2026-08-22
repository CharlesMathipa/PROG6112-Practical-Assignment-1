import static java.lang.System.out;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class PatientManager {
    private static ArrayList<Patient> patients = new ArrayList<>();
    String [][] HospitalBeds = new String[4][5]; // Assuming a maximum of 100 patients

    public static void main(String[] args) {

        //variable declaration
        String patientID; String firstName; String LastName; String gender; String medicalCondition;
        int age; int choice;

        int bedCounter = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
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
         out.println("6. Bed Management");
         out.println("7. Reports");
         out.println("8. Display the complete ward layout");
         out.println("9. Exit Application");
         out.println("Please select an option (1-9): ");
       
         while (!scanner.hasNextInt()){
            out.println("Invalid input. Please enter a number between 1 and 9.");
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
                   
                    int wardNumber = 1;
                   
                    String bedNumber = "Unassigned";
                    
                    Inpatient newInpatient = new Inpatient(wardNumber, bedNumber, patientID, firstName, LastName, age, gender, medicalCondition);
                    patients.add(newInpatient);
                    
                    out.println("---------------------------------------------------------");
                    out.println("Inpatient registered successfully!");
                    out.println("IMPORTANT: Bed is currently 'Unassigned'.");
                    out.println("Please go to Menu Option 6 (Bed Management) to allocate a bed.");
                    out.println("---------------------------------------------------------");
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
                    out.println("Patient, " + patientID + " not found.");
                }
                break;

            case 3:

                out.println("===Update Patient Details===");
                out.print("Enter Patient ID to update: ");
                patientID = scanner.next();

                boolean foundForUpdate = false;
                for (Patient patient : patients) {
                    if (patient.getPatientID().equals(patientID)) {
                        out.println("Patient found: " + patient.getFirstName() + " " + patient.getLastName());
                        
                        out.print("Enter new Age (or enter 0 to keep current age of " + patient.getAge() + "): ");
                        int newAge = scanner.nextInt();
                        if (newAge > 0) {
                            patient.setAge(newAge);
                        }
                        
                        out.print("Enter new Medical Condition (or type 'skip' to keep current): ");
                        String newCondition = scanner.next();
                        if (!newCondition.equalsIgnoreCase("skip")) {
                            patient.setMedicalCondition(newCondition);
                        }
                        
                        out.println("Patient details updated successfully!");
                        foundForUpdate = true;
                        break;
                    }
                }
                
                if (!foundForUpdate) {
                    out.println("Error: No patient found with ID " + patientID);
                }
                break;
            
            case 4:
                out.println("===Delete a Patient===");
                out.print("Enter Patient ID to delete: ");
                patientID = scanner.next();

                Patient patientToRemove = null;
                
                // this finds the patient first
                for (Patient patient : patients) {
                    if (patient.getPatientID().equals(patientID)) {
                        patientToRemove = patient;
                        break;
                    }
                }
                
                // If patient was found, we remove them
                if (patientToRemove != null) {
                    if (patientToRemove instanceof Inpatient) {
                        out.println("Note: Patient was an Inpatient. (Bed release logic will run here).");
                        if (patientToRemove instanceof Inpatient) {
                        Inpatient inpatientToRemove = (Inpatient) patientToRemove;
                        String bedToFree = inpatientToRemove.getBedNumber();
                        
                        if (!bedToFree.equals("Unassigned")) {
                            // Free the bed in the 2D array
                            for (int i = 0; i < 4; i++) {
                                for (int j = 0; j < 5; j++) {
                                    if (HospitalBeds[i][j].equals(bedToFree + "[OCC]")) {
                                        HospitalBeds[i][j] = bedToFree;
                                        break;
                                    }
                                }
                            }
                            out.println("Note: Bed " + bedToFree + " has been automatically released.");
                        }
                    }
                    }
                    
                    patients.remove(patientToRemove);
                    out.println("Patient " + patientID + " successfully deleted from the system.");
                } else {
                    out.println("Error: No patient found with ID " + patientID);
                }
                break;
            
            case 5:
                out.println("===Display All Registered Patients===");
                if (patients.isEmpty()) {
                    out.println("No patients registered.");
                } else {
                    for (Patient patient : patients) {
                        patient.displayPatientDetails();
                        out.println("-------------------------");
                    }
                }
                break;
            
            case 6:
                out.println("===Bed Management===");
                out.println("1. Display complete ward layout");
                out.println("2. Display available beds");
                out.println("3. Display occupied beds");
                out.println("4. Allocate a bed to an inpatient");
                out.println("5. Release a bed");
                out.print("Please select a bed option (1-5): ");
                int bedChoice = scanner.nextInt();

                switch (bedChoice) {
                    case 1:
                        out.println("\n--- Complete Ward Layout ---");
                        
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 5; j++) {
                                out.print(HospitalBeds[i][j] + "  ");
                            }
                            out.println(); 
                        }
                        out.println("----------------------------");
                        break;

                    case 2:
                        out.println("\n--- Available Beds ---");
                        int availableCount = 0;
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 5; j++) {
                                // If the string DOES NOT contain [OCC], it is available
                                if (!HospitalBeds[i][j].contains("[OCC]")) {
                                    out.print(HospitalBeds[i][j] + "  ");
                                    availableCount++;
                                }
                            }
                        }
                        out.println("\nTotal Available Beds: " + availableCount);
                        out.println("----------------------");
                        break;
                    case 3:
                        out.println("\n--- Occupied Beds ---");
                        int occupiedCount = 0;
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 5; j++) {
                                // If the string contains [OCC], it is occupied
                                if (HospitalBeds[i][j].contains("[OCC]")) {
                                    out.print(HospitalBeds[i][j] + "  ");
                                    occupiedCount++;
                                }
                            }
                        }

                        if (occupiedCount == 0) {
                            out.println("No beds are currently occupied.");
                        } else {
                            out.println("\nTotal Occupied Beds: " + occupiedCount);
                        }

                        out.println("\nTotal Occupied Beds: " + occupiedCount);
                        out.println("---------------------");
                        break;
                    case 4:
                        out.println("\n--- Allocate a Bed ---");
                        
                        
                        int allocAvailableCount = 0;
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (!HospitalBeds[i][j].contains("[OCC]")) {
                                    allocAvailableCount++;
                                }
                            }
                        }
                        
                        if (allocAvailableCount == 0) {
                            out.println("Error: Allocation failed. All the beds in the ward are currently occupied.");
                            break;
                        }

                       
                        out.print("Enter Patient ID to allocate a bed to: ");
                        String allocPatientID = scanner.next();
                        Patient targetPatient = null;
                        
                        for (Patient patient : patients) {
                            if (patient.getPatientID().equals(allocPatientID)) {
                                targetPatient = patient;
                                break;
                            }
                        }

                        if (targetPatient == null) {
                            out.println("Error: No patient found with ID " + allocPatientID);
                            break;
                        }

                        
                        if (!(targetPatient instanceof Inpatient)) {
                            out.println("Error: Only Inpatients can be allocated a hospital bed.");
                            break;
                        }
                        
                        
                        Inpatient allocInpatient = (Inpatient) targetPatient;
                        
                        if (!allocInpatient.getBedNumber().equals("Unassigned")) {
                            out.println("Notice: Patient is already allocated to bed " + allocInpatient.getBedNumber());
                            break;
                        }

                       
                        out.print("Enter the Bed Number to allocate (e.g., B01): ");
                        
                        String requestedBed = scanner.next().toUpperCase(); 
                        boolean bedProcessed = false;

                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (HospitalBeds[i][j].equals(requestedBed)) {
                                    
                                   
                                    HospitalBeds[i][j] = requestedBed + "[OCC]";
                                    
                                    
                                    allocInpatient.setBedNumber(requestedBed);
                                    
                                    out.println("Success: Bed " + requestedBed + " allocated to " + allocInpatient.getFirstName());
                                    bedProcessed = true;
                                    break;
                                    
                                } else if (HospitalBeds[i][j].equals(requestedBed + "[OCC]")) {
                                    out.println("Error: Bed " + requestedBed + " is already occupied!");
                                    bedProcessed = true;
                                    break;
                                }
                            }
                            if (bedProcessed) break; 
                        }

                        if (!bedProcessed) {
                            out.println("Error: Bed " + requestedBed + " does not exist in this ward.");
                        }
                        break;
                    case 5:
                    out.println("\n--- Release a Bed ---");
                        out.print("Enter Patient ID to discharge/release bed: ");
                        String releasePatientID = scanner.next();
                        Patient releaseTarget = null;

                        for (Patient patient : patients) {
                            if (patient.getPatientID().equals(releasePatientID)) {
                                releaseTarget = patient;
                                break;
                            }
                        }

                        if (releaseTarget == null) {
                            out.println("Error: No patient found with ID " + releasePatientID);
                            break;
                        }

                        if (!(releaseTarget instanceof Inpatient)) {
                            out.println("Error: Only Inpatients have allocated beds.");
                            break;
                        }

                        Inpatient releaseInpatient = (Inpatient) releaseTarget;
                        String currentBed = releaseInpatient.getBedNumber();

                        if (currentBed.equals("Unassigned")) {
                            out.println("Notice: This patient does not currently have a bed allocated.");
                            break;
                        }

                        
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (HospitalBeds[i][j].equals(currentBed + "[OCC]")) {
                                    HospitalBeds[i][j] = currentBed; 
                                    break;
                                }
                            }
                        }

                        releaseInpatient.setBedNumber("Unassigned");
                        out.println("Success: Bed " + currentBed + " has been released and is now available.");
                        break;
                    default:
                        out.println("Invalid bed management option.");
                        break;
                }
                break;

            case 7:
                out.println("===Reports===");
                break;

            case 8:
                out.println("===Complete Ward Layout==="); 
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 5; j++) {
                        out.print(HospitalBeds[i][j] + " ");
                    }
                    out.println();
                }
                break;

            case 9:
                out.println("Exiting the application. Goodbye!");
                break;

        }
    } while (choice != 9);
        
        scanner.close(); 

        
}
}