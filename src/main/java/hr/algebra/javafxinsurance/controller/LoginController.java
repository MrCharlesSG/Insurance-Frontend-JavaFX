package hr.algebra.javafxinsurance.controller;

import hr.algebra.javafxinsurance.dto.LoginRequestDTO;
import hr.algebra.javafxinsurance.dto.RegisterVehicleDTO;
import hr.algebra.javafxinsurance.service.AuthService;
import hr.algebra.javafxinsurance.utils.UIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label lbTitle;
    public TextField tfCarPlate;
    public TextField tfPassword;
    public VBox vbRegisterValues;
    public TextField tfBrand;
    public TextField tfModel;
    public TextField tfYear;
    public Button btnReset;
    public Button btnAccept;
    public Button btnLoginRegister;
    public ProgressIndicator piLoading;

    private boolean isLogin = true;

    public void onReset(ActionEvent actionEvent) {
    }

    public void onAccept(ActionEvent actionEvent) {
        piLoading.setVisible(true);
        try{
            if(isLogin){
                LoginRequestDTO loginRequestDTO = verifyLoginForm();

                 if(loginRequestDTO==null){
                    AuthService.INSTANCE.authenticate(new LoginRequestDTO("1234ABC", "user"));

                 }
                 else{
                     AuthService.INSTANCE.authenticate(loginRequestDTO);
                 }
            }else{
                RegisterVehicleDTO registerVehicleDTO = verifyRegisterForm();

                AuthService.INSTANCE.registerVehicle(registerVehicleDTO);
            }
            openHelloView(actionEvent);
        }catch (Exception e){
            e.printStackTrace();
            piLoading.setVisible(false);
            UIUtils.showInvalidFormAlert(e.getMessage());
        }

    }

    private RegisterVehicleDTO verifyRegisterForm() {
        LoginRequestDTO loginRequestDTO = verifyLoginForm();
        String password = loginRequestDTO.getPassword();
        String plate = loginRequestDTO.getUsername();
        String brand = tfBrand.getText();
        if(brand.isBlank()) throw new IllegalArgumentException("Brand is required");
        String model = tfModel.getText();
        if(model.isBlank()) throw new IllegalArgumentException("Model is required");
        String year = tfYear.getText();
        if(year.isBlank()) throw new IllegalArgumentException("Model is required");
        Year yearParsed;
        try{
            yearParsed = Year.parse(year);
        }catch (Exception e){
            throw new IllegalArgumentException("Year field is not a year");
        }
        return new RegisterVehicleDTO(plate, password, brand, model, yearParsed);
    }

    private LoginRequestDTO verifyLoginForm() {
        String plate = tfCarPlate.getText();
        if(plate.isBlank()) return null;//throw new IllegalArgumentException("Plate is required");
        String password = tfPassword.getText();
        if(password.isBlank()) return null;//throw new IllegalArgumentException("Password is required");
        return new LoginRequestDTO(plate, password);
    }

    public void onRegisterLogin(ActionEvent actionEvent) {
        if(isLogin){
            setRegisterMode();
        }else{
            setLoginMode();
        }

    }

    private void setLoginMode(){
        this.lbTitle.setText("LOGIN");
        this.btnLoginRegister.setText("Don't have an account");
        this.vbRegisterValues.setVisible(false);
        isLogin=true;
    }

    private void setRegisterMode(){
        this.lbTitle.setText("Register");
        this.btnLoginRegister.setText("Already have an account?");
        this.vbRegisterValues.setVisible(true);
        isLogin=false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        piLoading.setVisible(false);
        setLoginMode();
    }

    public void openHelloView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/algebra/javafxinsurance/hello-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root,715, 470);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Insurance");

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
