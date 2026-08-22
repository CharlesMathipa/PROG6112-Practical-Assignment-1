import static java.lang.System.out;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;

public class PatientManager {
    // Made public so the Test file can access them
    public static ArrayList<Patient> patients = new ArrayList<>();
    public static String[][] HospitalBeds = new String[4][5]; 

    public static void main(String[] args) {
        String patientID, firstName, lastName, gender, medicalCondition;
        int age, choice;
        Scanner scanner = new Scanner(System.in);

        resetSystem(); // Initialize beds

        do {
            out.println("\n===MediCare Hospital===");
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
            out.print("Please select an option (1-9): ");

            while (!scanner.hasNextInt()) {
                out.println("Invalid input. Please enter a number between 1 and 9.");
                scanner.next();
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
                    lastName = scanner.next();
                    age = getValidInt(scanner, "Enter Age: ");
                    out.print("Enter Gender (Male/Female): ");
                    gender = scanner.next();
                    out.print("Enter Medical Condition: ");
                    medicalCondition = scanner.next();

                    out.println("Select Category:\n1. Inpatient\n2. Outpatient\n3. Emergency");
                    int categoryChoice = getValidInt(scanner, "Enter your choice (1-3): ");

                    Patient newPatient = null;
                    if (categoryChoice == 1) {
                        newPatient = new Inpatient(1, "Unassigned", patientID, firstName, lastName, age, gender, medicalCondition);
                    } else if (categoryChoice == 2) {
                        newPatient = new Outpatient(patientID, firstName, lastName, age, gender, medicalCondition);
                    } else if (categoryChoice == 3) {
                        newPatient = new Emergency(patientID, firstName, lastName, age, gender, medicalCondition);
                    } else {
                        out.println("Invalid category selected.");
                        break;
                    }

                    if (addPatient(newPatient)) {
                        out.println("Patient registered successfully!");
                    } else {
                        out.println("Error: Patient ID " + patientID + " already exists.");
                    }
                    break;

                case 2:
                    out.print("Enter Patient ID to search: ");
                    patientID = scanner.next();
                    Patient found = searchPatient(patientID);
                    if (found != null) {
                        found.displayPatientDetails();
                    } else {
                        out.println("Patient " + patientID + " not found.");
                    }
                    break;

                case 3:
                    out.print("Enter Patient ID to update: ");
                    patientID = scanner.next();
                    out.print("Enter new Age (or 0 to skip): ");
                    int newAge = getValidInt(scanner, "Enter new Age: ");
                    out.print("Enter new Medical Condition (or 'skip'): ");
                    String newCond = scanner.next();

                    if (updatePatientDetails(patientID, newAge, newCond)) {
                        out.println("Patient updated successfully.");
                    } else {
                        out.println("Patient not found.");
                    }
                    break;

                case 4:
                    out.print("Enter Patient ID to delete: ");
                    if (deletePatient(scanner.next())) {
                        out.println("Patient deleted successfully.");
                    } else {
                        out.println("Patient not found.");
                    }
                    break;

                case 5:
                    if (patients.isEmpty()) {
                        out.println("No patients registered.");
                    } else {
                        for (Patient p : patients) { p.displayPatientDetails(); out.println("---"); }
                    }
                    break;

                case 6:
                    out.println("===Bed Management===\n1. Display layout\n2. Available\n3. Occupied\n4. Allocate bed\n5. Release bed");
                    int bedChoice = getValidInt(scanner, "Select option: ");
                    if (bedChoice == 4) {
                        out.print("Enter Patient ID: ");
                        String pID = scanner.next();
                        out.print("Enter Bed Number (e.g., B01): ");
                        String bNum = scanner.next().toUpperCase();
                        
                        String result = allocateBed(pID, bNum);
                        out.println(result);
                    } else if (bedChoice == 5) {
                        out.print("Enter Patient ID to release bed: ");
                        if (releaseBed(scanner.next())) {
                            out.println("Bed released successfully.");
                        } else {
                            out.println("Failed to release bed. Check ID or allocation status.");
                        }
                    }
                    break;
                case 7:
                    out.println("1. Stats\n2. Sort Alphabetically by Surname");
                    int repChoice = getValidInt(scanner, "Select: ");
                    if (repChoice == 2) {
                        sortPatientsBySurname();
                        for (Patient p : patients) {
                            out.println(p.getLastName() + ", " + p.getFirstName() + " (ID: " + p.getPatientID() + ")");
                        }
                    }
                    break;
                case 8:
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 5; j++) out.print(HospitalBeds[i][j] + " ");
                        out.println();
                    }
                    break;
                case 9:
                    out.println("Goodbye!");
                    break;
            }
        } while (choice != 9);
        scanner.close();
    }

    public static void resetSystem() {
        patients.clear();
        int bedCounter = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                HospitalBeds[i][j] = "B" + String.format("%02d", bedCounter++);
            }
        }
    }

    public static boolean addPatient(Patient patient) {
        if (searchPatient(patient.getPatientID()) != null) return false; // Prevent duplicates
        patients.add(patient);
        return true;
    }

    public static Patient searchPatient(String patientID) {
        for (Patient p : patients) {
            if (p.getPatientID().equals(patientID)) return p;
        }
        return null;
    }

    public static boolean updatePatientDetails(String patientID, int newAge, String newCondition) {
        Patient p = searchPatient(patientID);
        if (p == null) return false;
        if (newAge > 0) p.setAge(newAge);
        if (!newCondition.equalsIgnoreCase("skip")) p.setMedicalCondition(newCondition);
        return true;
    }

    public static boolean deletePatient(String patientID) {
        Patient p = searchPatient(patientID);
        if (p == null) return false;
        if (p instanceof Inpatient) releaseBed(patientID);
        patients.remove(p);
        return true;
    }

    public static boolean isWardFull() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!HospitalBeds[i][j].contains("[OCC]")) return false;
            }
        }
        return true;
    }

    public static String allocateBed(String patientID, String requestedBed) {
        if (isWardFull()) return "Error: All beds are occupied.";
        
        Patient p = searchPatient(patientID);
        if (p == null || !(p instanceof Inpatient)) return "Error: Valid Inpatient not found.";
        
        Inpatient inpatient = (Inpatient) p;
        if (!inpatient.getBedNumber().equals("Unassigned")) return "Error: Patient already has a bed.";

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (HospitalBeds[i][j].equals(requestedBed + "[OCC]")) return "Error: Bed is already occupied.";
                if (HospitalBeds[i][j].equals(requestedBed)) {
                    HospitalBeds[i][j] = requestedBed + "[OCC]";
                    inpatient.setBedNumber(requestedBed);
                    return "Success: Bed allocated.";
                }
            }
        }
        return "Error: Bed does not exist.";
    }

    public static boolean releaseBed(String patientID) {
        Patient p = searchPatient(patientID);
        if (p == null || !(p instanceof Inpatient)) return false;
        
        Inpatient inpatient = (Inpatient) p;
        String currentBed = inpatient.getBedNumber();
        if (currentBed.equals("Unassigned")) return false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (HospitalBeds[i][j].equals(currentBed + "[OCC]")) {
                    HospitalBeds[i][j] = currentBed;
                    inpatient.setBedNumber("Unassigned");
                    return true;
                }
            }
        }
        return false;
    }

    public static void sortPatientsBySurname() {
        patients.sort(Comparator.comparing(Patient::getLastName));
    }

    public static int getValidInt(Scanner scanner, String prompt) {
        while (true) {
            out.print(prompt);
            if (scanner.hasNextInt()) return scanner.nextInt();
            out.println("Invalid input. Numbers only.");
            scanner.next();
        }
    }
}