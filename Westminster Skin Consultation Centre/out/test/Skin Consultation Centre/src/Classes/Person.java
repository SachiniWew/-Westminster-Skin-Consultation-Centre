package Classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Objects;

import static Classes.ManagerOptions.*;

public class Person {
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String mobileNumber;
    private String address;

    protected Person(String name, String surname, String dateOfBirth) {
        this.name = name;
        this.surname = surname;
        setDateOfBirth(dateOfBirth);
    }
    protected Person(String name, String surname, Date dateOfBirth) {
        this.name = name;
        this.surname = surname;
        setDateOfBirth(dateOfBirth);
    }

    protected Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }


    public String getAge(){
        LocalDate today = LocalDate.now();

        Period period = Period.between(this.dateOfBirth,today);
        return "Years : "+period.getYears()+" Months : "+period.getMonths()+" Days : "+period.getDays();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public Date getDateOfBirth(boolean in){

        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(this.dateOfBirth.atStartOfDay(defaultZoneId).toInstant());
    }

    public void setDateOfBirth(String dateOfBirth) {
        String[] splinted = dateOfBirth.split("-");

        if ((splinted.length!=3 || splinted[0].length()!=4) &&
                (Integer.parseInt(splinted[1])>13 && Integer.parseInt(splinted[1])<0) &&
                (Integer.parseInt(splinted[2])>32 && Integer.parseInt(splinted[2])<0)
        ) {
            throw new DateTimeParseException("Format wrong", dateOfBirth, 3);
        }
        this.dateOfBirth = LocalDate.parse(dateOfBirth);
    }
    public void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth =Instant.ofEpochMilli(dateOfBirth.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        if (mobileNumber.length()!=10 || !mobileNumber.matches("[0-9]+")) throw new NumberFormatException("Not a Phone Number!!!");
        else this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (Objects.equals(address, "")) this.address=null;
        else this.address = address;
    }

    public static String[] setFullName(BufferedReader reader) throws IOException {
        String firstNameTemp,surNameTemp;
        do {
            firstNameTemp = readingNull(enterYourDetails("|                      Enter your first name                   |", reader));
            surNameTemp = readingNull(enterYourDetails("|                      Enter your surname                      |", reader));
            if (firstNameTemp == null || surNameTemp == null) print("|                       Cant be null                           |");
        } while (firstNameTemp == null || surNameTemp == null);
        String firstName = firstNameTemp.substring(0,1).toUpperCase()+firstNameTemp.substring(1).toLowerCase();
        String surName = surNameTemp.substring(0,1).toUpperCase()+surNameTemp.substring(1).toLowerCase();
        return new String[]{firstName, surName};
    }

    public static String[] setDetails(BufferedReader reader)throws IOException{

        String mobileNumber,address,DOB;
        try {
            while (true){
                try {

                    mobileNumber = enterYourDetails("|                      Enter your mobile number                |", reader);
                    if (mobileNumber.length()!=10 || !mobileNumber.matches("[0-9]+")) throw new NumberFormatException();
                    break;
                } catch (NumberFormatException e) {
                    inputMismatch();
                    print("|      The mobile phone number should contain 10 digits.       |");
                }
            }


            address =readingNull(enterYourDetails("|                      Enter your address                      |:", reader));

            while (true){
                try {
                    DOB = enterYourDetails("|            Enter your Date Of Birth (EX: 2001-06-29)         |:", reader);
                    String[] splinted = DOB.split("-");

                    if (splinted.length!=3 || splinted[0].length()!=4) throw new DateTimeParseException("Format wrong",DOB,3);
                    LocalDate.parse(DOB);
                    break;
                } catch (DateTimeParseException e) {
                    inputMismatch();
                    print("|          Date of birth should be YYYY-MM-DD patters          |");
                }
            }

        }catch (InputMismatchException Ignore){throw new InputMismatchException();}

        return new String[]{mobileNumber,DOB,address};
    }
    public static String nameCapitalising(String name){
        String n = name;
        StringBuilder s = new StringBuilder();
        String[] split = name.split(" ");
        if (split.length>0 && !name.equals("")) {
            for (String value : split) {
                String temp = value.substring(0, 1).toUpperCase()+ value.substring(1) + " ";
                s.append(temp);
            }
            n = s.toString().trim();
        }
        return n;
    }
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}