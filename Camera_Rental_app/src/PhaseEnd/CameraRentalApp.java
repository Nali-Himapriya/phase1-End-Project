package PhaseEnd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class CameraRentalApp {
    private Validator v = new Validator();
    private List<Camera> allCameras = new ArrayList<>();
    private List<Camera> myCameras = new ArrayList<>();
    private double walletBalance = 50000;

    public CameraRentalApp() {
        allCameras.add(new Camera(1, "Canon", "EOST7", 1500));
        allCameras.add(new Camera(2, "Nikon", "D3500", 1400));
        allCameras.add(new Camera(3, "Sony", "a6000", 1600));
        allCameras.add(new Camera(4, "Samsung", "50D", 1050));
        allCameras.add(new Camera(5, "Canon", "3D", 1100));
    }

    public void add_remove_camera() {
        Scanner sc = new Scanner(System.in);
        do {
            String choice = "";
            boolean choice_validation;

            do {
                System.out.println("1. ADD CAMERA\n2. REMOVE CAMERA\n3. VIEW ALL CAMERAS\n4. BACK TO PREVIOUS MENU");
                choice = sc.next();
                choice_validation = Validator.isValidChoice(choice);
                if (!choice_validation)
                    System.out.println("SORRY! INVALID CHOICE..");
            } while (!choice_validation);

            switch (Integer.parseInt(choice)) {
                case 1:
                    int addedCameraId = addCamera();
                    if (addedCameraId == -1) {
                        System.out.println("Camera with the same ID already exists. Try again.");
                    } else {
                        System.out.println("Camera with ID " + addedCameraId + " has been successfully added.");
                    }
                    break;
                case 2:
                    removeCamera();
                    break;
                case 3:
                    getFullListSorted();
                    break;
                case 4:
                    return;
            }
        } while (true);
    }

    public void displayCameraList(List<Camera> cameraList) {
        if (cameraList.size() > 0) {
            System.out.println("...................................................................................");
            System.out.printf("%5s %10s %12s %20s %12s", "CAMERA ID", "BRAND", "MODEL", "PRICE(PER DAY)", "STATUS");
            System.out.println();
            System.out.println("....................................................................................");
            for (Camera c : cameraList) {
                System.out.format("%5s %14s %11s %12s %22s", c.getId(), c.getBrand(), c.getModel(), c.getPrice_per_day(), c.getStatus() == 'a' ? "Available" : "Rented");
                System.out.println();
            }
            System.out.println(".....................................................................................");
        } else {
            System.out.println("SORRY! NO CAMERAS FOUND.");
        }
    }

    public void getFullListSorted() {
        List<Camera> fullList = new ArrayList<>(allCameras);
        fullList.addAll(myCameras);
        
        // Remove duplicates using a HashSet
        HashSet<Camera> uniqueCameras = new HashSet<>(fullList);
        fullList.clear();
        fullList.addAll(uniqueCameras);
        
        // Sort the list by camera ID
        Collections.sort(fullList, (c1, c2) -> Integer.compare(c1.getId(), c2.getId()));

        displayCameraList(fullList);
    }

    private int addCamera() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ENTER THE CAMERA BRAND - ");
        String brand = sc.nextLine();
        System.out.print("ENTER THE MODEL - ");
        String model = sc.nextLine();
        String per_day_price;

        do {
            System.out.print("ENTER THE PER DAY PRICE (INR) - ");
            per_day_price = sc.next();
        } while (!v.isValidPrice(per_day_price));

        int id = generateNextCameraID();
        
        // Check for duplicate IDs
        for (Camera camera : allCameras) {
            if (camera.getId() == id) {
                return -1; // Return -1 if a camera with the same ID already exists
            }
        }

        Camera camera = new Camera(id, brand, model, Double.parseDouble(per_day_price));
        myCameras.add(camera);
        allCameras.add(camera);

        return id;
    }

    private int generateNextCameraID() {
        int maxId = 0;
        for (Camera camera : allCameras) {
            if (camera.getId() > maxId) {
                maxId = camera.getId();
            }
        }
        return maxId + 1;
    }

    private void removeCamera() {
        Scanner sc = new Scanner(System.in);

        if (allCameras.size() > 0) {
            displayCameraList(allCameras);
            System.out.print("PLEASE ENTER THE CAMERA ID TO REMOVE - ");
            int camera_id = sc.nextInt();
            boolean isOnRent = isOnRent(allCameras, camera_id);

            if (isOnRent) {
                System.out.println("OOPS! THIS CAMERA CANNOT BE REMOVED AS IT IS GIVEN ON RENT..");
            } else {
                Camera cameraToRemove = null;
                for (Camera c : allCameras) {
                    if (c.getId() == camera_id) {
                        cameraToRemove = c;
                        break;
                    }
                }
                if (cameraToRemove != null) {
                    allCameras.remove(cameraToRemove);
                    System.out.println("CAMERA SUCCESSFULLY REMOVED..");
                } else {
                    System.out.println("ERROR: INCORRECT CAMERA ID.");
                }
            }
        } else {
            System.out.println("SORRY! NO CAMERAS FOUND.");
        }
    }

    private boolean isOnRent(List<Camera> myList, int camera_id) {
        for (Camera c : myList) {
            if (c.getId() == camera_id && c.getStatus() == 'r') {
                return true;
            }
        }
        return false;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void myWallet() {
        Scanner sc = new Scanner(System.in);
        System.out.println("AVAILABLE WALLET BALANCE IS - INR" + walletBalance);
        System.out.print("DO YOU WANT TO DEPOSIT MORE AMOUNT TO YOUR WALLET?(1.YES 2.NO) - ");
        switch (sc.nextInt()) {
            case 1:
                System.out.print("ENTER THE AMOUNT (INR) - ");
                double amount = sc.nextDouble();
                if (amount > 0) {
                    walletBalance += amount;
                    System.out.println("YOUR WALLET BALANCE UPDATED SUCCESSFULLY. CURRENT AVAILABLE WALLET BALANCE - INR." + walletBalance);
                } else {
                    System.out.println("SORRY! INVALID AMOUNT - INR." + amount);
                }
                break;
            case 2:
                return;
            default:
                System.out.println("ERROR: INVALID OPERATION");
        }
    }

    public boolean login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ENTER USERNAME--> ");
        String username = sc.next();
        System.out.print("ENTER PASSWORD--> ");
        String password = sc.next();
        List<UserCredentials> userCredentialsList = new ArrayList<>();
        userCredentialsList.add(new UserCredentials("Admin", "Admin@123"));
        userCredentialsList.add(new UserCredentials("Hima", "Hima@123"));
        userCredentialsList.add(new UserCredentials("Priya", "Priya@123"));
        userCredentialsList.add(new UserCredentials("Himapriya", "Himapriya@123"));

        for (UserCredentials userCredentials : userCredentialsList) {
            if (userCredentials.getUsername().equals(username) && userCredentials.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    class UserCredentials {
        private String username;
        private String password;

        public UserCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public void rent() {
        Scanner sc = new Scanner(System.in);
        double balance = getWalletBalance();
        List<Camera> availableList = new ArrayList<>();
        for (Camera c : allCameras) {
            if (c.getStatus() == 'a') {
                availableList.add(c);
            }
        }

        if (availableList.size() > 0) {
            System.out.println("AVAILABLE CAMERA(S) - ");
            displayCameraList(availableList);
            System.out.print("ENTER THE CAMERA ID YOU WANT TO RENT - ");
            int camera_id = sc.nextInt();

            Camera selectedCamera = null;
            for (Camera c : availableList) {
                if (c.getId() == camera_id) {
                    selectedCamera = c;
                    break;
                }
            }

            if (selectedCamera != null) {
                if (balance >= selectedCamera.getPrice_per_day()) {
                    selectedCamera.setStatus('r');
                    walletBalance -= selectedCamera.getPrice_per_day();
                    System.out.println("YOUR TRANSACTION FOR CAMERA - " + selectedCamera.getBrand() + " " + selectedCamera.getModel() + " with rent INR." + selectedCamera.getPrice_per_day() + " HAS SUCCESSFULLY COMPLETED.");
                } else {
                    System.out.println("ERROR: TRANSACTION FAILED DUE TO INSUFFICIENT WALLET BALANCE. PLEASE DEPOSIT THE AMOUNT TO YOUR WALLET.");
                }
            } else {
                System.out.println("ERROR: INVALID ID");
            }
        } else {
            System.out.println("OOPS!! CURRENTLY NO CAMERAS AVAILABLE FOR RENT.");
        }
    }

    public static void main(String[] args) {
    	CameraRentalApp camerarentalappobj = new CameraRentalApp();
        camerarentalappobj.add_remove_camera();
    }
}

class Camera {
    private int id;
    private String brand;
    private String model;
    private double price_per_day;
    private char status;

    public Camera(int id, String brand, String model, double price_per_day) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price_per_day = price_per_day;
        this.status = 'a';
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getPrice_per_day() {
        return price_per_day;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
}

class Validator {
    public static boolean isValidChoice(String choice) {
        try {
            int choiceValue = Integer.parseInt(choice);
            return choiceValue >= 1 && choiceValue <= 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidPrice(String price) {
        try {
            double priceValue = Double.parseDouble(price);
            return priceValue >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}