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

public class WhiteBalanceRecyclerAdapter extends RecyclerView.Adapter<WhiteBalanceRecyclerAdapter.ViewHolder> {

    private String TAG ="MyCamera.WhiteBalanceRecyclerAdapter";
    private RecyclerView  mRecyclerView;
//    private List<Cat> cats;
    private List<String> mEntries = new ArrayList<>();
    private List<String> mEntryValues = new ArrayList<>();
    private List<Integer> mIcons = new ArrayList<>();

    private IWhiteBalanceRecyclerItemClickListener  mListener;
    private String mWhiteBalanceSelectedValue;

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

    public WhiteBalanceRecyclerAdapter(RecyclerView recyclerView, List<String> entries,
                                       List<String> entryValues,
                                       List<Integer> icons) {
        mRecyclerView = recyclerView;
        mEntries = entries;
        mEntryValues = entryValues;
        mIcons = icons;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    public void setWhiteBalanceSelectedValue(String whiteBalanceSelectedValue){
        mWhiteBalanceSelectedValue = whiteBalanceSelectedValue;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String  name = mEntries.get(position);
        int id = mIcons.get(position);
        holder.image.setImageResource( id );
        holder.name.setText( name);

        if(mWhiteBalanceSelectedValue!=null && mWhiteBalanceSelectedValue.equals(name) ){
            holder.image.setSelected( true );
        }else{
            holder.image.setSelected( false );
        }
        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= holder.getAdapterPosition();
                String entry = mEntries.get(position);
                String entryValue = mEntryValues.get(position);
                Toast.makeText(v.getContext(), "你点击了 View " + entryValue, Toast.LENGTH_SHORT).show();
                mListener.onWhiteBalanceRecylerItemClick( entry, entryValue );
                Log.d(TAG, "onClick "+entryValue);
//
                if( mRecyclerView != null ){
                    int count = mRecyclerView.getChildCount();
                    for(int i =0; i< count; i++){
                        ViewGroup viewGroup = (ViewGroup) mRecyclerView.getChildAt( i );
                        ImageView imageView= (ImageView)viewGroup.findViewById(R.id.image);
                        imageView.setSelected(false);
                    }
//                    ViewGroup viewGroup = (ViewGroup) mRecyclerView.getChildAt( position );
//                    ImageView imageView= (ImageView)viewGroup.findViewById(R.id.image);
//                    imageView.setSelected(true);
                    holder.holderView.findViewById(R.id.image).setSelected(true);
                }
                mWhiteBalanceSelectedValue = entryValue;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public void setOnWhilteBalanceRecylerItemClickListener(IWhiteBalanceRecyclerItemClickListener listener) {
        mListener =  listener;
    }

}
