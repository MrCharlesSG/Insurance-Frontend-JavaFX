package hr.algebra.javafxinsurance.utils;

import javafx.scene.control.Alert;

public class UIUtils {
    public static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void showInvalidFormAlert(String message) {
        UIUtils.showAlert(Alert.AlertType.ERROR, "Invalid Form", message);
    }
}
