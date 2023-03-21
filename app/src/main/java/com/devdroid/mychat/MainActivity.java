package com.devdroid.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.devdroid.mychat.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
DatabaseReference databaseReference;

UserAdapter userAdapter;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userAdapter=new UserAdapter(this);
         binding.recycler.setLayoutManager(new LinearLayoutManager(this));
         binding.recycler.setAdapter(userAdapter);

        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userAdapter.clear();
                for(DataSnapshot datasnapshot:snapshot.getChildren()){
                    //                   String uid= snapshot.getKey();
//
//                    if(!uid.equals(FirebaseAuth.getInstance().getUid())){+
//                     Usermodel usermodel=datasnapshot.child(uid).getValue(Usermodel.class);
//                        userAdapter.add(usermodel);
 //                  }

                    Usermodel usermodel=datasnapshot.getValue(Usermodel.class);
                        userAdapter.add(usermodel);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         MenuInflater inflater=getMenuInflater();
         inflater.inflate(R.menu.main_menu,menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,AuthenticationActivity.class));
            finish();
            return true ;
        }
        return false;
    }
}