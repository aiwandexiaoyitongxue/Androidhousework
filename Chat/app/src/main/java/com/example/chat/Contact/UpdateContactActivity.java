package com.example.chat.Contact;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chat.database.DatabaseHelper;
import com.example.chat.R;

public class UpdateContactActivity extends AppCompatActivity {
    private EditText editTextName, editTextPhone;
    private DatabaseHelper db;
    private int contactId; // 保存联系人的 ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Initialize DatabaseHelper
        db = new DatabaseHelper(this);

        // Get contact ID from intent extras
        contactId = getIntent().getIntExtra("contact_id", -1);

        // Load contact details from database and populate EditText fields
        Contact contact = db.getContact(contactId);
        if (contact != null) {
            editTextName.setText(contact.getName());
            editTextPhone.setText(contact.getPhone());
        }

        // Setup save button click listener
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Update contact with new details
                String newName = editTextName.getText().toString().trim();
                String newPhone = editTextPhone.getText().toString().trim();

                // Validate inputs
                if (newName.isEmpty() || newPhone.isEmpty()) {
                    Toast.makeText(UpdateContactActivity.this, "姓名和电话不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    // Update contact in database
                    contact.setName(newName);
                    contact.setPhone(newPhone);
                    db.updateContact(contact);

                    // Show success message and finish activity
                    Toast.makeText(UpdateContactActivity.this, "联系人已更新", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    // Method to handle saving contact changes
    public void saveContact(View view) {
        // This method can be used as onClick handler for buttonSave
        // It's not necessary to implement this if you use onClick attribute in XML
        // It's here just for demonstration
    }
}