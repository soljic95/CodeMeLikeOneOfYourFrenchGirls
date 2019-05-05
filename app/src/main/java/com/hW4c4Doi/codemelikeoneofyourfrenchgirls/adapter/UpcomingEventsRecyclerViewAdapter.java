package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;

import java.util.ArrayList;
import java.util.List;

public class UpcomingEventsRecyclerViewAdapter extends RecyclerView.Adapter<UpcomingEventsRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Event> eventList;
    private Activity activity;

    public UpcomingEventsRecyclerViewAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.eventList = new ArrayList<>();
    }

    @NonNull
    @Override
    public UpcomingEventsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);


        return new MyViewHolder(view);
    }

    public void addEvent(Event event) {
        eventList.add(event);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Bundle bundle = new Bundle();
        Event event = eventList.get(position);
        event.setImageTransationName("transitionImage" + position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.ivEventImage.setTransitionName("transitionImage" + position);
        }
        bundle.putParcelable("event", event);
        Glide.with(context).load(event.getImageLocation()).centerCrop().into(holder.ivEventImage);
        final FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(holder.tvEventName, "transition" + position)
                .addSharedElement(holder.ivEventImage, "transitionImage" + position)
                .build();
        holder.tvEventName.setText(eventList.get(position).getEventName());
        holder.tvEventActivity.setText(eventList.get(position).getEventActivity());
        holder.tvEventPlayersNeeded.setText(5 + "");
        holder.tvEventTime.setText("26.april,11:00");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.fragmentInsideEvent, bundle, null, extras);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvEventName;
        TextView tvEventActivity;
        TextView tvEventPlayersNeeded;
        ImageView ivEventImage;
        TextView tvEventTime;
        Button btnJoinEvent;
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvEventName = itemView.findViewById(R.id.tvEventName);
            this.tvEventActivity = itemView.findViewById(R.id.tvEventActivity);
            this.tvEventPlayersNeeded = itemView.findViewById(R.id.tvEventPlayersNeeded);
            this.tvEventTime = itemView.findViewById(R.id.tvEventTime);
            this.btnJoinEvent = itemView.findViewById(R.id.btnJoinEvent);
            this.ivEventImage = itemView.findViewById(R.id.ivEventImage);
            this.layout = itemView.findViewById(R.id.constraintLayout);
        }
    }
    public void addAllEvents(List<Event> eventsList){
        this.eventList.clear();
        this.eventList.addAll(eventsList);
        notifyDataSetChanged();
    }
}
