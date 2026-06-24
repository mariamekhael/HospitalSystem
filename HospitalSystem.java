import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class HospitalSystem {
    public static int numofDoctors;
    public static int numofPatients;
    public static int numofAppointments;

    public static void countdoctor() {
        numofDoctors++;
    }
    public static void countpatient(){
        numofPatients++;
    }
    public static void countAppointment() {
        numofAppointments++;
    }
    public static void infoofHospitalSystem(ArrayList<Doctor> doctors){
        System.out.println("Total of Doctors : "+ numofDoctors+" Doctors");
        System.out.println("Total of Patients : "+ numofPatients+" Patients");
        System.out.println("Total of Appointments : " + numofAppointments + " Appointments");
        System.out.println("Top 3 Doctors (Highest Appointments): ");
        ArrayList<Doctor> sortedDoctors = new ArrayList<>(doctors);
        Collections.sort(sortedDoctors, new Comparator<Doctor>() {
            @Override
            public int compare(Doctor d1, Doctor d2) {
                return Integer.compare(d2.getMyAppointments().size(), d1.getMyAppointments().size());
            }
        });

      
        for (int i = 0; i < Math.min(3, sortedDoctors.size()); i++) {
            Doctor d = sortedDoctors.get(i);
            System.out.println("   - " + d.getName() + " (" + d.getMyAppointments().size() + " Appointments)");
        }
    }
}