package com.example.minhnguyen.tuongmin_sizebook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Declare variables name */
    ListView lv;
    TextView count;
    Button addNewButton;

    public static final String FileName ="data.json";
    public static List<Person> people = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Instantiate variables with objects of its kind */
        lv = (ListView) findViewById(R.id.aList);
        count = (TextView) findViewById(R.id.viewCount);
        addNewButton = (Button) findViewById(R.id.addNewButton);
        addNewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* When click Add New, open AddEdit activity */
                Intent intent = new Intent(v.getContext(), AddEdit.class);
                startActivity(intent);
            }
        });

        loadFromFile();
        List<String> people_name = new ArrayList<>();
        for (Person p : people) {
            people_name.add(p.getName());
        }

        /* The total number of entries. This is equal the length of the ID list created above */
        count.setText("Number of entry: " + String.valueOf(people.size()));

        /* Create an array adapter with the name list*/
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, people_name);
        lv.setAdapter(arrayAdapter);

            /* When click on an entry in the list view, open the AddEdit activity and send the ID
            * of that person to the AddEdit activity. The ID is the element at the index [position]
            * in the ID list. */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentEdit = new Intent(view.getContext(), AddEdit.class);
                intentEdit.putExtra("id", String.valueOf(position));
                startActivity(intentEdit);
            }
        });
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            people=gson.fromJson(in, new TypeToken<ArrayList<Person>>(){}.getType());
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            people=new ArrayList<Person>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
