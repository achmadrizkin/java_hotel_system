package com.example.java_hotel_system.dao;

import com.example.java_hotel_system.model.kamar.Kamar;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaoKamar {
    private DatabaseReference databaseReference;

    public DaoKamar() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Kamar.class.getSimpleName());
    }

    public Task<Void> addKamar(Kamar kamar) {
        return databaseReference.push().setValue(kamar);
    }
}
