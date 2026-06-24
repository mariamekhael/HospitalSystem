import java.util.ArrayList;
public class Doctor extends User{
    private String Specialization;
    private String Department;
    ArrayList<Patient>assignedpatients=new ArrayList<>();
    ArrayList<Appointment>MyAppointments=new ArrayList<>();
    
    public Doctor (){
        
    }
     public Doctor( String ID, String name, String password, String username,String phonenumber) {
        super(ID, name, password, username,phonenumber);
     }

    public Doctor(String Department,  String Specialization, String ID, String name, String password, String username,String phonenumber) {
        super(ID, name, password, username,phonenumber);
        this.Department = Department;
        this.Specialization = Specialization;
        
    }

    public void setSpecialization(String Specialization) {
        this.Specialization = Specialization;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }
    public ArrayList<Appointment> getMyAppointments() {
    return MyAppointments;
}
    



    @Override
    public void displayinfo(){
System.out.println("Name : "+ getName());
System.out.println("Doctor ID : "+ getID());
System.out.println("Username : "+getUsername());
System.out.println("Specialization : "+Specialization);
System.out.println("Department : "+Department);
System.out.println("Phone number : "+getPhonenumber());
System.out.println(" ");
}

public void viewAssignedPatients(){
    if(assignedpatients.isEmpty()){
        System.out.println("No Assigned Patients Now. ");
    }else{
    System.out.println("Assigned Patients : ");
    for ( Patient p: assignedpatients) {
        System.out.println("Patient Name : "+p.getName()+ ", Paient ID : "+p.getID());
    }
}
System.out.println();
}

public void viewMyAppointments(){
    if(MyAppointments.isEmpty()){
         System.out.println("Appointment list is empty ");
    }else{
        System.out.println("My Appointments :");
        for (Appointment appointment :MyAppointments) {
            System.out.println(" Appointment ID : "+appointment.appointmentID+",  Date : "+appointment.date+", Time : "+appointment.time+ 
            ", Patient ID:  "+appointment.patientID+", Status : "+appointment.status
            );
        }
    }
System.out.println();
}
    
public void updateAppointmentStatus(String id ,String newstatus ){

    boolean found = false;

    for (Appointment appointment : MyAppointments) {

        if(appointment.appointmentID.equals(id)) {

            found = true;

            appointment.setStatus(newstatus);

            System.out.println("Appointment Status updated to : " + newstatus);

            break;
        }
    }

    if(!found){

        System.out.println("The Appointment not found");
    }

    System.out.println();
}

    public String getSpecialization() {
        return Specialization;
    }

    public String getDepartment() {
        return Department;
    }

    public ArrayList<Patient> getAssignedpatients() {
        return assignedpatients;
    }
}


