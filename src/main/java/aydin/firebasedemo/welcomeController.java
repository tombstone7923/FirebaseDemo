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

    @FXML
    private TextField usernameLoginTextField;

    @FXML
    private TextField passwordLoginTextField;

    public void addData() {

        DocumentReference docRef = DemoApp.fstore.collection("Passwords").document(usernameTextField.getText());

        Map<String, Object> data = new HashMap<>();
        data.put("Password", passwordTextField.getText());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    }

    public boolean registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(emailTextField.getText())
                .setEmailVerified(false)
                .setPassword(passwordTextField.getText())
                .setPhoneNumber(phoneNumberTextField.getText())
                .setUid(usernameTextField.getText())
                .setDisabled(false);



        UserRecord userRecord;
        try {
            userRecord = DemoApp.fauth.createUser(request);
            System.out.println("Successfully created new user with Firebase Uid: " + userRecord.getUid()
                    + " check Firebase > Authentication > Users tab");
            addData();
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error creating a new user in the firebase");
            return false;
        }

    }

    public void signIn() {
        ApiFuture<QuerySnapshot> future =  DemoApp.fstore.collection("Passwords").get();
        List<QueryDocumentSnapshot> documents;
        boolean passwordMatch = false;
        boolean userMatch = false;
        try
        {
            documents = future.get().getDocuments();
            if(documents.size()>0)
            {
                System.out.println("Getting (reading) data from firabase database....");

                for (QueryDocumentSnapshot document : documents) {
                    if (document.getData().get("Password").equals(passwordLoginTextField.getText())) {
                        System.out.println(document.getData().get("Password"));
                        passwordMatch = true;
                    }
                    else{
                        System.out.println("password wrong");
                    }
                    if(document.getId().equals(usernameLoginTextField.getText())){
                        System.out.println(document.getId());
                        userMatch = true;
                    }
                    else{
                        System.out.println("username wrong");
                    }
                    if(passwordMatch == true && userMatch == true){
                        try {
                            DemoApp.setRoot("primary");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            else
            {
                System.out.println("No data");
            }

        }
        catch (InterruptedException | ExecutionException ex)
        {
            ex.printStackTrace();
        }
    }

    @FXML
    void registerButtonClicked(ActionEvent event) {
        registerUser();
    }

    @FXML
    void loginButtonClicked(ActionEvent event) {
        signIn();
    }

}
