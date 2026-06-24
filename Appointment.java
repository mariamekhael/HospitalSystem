import java.util.ArrayList;

public class Appointment{
    public String patientID;
public String doctorID;
public String appointmentID;
public String date;
public String time;
public String status;
    
    public Appointment(String appointmentID,String patientID,String doctorID,String date,String time,String status) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentID = appointmentID;
        this.date = date;
        this.time = time;
        this.status = status;
if(date == null || date.isEmpty()){
            System.out.println("Date cannot be empty.");
            this.date = "Not Set";
        }
        else{
            this.date = date;
        }

        // Validation for empty time
        if(time == null || time.isEmpty()){
            System.out.println("Time cannot be empty.");
            this.time = "Not Set";
        }
        else{
            this.time = time;
        }

        this.status = status;
    }





    public Appointment(){
        
    }


    public Appointment(Doctor d,Patient p) {
        this.doctorID=d.getID();
        this.patientID=p.getID();
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setDate(String date) {
        if(date == null || date.isEmpty()){
            System.out.println("Date cannot be empty.");
        }
        else{
            this.date = date;
        }
    }

    public void setTime(String time) {
        if(time == null || time.isEmpty()){
            System.out.println("Time cannot be empty.");
        }
        else{
            this.time = time;
        }
    }



    public void setStatus(String status) {

    if(this.status != null &&
       this.status.equalsIgnoreCase("Cancelled")
       && status.equalsIgnoreCase("Completed")) {

        System.out.println("Cancelled appointment cannot be completed.");
    }
    else {

        this.status = status;
    }
}

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
    

     @Override
    public String toString() {

        return appointmentID + "," +
               patientID + "," +
               doctorID + "," +
               date + "," +
               time + "," +
               status;
    }

    public static boolean isDoctorAvailable(ArrayList<Appointment> appointments,
                                            String doctorID,
                                            String date,
                                            String time) {

        for(Appointment a : appointments) {

            if(a.getDoctorID().equals(doctorID)
                    && a.getDate().equals(date)
                    && a.getTime().equals(time)
                    && !a.getStatus().equalsIgnoreCase("Cancelled")) {

                return false;
            }
        }

        return true;
    }

     public static boolean patientHasDoctor(Patient p) {

        if(p.getAssigned_doctor() == null) {
            return false;
        }

        return true;
    }


}