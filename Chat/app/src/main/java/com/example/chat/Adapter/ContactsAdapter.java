package com.example.chat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.chat.Contact.Contact;
import com.example.chat.database.DatabaseHelper;
import com.example.chat.PersonalActivity;
import com.example.chat.R;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<Contact> contacts;
    private DatabaseHelper db;
    private Context context; // 添加一个成员变量来保存上下文

    public ContactsAdapter(Context context,List<Contact> contacts) {
        this.contacts = contacts;
        this.context = context;
        this.db = new DatabaseHelper(context);
    }

    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public ContactsAdapter(Context context,List<Contact> contacts, DatabaseHelper db) {
        this.contacts = contacts;
        this.db = db;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName, textPhone;
        ImageButton btnDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textPhone = itemView.findViewById(R.id.textPhone);
            btnDelete = itemView.findViewById(R.id.btn_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                            // Perform your desired action here, for example:
                            Contact clickedContact = contacts.get(position);
                            if (clickedContact != null) {
                                Intent intent = new Intent(context, PersonalActivity.class);
                                intent.putExtra("contactName", clickedContact.getName());
                                intent.putExtra("contactPhone", clickedContact.getPhone());
                                intent.putExtra("contact_id", clickedContact.getId());
                                context.startActivity(intent);
                            }
                    }
                }
            });
            // Set click listener for delete button
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Perform delete operation
                        Contact contactToDelete = contacts.get(position);
                        deleteContact(contactToDelete); // Method to delete contact

                        // Remove from list and notify adapter
                        contacts.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }
        public void deleteContact(Contact contact) {
            // Perform delete operation using DatabaseHelper
            db.deleteContact(contact);

            // Remove from the adapter's data source
            contacts.remove(contact);

            // Notify adapter that item has been removed
            notifyDataSetChanged();
        }

        public void bind(Contact contact) {
            textName.setText(contact.getName());
            textPhone.setText(contact.getPhone());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
