package com.example.camera.setting;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.camera.preference.IRecyclerItemClickListener;
import com.example.camerabg.R;
import com.example.camera.preference.RecyclerAdapter;
import com.example.camera.preference.RecyclerPreference;

import java.util.ArrayList;
import java.util.List;

public class XdfSceneModeFragment extends PreferenceFragment implements IRecyclerItemClickListener {
    private RecyclerPreference recyclerPreference;
    String TAG ="MyCamera.XdfSceneModeFragment";
    private RecyclerView recyclerView;


    private RecyclerAdapter adapter;


    private ViewGroup  mContainer;
    private String mSelectedValue;
    private List<String> mEntries = new ArrayList<>();
    private List<String> mEntryValues = new ArrayList<>();
    private List<Integer> mIcons = new ArrayList<>();
    PreferenceScreen screen;
    private IRecyclerItemClickListener mListener;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        addPreferencesFromResource(R.xml.xdf_scene_mode_selector_preference);

        screen = getPreferenceScreen();
        recyclerPreference = new RecyclerPreference(getActivity());
        recyclerPreference.setSelectedValue(mSelectedValue);
        recyclerPreference.setEntriesAndEntryValues(mEntries,mEntryValues, mIcons);
        recyclerPreference.setOnRecylerItemClickListener( this );
        screen.addPreference(recyclerPreference);

//        recyclerPreference =(RecyclerPreference) findPreference("scene_mode_selector_prefs");
//        recyclerPreference.setSelectedValue(mSelectedValue);
//        recyclerPreference.setEntriesAndEntryValues(mEntries,mEntryValues, mIcons);
//        recyclerPreference.setOnRecylerItemClickListener( this );

    }
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "1.onAttach");
    }
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        Log.d(TAG, "2.onCreateView inflater "+inflater+" container "+container+ container.getVisibility());
//        View view = inflater.inflate(R.layout.recycler_preference_layout, container, false);
//        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(layoutManager);
//        if( adapter == null ){
//            adapter=new RecyclerAdapter(mEntries,mEntryValues, mIcons );
//            adapter.setOnRecylerItemClickListener( this );
//            recyclerView.setAdapter(adapter);
//        }
        mContainer = container;
        return  super.onCreateView(inflater, container, savedInstanceState);
//        return view;
    }

    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        screen = getPreferenceScreen();
        int preferenceSize =screen.getPreferenceCount();

        Log.d(TAG, "3.onViewCreated prefrenceSize"+preferenceSize+"  visible "+view.getVisibility());

    }
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        screen = getPreferenceScreen();
        int preferenceSize =screen.getPreferenceCount();
        Log.d(TAG, "4.onActivityCreated preferenceSize "+preferenceSize + this.isVisible());

    }

    public void onStart() {
        super.onStart();
        Log.d(TAG, "5.onStart "+this.isVisible());

    }

    public void onResume() {
        super.onResume();
        screen = getPreferenceScreen();
        int preferenceCount =screen.getPreferenceCount();
        Preference preference = screen.getPreference(0);

        Log.d(TAG, "6.onResume "+this.isVisible() +" "+mContainer.getVisibility()+" preferenceCount " +preferenceCount+" "+(preference==null?" get failed ":"get succed")) ;

    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "7.onPause");

    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "8.onDestroyView");

    }
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "9.onDestroy");

    }

    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "10.onDetach");

    }

    @Override
    public void onRecylerItemClick(String selectedValue) {
        Log.d(TAG, "onRecylerItemClick "+ getFragmentManager().getFragments().size()+" "+this);
        mSelectedValue = selectedValue;
        mListener.onRecylerItemClick( selectedValue );
        FragmentTransaction transaction = getActivity().getFragmentManager()
                .beginTransaction();
        getActivity().getFragmentManager().popBackStack();
        transaction.commit();
        Log.d(TAG, "onRecylerItemClick "+getActivity().getFragmentManager().getFragments().size()+" "+this);

    }

    public void setOnRecylerItemClickListener(IRecyclerItemClickListener listener) {
        mListener = listener;
    }

    public void setSelectedValue(String selectedValue) {
        mSelectedValue = selectedValue;
    }
    public void setEntriesAndEntryValues(List<String> entries,
                                         List<String> entryValues,
                                         List<Integer> icons) {
        mEntries = entries;
        mEntryValues = entryValues;
        mIcons = icons;
    }
}
