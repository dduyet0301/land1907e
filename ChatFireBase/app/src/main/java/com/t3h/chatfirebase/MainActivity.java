package com.t3h.chatfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t3h.chatfirebase.adapter.ChatAdapter;
import com.t3h.chatfirebase.databinding.ActivityMainBinding;
import com.t3h.chatfirebase.models.Chat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainListener, OnCompleteListener<Void>, OnFailureListener, ValueEventListener {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("Chat");

    private ActivityMainBinding binding;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        adapter = new ChatAdapter(getLayoutInflater());
        binding.lvChat.setAdapter(adapter);
        binding.setListener(this);

        //đọc dl về
        reference.addValueEventListener(this);
    }

    @Override
    public void onSendMessage() {
        String message = binding.edtMessage.getText().toString();
        if (message.isEmpty()) return;
        Chat chat = new Chat();
        chat.setUser("Duyet");
        chat.setMessage(message);
        reference.child(chat.getId() + "").setValue(chat) //gửi dl lên database với id
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        binding.edtMessage.setText("");
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        ArrayList<Chat> data = new ArrayList<>();
        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
            Chat c = snapshot.getValue(Chat.class);
            data.add(c);
        }
        adapter.setData(data);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
