package com.example.teacoffeecounterapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacoffeecounterapp.R;
import com.example.teacoffeecounterapp.models.EmployeeChaiTeaInfo;

import java.util.List;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.MyViewHolder>   {

    Context mctx;
    List<EmployeeChaiTeaInfo> employeeChaiTeaInfosList;

    public UserAdapter(Context mctx, List<EmployeeChaiTeaInfo> list) {
        this.mctx = mctx;
        this.employeeChaiTeaInfosList = list;
    }
    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);

        return new UserAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
      EmployeeChaiTeaInfo employeeChaiTeaInfo=employeeChaiTeaInfosList.get(position);
        holder.tvName.setText("Username: " + " " +employeeChaiTeaInfo.getUsername());
        holder.tvValue.setText( "Value: " +  " " +employeeChaiTeaInfo.getValue());
    }

    @Override
    public int getItemCount() {
        return employeeChaiTeaInfosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvCount,tvValue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvValue = itemView.findViewById(R.id.tvValue);

        }
    }

}
