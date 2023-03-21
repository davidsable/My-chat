package com.devdroid.mychat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewholder> {
    private Context context;
    private List<MessageModel> messagemodelList;


    public MessageAdapter(Context context) {
        this.context = context;
        messagemodelList = new ArrayList<MessageModel>();
    }

    public void add(MessageModel messageModell) {

        messagemodelList.add(messageModell);
        notifyDataSetChanged();
    }

    public void clear() {
        messagemodelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        MessageModel messagemodel = messagemodelList.get(position);
        holder.msg.setText(messagemodel.getMessage());
        if (messagemodel.getSenderId().equals(FirebaseAuth.getInstance().getUid())) {
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.teal_700));
            holder.msg.setTextColor(context.getResources().getColor(R.color.white));

        }
        else {
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.black));
            holder.msg.setTextColor(context.getResources().getColor(R.color.white));


        }
    }

    @Override
    public int getItemCount() {
        return messagemodelList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        private TextView msg;
        private LinearLayout main;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.message);
            main = itemView.findViewById(R.id.mainmessageLayout);
        }
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel msg = messagemodelList.get(position);

        if (msg.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            return 1;
        }else {
            return 2;
        }


    }
}


