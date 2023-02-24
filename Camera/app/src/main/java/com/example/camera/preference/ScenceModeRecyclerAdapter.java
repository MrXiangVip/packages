package com.example.camera.preference;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.camerabg.R;

import java.util.ArrayList;
import java.util.List;

public class ScenceModeRecyclerAdapter extends RecyclerView.Adapter<ScenceModeRecyclerAdapter.ViewHolder> {

    private String TAG ="MyCamera.ScenceModeRecyclerAdapter";
//    private List<Cat> cats;
    private List<String> mEntries = new ArrayList<>();
    private List<String> mEntryValues = new ArrayList<>();
    private List<Integer> mIcons = new ArrayList<>();

    private IScenceModeRecyclerItemClickListener  mListener;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View holderView;
        ImageView image;
        TextView name;

        public ViewHolder(View view) {
            super(view);
            holderView = view;
            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
        }
    }

    public ScenceModeRecyclerAdapter(List<String> entries,
                                     List<String> entryValues,
                                     List<Integer> icons) {
        mEntries = entries;
        mEntryValues = entryValues;
        mIcons = icons;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                String name = mEntries.get(position);
                Toast.makeText(v.getContext(), "你点击了 View " + name, Toast.LENGTH_SHORT).show();
                mListener.onScenceModeRecylerItemClick( name );
                Log.d(TAG, "onClick "+name);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String  name = mEntries.get(position);
        int id = mIcons.get(position);
        holder.image.setImageResource( id );
        holder.name.setText( name);
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public void setOnScenceModeRecylerItemClickListener(IScenceModeRecyclerItemClickListener listener) {
        mListener =  listener;
    }

}
