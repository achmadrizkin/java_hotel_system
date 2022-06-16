package com.example.java_hotel_system.dao;

import com.example.java_hotel_system.model.kamar.Kamar;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DaoKamar {
    private DatabaseReference databaseReference;

    public DaoKamar() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Kamar.class.getSimpleName());
    }

    public Task<Void> addKamar(Kamar kamar) {
        return databaseReference.push().setValue(kamar);
    }

    // query
    public Query getAll() {
        return databaseReference.orderByKey();
    }

    public Query searchKamar(String kamar) {
        return databaseReference.orderByChild("nama").startAt(kamar).endAt(kamar+ "~");
    }

    public Query getByHarga() {
        return databaseReference.orderByChild("harga").startAt("100").endAt("100" + "~");
    }

    public Query getByKota(String kotaKamar) {
        return databaseReference.orderByChild("kota").startAt(kotaKamar).endAt(kotaKamar + "~");
    }

    public Task<Void> remove(String key) {
        return databaseReference.child(key).removeValue();
    }

    public Task<Void> update(String key, HashMap<String ,Object> hashMap) {
        return databaseReference.child(key).updateChildren(hashMap);
    }
}
