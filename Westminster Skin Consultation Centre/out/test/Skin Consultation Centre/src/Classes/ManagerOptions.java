package Classes;

import java.io.*;
import java.util.InputMismatchException;

public class ManagerOptions {

    private static final WestminsterSkinConsultationManager[] managers = new WestminsterSkinConsultationManager[4];
    private static int managerVacancies;
    public ManagerOptions(String username, String password) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        readFile();
        System.out.println(7);
        boolean mainMenuExpression=isManagerObjectExist(username,password)<4;
        while (mainMenuExpression) {
            System.out.println("|--------------------------------------------------------------|");
            System.out.println("|                   SKIN CONSULTATION CENTRE                   |");
            System.out.println("|--------------------------------------------------------------|");
            System.out.println("|--------------------------------------------------------------|");
            System.out.println("|                      Select the option                       |");
            System.out.println("|              1. To change the data of managers               |");
            System.out.println("|              2. To change the data of doctors                |");
            System.out.println("|              3. Exit                                         |");
            System.out.println("|--------------------------------------------------------------|");
            System.out.print("|                           ANSWER                             |:");

            try {
                int input = Integer.parseInt(reader.readLine().trim());
                System.out.println("|--------------------------------------------------------------|");
                if (input==1){
                    handlingManagers(reader);
                } else if (input == 2) {
                    WestminsterSkinConsultationManager manager =
                            managers[isManagerObjectExist(username,password)];
                    manager.handlingDoctors(reader);
                }else if (input == 3) mainMenuExpression =false;
                else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                inputMismatch();
            }
        }

        saveInFile();



    }
    public static void saveInFile() throws IOException {
        FileWriter writer = new FileWriter("ManagerDetails.txt");
        BufferedWriter bufferedWriter  = new BufferedWriter(writer);


        for (int i = 0; i < 4; i++) {
            WestminsterSkinConsultationManager manager = managers[i];
            if (manager!=null){
                String write =
                        "Name             : "+manager.getName()+" , "+manager.getSurname()+"\n"+
                        "Mobile Number    : "+manager.getMobileNumber()+"   || "+"DOB    : "+manager.getDateOfBirth()+"\n"+
                        "Address          : "+manager.getAddress()+"\n"+
                        "Password         : "+manager.getPassword()+"\n"+
                        "Username         : "+manager.getUsername()+"\n\n\n";
                bufferedWriter.write(write);

            }
        }
        bufferedWriter.write("stop");
        bufferedWriter.close();
    }

    public static void readFile() throws IOException {
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
            switch (i % 7) {
                case 1 -> {
                    String fullName = line.split(":")[1];
                    name = readingNull(fullName.split(",")[0].trim());
                    surname = readingNull(fullName.split(",")[1].trim());
                }
                case 2 -> {
                    String[] splinted = line.split("\\|\\|");
                    mobileNumber = readingNull(splinted[0].split(":")[1].trim());
                    dateOfBirth = readingNull(splinted[1].split(":")[1].trim());
                }
                case 3 -> address = readingNull(line.split(":")[1].trim());
                case 4 -> password = readingNull(line.split(":")[1].trim());
                case 5 -> username = readingNull(line.split(":")[1].trim());
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

    private void handlingManagers(BufferedReader reader) throws IOException {

        boolean managerExpression = true;
        while (managerExpression) {
            try {

                print("|             "+managerVacancies+" Manager Vacancies are available here           |");
                System.out.println("|--------------------------------------------------------------|");
                System.out.println("|                      Select the option                       |");
                System.out.println("|--------------------------------------------------------------|");
                System.out.println("|              1. Delete a manager from queue                  |");
                if (managerVacancies != 0) {
                    System.out.println("|              2. Add a new manager's details                  |");
                }
                System.out.println("|              3. Print the all manager detail                 |");
                System.out.println("|              4. Exit                                         |");
                System.out.println("|--------------------------------------------------------------|");
                System.out.print("|                           ANSWER                             |:");

                int input2 = Integer.parseInt(reader.readLine().trim());

                if (input2 == 1)deletingAManager(reader);
                else if (input2 == 2&& managerVacancies !=0) addingAManager(reader);
                else if (input2==3) printManagerDetails();
                else if (input2 == 4) managerExpression = false;
                else inputMismatch();
            } catch (NumberFormatException e) {
                inputMismatch();
            }
        }
    }

    private void deletingAManager(BufferedReader reader) throws IOException {

        try {

            System.out.println("|--------------------------------------------------------------|");
            System.out.println("|                     Deleting a manager                       |:");

            String username = enterYourDetails("|                       Enter UserName                         |:",reader);

            String password = enterYourDetails("|                       Enter Password                         |:",reader);

            int i = isManagerObjectExist(username,password);
            while (i==8){
                print("|             Wrong Password. Re-enter the password.           |\n" +
                        "|             If you want Exit Please enter \"cancel\"         |");
                password = enterYourDetails("|                       Enter Password                         |:",reader);
                i = isManagerObjectExist(username,password);
            }

            if (i<4){
                managers[i]=null;
                print("|                    Deleted successfully                      |");
                managerVacancies++;
            }else print("|                      No such a person                        |");

        } catch (InputMismatchException Ignore) {
            print("|-------------------Canceled the operation---------------------|");}
    }

    private static void printManagerDetails() {
        for (int i = 0; i < 4; i++) {
            WestminsterSkinConsultationManager manager = managers[i];
            if (manager!=null){

                print(  "|      Name             : "+manager.getName()+" "+manager.getSurname()+"                  |\n" +
                        "|      Mobile Number    : "+manager.getMobileNumber()+"  || DOB    : "+manager.getDateOfBirth()+" |\n" +
                        "|      Address          : "+manager.getAddress()+"             |\n" +
                        "|      Password         : "+String.format("%0"+ (manager.getPassword().length())+"d",0).
                        replace("0","*")+"                              |\n" +
                        "|      Username         : "+manager.getUsername()+"                   |\n\n");
            }
        }
    }
    private void addingAManager(BufferedReader reader) throws IOException {
        print("|     If you want to cancel the function during operation      |\n|            Please type \"Cancel\" as the input               |");
        print("|            Enter \"null\" if field is not required           |");

        try {
            String[] fullName = Person.setFullName(reader);
            String firstName = fullName[0];
            String surName = fullName[1];
            String username = firstName.replace(" ","")+surName.replace(" ","");
            String password = null;

            System.out.println("|--------------------------------------------------------------|");


            //Check firstname and surname already exists.
            //If exists don't add a manager
            if (isManagerObjectExist2(firstName,surName) ==8){
                System.out.println("|                     User already exists.                     |");
            }
            else {
                //Confirm password by entering again the password
                boolean passwordNotEqual = true;
                while (passwordNotEqual) {
                    password = enterYourDetails("|                       Enter Password                         |:", reader);
                    String confirmPassword = enterYourDetails("|                       Confirm Password                       |:", reader);
                    if (password.equals(confirmPassword)) passwordNotEqual=false;
                    else inputMismatch();
                }

                //if only username already exists rename it and check that username and password exists.
                int j = isManagerObjectExist(username,password);
                while (j ==8 || j <4){
                    System.out.println("|                   UserName already exists.                   |");
                    username = enterYourDetails("|                   Enter your own UserName                    |:", reader);
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
                        print("|                     Added successfully                       |");
                        print(  "|      Name             : "+manager.getName()+" "+manager.getSurname()+"                  |\n" +
                                "|      Mobile Number    : "+manager.getMobileNumber()+"  || DOB    : "+manager.getDateOfBirth()+" |\n" +
                                "|      Address          : "+manager.getAddress()+"             |\n" +
                                "|      Password         : "+String.format("%0"+ (manager.getPassword().length())+"d",0).
                                replace("0","*")+"                              |\n" +
                                "|      Username         : "+manager.getUsername()+"                   |\n\n");
                        print("|         Please Remember the USERNAME and PASSWORD            |");
                        break;
                    }
                }

            }

        } catch (InputMismatchException e) {
            print("|-------------------Canceled the operation---------------------|");
        }
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

        System.out.println("|--------------------------------------------------------------|");
        System.out.print(print);
        String input = reader.readLine().trim();

        if (input.equalsIgnoreCase("Cancel")) throw new InputMismatchException();
        System.out.println("|--------------------------------------------------------------|");

        return input;
    }

    public static void print(String print) {

        System.out.println("|--------------------------------------------------------------|");
        System.out.println(print);
        System.out.println("|--------------------------------------------------------------|");
    }

    public static String readingNull(String s){
        if (s.equals("null")) return null;
        else return s;
    }

    public static void inputMismatch(){

        System.out.println("|--------------------------------------------------------------|");
        System.out.println("|--------------Input mismatch. Please try again.---------------|");
        System.out.println("|--------------------------------------------------------------|");
    }
}
