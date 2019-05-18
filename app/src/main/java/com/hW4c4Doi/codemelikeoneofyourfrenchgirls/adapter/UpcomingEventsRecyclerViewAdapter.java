package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network.FirebaseHelperClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UpcomingEventsRecyclerViewAdapter extends RecyclerView.Adapter<UpcomingEventsRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Event> eventList;
    private Activity activity;
    private FirebaseHelperClass helperClass;
    private SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm, EEE, d. MMM ", Locale.getDefault());


    public UpcomingEventsRecyclerViewAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.eventList = new ArrayList<>();
        this.helperClass = new FirebaseHelperClass();
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

        bundle.putParcelable("event", event);
        Glide.with(context).load(R.drawable.love).centerCrop().into(holder.ivEventImage);
        final FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(holder.tvEventName, "transition" + position)
                .addSharedElement(holder.ivEventImage, "transitionImage" + position)
                .build();
        if (event.getListOfUsersParticipatingInEvent().contains(FirebaseAuth.getInstance().getUid())) {
            holder.btnJoinEvent.setText("In event");
        }
        holder.tvEventName.setText(event.getName());
        holder.tvEventActivity.setText(event.getActivity());
        holder.tvEventPlayersNeeded.setText(5 + "");
        holder.tvEventTime.setText(sdfDate.format(event.getEventStart()));
        holder.layout.setOnClickListener(v -> Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.fragmentInsideEvent, bundle, null));
        holder.btnJoinEvent.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Join event?")
                    .setMessage("Are you sure you want to join event: " + event.getName() +
                            " ?").setPositiveButton("Yes", (dialog, which) -> {
                event.addUsersToArray(FirebaseAuth.getInstance().getUid());
                helperClass.joinEvent(event);
                Toast.makeText(context, "Event joined", Toast.LENGTH_SHORT).show();
                holder.btnJoinEvent.setText("In event");
                dialog.dismiss();
            }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();

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

    public void addAllEvents(List<Event> eventsList) {
        this.eventList.clear();
        this.eventList.addAll(eventsList);
        notifyDataSetChanged();
    }
}
