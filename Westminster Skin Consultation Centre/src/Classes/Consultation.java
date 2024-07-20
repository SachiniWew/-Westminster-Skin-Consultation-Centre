package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Consultation {

    private int appointmentID;
    private Date date;
    private int hours;
    private double cost;
    private String notes;
    private Doctor doctor;
    private final Patient patient;
    private static int appointmentCount;
    private static final ArrayList<Consultation> consultations = new ArrayList<>();

    public Consultation(Date date, int hours, Doctor doctor, Patient patient, String notes) {
        if (hours==1){
            cost=hours*15;
        }else{
            cost=15+(hours-1)*25;
        }
        this.date = date;
        this.doctor = doctor;
        this.hours = hours;
        this.patient = patient;
        setNotes(notes);
        setAppointmentID();
        consultations.add(this);

        System.out.println("Appointment ID : "+appointmentID);
        System.out.println("Patient        :"+patient);
        System.out.println("Doctor         :"+doctor);
        System.out.println("Cost           :"+cost);
        System.out.println("Hours          : "+hours);
        //Encrypting note
        System.out.print("Encrypted note : ");
        char[] chars=notes.toCharArray();
        for (char c:chars){
            c+=1;
            System.out.print(c);
        }
        System.out.println("\n__________________________________");
    }

    public Consultation(Date date, int hours, Doctor doctor, String firstName, String lastName, String address, Date DOB, String notes) {
        if (hours==1){
            cost=hours*15;
        }else{
            cost=15+(hours-1)*25;
        }

        this.date = date;
        this.doctor = doctor;
        this.patient = new Patient(firstName,lastName,DOB);
        this.patient.setAddress(address);
        this.hours = hours;

        setNotes(notes);
        setAppointmentID();
        consultations.add(this);
        System.out.println("""
        ---------------------------
           Submitted Appointments
        ---------------------------""");
        System.out.println("Appointment ID : "+appointmentID);
        System.out.println("Patient        :"+patient);
        System.out.println("Doctor         :"+doctor);
        System.out.println("Cost           :"+cost);
        System.out.println("Hours          : "+hours);
        //Encrypting note
        System.out.print("Encrypted note : ");
        char[] chars=notes.toCharArray();
        for (char c:chars){
            c+=1;
            System.out.print(c);
        }
        System.out.println("\n__________________________________");
    }
    public static Consultation findConsultationByAppointmentID(int appointmentID) {
        if (consultations.size()<appointmentID) return null;
          else return consultations.get(appointmentID);
    }
    public Date getEndTime() throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        Date myDate = parser.parse(parser.format(this.date));
        Calendar cal =Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.HOUR_OF_DAY,this.hours);

        System.out.println("Appointment Start Time : "+this.date);
        System.out.println("Appointment End Time   : "+cal.getTime());

        return cal.getTime();
    }
    public static Date getEndTime(Date date, int hours) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("dd:HH:mm");
        Date myDate = parser.parse(parser.format(date));
        Calendar cal =Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.HOUR_OF_DAY,hours);
        return cal.getTime();
    }
    public static Consultation checkDoctorAvailable(Doctor doctor,Date date ,int hours) throws ParseException {
        Date endTime  = getEndTime(date,hours);

        for (Consultation c:consultations){
            if (doctor.equals(c.getDoctor())){
                if (date.after(c.getDate())){
                    System.out.println("Your appointment is after patient "+c.getPatient().getName());
                    if (date.before(c.getEndTime())) {
                        System.out.println("Patient "+c.getPatient().getName()+"'s appointment isn't end before this appointment start.");

                        return c;
                    }
                } else if (date.before(c.getDate())) {
                    System.out.println("Patient"+c.getPatient().getName()+"'s time is after this appointment.");
                    if (endTime.after(c.getDate())) {
                        System.out.println("Patient"+ c.getEndTime()+c.getPatient().getName()+"'s time is starting before this appointment end time.");
                        return c;
                    }
                }else return c;
            }
        }
        return null;
    }
    public static Consultation findConsultationByMobile(String mobileNumber){
        for (Consultation c :
                consultations) {
            if (mobileNumber.equals(c.getPatient().getMobileNumber())) return c;
        }
        return null;
    }
    public static boolean checkMobileNumber(JTextField field, Component class1, KeyEvent e){
        try {
            Integer.parseInt(field.getText().trim());
            if (e.getKeyChar()!= KeyEvent.VK_BACK_SPACE) {
                String value = field.getText();
                if (value.length() <= 10) {
                    if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')) {
                        JOptionPane.showConfirmDialog(class1, "Enter only numeric digits(0-9)", "Warning", JOptionPane.OK_CANCEL_OPTION);
                        if (e.getSource() == field) field.setText("");
                    } else {
                        return value.length() == 9;
                    }
                } else {
                    JOptionPane.showConfirmDialog(class1, "Cannot exceed 10 digits", "Warning", JOptionPane.OK_CANCEL_OPTION);
                    StringBuilder s = new StringBuilder();
                    String[] split = value.split("");
                    for (int i = 0; i < 10; i++) {
                        s.append(split[i]);
                    }
                    field.setText(s.toString());
                }
            }
        }catch (NumberFormatException a){
            System.out.println(7890);
            JOptionPane.showConfirmDialog(class1, "Enter only numeric digits(0-9)", "Warning", JOptionPane.OK_CANCEL_OPTION);
            field.setText("");
        }
        return false;
    }

    public static boolean checkConsultationByAppointmentID(JTextField field, Component class1){
        try {
            int appointmentIDTemp = Integer.parseInt(field.getText().trim());
            if (appointmentIDTemp>consultations.size()) {
                JOptionPane.showConfirmDialog(class1, "There isn't such a ID", "Warning", JOptionPane.OK_CANCEL_OPTION);
                return false;
            }else return true;
        }catch (NumberFormatException ignored){
            JOptionPane.showConfirmDialog(class1, "Enter only numeric digits(0-9)", "Warning", JOptionPane.OK_CANCEL_OPTION);
            field.setText("");
        }
        return false;
    }

    public Date getDate() {
        return date;
    }

    public static void deleteConsultation(Consultation c){
        for (Consultation d :
                consultations) {
            if (c.getAppointmentID()==d.appointmentID &&
                    Objects.equals(c.getPatient().getMobileNumber(), d.getPatient().getMobileNumber())){
                consultations.remove(c);
                return;
            }
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        if (notes.equals("")) this.notes = null;
        else this.notes = notes;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public static ArrayList<Consultation> getConsultations(){
        return consultations;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID() {
        this.appointmentID = appointmentCount++;
    }
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setHours(int hours) {
        this.cost = (25*hours);
        this.hours = hours;
    }

    public int getHours() {

        return hours;
    }

    public static void fileWrite() throws IOException {
        FileWriter writer = new FileWriter("ConsultationsDetails.txt");
        BufferedWriter bufferedWriter  = new BufferedWriter(writer);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm");


        for (Consultation c : consultations) {
            Patient p = c.getPatient();
            String write =
                    "Name             : " + p.getName() + " , " + p.getSurname() + "\n" +
                            "Mobile Number    : " + p.getMobileNumber() + "\n" +
                            "Date Of Birth    : " + p.getDateOfBirth() + "\n" +
                            "Address          : " + p.getAddress() + "\n" +
                            "Note             : " + c.getNotes() + "\n" +
                            "Date             : " + formatter.format(c.getDate()) + "\n" +
                            "Hours            : " + c.getHours() + "\n" +
                            "Doctor Medical License  : " + c.getDoctor().getMedicalLicenceNumber() + "\n" +
                            "Specialisation Number   : " + c.getDoctor().getSpecialisation().getNumberOfSpecialisation() + "\n" +
                            "Appointment ID          : " + c.getAppointmentID() + "\n\n\n";
            bufferedWriter.write(write);
        }
        bufferedWriter.write("stop");
        bufferedWriter.close();
    }
    public static void readFile(Component class1) throws IOException, ParseException {
        FileReader reader = new FileReader("ConsultationsDetails.txt");
        BufferedReader buffer = new BufferedReader(reader);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        String name = "", surname = "", address = "", mobileNumber= "",
                note = "", medicalLicense = "";
        Date date = null, dateOfBirth = null;
        int hours =0, specialisationNum =0, appointmentID =0;

        int i=0;

        String line;
        while(true){
            i++;
            line=buffer.readLine().trim();
            if (line.equals("stop")) break;
            switch (i % 12) {
                case 1 -> {
                    String fullName = line.split(":")[1];
                    name = ManagerOptions.readingNull(fullName.split(",")[0].trim());
                    surname = ManagerOptions.readingNull(fullName.split(",")[1].trim());
                }

                case 2 -> mobileNumber = ManagerOptions.readingNull(line.split(":")[1].trim());
                case 3 -> dateOfBirth = formatter2.parse(ManagerOptions.readingNull(line.split(":")[1].trim()));
                case 4 -> address = ManagerOptions.readingNull(line.split(":")[1].trim());
                case 5 -> note = line.split(":")[1].trim();
                case 6 -> date = formatter.parse(ManagerOptions.readingNull(line.split(":")[1].trim()));
                case 7 -> {
                    try {
                        hours = Integer.parseInt(line.split(":")[1].trim());
                    }catch (NumberFormatException ignored){}
                }
                case 8 -> medicalLicense = ManagerOptions.readingNull(line.split(":")[1].trim());
                case 9 -> {
                    try {
                        specialisationNum = Integer.parseInt(line.split(":")[1].trim());
                    }catch (NumberFormatException ignored){}
                }
                case 10 -> appointmentID = Integer.parseInt(line.split(":")[1].trim());
                case 0 -> {
                    Doctor doctor = Doctor.checkDoctorIsAvailable(medicalLicense, specialisationNum,date,hours);
                    if (doctor!= null) {
                        if (name != null && surname != null && mobileNumber != null && dateOfBirth!=null
                                && medicalLicense != null && date != null && hours != 0 && specialisationNum != 0) {
                            Consultation c =
                                    new Consultation(date, hours, doctor, name, surname, address, dateOfBirth, note);
                            c.getPatient().setMobileNumber(mobileNumber.trim());
                            c.setAppointmentID(appointmentID);
                            System.out.println(c);
                        }
                    }
                    else {
                        String message = "Dr."+name+" no longer working in here.Sorry for the inconvenience";
                        JOptionPane.showMessageDialog(class1, message, "Warning", JOptionPane.WARNING_MESSAGE);


                    }
                }
            }

        }
        buffer.close();
    }

}
