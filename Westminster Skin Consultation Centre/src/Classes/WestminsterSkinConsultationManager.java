package Classes;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

import static Classes.Doctor.*;
import static Classes.ManagerOptions.*;

class WestminsterSkinConsultationManager extends Person implements SkinConsultationManager{


    private String password;
    private String username;

    public WestminsterSkinConsultationManager(String name, String surname, String dateOfBirth, String mobileNumber,
                                              String password, String username) {
        super(name, surname, dateOfBirth);
        super.setMobileNumber(mobileNumber);
        this.password = password;
        this.username = username;
    }

    public WestminsterSkinConsultationManager(String name, String sureName, String password, String username) {
        super(name, sureName);
        this.password = password;
        this.username = username;
    }

    public void handlingDoctors(BufferedReader reader) throws IOException {

        SkinConsultationManager.readDoctorFile();
        boolean managerExpression = true;
        while (managerExpression) {
            try {
                System.out.println("****************************************************************");
                System.out.println("                           Doctor Menu                         ");
                System.out.println("****************************************************************");
                System.out.println("         Number of Available Doctor Vacancies   : "+(10-Doctor.getAvailableNumOfDoctors()));
                System.out.println("               1. Delete a doctor                              ");
                if (Doctor.getAvailableNumOfDoctors() != 10) {
                    System.out.println("               2. Add a Doctor                                  ");
                }
                System.out.println("               3. Print the all doctor detail                   ");
                System.out.println("               4. Edit a doctor detail                          ");
                System.out.println("               5. Exit                                          ");
                System.out.println("----------------------------------------------------------------");
                System.out.print("                     Enter Your Option                            :");

                int input2 = Integer.parseInt(reader.readLine().trim());
                System.out.println("----------------------------------------------------------------");

                if (input2 == 1) {
                    try {
                        System.out.println("             If you want Exit Please enter \"E\"                  ");
                        System.out.println("****************************************************************");
                        System.out.println("                      Delete a Doctor                           ");
                        System.out.println("****************************************************************");
                        int doctorId = Integer.parseInt(enterYourDetails(" Enter the ID Number of the doctor                              :",reader));
                        boolean isDoctorDelete =deleteDoctor(doctorList[findTheDoctor(doctorId)]);
                        System.out.println(isDoctorDelete? """      
                        ----------------------------------------------------------------
                                                 Deleted Successfully
                        ----------------------------------------------------------------""": """
                                ----------------------------------------------------------------
                                                   No Doctor from entered ID Number
                                ----------------------------------------------------------------""");

                    } catch (InputMismatchException ignored) {}
                }
                else if (input2 == 2&& Doctor.getAvailableNumOfDoctors() !=10) addNewDoctor(reader);
                else if (input2==3) printTheListOfDoctors();
                else if (input2==4) editDoctorDetails(reader);
                else if (input2 == 5) managerExpression = false;
                else wrongInput();
            }catch (NumberFormatException e){wrongInput();}
        }
        saveInDoctorFile();

    }

    @Override
    public boolean deleteDoctor(Doctor doctor) {
        int i=0;
        for (int j = 0; j < 10; j++) {
            if (doctorList[j]!=null && doctor.getID()==doctorList[j].getID()) {
                doctorList[j] = null;
                Doctor.setAvailableNumOfDoctors();
                System.out.println("---------------------------------------------------------------");
                System.out.println("                       Found the doctor                        ");
                System.out.println("---------------------------------------------------------------");

                printingTheDoctorDetail(doctor);
                i=1;
            }
        }
        return i == 1;
    }

    @Override
    public void addNewDoctor(BufferedReader reader) throws IOException {

        System.out.println("             If you want Exit Please enter \"E\"                  ");

        int k=0;
        try {
            System.out.println("****************************************************************");
            System.out.println("                      Add a Doctor                             ");
            System.out.println("****************************************************************");

            String[] fullName = Person.setFullName(reader);
            String firstName = fullName[0];
            String surName = fullName[1];

            //Check firstname and surname already exists.
            if (isDoctorObjectExist2(firstName,surName) ==8){
                System.err.println("----------------------------------------------------------------");
                System.err.println("                      Doctor already Exist                      ");
                System.err.println("----------------------------------------------------------------");
            }
            else {
                for (int i = 0; i < 10; i++) {
                    Doctor doctor = doctorList[i];
                    if (doctor==null){
                        String[] details= Person.setDetails(reader);
                        String DOB = details[1];
                        doctor = new Doctor(firstName, surName, DOB, i);
                        k++;
                        doctor.setMobileNumber(details[0]);
                        doctor.setAddress(details[2]);
                        setDoctorSpecializationNumber(doctor,reader);
                        while (true){
                            try {
                                String medicalLicenceNumber = readingNull(enterYourDetails("                    Enter medical license number                :", reader));
                                if (medicalLicenceNumber==null) throw new NullPointerException();
                                doctor.setMedicalLicenceNumber(medicalLicenceNumber);
                                break;
                            } catch (NullPointerException e) {
                                wrongInput();
                            }
                        }
                        doctorList[i] = doctor;
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("                       Added Successfully                       ");
                        System.out.println("----------------------------------------------------------------");
                        printingTheDoctorDetail(doctor);
                        break;
                    }
                }

            }
        } catch (InputMismatchException e) {
            if (k>=1) Doctor.setAvailableNumOfDoctors();
            System.out.println("---------------------------------------------------------------");
        }

    }

    @Override
    public void printTheListOfDoctors() {
        System.out.println("****************************************************************");
        System.out.println("                   Print Doctor's Details                      ");
        System.out.println("****************************************************************\n");
        for (int i = 0; i < 10; i++) {
            Doctor doctor = doctorList[i];
            if (doctor!=null){
                printingTheDoctorDetail(doctor);
            }
        }
    }

    public static void editDoctorDetails(BufferedReader reader) {
        boolean editDoctor = true;
        while (editDoctor){
            try {
                int doctorID = Integer.parseInt(enterYourDetails("               Enter the ID number of the doctor               :",reader));
                int doctorIndex = findTheDoctor(doctorID);
                if (doctorIndex==10)
                    System.out.println("""
                            ----------------------------------------------------------------
                                             No Doctor from entered ID Number
                            ----------------------------------------------------------------""");
                else {
                    Doctor doctor = doctorList[doctorIndex];
                    printingTheDoctorDetail(doctorList[doctorIndex]);
                    System.out.println(  "                Enter the field number you want edit            ");
                    System.out.println(  "----------------------------------------------------------------");
                    System.out.print(
                            """
                                       1. Medical licence number                        
                                       2. Name                                         
                                       3. DOB                                          
                                       4. Mobile Number                                
                                       5. Address                                      
                                       6. Specialisation Number                       
                                       7. Exit                                        
                                    """);
                    System.out.println(  "----------------------------------------------------------------");
                    System.out.print  ("                       Field Number                              :");

                    switch (Integer.parseInt(reader.readLine().trim())){
                        case 1 -> {
                            while (true){
                                String medicalLicenceNumber = readingNull(enterYourDetails("                  Enter medical license number                :", reader));
                                if (medicalLicenceNumber==null)
                                    wrongInput();
                                else {
                                    doctor.setMedicalLicenceNumber(medicalLicenceNumber);
                                    break;
                                }
                            }
                        }case 2 ->{
                            String[] fullName =Person.setFullName(reader);
                            doctor.setName(fullName[0]);
                            doctor.setSurname(fullName[1]);
                        }
                        case 3 ->{
                            while (true){
                                try {
                                    String DOB = enterYourDetails("             Enter Date Of Birth (yyyy-mm-dd)               :", reader);
                                    String[] splinted = DOB.split("-");

                                    if (splinted.length!=3 || splinted[0].length()!=4) throw new DateTimeParseException("Format wrong",DOB,3);
                                    LocalDate.parse(DOB);
                                    break;
                                } catch (DateTimeParseException e) {
                                    System.out.println("---------------------------------------------------------------");
                                    System.out.println("          Date of birth should be YYYY-MM-DD pattern           ");
                                    System.out.println("---------------------------------------------------------------");
                                }
                            }
                        }
                        case 4 ->{
                            while (true){
                                try {

                                    String mobileNumber = enterYourDetails("                       Enter your mobile number                :", reader);
                                    if (mobileNumber.length()!=10 || !mobileNumber.matches("[0-9]+")) throw new NumberFormatException();
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("\n");
                                    System.out.println("---------------------------------------------------------------");
                                    System.out.println("   The mobile phone number should contain 10 digits.           ");
                                    System.out.println("---------------------------------------------------------------");

                                }
                            }
                        }
                        case 5 -> doctor.setAddress(readingNull(enterYourDetails("                       Enter your address                       :", reader)));
                        case 6 -> setDoctorSpecializationNumber(doctor, reader);
                        case 7 ->{
                            editDoctor=false;
                            System.out.println("""
                                    ----------------------------------------------------------------
                                                                Exit
                                    ----------------------------------------------------------------""");
                        }
                    }

                }
                System.out.println("""
                                    ----------------------------------------------------------------
                                                            Edited Details
                                    ----------------------------------------------------------------""");
                printingTheDoctorDetail(doctorList[doctorIndex]);
                break;
            } catch (NumberFormatException|InputMismatchException | IOException ignored) {
                wrongInput();
            }
        }
    }

    @Override
    public void saveInDoctorFile() throws IOException {
        FileWriter writer = new FileWriter("DoctorDetails.txt");
        BufferedWriter bufferedWriter  = new BufferedWriter(writer);


        for (int i = 0; i < 10; i++) {
            Doctor doctor = doctorList[i];
            if (doctor!=null){
                String write = "Medical licence number: "+doctor.getMedicalLicenceNumber()+"\n"+
                        "Name                  : "+doctor.getName()+" , "+doctor.getSurname()+"\n"+
                        "Mobile Number         : "+doctor.getMobileNumber()+"\n"+
                        "Date Of Birth         : "+doctor.getDateOfBirth()+"\n"+
                        "Address               : "+doctor.getAddress()+"\n"+
                        "Specialisation Number : "+doctor.getSpecialisation().getNumberOfSpecialisation()+"\n\n\n";
                bufferedWriter.write(write);

            }
        }
        bufferedWriter.write("stop");
        bufferedWriter.close();
    }

    @Override
    public Doctor[] getTheListOfDoctors() {
        return doctorList;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private static int findTheDoctor(int ID){
        for (int i = 0; i < 10; i++) {
            Doctor doctor = doctorList[i];
            if (doctor!=null){
                if (doctor.getID()==ID) return i;
            }
        }
        return 10;
    }
    public static int isDoctorObjectExist2(String firstName, String sureName){
        for (int i = 0; i <4 ; i++) {
            Doctor doctor = doctorList[i];
            if (doctor==null) continue;
            if (doctor.getName().equalsIgnoreCase(firstName) &&
                    doctor.getSurname().equalsIgnoreCase(sureName)) return 8;
        }
        return 10;
    }




    @Override
    public String toString() {
        return "WestminsterSkinConsultationManager{" +
                "name='" + super.getName() + '\'' +
                ", surname='" + super.getSurname() + '\'' +
                ", dateOfBirth=" + super.getDateOfBirth() +
                ", mobileNumber='" + super.getMobileNumber() + '\'' +
                ", address='" + super.getAddress() + '\'' +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }



}
