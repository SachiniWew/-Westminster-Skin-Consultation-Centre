package Classes;

import java.util.Date;
import java.util.Objects;

public class Patient extends Person{
    private int patientID;
    private static int numOfPatient;

    protected Patient(String name, String surname, String dateOfBirth) {
        super(name, surname, dateOfBirth);
        numOfPatient++;
    }
    protected Patient(String name, String surname, Date dateOfBirth) {
        super(name, surname, dateOfBirth);
        numOfPatient++;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public static int getNumOfPatient() {
        return numOfPatient;
    }

    public static Patient isCustomer(String firstName, String lastName){
        for (Consultation c: Consultation.getConsultations()){
            if (Objects.equals(c.getPatient().getName(), firstName) &&
                    Objects.equals(c.getPatient().getSurname(), lastName)) {
                System.out.println("Previous Customer");
                return c.getPatient();
            }
        }
        return null;
    }
}
