package Classes;

import java.io.*;
import java.sql.SQLOutput;
import java.util.InputMismatchException;

public class ManagerOptions {

    private static final WestminsterSkinConsultationManager[] managers = new WestminsterSkinConsultationManager[4];
    private static int managerVacancies;
    public ManagerOptions(String username, String password) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        readManagerFile();
        boolean mainMenuExpression=isManagerObjectExist(username,password)<4;
        while (mainMenuExpression) {

            System.out.println("****************************************************************");
            System.out.println("             WESTMINSTER SKIN CONSULTATION CENTRE               ");
            System.out.println("****************************************************************");
            System.out.println("****************************************************************");
            System.out.println("                           Main Menu                            ");
            System.out.println("****************************************************************");
            System.out.println("                 1. To Edit Manager's Details                   ");
            System.out.println("                 2. To Edit Doctor's Details                    ");
            System.out.println("                 3. Exit                                        ");
            System.out.println("----------------------------------------------------------------");
            System.out.print  ("                      Enter Your Option                        :");

            try {
                int input = Integer.parseInt(reader.readLine().trim());
                System.out.println("----------------------------------------------------------------\n");
                if (input==1){
                    handlingManagers(reader);
                } else if (input == 2) {
                    WestminsterSkinConsultationManager manager =
                            managers[isManagerObjectExist(username,password)];
                    manager.handlingDoctors(reader);
                }else if (input == 3) mainMenuExpression =false;
                else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                wrongInput();
            }
        }
    }

    private void handlingManagers(BufferedReader reader) throws IOException {

        boolean managerExpression = true;
        while (managerExpression) {
            try {

                System.out.println("****************************************************************");
                System.out.println("                         Manager Menu                           ");
                System.out.println("****************************************************************");
                System.out.println("         Number of Available Manager Vacancies   : "+managerVacancies);
                System.out.println("----------------------------------------------------------------");
                System.out.println("               1. Delete a Manager                              ");
                if (managerVacancies != 0) {
                    System.out.println("               2. Add a Manager                  ");
                }
                System.out.println("               3. Print Manager's Details                 ");
                System.out.println("               4. Exit                                          ");
                System.out.println("----------------------------------------------------------------");
                System.out.print("                      Enter Your Option                        :");


                int input2 = Integer.parseInt(reader.readLine().trim());
                System.out.println("----------------------------------------------------------------\n");

                if (input2 == 1)deletingAManager(reader);
                else if (input2 == 2&& managerVacancies !=0) addingAManager(reader);
                else if (input2==3) printManagerDetails();
                else if (input2 == 4) managerExpression = false;
                else wrongInput();
            } catch (NumberFormatException e) {
                wrongInput();
            }
        }
        saveInManagerFile();
    }

    private void deletingAManager(BufferedReader reader) throws IOException {
        System.out.println("             If you want Exit Please enter \"E\"                  ");

        try {

            System.out.println("****************************************************************");
            System.out.println("                      Delete a Manager                          ");
            System.out.println("****************************************************************");

            String username = enterYourDetails("                       Enter UserName                           :",reader);

            String password = enterYourDetails("                       Enter Password                           :",reader);

            int i = isManagerObjectExist(username,password);
            while (i==8){
                System.out.println("----------------------------------------------------------------");
                System.out.println("              Wrong Password. Re-enter the password.             ");
                System.out.println("----------------------------------------------------------------");
                System.out.println("             If you want Exit Please enter \"E\"                  ");
                password = enterYourDetails("                        Enter Password                          :",reader);
                i = isManagerObjectExist(username,password);
            }

            if (i<4){
                managers[i]=null;
                System.out.println("----------------------------------------------------------------");
                System.out.println("                      Deleted Successfully                      ");
                System.out.println("----------------------------------------------------------------");
                managerVacancies++;
            }else {
                System.out.println("----------------------------------------------------------------");
                System.out.println("                    Manager doesn't Exist                       ");
                System.out.println("----------------------------------------------------------------");
            }

        } catch (InputMismatchException Ignore) {
            System.out.println("****************************************************************");
        }
    }
    private void addingAManager(BufferedReader reader) throws IOException {
        System.out.println("             If you want Exit Please enter \"E\"                  ");

        try {
            System.out.println("****************************************************************");
            System.out.println("                      Add a Manager                             ");
            System.out.println("****************************************************************");

            String[] fullName = Person.setFullName(reader);
            String firstName = fullName[0];
            String surName = fullName[1];
            String username = firstName.replace(" ","")+surName.replace(" ","");
            String password = null;

            //Check firstname and surname already exists.
            //If exists don't add a manager
            if (isManagerObjectExist2(firstName,surName) ==8){
                System.err.println("----------------------------------------------------------------");
                System.err.println("                     Manager already Exist.                     ");
                System.err.println("----------------------------------------------------------------");
            }
            else {
                //Confirm password by entering again the password
                boolean passwordNotEqual = true;
                while (passwordNotEqual) {

                    password = enterYourDetails("                    Enter Password                              :", reader);
                    String confirmPassword = enterYourDetails("                    Confirm Password                            :", reader);
                    if (password.equals(confirmPassword)) passwordNotEqual=false;
                    else wrongInput();
                }

                //if only username already exists rename it and check that username and password exists.
                int j = isManagerObjectExist(username,password);
                while (j ==8 || j <4){
                    System.err.println("----------------------------------------------------------------");
                    System.err.println("                     UserName already exists.                   ");
                    System.err.println("----------------------------------------------------------------");

                    username = enterYourDetails("                   Renter your UserName                      :", reader);
                    j = isManagerObjectExist(username,password);
                }

                for (int i = 0; i < 4; i++) {
                    WestminsterSkinConsultationManager manager = managers[i];
                    if (manager==null){
                        manager = new WestminsterSkinConsultationManager(firstName, surName, password, username);

                        String[] details= Person.setDetails(reader);
                        manager.setDateOfBirth(details[1]);
                        manager.setMobileNumber(details[0]);
                        manager.setAddress(details[2]);
                        managers[i] = manager;
                        managerVacancies--;
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("                       Added Successfully                       ");
                        System.out.println("----------------------------------------------------------------");

                        System.out.println(  "           Name             : "+manager.getName()+" "+manager.getSurname()+"\n" +
                                "           Mobile Number    : "+manager.getMobileNumber()+"\n" +
                                "           Date Of Birth    : "+manager.getDateOfBirth()+"\n" +
                                "           Address          : "+manager.getAddress()+"\n" +
                                "           Password         : "+String.format("%0"+ (manager.getPassword().length())+"d",0).
                                replace("0","*")+"\n" +
                                "           Username         : "+manager.getUsername()+"\n");
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("          Please Remember the USERNAME and PASSWORD             ");
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }
                }

            }

        } catch (InputMismatchException e) {
            System.out.println("****************************************************************");
        }
    }
    private static void printManagerDetails() {
        System.out.println("****************************************************************");
        System.out.println("                   Print Manager's Details                      ");
        System.out.println("****************************************************************\n");
        for (int i = 0; i < 4; i++) {
            WestminsterSkinConsultationManager manager = managers[i];
            if (manager!=null){
                System.out.println(  "          Name             : "+manager.getName()+" "+manager.getSurname()+"\n" +
                        "           Mobile Number    : "+manager.getMobileNumber()+"\n" +
                        "           Date Of Birth    : "+manager.getDateOfBirth()+"\n" +
                        "           Address          : "+manager.getAddress()+"\n" +
                        "           Password         : "+String.format("%0"+ (manager.getPassword().length())+"d",0).
                        replace("0","*")+"\n" +
                        "           Username         : "+manager.getUsername()+"\n");
                System.out.println("----------------------------------------------------------------");
            }
        }
    }

    public static void saveInManagerFile() throws IOException {
        FileWriter writer = new FileWriter("ManagerDetails.txt");
        BufferedWriter bufferedWriter  = new BufferedWriter(writer);


        for (int i = 0; i < 4; i++) {
            WestminsterSkinConsultationManager manager = managers[i];
            if (manager!=null){
                String write =
                        "Name             : "+manager.getName()+" , "+manager.getSurname()+"\n"+
                        "Mobile Number    : "+manager.getMobileNumber()+"\n"+
                         "Date Of Birth   : "+manager.getDateOfBirth()+"\n"+
                        "Address          : "+manager.getAddress()+"\n"+
                        "Password         : "+manager.getPassword()+"\n"+
                        "Username         : "+manager.getUsername()+"\n\n\n";
                bufferedWriter.write(write);

            }
        }
        bufferedWriter.write("stop");
        bufferedWriter.close();
    }

    public static void readManagerFile() throws IOException {
        FileReader reader = new FileReader("ManagerDetails.txt");
        BufferedReader buffer = new BufferedReader(reader);

        String name = "", surname = "", address = "", mobileNumber= "", dateOfBirth = "",
        password = "", username ="";

        int i=0,j=0;

        String line;
        while(true){
            i++;
            line=buffer.readLine().trim();
            if (line.equals("stop")) break;
            switch (i % 8) {
                case 1 -> {
                    String fullName = line.split(":")[1];
                    name = readingNull(fullName.split(",")[0].trim());
                    surname = readingNull(fullName.split(",")[1].trim());
                }

                case 2 -> mobileNumber = readingNull(line.split(":")[1].trim());
                case 3 -> dateOfBirth = readingNull(line.split(":")[1].trim());
                case 4 -> address = readingNull(line.split(":")[1].trim());
                case 5 -> password = readingNull(line.split(":")[1].trim());
                case 6 -> username = readingNull(line.split(":")[1].trim());
                case 0 -> {
                    WestminsterSkinConsultationManager manager =
                            new WestminsterSkinConsultationManager(name, surname, dateOfBirth, mobileNumber, password, username);
                    manager.setAddress(address);
                    managers[j] = manager;
                    j++;
                }
            }

        }
        buffer.close();
        managerVacancies = 4-j;
    }

    public static int isManagerObjectExist(String username, String password){
        for (int i = 0; i <4 ; i++) {
            WestminsterSkinConsultationManager manager = managers[i];
            if (manager==null) continue;
            if (manager.getUsername().equals(username) && manager.getPassword().equals(password)) return i;
            else if (manager.getUsername().equals(username)) return 8;
        }
        return 10;
    }

    public int isManagerObjectExist2(String firstName, String sureName){
        for (int i = 0; i <4 ; i++) {
            WestminsterSkinConsultationManager manager = managers[i];
            if (manager==null) continue;
            if (manager.getName().equalsIgnoreCase(firstName) &&
                    manager.getSurname().equalsIgnoreCase(sureName)) return 8;
        }
        return 10;
    }
    public static String enterYourDetails(String print, BufferedReader reader) throws IOException {
        System.out.print(print);
        String input = reader.readLine().trim();

        if (input.equalsIgnoreCase("E")) throw new InputMismatchException();

        return input;
    }

    public static String readingNull(String s){
        if (s.equals("null")) return null;
        else return s;
    }

    public static void wrongInput(){

        System.out.println("--------------------------------------------------------------");
        System.out.println("                Wrong Input. Please try again.                ");
        System.out.println("--------------------------------------------------------------");
    }
}
