package com.example.mentsync.AfterLogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentsync.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {
    Context context;
    ArrayList<SearchUserItemModel> arrayList;

    SearchUserAdapter(Context context, ArrayList<SearchUserItemModel> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }
    //ye container for card banata ha
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.serachuseritem,parent,false);
        return new ViewHolder(view);
    }

    //ye data set kre ga container k upar
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(arrayList.get(position).profilepic).into(holder.image);
        holder.name.setText(arrayList.get(position).name);
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.profilepic);
            name=itemView.findViewById(R.id.name);
        }
    }

}
