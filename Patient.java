import java.util.ArrayList;
public class Patient extends User{
    private String Gender;
    private String Age;
    private String Assigned_doctor;
    ArrayList<Appointment>MyAppointments;
    
   public Patient(String ID, String name, String password, String username, String phonenumber) {
        super(ID, name, password, username, phonenumber);
    }
    
    
    public Patient(){
        this.MyAppointments = new ArrayList<>();
    }

     public Patient(String id,String name,String Age,String Gender,String phonenumber,String Assigned_doctor,String password,String username) {
        super(id, name, password, username, phonenumber);
        this.Age = Age;
        this.Gender = Gender;
        this.Assigned_doctor=Assigned_doctor;
        this.MyAppointments = new ArrayList<>();
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public void setAssigned_doctor(String Assigned_doctor) {
        this.Assigned_doctor = Assigned_doctor;
    }

    public String getGender() {
        return Gender;
    }

    public String getAge() {
        return Age;
    }
    public ArrayList<Appointment> getPatientAppointments() {
    return MyAppointments;
}

    public String getAssigned_doctor() {
        if (Assigned_doctor==null){
            return "You have not assigned your doctor yet.";
        }
        else 
            return Assigned_doctor;
        
    }

    public void getList_of_Appointments() {
        if (MyAppointments.isEmpty()) {
            System.out.println("You have not reserved any appointments.");
        } else {
            System.out.println("Your Appointments:");
            for (Appointment app : MyAppointments) {
                System.out.println("- Date: " + app.getDate() + " | Time: " + app.getTime() + " | Status: " + app.getStatus());            }
        }
    }
    
    @Override
    public void displayinfo(){
        System.out.println("Name: "+getName());
        System.out.println("Username: "+getUsername());
        System.out.println("Age: "+getAge());
        System.out.println("Gender: "+getGender());
        System.out.println("Phone number: "+getPhonenumber());
        System.out.println("ID: "+getID());
        System.out.println("Assigned Doctor: " +getAssigned_doctor());
        
    }
    public String Book_Appointment(String date, String time, Admin admin) {
    if (this.getAssigned_doctor() == null || this.getAssigned_doctor().equals("You have not assigned your doctor yet.")) {
        return "You must be assigned to a doctor first!";
    }

    Doctor targetDoctor = null;
    for (Doctor d : admin.Doctors) { 
        if (d.getName().equalsIgnoreCase(this.getAssigned_doctor())) {
            targetDoctor = d;
            break;
        }
    }

    if (targetDoctor == null) {
        return "Error: Assigned doctor not found in the system!";
    }

    boolean available =
admin.checkDoctorAvailability(
        targetDoctor.getID(),
        date,
        time
);

if(!available){

    return "Doctor already has appointment at this time!";
}

    Appointment newAppointment =
new Appointment(
        this.getID(),
        targetDoctor.getID(),
        "APP" + (admin.Appointments.size() + 1),
        date,
        time,
        "Confirmed"
);
    
    newAppointment.patientID = this.getID();
    newAppointment.doctorID = targetDoctor.getID();
    newAppointment.status = "Confirmed";
    newAppointment.appointmentID = "APP" + (admin.Appointments.size() + 1);

    targetDoctor.MyAppointments.add(newAppointment);
    this.MyAppointments.add(newAppointment);
    admin.createappointment(newAppointment, targetDoctor, this);
  
    
    System.out.println("Appointment booked and shared with your Doctor and Admin.");


    return "Appointment booked successfully with Dr. " + targetDoctor.getName();
}
    public void bookAppointment(Appointment newApp, Admin admin, Doctor doctor) {
    if (newApp != null && admin != null && doctor != null) {
        this.MyAppointments.add(newApp); 
        
        doctor.getMyAppointments().add(newApp);
        
        admin.getAppointments().add(newApp);
        
        System.out.println("Appointment booked and shared successfully.");
    }
}

    public String Cancel_Appointment(String date, String time, Admin admin) {
    Appointment appointmentToCancel = null;

    for (Appointment app : MyAppointments) {
        if (app.getDate().equals(date) && app.getTime().equals(time)) {
            appointmentToCancel = app;
            break;
        }
    }

    if (appointmentToCancel != null) {
        MyAppointments.remove(appointmentToCancel);

        admin.Appointments.remove(appointmentToCancel);

        for (Doctor d : admin.Doctors) {
            if (d.getID().equals(appointmentToCancel.getDoctorID())) {
                d.MyAppointments.remove(appointmentToCancel);
                break;
            }
        }

        return "Appointment on " + date + " at " + time + " cancelled successfully.";
    }

    return "There is no Appointment with this date or time!!";
}
}
    



