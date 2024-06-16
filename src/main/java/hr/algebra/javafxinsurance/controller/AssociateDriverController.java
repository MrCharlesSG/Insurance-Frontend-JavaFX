package hr.algebra.javafxinsurance.controller;

import hr.algebra.javafxinsurance.dto.DriverDTO;
import hr.algebra.javafxinsurance.service.DriverService;
import hr.algebra.javafxinsurance.utils.DateUtils;
import hr.algebra.javafxinsurance.utils.UIUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class AssociateDriverController {
    public TextField tfName;
    public TextField tfSurname;
    public TextField tfPassport;
    public TextField tfEmail;
    public DatePicker dpBirthday;
    private DriverDTO driver;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void onReset(ActionEvent actionEvent) {
        dpBirthday.setValue(LocalDate.now());
        tfName.setText("");
        tfSurname.setText("");
        tfPassport.setText("");
        tfEmail.setText("");
        driver= null;
    }

    public void onExisting(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Email Dialog");
        dialog.setHeaderText("Enter drivers email");
        dialog.setContentText("Email:");

        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initModality(Modality.NONE);

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.TRANSPARENT);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(text -> {
            try {
                driver = DriverService.INSTANCE.getDriverByEmail(text);
                if (driver == null) {
                    UIUtils.showAlert(Alert.AlertType.ERROR, "Invalid Form", "There is no driver with email: " + text);
                } else {
                    bindDriver();
                }
            } catch (IllegalAccessException e) {
                UIUtils.showInvalidFormAlert(e.getMessage());
            }
        });
    }

    private void bindDriver() {
        dpBirthday.setValue(DateUtils.getLocalDateFromDate(driver.getBirthday()));
        //dpBirthday.setEditable(false);
        tfName.setText(driver.getName());
        tfSurname.setText(driver.getSurnames());
        tfPassport.setText(driver.getPassport());
        tfEmail.setText(driver.getEmail());
    }

    private DriverDTO getDriverOfFormAndValidate(){
        LocalDate birthdayValue = dpBirthday.getValue();
        if(birthdayValue==null) throw new IllegalArgumentException("Birthday Is compulsory");
        String emailText = tfEmail.getText();
        if(emailText.isBlank()) throw new IllegalArgumentException("Email is Compulsory");
        String nameText = tfName.getText();
        if(nameText.isBlank()) throw new IllegalArgumentException("Name is Compulsory");
        String surnameText = tfSurname.getText();
        if(surnameText.isBlank()) throw new IllegalArgumentException("Surname is Compulsory");
        String passportText = tfPassport.getText();
        if(passportText.isBlank()) throw new IllegalArgumentException("Passport is Compulsory");
        Date birthday = DateUtils.getDateFromLocalDate(birthdayValue);
        driver=new DriverDTO(0L, nameText, surnameText,  passportText,emailText, birthday);
        return driver;
    }

    public void onAccept(ActionEvent actionEvent) {
        if(driver!=null) {
            try {
                DriverService.INSTANCE.associateDriver(driver.getEmail());
                dialogStage.close();
            } catch (Exception e) {
                showAlert(e.getMessage());
            }
        }else {
            try {
                DriverService.INSTANCE.createDriverAndAssociate(getDriverOfFormAndValidate());
                dialogStage.close();
            } catch (Exception e) {
                showAlert(e.getMessage());
            }
        }
    }

    private void showAlert(String message) {
        UIUtils.showInvalidFormAlert(message);
    }
}
