package com.devdroid.mychat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.MyViewholder>{
    private Context context;
    private List<Usermodel> usermodelList;

    public UserAdapter(Context context) {
        this.context = context;
        usermodelList=new ArrayList<>();
    }
    public void add(Usermodel usermodel){

        usermodelList.add(usermodel);
        notifyDataSetChanged();
    }
    public void clear(){
        usermodelList.clear();

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new MyViewholder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
          Usermodel usermodel=usermodelList.get(position);
          holder.name.setText(usermodel.getUsername());
        holder.email.setText(usermodel.getUserEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,chatActivity.class);
                intent.putExtra("id",usermodel.getUserId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usermodelList.size();
    }

    public static class MyViewholder extends RecyclerView.ViewHolder{
        private TextView name,email;
    public MyViewholder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.userName);
        email=itemView.findViewById(R.id.userEmail);
    }
}

}
