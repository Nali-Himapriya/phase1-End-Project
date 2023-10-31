package PhaseEnd;

import java.util.Scanner;

public class CameraRentalAppMain {
    public static void main(String[] args) {
    	
        Scanner sc = new Scanner(System.in);
        System.out.println("                               	 	@~~~~~~~~~~~~~~~~~~~@                  ");
        System.out.println("                                	|CAMERA--RENTAL--APP|                  ");
        System.out.println("                                	@~~~~~~~~~~~~~~~~~~~@                  ");
        
        System.out.println("\n                                           WELCOME BACK!!               ");
        System.out.println("\nLOGIN TO CONTINUE.....");
        CameraRentalApp obj = new CameraRentalApp();
        try {
            while (!obj.login()) {
                System.out.println("OOPS! LOGIN FAILURE--->ENTER VALID CREDENTIALS..");
            }
            do {
                String choice = "";
                boolean choice_validation;
                do {
                    System.out.println("1. GO TO MY CAMERA");
                    System.out.println("2. RENT A CAMERA");
                    System.out.println("3. VIEW ALL CAMERAS");
                    System.out.println("4. GO TO MY WALLET");
                    System.out.println("5. EXIT");
                    choice = sc.next();
                    choice_validation = Validator.isValidChoice(choice);
                    if (!choice_validation)
                        System.out.println("SORRY! INVALID CHOICE");
                } while (!choice_validation);
                try {
                    switch (Integer.parseInt(choice)) {
                        case 1:
                            obj.add_remove_camera();
                            break;
                        case 2:
                            obj.rent();
                            break;
                        case 3:
                            obj.getFullListSorted();
                            break;
                        case 4:
                            obj.myWallet();
                            break;
                        case 5:
                            System.out.println("THANK YOU! VISIT AGAIN.");
                            return;
                        default:
                            System.out.println("SORRY! INVALID CHOICE--> PLEASE SELECT THE OPTION BETWEEN 1 TO 5.");
                    }
                } catch (Exception e) {
                    System.out.println("INVALID INPUT..");
                    break;
                }
            } while (true);
        } catch (Exception e) {
            System.out.println("SORRY! " + e.getMessage());
        }
        CameraRentalApp camerarentalappobj = new CameraRentalApp();
       camerarentalappobj.add_remove_camera();
    }
}