package com.example.funchat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    EditText et_msg;
    Button btn_send;

    String msg = "";
    ChatAdapter chatAdapter;

    List<ChatModel> chatModelList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance();

        et_msg = findViewById(R.id.chat_msg);
        btn_send = findViewById(R.id.chat_send);
        recyclerView = findViewById(R.id.chats_rv);


        chatModelList = new ArrayList<>();

        reference = database.getReference("messages");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatModelList.clear();
//                Log.d("data_debug",dataSnapshot.child("data").getValue().toString());
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String message = (String) snapshot.child("data").getValue();
                    String username = (String) snapshot.child("user").getValue();
                    String time = (String) snapshot.child("time").getValue();



                    ChatModel chatModel = new ChatModel();
                    chatModel.data = message;
                    chatModel.time = time;
                    chatModel.user = username;

                    chatModelList.add(chatModel);

                    chatAdapter =new ChatAdapter(ChatActivity.this,chatModelList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = et_msg.getText().toString();
                if (!msg.isEmpty()){
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    reference = database.getReference("messages").child(UUID.randomUUID().toString());
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
                    reference.child("data").setValue(msg);
                    reference.child("user").setValue(auth.getCurrentUser().getEmail());
                    reference.child("time").setValue(dateFormatter.format(new Date()));
                    et_msg.setText("");
                }
            }
        });

    }
}
