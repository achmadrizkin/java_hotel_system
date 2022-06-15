package com.example.java_hotel_system.dao;

import com.example.java_hotel_system.model.kamar.Kamar;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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
}
