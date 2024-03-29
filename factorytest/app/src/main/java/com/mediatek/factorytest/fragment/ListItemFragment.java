package com.mediatek.factorytest.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mediatek.factorytest.R;
import com.mediatek.factorytest.model.FactoryBean;
import com.mediatek.factorytest.model.FactoryDatas;
import com.mediatek.factorytest.utils.Utils;

import java.util.Iterator;
import java.util.List;


//import androidx.fragment.app.Fragment;

@SuppressLint("NewApi")
public class ListItemFragment extends Fragment {

    private static final String TAG = Utils.TAG+"ListItemFragment";

    private ListView            mListView;
    private FactoryListAdapter  mAdapter;

    private HandlerThread       mHandlerThread ;
    private Handler             handler;
    private List<FactoryBean>   mDatas;
    private View                mContentView;
    private Toolbar             mToolbar;
    private int                 handlerStatus=0;
    private Button              play;
    private Button              pause;
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        mContentView = inflater.inflate(R.layout.list_item_fragment,container, false);

        mDatas = FactoryDatas.getInstance( getContext()).getListFactoryBean();

        initListView();
        initOtherViews();
        mHandlerThread = new HandlerThread( "handler-thread") ;
        //开启一个线程
        mHandlerThread.start();
        handler =  new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int what = msg.what;
                switch ( what ){
                    case Utils.START:
                        Log.d(TAG, "handleMessage: "+what);
                        try {
                            Log.d(TAG, "sleep: ");
//                            Thread.sleep(500);
                            Thread.sleep(0);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if( mDatas != null && (handlerStatus != -1) ) {
                            Iterator<FactoryBean> it = mDatas.iterator();
                            while(it.hasNext()) {
                                FactoryBean bean = it.next();
                                int status  = bean.getStatus();
                                if( status == Utils.NONE ) {
                                    String action = bean.getAction();
                                    Intent intent = new Intent();
                                    intent.setAction(action);
                                    startActivityForResult(intent, Utils.REQUEST_CODE_CIRCLE);
                                    break;
                                }
                            }
                        }
                        break;
                    case Utils.PAUSE:
                        Log.d(TAG, "handleMessage: "+what);
                        handlerStatus = -1;
                        break;
                    default:
                        break;
                }
            }
        };

        return mContentView;
    }

    public void onStart( ){
        super.onStart();
        Log.d(TAG, "onStart: ");

    }

    private void initListView() {
        Log.d(TAG, "initListView: ");
        mListView = mContentView.findViewById( R.id.list_factory );

        //为数据绑定适配器
        mAdapter = new FactoryListAdapter(getContext());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: "+ position+" : " +id);
                FactoryBean bean = mAdapter.getItemBean( position );
                Intent intent = new Intent();
                String action = bean.getAction();
                Log.d(TAG, "onItemClick: "+ action);
                intent.setAction( action );
                startActivityForResult( intent , Utils.REQUEST_CODE);

            }
        });
    }
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
//        changeButtonColor();
        updateOtherView();
        mAdapter.notifyDataSetChanged();
    }


    public  void onActivityResult( int requestCode, int resultCode, Intent data ){
        Log.d(TAG, "onActivityResult: requestCode"+requestCode+ " resultCode "+requestCode +" "+data);
        if( data != null ){
            try {
                if (requestCode == Utils.REQUEST_CODE_CIRCLE) {

                    String factory = data.getStringExtra(Utils.KEY);
                    int status = data.getIntExtra(Utils.TEST_RESULT, Utils.NONE);
                    FactoryDatas.updateBeanStatus(factory, status);
                    FactoryDatas.getInstance(getContext()).storeDatas(mDatas);
                    //                Sleep();
                    Message message = Message.obtain();
                    message.what = Utils.START;
                    handler.sendMessage(message);
                } else if (requestCode == Utils.REQUEST_CODE) {
                    String factory = data.getStringExtra(Utils.KEY);
                    int status = data.getIntExtra(Utils.TEST_RESULT, Utils.NONE);
                    FactoryDatas.updateBeanStatus(factory, status);
                    FactoryDatas.getInstance(getContext()).storeDatas(mDatas);
                }
            }catch(Exception e){
                Log.d(TAG, "onActivityResult: ", e.fillInStackTrace());
                e.printStackTrace();
            }
        }

    }


    public void onDestroy() {
        // TODO Auto-generated method stub
        //Settings.System.putString(getContentResolver(),Settings.System.HOME_KEY,"no");////fangfengfan delete

        super.onDestroy();

        FactoryDatas.getInstance(getContext()).storeDatas( mDatas );
    }

    private void initOtherViews( ) {

            mToolbar = mContentView.findViewById( R.id.list_fragment_toolbar );

            TextView mTextView = mToolbar.findViewById(R.id.action_bar_title);
            mTextView.setText( getText(R.string.app_name) );

            play = mToolbar.findViewById(R.id.action_bar_play );
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlerStatus=0;
                    Message message = Message.obtain();
                    message.what = Utils.START;
                    handler.sendMessage( message );
                }
            });

            pause = mToolbar.findViewById(R.id.action_bar_pause );
            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message message = Message.obtain();
                    message.what = Utils.PAUSE;
                    handler.sendMessage( message );

                }
            });


            mToolbar.inflateMenu( R.menu.list_fragment_menu );
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch ( item.getItemId() ){
                        case R.id.menu_test_report:
                            Intent miIntent = new Intent();
                            miIntent.setAction("com.mediatek.intent.action.ALL_REPORT");
                            //miIntent.putExtra("textall", true);
                            startActivityForResult(miIntent, Utils.REQUEST_CODE );
                            break;

                        case R.id.clear_status:
                            FactoryDatas.getInstance(getContext()).cleanDatasStatus();
                            updateOtherView();
                            mAdapter.notifyDataSetChanged();

                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
    }

    public void updateOtherView() {
            TextView  textDesc = mToolbar.findViewById(R.id.action_bar_desc);
            textDesc.setText( "("+ FactoryDatas.getPassedBeanSize()+"/"+FactoryDatas.getInstance(getContext()).getListFactoryBean().size()+")");
    }


}
