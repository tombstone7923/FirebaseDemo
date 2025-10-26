package aydin.firebasedemo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class welcomeController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneNumberTextField;

    public boolean registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(emailTextField.getText())
                .setEmailVerified(false)
                .setPassword(passwordTextField.getText())
                .setPhoneNumber(phoneNumberTextField.getText())
                .setDisplayName(usernameTextField.getText())
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = DemoApp.fauth.createUser(request);
            System.out.println("Successfully created new user with Firebase Uid: " + userRecord.getUid()
                    + " check Firebase > Authentication > Users tab");
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error creating a new user in the firebase");
            return false;
        }

    }

    //public boolean signIn() {

    //}

    @FXML
    void registerButtonClicked(ActionEvent event) {
        registerUser();
    }

}
