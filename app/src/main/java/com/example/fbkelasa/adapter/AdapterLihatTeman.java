package com.example.fbkelasa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbkelasa.LihatTeman;
import com.example.fbkelasa.MainActivity;
import com.example.fbkelasa.R;
import com.example.fbkelasa.TemanEdit;
import com.example.fbkelasa.database.Teman;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterLihatTeman extends RecyclerView.Adapter<AdapterLihatTeman.ViewHolder> {
    private ArrayList<Teman> daftarTeman;
    private Context context;
    DatabaseReference database;

    public AdapterLihatTeman(ArrayList<Teman> daftarTeman, Context context) {
        this.daftarTeman = daftarTeman;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teman,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String kode, nama, telpon;

        nama = daftarTeman.get(position).getNama();
        kode = daftarTeman.get(position).getKode();
        telpon = daftarTeman.get(position).getTelpon();

        holder.tvNama.setText(nama);

        holder.tvNama.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu pm = new PopupMenu(v.getContext(),v);
                pm.inflate(R.menu.menuteman);

                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit:
                                Bundle bundle = new Bundle();
                                bundle.putString("kunci1", kode);
                                bundle.putString("kunci2", nama);
                                bundle.putString("kunci3", telpon);

                                Intent intent = new Intent(v.getContext(), TemanEdit.class);
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);
                                break;

                            case R.id.hapus:
                                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                                alertDlg.setTitle("Yakin data "+nama+" akan dihapus?");
                                alertDlg.setMessage("Tekan 'Ya' untuk menghapus")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DeleteData(kode);
                                                Toast.makeText(v.getContext(),"Data "+ nama + " berhasil dihapus",Toast.LENGTH_LONG).show();
                                                Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                                                v.getContext().startActivity(intent1);
                                            }
                                        })
                                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog aDlg = alertDlg.create();
                                aDlg.show();
                                break;
                        }
                        return true;
                    }
                });
                pm.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarTeman.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNama;

        ViewHolder(View v){
            super(v);
            tvNama = v.findViewById(R.id.tv_nama);
            database = FirebaseDatabase.getInstance().getReference();
        }
    }

    public void DeleteData(String kd){
        if (database != null){
            database.child("Teman").child(kd).removeValue();
        }
    }
}
