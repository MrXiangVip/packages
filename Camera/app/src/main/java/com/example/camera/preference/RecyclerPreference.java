package com.example.camera.preference;


import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camerabg.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerPreference extends Preference implements IRecyclerItemClickListener {
    private String TAG ="MyCamera.RecyclerPreference";
    private RecyclerView recyclerView;

    private String mSelectedValue;
    private List<String> mEntries = new ArrayList<>();
    private List<String> mEntryValues = new ArrayList<>();
    private List<Integer> mIcons = new ArrayList<>();

    private RecyclerAdapter adapter;
    private  LayoutInflater inflater;

    private Context mContext;
    private View mRootView;
    private  IRecyclerItemClickListener mListener;

    public RecyclerPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
//        inflater = LayoutInflater.from(context);
    }

    public RecyclerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecyclerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerPreference(Context context) {
        super(context);
        mContext= context;
//        setLayoutResource(R.layout.recycler_preference_layout);
    }

    public void setEntriesAndEntryValues(List<String> entries,
                                         List<String> entryValues,
                                         List<Integer> icons ){
        Log.d(TAG, "setEntriesAndEntryValues");
        mEntries = entries;
        mEntryValues = entryValues;
        mIcons = icons;
    }
    public void setSelectedValue(String selectedValue) {
        mSelectedValue = selectedValue;
    }

    public void setOnRecylerItemClickListener(IRecyclerItemClickListener listener) {
        mListener = listener;

    }
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        Log.d(TAG, "onCreateView parent "+parent);
//        return super.onCreateView(parent);
        if( mRootView == null){
            inflater = LayoutInflater.from(mContext);
            mRootView = inflater.inflate(R.layout.recycler_preference_layout, null);
        }
        return mRootView;
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        Log.d(TAG, "onBindView view "+view);
        if( recyclerView ==null ){
            recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
        }

        if( adapter == null ){
            adapter=new RecyclerAdapter(mEntries,mEntryValues, mIcons );
            adapter.setOnRecylerItemClickListener( this );
            recyclerView.setAdapter(adapter);
        }

    }
    
    @Override
    public void onRecylerItemClick(String selectedValue) {
        mListener.onRecylerItemClick( selectedValue );
    }
}
