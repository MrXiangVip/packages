package com.android.factorymode;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.android.factorymode.ext.FactoryGridAdapter;
import com.android.factorymode.util.FactoryBean;
import com.android.factorymode.util.Utils;

public class FactoryTestFragment extends Fragment {

    private static final String TAG = Utils.TAG+"GridItemFragment";
    private FactoryGridAdapter  mAdapter;
    private GridView            mGridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_factory_test, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGridView(view);
    }

    private void initGridView(View mContentView ) {
        Log.d(TAG, "initGridView: ");
        mGridView = mContentView.findViewById( R.id.grid_factory );

        //为数据绑定适配器
        mAdapter = new FactoryGridAdapter(getContext());
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: "+ position+" : " +id);
                FactoryBean bean = mAdapter.getItemBean( position );
//                Intent intent = new Intent();
//                String action = bean.getFragment();
//                Log.d(TAG, "onItemClick: "+ action);
//                intent.setAction( action );
//                startActivityForResult( intent , Utils.REQUEST_CODE);
                ((FactoryMainActivity)getActivity()).pushBackFragment( bean.getFragment(), null);
            }
        });
    }
}
