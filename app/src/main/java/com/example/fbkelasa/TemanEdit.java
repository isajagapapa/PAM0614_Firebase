package com.example.fbkelasa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbkelasa.database.Teman;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TemanEdit extends AppCompatActivity {
    TextView tv_Key;
    EditText ed_nama, ed_Telpon;
    Button btnEdit;
    DatabaseReference databaseReference;
    String kode,nama,telpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) { ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teman_edit);

        tv_Key = findViewById(R.id.tv_key);
        ed_nama = findViewById(R.id.eddNama);
        ed_Telpon = findViewById(R.id.eddTelpon);
        btnEdit = findViewById(R.id.btnEdit);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = this.getIntent().getExtras();
        kode = bundle.getString("kunci1");
        nama = bundle.getString("kunci2");
        telpon = bundle.getString("kunci3");

        tv_Key.setText(kode);
        ed_nama.setText(nama);
        ed_Telpon.setText(telpon);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTeman(new Teman(ed_nama.getText().toString(),ed_Telpon.getText().toString()));
            }
        });
    }
    public void editTeman(Teman teman){
        databaseReference.child("Teman")
                .child(kode)
                .setValue(teman)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(TemanEdit.this, "Data Berhasil Diupdate", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}