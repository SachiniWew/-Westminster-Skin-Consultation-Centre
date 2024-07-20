package Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public interface SkinConsultationManager {
    Doctor[] doctorList = new Doctor[10];


    void addNewDoctor(BufferedReader reader) throws IOException;
    boolean deleteDoctor(Doctor doctor);
    void printTheListOfDoctors();
    Doctor[] getTheListOfDoctors();
    void saveInFile() throws IOException;

    static void readFile() throws IOException{
        FileReader reader = new FileReader("DoctorDetails.txt");
        BufferedReader buffer = new BufferedReader(reader);

        String name = "", surname = "", address = "",
                medicalLicenceNumber = "", mobileNumber= "", dateOfBirth = "";
        int specialisation = 0;

        int i=0;

        String line;
        while(true){
            i++;
            line=buffer.readLine().trim();
            if (line.equals("stop")) break;
            switch (i % 7) {
                case 1 -> medicalLicenceNumber = line.split(":")[1].trim();
                case 2 -> {
                    String fullName = line.split(":")[1];
                    name = fullName.split(",")[0].trim();
                    surname = fullName.split(",")[1].trim();
                }
                case 3 -> {
                    String[] splited = line.split("\\|\\|");
                    mobileNumber = splited[0].split(":")[1].trim();
                    dateOfBirth = splited[1].split(":")[1].trim();
                }
                case 4 -> address = line.split(":")[1].trim();
                case 5 -> specialisation = Integer.parseInt(line.split(":")[1].trim());
                case 0 -> {
                    Doctor doctor = new Doctor(name, surname, dateOfBirth, medicalLicenceNumber, specialisation);
                    doctor.setAddress(address);
                    doctor.setMobileNumber(mobileNumber);
                    int added = 0;
                    for (int j = 0; j < 10; j++) {
                        if (doctorList[j]==null) {
                            doctorList[j] = doctor;
                            doctor.setID(j+1);
                            added = 1;
                            break;
                        }
                    }
                    if (added == 0) System.out.println("The queue is full of doctors.");
                }
            }

        }
        buffer.close();
    }

}
