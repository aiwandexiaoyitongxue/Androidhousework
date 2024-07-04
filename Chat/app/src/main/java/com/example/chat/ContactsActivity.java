package com.example.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat.Adapter.ContactsAdapter;
import com.example.chat.Contact.AddContactActivity;
import com.example.chat.Contact.Contact;
import com.example.chat.Contact.DeleteContactActivity;
import com.example.chat.Contact.SearchContactActivity;
import com.example.chat.Contact.UpdateContactActivity;
import com.example.chat.database.DatabaseHelper;

import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewContacts;
    private Button btnAddContact;
    private DatabaseHelper db;
    private ContactsAdapter contactsAdapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Initialize views
        recyclerViewContacts = findViewById(R.id.recyclerViewContacts);
        btnAddContact = findViewById(R.id.btn_add_contact);

        // Initialize DatabaseHelper
        db = new DatabaseHelper(this);

        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        contactList = db.getAllContacts();
        contactsAdapter = new ContactsAdapter(this,contactList,db);
        recyclerViewContacts.setAdapter(contactsAdapter);


    }
    public void openAddContactActivity(View view) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }
    public void openDeleteContactActivity(View view) {
        Intent intent = new Intent(this, DeleteContactActivity.class);
        startActivity(intent);
    }
    public void openUpdateContactActivity(View view) {
        Intent intent = new Intent(this, UpdateContactActivity.class);
        startActivity(intent);
    }
    public void openSearchContactActivity(View view) {
        Intent intent = new Intent(this, SearchContactActivity.class);
        startActivity(intent);
    }
    protected void onResume() {
        super.onResume();
        // Refresh contactList from database when returning to this activity
        contactList.clear();
        contactList.addAll(db.getAllContacts());
        contactsAdapter.notifyDataSetChanged();
    }

}