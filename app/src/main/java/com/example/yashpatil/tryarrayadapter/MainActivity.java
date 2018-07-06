package com.example.yashpatil.tryarrayadapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private EditText idText;
    private EditText nameText;
    private Button addButton;

    private String newId = "";
    private String newName = "";
    private int index = 0;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;

    SwipeRefreshLayout swipeRefreshLayout;

    EmployeeAdapter adapter;
    ArrayList<Employee> employeeArrayList;
    ArrayList<String> keys;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("list");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        idText = (EditText) findViewById(R.id.employee_id);
        nameText = (EditText) findViewById(R.id.employee_name);
        addButton = (Button) findViewById(R.id.addNewEmp);


        employeeArrayList = new ArrayList<Employee>();
        keys = new ArrayList<>();
//        employeeArrayList.add(new Employee("2016120036", "Yash Patil"));
//        employeeArrayList.add(new Employee("1234567890", "SomeOne Hello"));


        adapter = new EmployeeAdapter(this, employeeArrayList);

        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);

        index = 0;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (childEventListener == null) {
                    childEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        Product newProduct = dataSnapshot.getValue(Product.class);
//                        productList.add(newProduct);
                            Employee newEmployee = dataSnapshot.getValue(Employee.class);
                            employeeArrayList.add(newEmployee);
                            keys.add(dataSnapshot.getKey());

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            Log.d("DEBUG", "previousChildNam$$#" + s);
                            Log.d("DEBUG", "indexofThisKey@@@@@#" + keys.indexOf(dataSnapshot.getKey()));
//                            employeeArrayList.set(employeeArrayList.indexOf(databaseReference.child(s)), dataSnapshot.getValue(Employee.class));
                            employeeArrayList.set(keys.indexOf(dataSnapshot.getKey()), dataSnapshot.getValue(Employee.class));

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    databaseReference.addChildEventListener(childEventListener);
                }

                adapter.notifyDataSetChanged();
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }
        });

//        try {
//            adapter.notifyDataSetChanged();
//        } catch (ArrayIndexOutOfBoundsException e) {
//            e.printStackTrace();
//        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("emp", employeeArrayList.get(i));
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                try {
//                    e = (Employee) adapterView.getItemAtPosition(i);
//                    index = employeeArrayList.indexOf(e);
//                    employeeArrayList.remove(index);
//                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                    Toast.makeText(getApplicationContext(), "ID :" + e.getId() + " Name :" + e.getName(),
//                            Toast.LENGTH_SHORT).show();
//
//                    startActivity(intent);
//                } finally {
//                    employeeArrayList.add(index, e);
//                }
//
//
//            }
//        });

//        idText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().length() == 0) {
//                    addButton.setEnabled(false);
//                } else {
//                    addButton.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                newId = String.valueOf(charSequence);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        nameText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().length() == 0) {
//                    addButton.setEnabled(false);
//                } else {
//                    addButton.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                newName = String.valueOf(charSequence);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                newId = idText.getText().toString();
                newName = nameText.getText().toString();
//                employeeArrayList.add(new Employee(newId, newName));
                Employee newEmployee = new Employee(newId, newName);
                databaseReference.push().setValue(newEmployee);
                idText.setText("");
                nameText.setText("");
            }
        });

    }
}
