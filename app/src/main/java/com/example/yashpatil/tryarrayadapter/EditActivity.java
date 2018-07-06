package com.example.yashpatil.tryarrayadapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    private EditText id;
    private EditText name;
    private Button update;

    private Employee emp = null;

    private String newId = "", newName = "";

    HashMap<String, Object> hashMap;
    Employee updatedEmployee;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("list");

//        emp = MainActivity.e;

        id = (EditText) findViewById(R.id.newIdUpdated);
        name = (EditText) findViewById(R.id.newNameUpdated);
        update = (Button) findViewById(R.id.button);

        hashMap = new HashMap<>();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newId = id.getText().toString();
                newName = name.getText().toString();

                updatedEmployee = new Employee(newId, newName);

                hashMap.put("id", updatedEmployee.getId());
                hashMap.put("name", updatedEmployee.getName());
                Intent i = getIntent();
                final Employee oldEmployee = (Employee) i.getSerializableExtra("emp");


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot childDataSnapshot2 : dataSnapshot.getChildren()) {
                            Log.d("DEBUG:", "Child Employee found#" + childDataSnapshot2.getValue(Employee.class));
                            if (oldEmployee.getId().equals(childDataSnapshot2.getValue(Employee.class).getId())) {
                                Log.d("DEBUG:", "Equal object employee found$$$");
                                databaseReference.child(childDataSnapshot2.getKey()).updateChildren(hashMap);
                            }

                        }

//                        Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
//
//                        while (dataSnapshotIterator.hasNext()) {
//                            Employee e = dataSnapshotIterator.next().getValue(Employee.class);
//
//                            if (e.getId().equals(oldEmployee.getId())) {
////                                Toast.makeText(EditActivity.this, "Employee Found" , Toast.LENGTH_LONG).show();
//                                Log.d("DEBUG:", "Employee found#" + dataSnapshot.child(e.getName()));
//
//
//                            } else
//                                Log.d("DEBUG:", "Employee not found#" + e.toString());
//                            Log.d("DEBUG:", "Old Employee#" + oldEmployee.toString());
//                        }

//                        Map<String, Employee> td = (HashMap<String, Employee>) dataSnapshot.getValue();

//                        for (Map.Entry<String, Employee> map : td.entrySet()) {
//                            if ( map.getValue().equals(oldEmployee)) {
//                                Toast.makeText(EditActivity.this, "Update Successful#" + map.getKey(), Toast.LENGTH_LONG).show();
//                            }
//                        }
//                        dataSnapshot.getRef().child(oldEmployee.getId()).setValue(updatedEmployee.getId());
//                        dataSnapshot.getRef().child(oldEmployee.getName()).setValue(updatedEmployee.getName());
//                        databaseReference.child("-LFqIguPNYL0EZ_DWazU").updateChildren(hashMap);

//                        Toast.makeText(EditActivity.this, "Update Successful#" + td.values(), Toast.LENGTH_LONG).show();
//                        Toast.makeText(EditActivity.this, "Update Successful#" + td.keySet(), Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                databaseReference.updateChildren(hashMap);
//                Snackbar.make(findViewById(R.id.swipeRefreshLayout), "Updated#", Snackbar.LENGTH_SHORT).show();
//                Toast.makeText(EditActivity.this, "Update Successful#", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


//        if (id.getText().equals("") || name.getText().equals("")) {
//            update.setEnabled(false);
//        }
//
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity.e.setId(newId);
//                MainActivity.e.setName(newName);
//                finish();
//
//            }
//        });

    }
}
