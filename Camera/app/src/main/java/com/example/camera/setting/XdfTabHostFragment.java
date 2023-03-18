package com.example.camera.setting;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camerabg.R;
import com.example.camera.preference.IScenceModeRecyclerItemClickListener;
import com.example.camera.preference.IWhiteBalanceRecyclerItemClickListener;
import com.example.camera.preference.ScenceModeRecyclerAdapter;
import com.example.camera.preference.WhiteBalanceRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class XdfTabHostFragment extends Fragment  implements IScenceModeRecyclerItemClickListener, IWhiteBalanceRecyclerItemClickListener {
    private String TAG="MyCamera.XdfWhiteBalanceFragment";

//    private static IRecyclerItemClickListener mSenceModeItemListener;
//    private static IRecyclerItemClickListener mWhiteBalanceItemListener;

    private static IScenceModeRecyclerItemClickListener mSenceModeItemListener;
    private static IWhiteBalanceRecyclerItemClickListener  mWhiteBalanceItemListener;
    private static String mWhiteBalanceSelectedValue;
    private static String mScenceModeSelectedValue;
    private static List<String> mWhiteBalanceEntries = new ArrayList<>();
    private static List<String> mWhiteBalanceValues = new ArrayList<>();
    private static List<Integer> mWhiteBalanceIcons = new ArrayList<>();

    private static List<String> mScenceModeEntries = new ArrayList<>();
    private static List<String> mScenceModeEntryValues = new ArrayList<>();
    private static List<Integer> mScenceModeIcons = new ArrayList<>();

    private RecyclerView scenceModeRecycle;
    private RecyclerView whiteBalanceRecycle;
    private View        rootView;
    private TabHost tabHost;
    private static int currentIndex;

    private FrameLayout scenceModeTab;
    private FrameLayout  whiteBalanceTab;

    
    public void setCurrentIndex( int index ){
        currentIndex = index;
    }
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "1.onCreate ");
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "2.onCreateView inflater "+inflater+" container "+container+ container.getVisibility());
        super.onCreateView( inflater, container, savedInstanceState);
        if( tabHost == null ){
            tabHost = (TabHost) inflater.inflate(R.layout.xdf_tabhost_layout, container, false);
            tabHost.setup();

            inflater.inflate( R.layout.tab1, tabHost.getTabContentView());
            inflater.inflate( R.layout.tab2, tabHost.getTabContentView());
            tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("取景模式" , null).setContent(R.id.tab01));
            tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("白平衡" , null).setContent(R.id.tab02));
            tabHost.setOnTabChangedListener( new TabChangeListener());
            tabHost.setCurrentTab(currentIndex);
            TabWidget tabWidget = tabHost.findViewById( android.R.id.tabs);
            tabWidget.setStripEnabled( false );
            tabWidget.setDividerDrawable( null);
        }
        return  tabHost;
    }

    public void onViewCreated(View view,  Bundle savedInstanceState) {
        Log.d(TAG, "3.onViewCreated  ");
        if( tabHost !=null ){
            tabHost.setCurrentTab(currentIndex);
            String tabTag = tabHost.getCurrentTabTag();
            if( tabTag.equals("tab1")){

                scenceModeRecycle =tabHost.findViewById(R.id.scence_mode_recycler_view);
                LinearLayoutManager layoutManager=new LinearLayoutManager( getContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                scenceModeRecycle.setLayoutManager(layoutManager);
                ScenceModeRecyclerAdapter  scenceModeAdapter=new ScenceModeRecyclerAdapter(scenceModeRecycle, mScenceModeEntries,mScenceModeEntries, mScenceModeIcons  );
                scenceModeAdapter.setOnScenceModeRecylerItemClickListener( XdfTabHostFragment.this );
                scenceModeAdapter.setScenceModeSelectedValue(mScenceModeSelectedValue);
                scenceModeRecycle.setAdapter(scenceModeAdapter);
            }else if(tabTag.equals("tab2")){

                whiteBalanceRecycle =tabHost.findViewById(R.id.white_balance_recycler_view);
                LinearLayoutManager layoutManager=new LinearLayoutManager( getContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                whiteBalanceRecycle.setLayoutManager(layoutManager);
                WhiteBalanceRecyclerAdapter whiteBalanceAdapter=new WhiteBalanceRecyclerAdapter(whiteBalanceRecycle, mWhiteBalanceEntries,mWhiteBalanceValues, mWhiteBalanceIcons);
                whiteBalanceAdapter.setOnWhilteBalanceRecylerItemClickListener( XdfTabHostFragment.this );
                whiteBalanceAdapter.setWhiteBalanceSelectedValue(mWhiteBalanceSelectedValue);
                whiteBalanceRecycle.setAdapter(whiteBalanceAdapter);
            }
        }
    }
    public void setOnSconceModeRecylerItemClickListener(IScenceModeRecyclerItemClickListener listener) {
        mSenceModeItemListener =  listener;
    }
    public void setOnWhiteBalanceRecylerItemClickListener(IWhiteBalanceRecyclerItemClickListener listener) {
        mWhiteBalanceItemListener =  listener;
    }
    public void setWhiteBalanceSelectedValue(String selectedValue) {
        mWhiteBalanceSelectedValue = selectedValue;
    }
    public void setScenceModeSelectedValue(String selectedValue) {
        mScenceModeSelectedValue = selectedValue;
    }
    public void setWhiteBalanceEntriesAndEntryValues(List<String> entries,
                                         List<String> entryValues,
                                         List<Integer> icons) {
        mWhiteBalanceEntries = entries;
        mWhiteBalanceValues = entryValues;
        mWhiteBalanceIcons = icons;
    }

    public void setScenceModeEntriesAndEntryValues( List<String> entries,
                                                    List<String> entryValues,
                                                    List<Integer> icons){
        mScenceModeEntries = entries;
        mScenceModeEntryValues = entryValues;
        mScenceModeIcons = icons;
    }

    public void onRecylerItemClick(String entry) {
        Log.d(TAG, entry+" "+this);
        mWhiteBalanceSelectedValue = entry;
        mWhiteBalanceItemListener.onWhiteBalanceRecylerItemClick( entry, entry );
        mSenceModeItemListener.onScenceModeRecylerItemClick( entry);

        FragmentTransaction transaction = getActivity().getFragmentManager()
                .beginTransaction();
        getActivity().getFragmentManager().popBackStack();
        transaction.commit();
        Log.d(TAG, "onRecylerItemClick "+getActivity().getFragmentManager().getFragments().size()+" "+this);
    }

    @Override
    public void onScenceModeRecylerItemClick(String selectedValue) {
        Log.d(TAG, selectedValue+" "+this);
        mScenceModeSelectedValue = selectedValue;
        mSenceModeItemListener.onScenceModeRecylerItemClick( selectedValue);
//        取景模式点击后应停留在当前页面
//        FragmentTransaction transaction = getActivity().getFragmentManager()
//                .beginTransaction();
//        getActivity().getFragmentManager().popBackStack(null, 1);
//        transaction.commit();
//        scenceModeRecycle.invalidate();
        Log.d(TAG, "onRecylerItemClick "+getActivity().getFragmentManager().getFragments().size()+" "+this);
    }

    @Override
    public void onWhiteBalanceRecylerItemClick(String entry, String entryValue) {
        Log.d(TAG, entry+" "+this);
        mWhiteBalanceSelectedValue = entry;
        mWhiteBalanceItemListener.onWhiteBalanceRecylerItemClick( entry, entryValue );
//        白平衡点击后应停留在当前页面
//        FragmentTransaction transaction = getActivity().getFragmentManager()
//                .beginTransaction();
//        getActivity().getFragmentManager().popBackStack( null, 1);
//        transaction.commit();
//        whiteBalanceRecycle.invalidate();

        Log.d(TAG, "onRecylerItemClick "+getActivity().getFragmentManager().getFragments().size()+" "+this);
    }

    public class TabChangeListener implements TabHost.OnTabChangeListener{
        @Override
        public void onTabChanged(String tabId) {
            Log.d(TAG, "onTabChanged "+tabId);
            Toast.makeText( getContext(), tabId, Toast.LENGTH_SHORT).show();
            if( tabId.equals("tab1")){
                scenceModeRecycle =tabHost.findViewById(R.id.scence_mode_recycler_view);
                LinearLayoutManager layoutManager=new LinearLayoutManager( getContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                scenceModeRecycle.setLayoutManager(layoutManager);
                ScenceModeRecyclerAdapter scenceModeAdapter=new ScenceModeRecyclerAdapter(scenceModeRecycle, mScenceModeEntries,mScenceModeEntries, mScenceModeIcons );
                scenceModeAdapter.setOnScenceModeRecylerItemClickListener( XdfTabHostFragment.this );
                scenceModeRecycle.setAdapter(scenceModeAdapter);
            }else if(tabId.equals("tab2")){
                whiteBalanceRecycle =tabHost.findViewById(R.id.white_balance_recycler_view);
                LinearLayoutManager layoutManager=new LinearLayoutManager( getContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                whiteBalanceRecycle.setLayoutManager(layoutManager);
                WhiteBalanceRecyclerAdapter whiteBalanceAdapter=new WhiteBalanceRecyclerAdapter( whiteBalanceRecycle, mWhiteBalanceEntries,mWhiteBalanceEntries, mWhiteBalanceIcons );
                whiteBalanceAdapter.setOnWhilteBalanceRecylerItemClickListener( XdfTabHostFragment.this );
                whiteBalanceRecycle.setAdapter(whiteBalanceAdapter);
            }

        }
    }
}
