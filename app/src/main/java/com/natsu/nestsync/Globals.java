package com.natsu.nestsync;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.natsu.nestsync.models.User;

public class Globals {
    // nicht instanziierbarer Singelton, um Daten zu speichern, die über alle Klassen zugreifbar sein sollen
    private static final Globals instance = new Globals();
    private static FirebaseDatabase database;
    private static DatabaseReference rootRef;
    private Globals() {
        //hier Daten aus de Datenbank einlesen bzw Testdaten hart einkodieren
        User user = new User("uID", "name");

        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference("");

        //rootRef.setValue("Hello World");
        /*rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
    }

    public static Globals getInstance() {
        return instance;
    }

    //public static DatabaseReference getReference(){ return rootRef;}

    public static void setNewUser(String regName, String regMail){
        User user = new User (regName, regMail);
        rootRef.child("users").child("userID").setValue(user);
    }
}
