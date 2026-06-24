import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static List<Doctor> loadDoctors(String fileName) {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                String id             = data[0].trim();
                String name           = data[1].trim();
                String specialization = data[2].trim();
                String department     = data[3].trim();
                String phone          = data[4].trim();
                String password       = data[5].trim();
                String username       = data[6].trim();
                Doctor d = new Doctor(department, specialization, id, name, password, username, phone);
                doctors.add(d);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: doctors file not found!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return doctors;
    }

    public static void saveDoctors(String fileName, List<Doctor> doctors) {
        if (doctors == null || doctors.isEmpty()) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            for (Doctor d : doctors) {
                bw.write(d.getID() + "," + d.getName() + "," + d.getSpecialization() + "," +
                         d.getDepartment() + "," + d.getPhonenumber() + "," +
                         d.getPassword() + "," + d.getUsername());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static List<Patient> LoadPatients(String fileName) {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                String id              = data[0].trim();
                String name            = data[1].trim();
                String age             = data[2].trim();
                String gender          = data[3].trim();
                String phone           = data[4].trim();
                String assignedDoctor  = data[5].trim();
                String password        = data[6].trim();
                String username        = data[7].trim();
                Patient p = new Patient(id, name, age, gender, phone, assignedDoctor, password, username);
                patients.add(p);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: patients file not found!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return patients;
    }

    public static void savePatients(String fileName, List<Patient> patients) {
        if (patients == null || patients.isEmpty()) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            for (Patient p : patients) {
                bw.write(p.getID() + "," + p.getName() + "," + p.getAge() + "," +
                         p.getGender() + "," + p.getPhonenumber() + "," +
                         p.getAssigned_doctor() + "," + p.getPassword() + "," + p.getUsername());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static List<Appointment> LoadAppointments(String fileName) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                String appointmentId = data[0].trim();
                String patientId     = data[1].trim();
                String doctorId      = data[2].trim();
                String date          = data[3].trim();
                String time          = data[4].trim();
                String status        = data[5].trim();
                Appointment a = new Appointment(appointmentId, patientId, doctorId, date, time, status);
                appointments.add(a);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: appointments file not found!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return appointments;
    }

    public static void saveAppointments(String fileName, List<Appointment> appointments) {
        if (appointments == null || appointments.isEmpty()) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            for (Appointment a : appointments) {
                bw.write(a.getAppointmentID() + "," + a.getPatientID() + "," +
                         a.getDoctorID() + "," + a.getDate() + "," +
                         a.getTime() + "," + a.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static final String USERS_FILE = "users.txt";

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                String id          = data[0].trim();
                String username    = data[1].trim();
                String password    = data[2].trim();
                String role        = data[3].trim();
                String phonenumber = (data.length > 4) ? data[4].trim() : "00000";
                String name        = (data.length > 5) ? data[5].trim() : "Default Name";

                User user = null;
                if (role.equalsIgnoreCase("Admin")) {
                    user = new Admin(username, password, id, name, phonenumber);
                } else if (role.equalsIgnoreCase("Doctor")) {
                    user = new Doctor(id, name, password, username, phonenumber);
                } else if (role.equalsIgnoreCase("Patient")) {
                    user = new Patient(id, name, password, username, phonenumber);
                }

                if (user != null) {
                    users.add(user);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: users.txt file not found. Starting with empty list.");
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
        }
        return users;
    }

    public static void saveUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, false))) {
            for (User u : users) {
                String role = "User";
                if (u instanceof Admin)   role = "Admin";
                else if (u instanceof Doctor)  role = "Doctor";
                else if (u instanceof Patient) role = "Patient";

                String record = String.join(",",
                    u.getID(),
                    u.getUsername(),
                    u.getPassword(),
                    role,
                    u.getPhonenumber(),
                    u.getName()
                );
                bw.write(record);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
}
