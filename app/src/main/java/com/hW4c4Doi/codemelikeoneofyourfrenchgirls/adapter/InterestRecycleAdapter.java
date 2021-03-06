package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network.FirebaseHelperClass;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui.MyProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class InterestRecycleAdapter extends RecyclerView.Adapter<InterestRecycleAdapter.MyViewHolder> {
    private Context context;
    private List<String> interests;
    private FirebaseHelperClass helperClass;
    private MyProfileFragment myProfile;
    public InterestRecycleAdapter(Context context, MyProfileFragment myProfile) {
        this.context = context;
        this.interests = new ArrayList<>();
        this.helperClass = new FirebaseHelperClass();
        this.myProfile = myProfile;
    }

    @NonNull
    @Override
    public InterestRecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.interest_item, parent, false);


        return new MyViewHolder(view);
    }

    public void addInterest(String interest) {
        interests.add(interest);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvInterestName.setText(interests.get(position));
        for(String interest : myProfile.myUser.getInterests()){
            if(holder.tvInterestName.getText().equals(interest)){
                holder.checkbox.setChecked(true);
                holder.tvInterestName.setTextColor(Color.parseColor("#CE5036"));
                holder.line.setBackgroundColor(Color.parseColor("#CE5036"));
                break;
            }
        }
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    myProfile.myUser.removeInterest(holder.tvInterestName.getText().toString());
                    holder.tvInterestName.setTextColor(Color.parseColor("#D0D1D1"));
                    holder.line.setBackgroundColor(Color.parseColor("#D0D1D1"));

                }else{
                    holder.tvInterestName.setTextColor(Color.parseColor("#CE5036"));
                    holder.line.setBackgroundColor(Color.parseColor("#CE5036"));
                    myProfile.myUser.addInterest(holder.tvInterestName.getText().toString());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return interests.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvInterestName;
        CheckBox checkbox;
        View line;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvInterestName = itemView.findViewById(R.id.interestName);
            this.checkbox = itemView.findViewById(R.id.interestCB);
            this.line = itemView.findViewById(R.id.lower_line_interest);
        }
    }

}
