package com.sourceit.task1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sourceit.task1.R;
import com.sourceit.task1.model.Contact;
import com.sourceit.task1.utils.L;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int DEFAULT = 20;
    private final int ADD_ONE = 1;

    private ArrayList<Contact> contacts;
    private int numberOfContact;

    private RecyclerView contact_list;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        init();

        if (savedInstanceState == null) addContacts(DEFAULT);
        else addContacts(savedInstanceState.getInt("contactsSize"));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        contact_list.setLayoutManager(layoutManager);
        contact_list.setAdapter(new RecyclerViewAdapter(contacts, new OnItemClickWatcher<Contact>() {
            @Override
            public void onItemClick(View v, int position, Contact item) {
                openInformation(position);
            }
        }));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContacts(ADD_ONE);
                contact_list.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void addContacts(int count) {
        for (int i = 0; i < count; i++) {
            numberOfContact++;
            L.d("numberOfContact++" + numberOfContact);
            contacts.add(new Contact("Name" + numberOfContact, "Email@" + numberOfContact, "Adress " + numberOfContact, R.drawable.ic_contact_phone_black_48dp));
        }
    }

    private void init() {
        contact_list = (RecyclerView) findViewById(R.id.contact_list);
        add = (Button) findViewById(R.id.button_add);
        contacts = new ArrayList<>();
    }

    private void openInformation(int position) {
        Intent intent = new Intent(this, Information.class);
        Contact contact_temp = contacts.get(position);
        intent.putExtra("name", contact_temp.getName());
        intent.putExtra("email", contact_temp.getEmail());
        intent.putExtra("adress", contact_temp.getAdress());
        intent.putExtra("image", contact_temp.getImage());
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("contactsSize", contacts.size());
    }
}
