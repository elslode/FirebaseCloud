package slode.elsloude.firebasecloud;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddUserActivity extends AppCompatActivity {

    FirebaseFirestore db;

    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Spinner spinnerSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_add_user);
        editTextName = findViewById(R.id.editTextTextName);
        editTextLastName = findViewById(R.id.editTextTextLastName);
        editTextAge = findViewById(R.id.editTextTextAge);
        spinnerSex = findViewById(R.id.spinnerSex);
    }

    public void onClickSaveUser(View view) {
        String name = editTextName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        int age = 0;
        try {
            age = Integer.parseInt(editTextAge.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String sex = spinnerSex.getSelectedItem().toString().trim();
        saveUser(name, lastName, age, sex);

    }

    private void saveUser(String name, String lastName, int age, String sex) {
        if (!name.isEmpty() && !lastName.isEmpty() && !sex.isEmpty() && age > 0) {
            db.collection("users").add(new User(name, lastName, age, sex)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddUserActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
