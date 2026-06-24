import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Admin admin = new Admin("admin", "admin123", "AD101", "System Admin", "0123456789");

        List<Doctor> loadedDoctors = FileManager.loadDoctors("doctors.txt");
        for (Doctor d : loadedDoctors) {
            admin.Doctors.add(d);
            HospitalSystem.countdoctor();
        }

        List<Patient> loadedPatients = FileManager.LoadPatients("patients.txt");
        for (Patient p : loadedPatients) {
            admin.patients.add(p);
            HospitalSystem.countpatient();
        }

        List<Appointment> loadedAppointments = FileManager.LoadAppointments("appointments.txt");
        for (Appointment a : loadedAppointments) {
            admin.Appointments.add(a);
            HospitalSystem.countAppointment();
        }

        int mainChoice;

        do {
            System.out.println("\n==** Hospital Management System **==");
            System.out.println("[1] Login as Admin");
            System.out.println("[2] Login as Doctor");
            System.out.println("[3] Login as Patient");
            System.out.println("[4] Exit System");
            System.out.print("Choose any option from 1-4: ");
            mainChoice = input.nextInt();
            input.nextLine();

            switch (mainChoice) {
                case 1:
                    System.out.print("Enter Admin Username: ");
                    String admU = input.nextLine();
                    System.out.print("Enter Admin Password: ");
                    String admP = input.nextLine();
                    if (admin.getUsername().equals(admU) && admin.getPassword().equals(admP)) {
                        adminMenu(admin, input);
                    } else {
                        System.out.println("Invalid Admin Username or Password!");
                    }
                    break;

                case 2:
                    if (admin.Doctors.isEmpty()) {
                        System.out.println("No doctors in the system. Contact Admin.");
                    } else {
                        System.out.print("Enter Doctor Username: ");
                        String docU = input.nextLine();
                        System.out.print("Enter Password: ");
                        String docP = input.nextLine();
                        Doctor currentDoc = null;
                        for (Doctor d : admin.Doctors) {
                            if (d.getUsername().equals(docU) && d.getPassword().equals(docP)) {
                                currentDoc = d;
                                break;
                            }
                        }
                        if (currentDoc != null) {
                            doctorMenu(currentDoc, input);
                        } else {
                            System.out.println("Invalid Username or Password!");
                        }
                    }
                    break;

                case 3:
                    if (admin.patients.isEmpty()) {
                        System.out.println("No patients registered yet!");
                    } else {
                        System.out.print("Enter Username: ");
                        String patU = input.nextLine();
                        System.out.print("Enter Password: ");
                        String patP = input.nextLine();
                        Patient currentPat = null;
                        for (Patient p : admin.patients) {
                            if (p.getUsername().equals(patU) && p.getPassword().equals(patP)) {
                                currentPat = p;
                                break;
                            }
                        }
                        if (currentPat != null) {
                            patientMenu(currentPat, input, admin);
                        } else {
                            System.out.println("Invalid Username or Password!");
                        }
                    }
                    break;

                case 4:
                    FileManager.saveDoctors("doctors.txt", admin.Doctors);
                    FileManager.savePatients("patients.txt", admin.patients);
                    FileManager.saveAppointments("appointments.txt", admin.Appointments);
                    System.out.println("Data saved. Goodbye!!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }
        } while (mainChoice != 4);
    }

    public static void adminMenu(Admin a, Scanner input) {
        int choice;
        do {
            System.out.println("\n--- Admin Page ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. Register Patient");
            System.out.println("3. Assign Patient to Doctor");
            System.out.println("4. Create Appointment");
            System.out.println("5. View All Doctors");
            System.out.println("6. View All Patients");
            System.out.println("7. View All Appointments");
            System.out.println("8. Search Patient by ID");
            System.out.println("9. Search Doctor by ID");
            System.out.println("10. Generate Reports");
            System.out.println("11. Save Data");
            System.out.println("12. Logout");
            System.out.print("Choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: {
                    Doctor D = new Doctor();
                    System.out.println("Add a name of Doctor:");
                    D.setName(input.nextLine());
                    System.out.println("Enter username:");
                    D.setUsername(input.nextLine());
                    System.out.println("Enter password:");
                    D.setPassword(input.nextLine());
                    System.out.println("Enter ID:");
                    D.setID(input.nextLine());
                    System.out.println("Enter department:");
                    D.setDepartment(input.nextLine());
                    System.out.println("Enter Specialization:");
                    D.setSpecialization(input.nextLine());
                    System.out.println("Enter phone number:");
                    D.setPhonenumber(input.nextLine());
                    a.adddoctor(D);
                    break;
                }
                case 2: {
                    Patient P = new Patient();
                    System.out.println("Enter name:");
                    P.setName(input.nextLine());
                    System.out.println("Enter username:");
                    P.setUsername(input.nextLine());
                    System.out.println("Enter password:");
                    P.setPassword(input.nextLine());
                    System.out.println("Enter ID:");
                    P.setID(input.nextLine());
                    System.out.println("Enter Age:");
                    P.setAge(input.nextLine());
                    System.out.println("Enter Gender:");
                    P.setGender(input.nextLine());
                    System.out.println("Enter phone number:");
                    P.setPhonenumber(input.nextLine());
                    System.out.println("Enter Assigned Doctor name:");
                    P.setAssigned_doctor(input.nextLine());
                    a.addpatient(P);
                    break;
                }
                case 3: {
                    System.out.print("Enter Patient ID: ");
                    String pID = input.nextLine();
                    System.out.print("Enter Doctor ID: ");
                    String dID = input.nextLine();

                    Patient foundPatient = null;
                    Doctor foundDoctor = null;

                    for (Patient p : a.patients) {
                        if (p.getID().equals(pID)) { foundPatient = p; break; }
                    }
                    for (Doctor d : a.Doctors) {
                        if (d.getID().equals(dID)) { foundDoctor = d; break; }
                    }

                    if (foundPatient != null && foundDoctor != null) {
                        foundPatient.setAssigned_doctor(foundDoctor.getName());
                        a.assignpatient(foundPatient, foundDoctor);
                    } else {
                        System.out.println("Error: Patient or Doctor ID not found!");
                    }
                    break;
                }
                case 4: {
                    System.out.print("Enter ID of Doctor: ");
                    String docId = input.nextLine();
                    System.out.print("Enter ID of Patient: ");
                    String patId = input.nextLine();
                    System.out.print("Enter Appointment ID: ");
                    String appID = input.nextLine();
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = input.nextLine();
                    System.out.print("Enter Time (HH:MM): ");
                    String time = input.nextLine();

                    boolean available = a.checkDoctorAvailability(docId, date, time);
                    if (!available) {
                        System.out.println("Doctor already has an appointment at this time.");
                        break;
                    }

                    Doctor targetDoctor = null;
                    for (Doctor d : a.Doctors) {
                        if (d.getID().equals(docId)) { targetDoctor = d; break; }
                    }
                    Patient targetPatient = null;
                    for (Patient p : a.patients) {
                        if (p.getID().equals(patId)) { targetPatient = p; break; }
                    }

                    if (targetDoctor != null && targetPatient != null) {
                        Appointment appointment = new Appointment(appID, patId, docId, date, time, "Confirmed");
                        a.createappointment(appointment, targetDoctor, targetPatient);
                        System.out.println("Appointment created successfully!");
                    } else {
                        System.out.println("Error: Doctor ID or Patient ID not found.");
                    }
                    break;
                }
                case 5:
                    a.displayinfoDoctors();
                    break;
                case 6:
                    a.displayinfopatients();
                    break;
                case 7:
                    a.displayappointment();
                    break;
                case 8: {
                    System.out.println("Enter ID of Patient:");
                    a.searchpatientbyID(input.nextLine());
                    break;
                }
                case 9: {
                    System.out.println("Enter ID of Doctor:");
                    a.searchDoctorbyID(input.nextLine());
                    break;
                }
                case 10:
                    a.reports();
                    break;
                case 11:
                    FileManager.saveDoctors("doctors.txt", a.Doctors);
                    FileManager.savePatients("patients.txt", a.patients);
                    FileManager.saveAppointments("appointments.txt", a.Appointments);
                    System.out.println("Data saved successfully.");
                    break;
                case 12:
                    System.out.println("Logging out Admin...");
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        } while (choice != 12);
    }

    public static void doctorMenu(Doctor dr, Scanner input) {
        int choice;
        do {
            System.out.println("\n----- Doctor Menu (" + dr.getName() + ") -----");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Patients");
            System.out.println("3. View My Appointments");
            System.out.println("4. Update Appointment Status");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    dr.displayinfo();
                    break;
                case 2:
                    dr.viewAssignedPatients();
                    break;
                case 3:
                    dr.viewMyAppointments();
                    break;
                case 4:
                    System.out.print("Enter Appointment ID: ");
                    String id = input.nextLine();
                    System.out.println("Status: 1. Confirmed  2. Completed  3. Cancelled");
                    System.out.print("Enter number: ");
                    int num = input.nextInt();
                    input.nextLine();
                    String newstatus = "";
                    if (num == 1) newstatus = "Confirmed";
                    else if (num == 2) newstatus = "Completed";
                    else if (num == 3) newstatus = "Cancelled";
                    else System.out.println("Invalid Choice!");
                    if (!newstatus.equals("")) dr.updateAppointmentStatus(id, newstatus);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        } while (choice != 5);
    }

    public static void patientMenu(Patient patient, Scanner input, Admin admin) {
        int choice;
        do {
            System.out.println("\n--- Patient Menu (" + patient.getName() + ") ---");
            System.out.println("[1] View My Profile");
            System.out.println("[2] View Assigned Doctor");
            System.out.println("[3] View My Appointments");
            System.out.println("[4] Book an Appointment");
            System.out.println("[5] Cancel an Appointment");
            System.out.println("[6] Logout");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    patient.displayinfo();
                    break;
                case 2:
                    System.out.println("Your Assigned Doctor is Dr. " + patient.getAssigned_doctor());
                    break;
                case 3:
                    patient.getList_of_Appointments();
                    break;
                case 4:
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = input.nextLine();
                    System.out.print("Enter Time (HH:MM): ");
                    String time = input.nextLine();
                    System.out.println(patient.Book_Appointment(date, time, admin));
                    break;
                case 5:
                    System.out.print("Enter the date of Appointment to cancel: ");
                    String canceldate = input.nextLine();
                    System.out.print("Enter Time: ");
                    String canceltime = input.nextLine();
                    System.out.println(patient.Cancel_Appointment(canceldate, canceltime, admin));
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        } while (choice != 6);
    }
}
