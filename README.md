# Hospital Appointments Management System

A console-based Java application for managing hospital appointments, supporting three roles — **Admin**, **Doctor**, and **Patient** — with data persisted to text files. This README documents the actual implementation, based on the provided source files.

## Overview

The system runs entirely from `main.java`, which loads existing doctors, patients, and appointments from text files at startup, then presents a login menu. Once logged in, each role is routed to its own console sub-menu (`adminMenu`, `doctorMenu`, `patientMenu`), all defined as static methods inside `main`.

## How to Run

1. Compile all files together:

```bash
javac *.java
```

2. Run the entry point:

```bash
java main
```

3. Make sure `doctors.txt`, `patients.txt`, and `appointments.txt` exist in the working directory (they can be empty; missing files are handled gracefully and simply result in an empty starting list).

## Logging In

- **Admin** is a single hardcoded account created directly in `main`:
  - Username: `admin`
  - Password: `admin123`
  - ID: `AD101`
- **Doctor** and **Patient** accounts are matched against the username/password of doctors and patients already loaded from `doctors.txt` / `patients.txt`. A doctor or patient must therefore already exist in the corresponding file (or have been added during the current session) before they can log in.

## Class Structure

### `User` (abstract)
Base class holding shared fields: `ID`, `name`, `username`, `password`, `phonenumber`, with getters/setters and an abstract `displayinfo()` method that each subclass implements.

### `Admin extends User`
Holds the system's central in-memory lists: `Doctors`, `patients`, and `Appointments`. Responsible for:
- `adddoctor(Doctor)` / `addpatient(Patient)` — add records and update global counters via `HospitalSystem`
- `assignpatient(Patient, Doctor)` — links a patient to a doctor's assigned-patient list
- `createappointment(Appointment, Doctor, Patient)` — adds an appointment to the admin, doctor, and patient's appointment lists simultaneously
- `checkDoctorAvailability(doctorID, date, time)` — scans all appointments to detect a scheduling conflict
- `displayappointment()`, `displayinfo()`, `displayinfoDoctors()`, `displayinfopatients()` — console listing methods
- `searchDoctorbyID(ID)` / `searchpatientbyID(ID)` — linear search by ID
- `reports()` — delegates to `HospitalSystem.infoofHospitalSystem(...)`

### `Doctor extends User`
Adds `Specialization`, `Department`, a list of `assignedpatients`, and a list of `MyAppointments`. Provides:
- `viewAssignedPatients()`, `viewMyAppointments()`
- `updateAppointmentStatus(appointmentID, newStatus)` — finds the appointment by ID and updates its status through `Appointment.setStatus(...)`

### `Patient extends User`
Adds `Age`, `Gender`, `Assigned_doctor` (stored as the doctor's **name**, not ID), and a list of `MyAppointments`. Provides:
- `getList_of_Appointments()` — prints all booked appointments
- `Book_Appointment(date, time, admin)` — looks up the assigned doctor by name, checks availability through the admin, creates a new `Appointment` with an auto-generated ID (`"APP" + size + 1`), and registers it with the doctor, patient, and admin
- `Cancel_Appointment(date, time, admin)` — finds a matching appointment by date/time and removes it from the patient, the admin, and the relevant doctor's lists
- `bookAppointment(Appointment, Admin, Doctor)` — an alternative, simpler booking method that adds a pre-built appointment to all three lists (currently unused by `main`, since `Book_Appointment` is called instead)

### `Appointment`
Holds `appointmentID`, `patientID`, `doctorID`, `date`, `time`, and `status`. Validates that date and time are not empty (defaulting to `"Not Set"` if so) and prevents a cancelled appointment from being marked completed in `setStatus(...)`. Also exposes static helpers `isDoctorAvailable(...)` and `patientHasDoctor(...)`, which duplicate logic already present in `Admin.checkDoctorAvailability(...)` and are not currently called from `main`.

### `HospitalSystem`
A static utility/counter class tracking `numofDoctors`, `numofPatients`, and `numofAppointments`, and printing the reports summary, including the top 3 doctors by appointment count.

### `FileManager`
Handles all file I/O:
- `loadDoctors` / `saveDoctors` ↔ `doctors.txt`
- `LoadPatients` / `savePatients` ↔ `patients.txt`
- `LoadAppointments` / `saveAppointments` ↔ `appointments.txt`
- `loadUsers` / `saveUsers` ↔ `users.txt` — implemented, but **not currently called anywhere in `main`**, so `users.txt` is not actually used by the running program

All load methods catch `FileNotFoundException` and general `IOException`, printing a message and returning an empty list rather than crashing.

## Data File Formats (as actually read/written by `FileManager`)

**doctors.txt** — `ID,Name,Specialization,Department,Phone,Password,Username`
```
D001,Dr Mona,Cardiology,Heart Department,01012345678,pass123,drmona
```

**patients.txt** — `ID,Name,Age,Gender,Phone,AssignedDoctorName,Password,Username`
```
P001,Ahmed Ali,20,Male,01098765432,Dr Mona,pass456,ahmed
```

**appointments.txt** — `AppointmentID,PatientID,DoctorID,Date,Time,Status`
```
APP1,P001,D001,2026-05-15,10:30,Confirmed
```

> Note: these column orders differ from the example formats shown in the original project specification (which listed doctor and patient records without password/username fields). The formats above reflect what the code actually reads and writes.

## Console Menus

### Main Menu
```
[1] Login as Admin
[2] Login as Doctor
[3] Login as Patient
[4] Exit System
```

### Admin Menu
```
1. Add Doctor
2. Register Patient
3. Assign Patient to Doctor
4. Create Appointment
5. View All Doctors
6. View All Patients
7. View All Appointments
8. Search Patient by ID
9. Search Doctor by ID
10. Generate Reports
11. Save Data
12. Logout
```

### Doctor Menu
```
1. View My Profile
2. View Assigned Patients
3. View My Appointments
4. Update Appointment Status
5. Logout
```

### Patient Menu
```
[1] View My Profile
[2] View Assigned Doctor
[3] View My Appointments
[4] Book an Appointment
[5] Cancel an Appointment
[6] Logout
```

## Known Issues and Deviations from the Specification

- **`users.txt` is unused.** `FileManager.loadUsers()` / `saveUsers()` exist but are never invoked from `main`, so the "read/write `users.txt`" requirement from the specification is implemented but not wired in.
- **Loose encapsulation.** Fields like `Admin.Doctors`, `Admin.patients`, `Admin.Appointments`, `Doctor.MyAppointments`, and `Doctor.assignedpatients` are package-private rather than private, and are accessed directly from `main` and from `Patient` methods instead of exclusively through accessor methods.
- **Duplicate booking logic.** `Patient` has both `Book_Appointment(...)` (used by `main`, name-based doctor matching, auto-generated appointment IDs) and `bookAppointment(...)` (a simpler variant that is never called). Only one should likely remain.
- **Duplicate availability-check logic.** Doctor time-slot conflict checking exists both in `Admin.checkDoctorAvailability(...)` and as the static `Appointment.isDoctorAvailable(...)`; only the `Admin` version is actually used.
- **Doctor login requires pre-existing doctors.** A doctor cannot log in until an admin has added them (via option 1 in the Admin menu) or they were loaded from `doctors.txt`, since there is no separate doctor self-registration flow.
- **Assigned doctor is stored by name, not ID.** `Patient.Assigned_doctor` holds the doctor's name (set in `main`'s "Assign Patient to Doctor" flow), which works but is fragile if two doctors share the same name, since matching in `Book_Appointment` is name-based.
- **Appointment ID generation is inconsistent.** IDs entered manually by the admin in the "Create Appointment" flow (case 4) can collide with the auto-generated `"APP" + size + 1` IDs used in `Patient.Book_Appointment`, since there is no shared ID counter or uniqueness check across both paths.
