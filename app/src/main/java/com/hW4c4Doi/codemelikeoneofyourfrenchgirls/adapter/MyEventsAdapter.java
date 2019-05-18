package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;

public class MyEventsAdapter extends FirestoreRecyclerAdapter<Event, MyEventsAdapter.MyEventsHolder> {
    private Context context;
    private AppCompatActivity view;

    public MyEventsAdapter(@NonNull FirestoreRecyclerOptions<Event> options, Context mContext, AppCompatActivity view) {
        super(options);
        this.context = mContext;
        this.view = view;
    }


    @Override
    protected void onBindViewHolder(@NonNull MyEventsAdapter.MyEventsHolder holder, int position, @NonNull Event model) {
        final Bundle bundle = new Bundle();
        Event event = model;

        bundle.putParcelable("event", event);
        Glide.with(context).load(R.drawable.love).centerCrop().into(holder.ivEventImage);
        final FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(holder.tvEventName, "transition" + position)
                .addSharedElement(holder.ivEventImage, "transitionImage" + position)
                .build();

        holder.tvEventName.setText(model.getName());
        holder.tvEventActivity.setText(model.getActivity());
        holder.tvEventPlayersNeeded.setText("2");
        holder.tvEventTime.setText("26.april,11:00");
        holder.layout.setOnClickListener(v -> Navigation.findNavController(view, R.id.nav_host_fragment).navigate(R.id.fragmentInsideEvent, bundle, null));
        holder.btnJoinEvent.setText("In event");
        holder.btnJoinEvent.setClickable(false);

    }

    @NonNull
    @Override
    public MyEventsAdapter.MyEventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new MyEventsHolder(view);

    }

    public class MyEventsHolder extends RecyclerView.ViewHolder {
        TextView tvEventName;
        TextView tvEventActivity;
        TextView tvEventPlayersNeeded;
        ImageView ivEventImage;
        TextView tvEventTime;
        Button btnJoinEvent;
        ConstraintLayout layout;

        public MyEventsHolder(View itemView) {
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
}
