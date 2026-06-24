import java.util.ArrayList;
public class Admin extends User {

      ArrayList<Doctor>Doctors=new ArrayList<>();
      ArrayList<Patient>patients=new ArrayList<>();
      ArrayList<Appointment>Appointments=new ArrayList<>();
    

    public Admin(String username, String password,String ID, String name, String phonenumber) {
        super(username,password,ID, name, phonenumber);
    }
    public void adddoctor(Doctor doctor){
        Doctors.add(doctor);
        HospitalSystem.countdoctor();
        System.out.println("Doctor added successfully.");
    }
    public void addpatient(Patient patient){
        patients.add(patient);
        HospitalSystem.countpatient();
        System.out.println("Patient added successfully.");
    }
    public ArrayList<Appointment> getAppointments() {
    return Appointments;
}
    public void assignpatient(Patient patient,Doctor doctor){
        doctor.assignedpatients.add(patient);
        System.out.println("Patient " + patient.getName() + " assigned to Dr. " + doctor.getName() + " successfully.");    }
   
   
   
        public void createappointment(Appointment newApp, Doctor doctor, Patient patient) {
    if (newApp != null && doctor != null && patient != null) {
        this.Appointments.add(newApp);
        
        doctor.getMyAppointments().add(newApp);
        
        patient.getPatientAppointments().add(newApp);
        HospitalSystem.countAppointment();
        System.out.println("Appointment created and synced everywhere!");
    }
}




    public boolean checkDoctorAvailability(String doctorID,
                                       String date,
                                       String time){

    for(Appointment a : Appointments){

        if(a.getDoctorID().equals(doctorID)
                && a.getDate().equals(date)
                && a.getTime().equals(time)
                && !a.getStatus().equalsIgnoreCase("Cancelled")){

            return false;
        }
    }

    return true;
}


    public void displayappointment(){
       for (Appointment appointment: Appointments){
        System.out.println(" ");
        System.out.println("The info of Appointment :");
        System.out.println( "Appointment ID : "+appointment.appointmentID);
        System.out.println( "Patient ID : "+appointment.patientID);
        System.out.println( "Doctor ID : "+appointment.doctorID);
        System.out.println("Date : " +appointment.date);
        System.out.println("Time : " +appointment.time);
        System.out.println("Status of Appointment : " +appointment.status);
        System.out.println(" ");
       }
    }
    @Override
   public void displayinfo(){
        System.out.println("\nAll Doctors : ");
    for(Doctor doctor :Doctors){
        System.out.println("Name of Doctor : "+doctor.getName());
    }
        System.out.println("\nAll patients : ");
    for(Patient patient:patients){
        System.out.println("Name of Patient : "+patient.getName());
    }
   }
   
   public void displayinfoDoctors(){
        System.out.println("\nAll Doctors : ");
    for(Doctor doctor :Doctors){
        System.out.println("Name of Doctor : "+doctor.getName());
    }
        
   }
   
   public void displayinfopatients(){
       System.out.println("\nAll patients : ");
    for(Patient patient:patients){
        System.out.println("Name of Patient : "+patient.getName());
    }
   }
   public void searchDoctorbyID(String ID){
        boolean Check=false;
        String doctor1=null;
        for(Doctor doctor :Doctors){
            if(doctor.getID().equals(ID)){
                Check=true;
               doctor1=doctor.getName();
               break;
            }
            
    }
            if(Check)
               System.out.println("\nThis is the doctors ID: "+doctor1);
            else
               System.out.println("\nNo doctor has this ID !");
    }
    public void searchpatientbyID(String ID){
        boolean check=false;
        String patient1=null;

        for(Patient patient :patients){
            if(patient.getID().equals(ID)){
                check=true;
                patient1=patient.getName();
        }
            else
                check=false; 
        }     
            if(check)
                System.out.println("\nThis is the patient's name: "+patient1);
            else
                System.out.println("\nNo patient has this ID !");
    }
    public void reports(){
      HospitalSystem.infoofHospitalSystem(this.Doctors);  
    }
}