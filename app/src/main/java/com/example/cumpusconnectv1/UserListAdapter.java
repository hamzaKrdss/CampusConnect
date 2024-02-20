package com.example.cumpusconnectv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private List<Cleaner> userList;
    private Context context;



    public UserListAdapter(Context context, List<Cleaner> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Cleaner user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTextView;
        private TextView userHobbyTextView;
        private TextView userMevkiTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            userHobbyTextView = itemView.findViewById(R.id.userHobbyTextView);
            userMevkiTextView = itemView.findViewById(R.id.userMevkiTextView);

        }

        public void bind(Cleaner user) {
            userNameTextView.setText(user.getFirstName() + " " + user.getSecondName());
            userHobbyTextView.setText(user.getHobi());
            userMevkiTextView.setText(user.getMevki());

        }
    }
}
