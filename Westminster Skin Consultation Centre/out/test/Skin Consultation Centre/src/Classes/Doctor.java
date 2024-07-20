package Classes;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static Classes.ManagerOptions.*;

public class Doctor extends Person{

    private int ID;
    private String medicalLicenceNumber;
    private Specialisation specialisation;
    private static int numOfDoctors;

    public Doctor(String name, String surname, String dateOfBirth,int ID) {
        super(name, surname, dateOfBirth);
        this.ID = ID+1;
        numOfDoctors++;
    }

    public Doctor(String name, String surname, String dateOfBirth, String medicalLicenceNumber, int specialisationNum) {
        super(name, surname, dateOfBirth);
        this.medicalLicenceNumber = medicalLicenceNumber;
        setSpecialisation(specialisationNum);
        numOfDoctors++;
    }

    public String getMedicalLicenceNumber() {
        return medicalLicenceNumber;
    }

    public void setMedicalLicenceNumber(String medicalLicenceNumber) {
        this.medicalLicenceNumber = medicalLicenceNumber;
    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(int specialisationNum) {
        this.specialisation = Specialisation.Acanthosis_nigricans.getSpecialisationOfNumber(specialisationNum);
    }

    public static int getAvailableNumOfDoctors() {
        return numOfDoctors;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public static void setAvailableNumOfDoctors() {
        numOfDoctors--;
    }


    public static void printingTheDoctorDetail(Doctor doctor){
        print("|   ID                    : "+doctor.getID()+"                                  |\n"+
                "|   Medical licence number: "+doctor.getMedicalLicenceNumber()+"       |\n"+
                "|   Name                  : "+doctor.getName()+" , "+doctor.getSurname()+"       |\n"+
                "|   Mobile Number    : "+doctor.getMobileNumber()+"   || "+"DOB    : "+doctor.getDateOfBirth()+"       |\n"+
                "|   Address          : "+doctor.getAddress()+"       |\n"+
                "|   Specialisation Number : "+doctor.getSpecialisation().toString()+"       |\n\n");
    }

    static void setDoctorSpecializationNumber(Doctor doctor, BufferedReader reader){
        print("|          Enter doctors specialization belongs to the list    |");
        for (Specialisation s : Specialisation.values()){
            System.out.println("|                 "+s.getNumberOfSpecialisation()+" - "+ s +"                  |");
        }

        int specializationNumber;
        while (true){
            try {
                specializationNumber = Integer.parseInt(enterYourDetails("|                  Enter doctor's specialization               |:",reader));
                if (specializationNumber<1 || 19<specializationNumber) throw new NumberFormatException();
                doctor.setSpecialisation(specializationNumber);
                break;
            } catch (NumberFormatException | IOException Ignore){
                print("|                    Integer format expected                   |");
                inputMismatch();
            }
        }
    }

    public static Doctor findDoctorObjectByFullName(String fullName){
        for (Doctor doctor: SkinConsultationManager.doctorList ) {
            String value = doctor.getName()+" "+doctor.getSurname();
            if (fullName.trim().equals(value)) return doctor;
        }
        return null;
    }

    public static Doctor randomlySelectAnotherDoctorSpecializedIfUnavailable(Doctor doctor, Date date, int hours, Component component) throws ParseException {
        if ( Consultation.checkDoctorAvailable(doctor,date,hours)==null) return doctor;
        else {
            for (Doctor d : SkinConsultationManager.doctorList) {
                if (d==null) continue;
                if (d != doctor && d.getSpecialisation() == doctor.getSpecialisation()) {
                    Consultation c = Consultation.checkDoctorAvailable(d, date, hours);
                    if (c == null) {
                        JOptionPane.showMessageDialog(component, "The doctor is not available at this time.", "Info", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showConfirmDialog(component, "Added randomly another doctor that specializes category. " +
                                "If you want to change or delete a reservation now can do it.", "Warning", JOptionPane.OK_CANCEL_OPTION);
                        return d;
                    }
                }
            }
        }
        return null;
    }

    public static Doctor checkDoctorIsAvailable(String medicalLicenceNumber, int specializationNum, Date date, int hours) throws ParseException {
        Doctor[] specializedDoctors = new Doctor[10];
        int i=0;
        for (Doctor d : SkinConsultationManager.doctorList) {
            if (d==null) continue;
            if (d.getSpecialisation().getNumberOfSpecialisation() == specializationNum){
                specializedDoctors[i]=d;
                i++;
                if (medicalLicenceNumber.trim().equals(d.getMedicalLicenceNumber())) return d;
            }
        }
        for (Doctor d : specializedDoctors) {
            if (d==null) continue;
            if (Consultation.checkDoctorAvailable(d, date, hours) == null) return d;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString()+"Doctor{" +
                "medicalLicenceNumber='" + medicalLicenceNumber + '\'' +
                ", specialisation=" + specialisation +
                '}';
    }

}
