//to run, type mvn javafx:run in the console.
//password for admin dialog box is: 123456
package com.barbershop;

import com.barbershop.booking.BookingManager;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.Month;

public class BarberShopApp extends Application {

    private BookingManager bookingManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        bookingManager = new BookingManager();

        // get logo image from absolute path
        String imagePath = Paths.get("C:", "Users", "thoma", "OneDrive", "Desktop", "Misc Coding", "BarberShop",
                "barbershop-booking", "src", "main", "java", "com", "barbershop", "images",
                "barbershop-logo-barber-shop-logo-template-vector.jpg").toUri().toString();
        Image image = new Image(imagePath);

        // show main dialogue buttons for customer staff and admin
        showMainDialog(primaryStage, image);
    }

    // Show dialog with customer, staff, and admin buttons
    private void showMainDialog(Stage primaryStage, Image logoImage) {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setAlignment(Pos.CENTER);

        // welcome
        Label welcomeLabel = new Label("Welcome to The Barbershop!");
        welcomeLabel.setFont(new Font("Calibri", 20));

        // add image
        ImageView imageView = new ImageView(logoImage);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        // customer staff admin buttons
        Button customerBtn = new Button("Customer");
        Button staffBtn = new Button("Staff");
        Button adminBtn = new Button("Admin");

        // style buttons
        styleButton(customerBtn);
        styleButton(staffBtn);
        styleButton(adminBtn);

        // set button actions
        customerBtn.setOnAction(e -> showCustomerMainPage(primaryStage, logoImage));
        staffBtn.setOnAction(e -> showStaffPage(logoImage));

        adminBtn.setOnAction(e -> showAdminPasswordPrompt(logoImage)); // password prompt for admin

        // location Text w/ pin
        String pinIconPath = Paths.get("C:", "Users", "thoma", "OneDrive", "Desktop", "Misc Coding", "BarberShop",
                "barbershop-booking", "src", "main", "java", "com", "barbershop", "images", "location-icon.png").toUri()
                .toString();
        ImageView pinIcon = new ImageView(new Image(pinIconPath));
        pinIcon.setFitWidth(12);
        pinIcon.setFitHeight(15);

        Label locationLabel = new Label("The Barbershop, 15 Main Street, Limerick");
        locationLabel.setFont(new Font("Calibri", 14));

        HBox locationBox = new HBox(5, pinIcon, locationLabel);
        locationBox.setAlignment(Pos.CENTER);

        // add to layout
        mainLayout.getChildren().addAll(welcomeLabel, imageView, customerBtn, staffBtn, adminBtn, locationBox);

        // setup scene / show
        Scene scene = new Scene(mainLayout, 400, 400);
        // background = white
        scene.getRoot().setStyle("-fx-background-color: white;");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // prompt for admin pass before accessing admin page
    private void showAdminPasswordPrompt(Image logoImage) {
        // create new dialogue box for password
        Stage passwordDialog = new Stage();
        passwordDialog.setTitle("Admin Access");

        VBox passwordLayout = new VBox(10);
        passwordLayout.setPadding(new Insets(10));
        passwordLayout.setAlignment(Pos.CENTER);

        // label for password prompt
        Label passwordLabel = new Label("Enter Admin Password:");
        passwordLabel.setFont(new Font("Calibri", 16));

        // password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // buttons
        Button confirmButton = new Button("Confirm");
        styleButton(confirmButton);
        Button cancelButton = new Button("Cancel");
        styleButton(cancelButton);

        // confirm button action to validate password
        confirmButton.setOnAction(e -> {
            String enteredPassword = passwordField.getText();
            String correctPassword = "123456"; // mock password NB

            // conditional statement
            if (enteredPassword.equals(correctPassword)) {
                // if correct password entered, show admin page
                passwordDialog.close();
                showAdminPage(logoImage);
            } else {
                // wrong password, show error ALERT
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect password. Please try again.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // cancel button action to close dialogue box
        cancelButton.setOnAction(e -> passwordDialog.close());

        // buttons layout
        HBox buttonLayout = new HBox(10, confirmButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // add components to layout
        passwordLayout.getChildren().addAll(passwordLabel, passwordField, buttonLayout);

        // create / show the password dialogue
        Scene passwordScene = new Scene(passwordLayout, 300, 150);
        passwordScene.getRoot().setStyle("-fx-background-color: white;");
        passwordDialog.setScene(passwordScene);
        passwordDialog.show();
    }

    // Method to show admin page
    private void showAdminPage(Image image) {
        Stage dialog = new Stage();
        dialog.setTitle("Admin Page");

        // make layout for admin page
        VBox adminLayout = new VBox(20);
        adminLayout.setPadding(new Insets(15));
        adminLayout.setAlignment(Pos.CENTER);

        // add image to admin page
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        Label adminLabel = new Label("Admin Access");
        adminLabel.setFont(new Font("Calibri", 18));

        // Add/Remove staff button
        Button manageStaffButton = new Button("Add/Remove Staff");
        styleButton(manageStaffButton);
        manageStaffButton.setOnAction(e -> showManageStaffDialog(image));

        // change admin password button
        Button changePasswordButton = new Button("Change Admin Password");
        styleButton(changePasswordButton);
        changePasswordButton.setOnAction(e -> showChangePasswordDialog(image));

        // Logout button to go back to main dialgoue
        Button logoutButton = new Button("Logout");
        styleButton(logoutButton);
        logoutButton.setOnAction(e -> {
            dialog.close(); // close admin dialog
            showMainDialog((Stage) dialog.getOwner(), image); // go back to main dialogue
        });

        // add components to layout
        adminLayout.getChildren().addAll(imageView, adminLabel, manageStaffButton, changePasswordButton, logoutButton);

        // create and show the scene
        Scene adminScene = new Scene(adminLayout, 300, 250);
        adminScene.getRoot().setStyle("-fx-background-color: white;");
        dialog.setScene(adminScene);
        dialog.show();
    }

    // placeholder method for managing staff
    private void showManageStaffDialog(Image logoImage) {
        Stage staffDialog = new Stage();
        staffDialog.setTitle("Manage Staff");

        VBox staffLayout = new VBox(20);
        staffLayout.setPadding(new Insets(15));
        staffLayout.setAlignment(Pos.CENTER);

        Label staffLabel = new Label("Add/Remove Staff");
        staffLabel.setFont(new Font("Calibri", 18));

        // TODO: Add controls for managing staff (i.e., adding/ removing staff
        // members)
        // Example: A ListView to display staff and buttons for adding/removing
        // NB: Placeholder content for now
        Label placeholderContent = new Label("Feature to add/remove staff coming soon!");
        placeholderContent.setFont(new Font("Calibri", 14));

        // Back button
        Button backButton = new Button("Back");
        styleButton(backButton);
        backButton.setOnAction(e -> staffDialog.close());

        staffLayout.getChildren().addAll(staffLabel, placeholderContent, backButton);

        Scene staffScene = new Scene(staffLayout, 300, 200);
        staffScene.getRoot().setStyle("-fx-background-color: white;");
        staffDialog.setScene(staffScene);
        staffDialog.show();
    }

    // Placeholder method for changing admin password
    private void showChangePasswordDialog(Image logoImage) {
        Stage passwordDialog = new Stage();
        passwordDialog.setTitle("Change Admin Password");

        VBox passwordLayout = new VBox(20);
        passwordLayout.setPadding(new Insets(15));
        passwordLayout.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label("Change Admin Password");
        passwordLabel.setFont(new Font("Calibri", 18));

        // Current password field
        PasswordField currentPasswordField = new PasswordField();
        currentPasswordField.setPromptText("Current Password");

        // New password field
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");

        // Confirm new password field
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm New Password");

        // Change password button
        Button changePasswordButton = new Button("Change Password");
        styleButton(changePasswordButton);
        changePasswordButton.setOnAction(e -> {
            String currentPassword = currentPasswordField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Validate password change (mock password is "123456")
            if (!currentPassword.equals("123456")) {
                new Alert(Alert.AlertType.ERROR, "Current password is incorrect.", ButtonType.OK).showAndWait();
            } else if (!newPassword.equals(confirmPassword)) {
                new Alert(Alert.AlertType.ERROR, "New passwords do not match.", ButtonType.OK).showAndWait();
            } else {
                // TODO: Implement password change logic
                // For now, just show a confirmation dialogue box
                new Alert(Alert.AlertType.INFORMATION, "Password changed successfully!", ButtonType.OK).showAndWait();
                passwordDialog.close();
            }
        });

        // back button
        Button backButton = new Button("Back");
        styleButton(backButton);
        backButton.setOnAction(e -> passwordDialog.close());

        passwordLayout.getChildren().addAll(passwordLabel, currentPasswordField, newPasswordField, confirmPasswordField,
                changePasswordButton, backButton);

        Scene passwordScene = new Scene(passwordLayout, 300, 250);
        passwordScene.getRoot().setStyle("-fx-background-color: white;");
        passwordDialog.setScene(passwordScene);
        passwordDialog.show();
    }

    // method to display original customer main page
    private void showCustomerMainPage(Stage primaryStage, Image image) {
        VBox customerLayout = new VBox(20);
        customerLayout.setPadding(new Insets(15));
        customerLayout.setAlignment(Pos.CENTER);

        // image at top
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        // customer main page buttons
        Button createBookingBtn = new Button("Create Booking");
        Button cancelBookingBtn = new Button("Cancel/Change Booking");
        Button pricesBtn = new Button("Prices");
        Button aboutUsBtn = new Button("About Us");
        Button backButton = new Button("Back");

        // style buttons
        styleButton(createBookingBtn);
        styleButton(cancelBookingBtn);
        styleButton(pricesBtn);
        styleButton(aboutUsBtn);
        styleButton(backButton);

        // set button actions
        createBookingBtn.setOnAction(e -> showCreateBookingDialog(image, null));
        cancelBookingBtn.setOnAction(e -> showCancelBookingDialog(image));
        pricesBtn.setOnAction(e -> showPricesDialog(image));
        aboutUsBtn.setOnAction(e -> showAboutUsDialog(image));
        backButton.setOnAction(e -> showMainDialog(primaryStage, image));

        // location text w/ pin icon
        String pinIconPath = Paths.get("C:", "Users", "thoma", "OneDrive", "Desktop", "Misc Coding", "BarberShop",
                "barbershop-booking", "src", "main", "java", "com", "barbershop", "images", "location-icon.png").toUri()
                .toString();
        ImageView pinIcon = new ImageView(new Image(pinIconPath));
        pinIcon.setFitWidth(12);
        pinIcon.setFitHeight(15);

        Label locationLabel = new Label("The Barbershop, 15 Main Street, Limerick");
        locationLabel.setFont(new Font("Calibri", 14));

        HBox locationBox = new HBox(5, pinIcon, locationLabel);
        locationBox.setAlignment(Pos.CENTER);

        // add components to layout
        customerLayout.getChildren().addAll(imageView, createBookingBtn, cancelBookingBtn, pricesBtn, aboutUsBtn,
                backButton, locationBox);

        // setup scene & show
        Scene customerScene = new Scene(customerLayout, 400, 550);
        customerScene.getRoot().setStyle("-fx-background-color: white;");
        primaryStage.setScene(customerScene);
    }

    // placeholder method for showing staff page
    private void showStaffPage(Image image) {
        Stage dialog = new Stage();
        dialog.setTitle("Staff Page");

        // create layout for staff page
        VBox staffLayout = new VBox(20);
        staffLayout.setPadding(new Insets(15));
        staffLayout.setAlignment(Pos.CENTER);

        // add image to staff page
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        Label staffLabel = new Label("Staff Access");
        staffLabel.setFont(new Font("Calibri", 18));

        // add components to layout
        staffLayout.getChildren().addAll(imageView, staffLabel);

        // create and show scene
        Scene staffScene = new Scene(staffLayout, 300, 200);
        staffScene.getRoot().setStyle("-fx-background-color: white;");
        dialog.setScene(staffScene);
        dialog.show();
    }

    private void styleButton(Button button) {
        button.setFont(new Font("Calibri", 16));
        button.setMinWidth(200);

        // default style
        button.setStyle(
                "-fx-border-color: #1E90FF; -fx-border-width: 2px; -fx-background-color: white; -fx-text-fill: black;");

        // hover effect
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-border-color: #1E90FF; -fx-border-width: 2px; -fx-background-color: #ADD8E6; -fx-text-fill: black;"));

        // normal effect when not hovered
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-border-color: #1E90FF; -fx-border-width: 2px; -fx-background-color: white; -fx-text-fill: black;"));

        // pressed effect
        button.setOnMousePressed(e -> button.setStyle(
                "-fx-border-color: #1E90FF; -fx-border-width: 2px; -fx-background-color: #87CEFA; -fx-text-fill: black;"));

        // revert click effect
        button.setOnMouseReleased(e -> button.setStyle(
                "-fx-border-color: #1E90FF; -fx-border-width: 2px; -fx-background-color: #ADD8E6; -fx-text-fill: black;"));
    }

    // create booking dialogue with email, newsletter checkbox, and back button
    private void showCreateBookingDialog(Image logoImage, String existingReference) {
        Stage dialog = new Stage();
        dialog.setTitle(existingReference == null ? "Create Booking" : "Modify Booking");

        VBox dialogLayout = new VBox(10);
        dialogLayout.setPadding(new Insets(10));
        dialogLayout.setAlignment(Pos.CENTER);

        // add image to dialogue box
        ImageView imageView = new ImageView(logoImage);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.RIGHT);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.LEFT);
        grid.getColumnConstraints().addAll(col1, col2);

        // customer name input
        Label nameLabel = new Label("Your Name:");
        TextField nameInput = new TextField();

        // email input
        Label emailLabel = new Label("Your Email:");
        TextField emailInput = new TextField();

        // month choose
        Label monthLabel = new Label("Select Month:");
        ComboBox<Month> monthCombo = new ComboBox<>();
        monthCombo.getItems().addAll(Month.values());

        // day choose
        Label dayLabel = new Label("Select Day:");
        Spinner<Integer> daySpinner = new Spinner<>(1, 31, 1);

        // time choose
        Label timeLabel = new Label("Select Time:");
        ComboBox<LocalTime> timeCombo = new ComboBox<>();
        for (int hour = 9; hour < 17; hour++) {
            timeCombo.getItems().add(LocalTime.of(hour, 0));
            timeCombo.getItems().add(LocalTime.of(hour, 30));
        }

        // Barber choose
        Label barberLabel = new Label("Select Barber:");
        ComboBox<String> barberCombo = new ComboBox<>();
        barberCombo.getItems().addAll(bookingManager.getBarbers());

        // Newsletter checkbox
        Label newsletterLabel = new Label("Sign up for our newsletter:");
        CheckBox newsletterCheckBox = new CheckBox();

        // create booking button
        Button bookButton = new Button(existingReference == null ? "Book" : "Modify Booking");
        styleButton(bookButton);

        // back button
        Button backButton = new Button("Back");
        styleButton(backButton);
        backButton.setOnAction(e -> dialog.close());

        // set layout
        grid.add(nameLabel, 0, 0);
        grid.add(nameInput, 1, 0);
        grid.add(emailLabel, 0, 1);
        grid.add(emailInput, 1, 1);
        grid.add(monthLabel, 0, 2);
        grid.add(monthCombo, 1, 2);
        grid.add(dayLabel, 0, 3);
        grid.add(daySpinner, 1, 3);
        grid.add(timeLabel, 0, 4);
        grid.add(timeCombo, 1, 4);
        grid.add(barberLabel, 0, 5);
        grid.add(barberCombo, 1, 5);
        grid.add(newsletterLabel, 0, 6);
        grid.add(newsletterCheckBox, 1, 6);
        grid.add(bookButton, 1, 7);

        // booking action
        bookButton.setOnAction(e -> {
            String customerName = nameInput.getText();
            String customerEmail = emailInput.getText();
            Month month = monthCombo.getValue();
            int day = daySpinner.getValue();
            LocalTime time = timeCombo.getValue();
            String barber = barberCombo.getValue();
            boolean newsletterSignUp = newsletterCheckBox.isSelected();

            String bookingReference = bookingManager.createBooking(customerName, customerEmail, month, day, time,
                    barber, newsletterSignUp);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your booking has been confirmed! " +
                    "Your reference number is " + bookingReference
                    + ". Please use this to cancel or modify your appointment.", ButtonType.OK);
            alert.showAndWait();
            dialog.close();
        });

        // add image, form, and back button to dialogue layout
        dialogLayout.getChildren().addAll(imageView, grid, backButton);

        Scene dialogScene = new Scene(dialogLayout, 400, 400);
        dialogScene.getRoot().setStyle("-fx-background-color: white;");
        dialog.setScene(dialogScene);
        dialog.show();
    }

    // cancel / change booking dialog
    private void showCancelBookingDialog(Image logoImage) {
        Stage dialog = new Stage();
        dialog.setTitle("Cancel/Change Booking");

        VBox dialogLayout = new VBox(10);
        dialogLayout.setPadding(new Insets(10));
        dialogLayout.setAlignment(Pos.CENTER);

        // booking reference input
        Label referenceLabel = new Label("Enter Booking Reference:");
        TextField referenceInput = new TextField();

        // buttons
        Button findButton = new Button("Find Booking");
        styleButton(findButton);
        Button backButton = new Button("Back");
        styleButton(backButton);
        backButton.setOnAction(e -> dialog.close());

        // find booking action
        findButton.setOnAction(e -> {
            String reference = referenceInput.getText();
            if (bookingManager.hasBooking(reference)) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                        "Would you like to cancel or modify your booking?", ButtonType.YES, ButtonType.CANCEL);
                confirmation.setHeaderText(null);
                confirmation.showAndWait();

                if (confirmation.getResult() == ButtonType.YES) {
                    bookingManager.cancelBooking(reference);
                    new Alert(Alert.AlertType.INFORMATION, "Booking canceled successfully.", ButtonType.OK)
                            .showAndWait();
                    dialog.close();
                } else {
                    dialog.close();
                    showCreateBookingDialog(logoImage, reference);
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "No booking found with the provided reference.", ButtonType.OK)
                        .showAndWait();
            }
        });

        dialogLayout.getChildren().addAll(referenceLabel, referenceInput, findButton, backButton);

        Scene dialogScene = new Scene(dialogLayout, 300, 200);
        dialogScene.getRoot().setStyle("-fx-background-color: white;");
        dialog.setScene(dialogScene);
        dialog.show();
    }

    // about dialogue box stuff
    private void showAboutUsDialog(Image logoImage) {
        Stage dialog = new Stage();
        dialog.setTitle("About Us");

        VBox dialogLayout = new VBox(10);
        dialogLayout.setPadding(new Insets(10));
        dialogLayout.setAlignment(Pos.CENTER);

        // add image to the dialogue box
        ImageView imageView = new ImageView(logoImage);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        // about us
        Label aboutUsLabel = new Label(
                "Welcome to The Barbershop!\n\n" +
                        "Established in 2024, we are located at 15 Main Street, Limerick.\n\n" +
                        "Founded by Thomas, who had a vision of better barbering,\n" +
                        "The Barbershop has since become a local favourite.\n" +
                        "We pride ourselves on offering quality services for all ages.\n\n" +
                        "From classic cuts to the latest styles, we cater for all needs.\n" +
                        "Come and experience a friendly atmosphere, where\n" +
                        "customer satisfaction is our main priority.\n" +
                        "We look forward to welcoming you!");
        aboutUsLabel.setFont(new Font("Calibri", 16));
        aboutUsLabel.setWrapText(true);
        aboutUsLabel.setMaxWidth(350);

        // back button
        Button backButton = new Button("Back");
        styleButton(backButton);
        backButton.setOnAction(e -> dialog.close());

        // add image, about text, and back button to the dialogue layout
        dialogLayout.getChildren().addAll(imageView, aboutUsLabel, backButton);

        // create and display scene
        Scene dialogScene = new Scene(dialogLayout, 400, 350);
        dialogScene.getRoot().setStyle("-fx-background-color: white;");
        dialog.setScene(dialogScene);
        dialog.show();
    }

    // show the prices dialog
    private void showPricesDialog(Image logoImage) {
        Stage dialog = new Stage();
        dialog.setTitle("Service Prices");

        VBox dialogLayout = new VBox(10);
        dialogLayout.setPadding(new Insets(10));
        dialogLayout.setAlignment(Pos.CENTER);

        // add image to the dialogue
        ImageView imageView = new ImageView(logoImage);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        // create label to list services and prices
        Label pricesLabel = new Label(
                "Shampoo and cut - €30\n" +
                        "Scissors cut - €25\n" +
                        "Machine cut - €20\n" +
                        "Hair gel - €20\n" +
                        "Hair wax - €15");
        pricesLabel.setFont(new Font("Calibri", 16));

        // back button
        Button backButton = new Button("Back");
        styleButton(backButton);
        backButton.setOnAction(e -> dialog.close());

        // add image, prices, and back button to dialogue layout
        dialogLayout.getChildren().addAll(imageView, pricesLabel, backButton);

        // create and display scene
        Scene dialogScene = new Scene(dialogLayout, 300, 250);
        dialogScene.getRoot().setStyle("-fx-background-color: white;");
        dialog.setScene(dialogScene);
        dialog.show();
    }
}