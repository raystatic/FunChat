package com.example.funchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    Context context;
    List<ChatModel> chatModels;

    public ChatAdapter(Context context, List<ChatModel> chatModels) {
        this.context = context;
        this.chatModels = chatModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_adapter_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatModel chatModel = chatModels.get(position);

        holder.msg.setText(chatModel.data);
        holder.time.setText(chatModel.time);
        holder.username.setText(chatModel.user);

    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView msg, username, time;

        public MyViewHolder(View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.chat_adapter_msg);
            username = itemView.findViewById(R.id.email_adapter_chat);
            time = itemView.findViewById(R.id.time_adapter_chat);
        }
    }

}
