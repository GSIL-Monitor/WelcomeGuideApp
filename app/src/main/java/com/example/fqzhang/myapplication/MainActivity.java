package com.example.fqzhang.myapplication;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fqzhang.myapplication.Util.CustomTranslateUtil;
import com.example.fqzhang.myapplication.fragment.MDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private List<View> transitionViewList = new ArrayList<>();
    private List<String> datas = new ArrayList<>();
    private TextView reloadTextView, getfirstVisibleTv, chatTextView, emailTextView, telTextView;
    private ListView showlistView;
    private MyAdapter mAdapter;
    private boolean isReLoad;
    private LinearLayout bottomView;
    private float moveY = 0.0f;
    private boolean isVisible = false;
    private PopupWindow pop;
    private SharedPreferences sp;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private boolean isFirstShow = false;
    private FloatingActionButton fab;
    private View mTopView;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        bindData(false);
        setListener();
        reloadTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (reloadTextView.getMeasuredWidth() > 0) {
                    showPop();
                    reloadTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isReLoad && hasFocus) {
            bindData(isReLoad);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MainActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "onResume");
        clearListTransitionAnims();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity", "onStop");
    }

    private void showDialog() {
        //isReLoad = true;
        int alertDialogStyle = R.attr.alertDialogStyle;
/*        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog);
        builder.setIcon(android.R.drawable.btn_dialog)
                .setTitle("提醒")
                .setMessage("hello world!")
                .setCancelable(true)
                .setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // bindData();
            }
        });
        builder.create().show();*/
        MDialogFragment dialogFragment = MDialogFragment.newInstance();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(dialogFragment, "mdialogFragment");
        ft.commit();
    }

    private void setListener() {
        reloadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                mAdapter.setDatas(datas);
                mAdapter.notifyDataSetChanged();
                Log.e("zfq", "重新绑定了");
                showDialog();
            }
        });
        getfirstVisibleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int headerViewsCount = showlistView.getHeaderViewsCount();
                final int firstVisiblePosition = showlistView.getFirstVisiblePosition();
                Toast.makeText(MainActivity.this, headerViewsCount + ":" + firstVisiblePosition, Toast.LENGTH_SHORT).show();
                showlistView.setSelection(1);
                // showlistView.scrollTo(0,0);
                int top = (showlistView.getChildAt(0) == null) ? 0 : v.getTop();
//                showlistView.setSelectionFromTop(firstVisiblePosition,top);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getfirstVisibleTv.setText("当前" + firstVisiblePosition);
                    }
                });
            }
        });
        showlistView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("zfq", firstVisibleItem + ":" + visibleItemCount + ":" + totalItemCount);
            }
        });
        showlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final int pos = position - 1;
                if (!datas.get(pos).contains(":")) {
                    view.setEnabled(false);
                    return;
                }
                //TODO 动画
                startExitTransition(showlistView,position,300);
  /*              CustomTranslateUtil.getInstance().hideViewWithAlpha(view, CustomTranslateUtil.DOWN_TO_TOP, 300, 200, true);
                transitionViewList.add(view);*/

                showlistView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", pos);
                        bundle.putString("detail", datas.get(pos));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        MainActivity.this.overridePendingTransition(0,0);
                    }
                },300);
            }
        });
        showlistView.setOnTouchListener(new View.OnTouchListener() {
            private float lastY = 0;
            private float startY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int motionEvent = event.getActionMasked();
                switch (motionEvent) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveY = event.getY();
                        if (moveY - lastY < -10 && moveY - startY < -20) {
                            doTopAndBottomAnimation(-1);
                        } else if (moveY - lastY > 10 && moveY - startY > 20) {
                            doTopAndBottomAnimation(1);
                        }
                        lastY = moveY;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        chatTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop != null && pop.isShowing()) {
                    sp.edit().putBoolean("isFirstShow", true).commit();
                    pop.dismiss();
                    pop = null;
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "this is a floatActionButton!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPop() {
        sp = getPreferences(Context.MODE_APPEND);
        boolean isFirstShow = sp.getBoolean("isFirstShow", false);
        if (!isFirstShow) {
            TextView tv = new TextView(this);
            tv.setTextColor(Color.parseColor("#aaddee"));
            tv.setText("点这里，开始聊天！");
            tv.setTextSize(16);
            pop = new PopupWindow(tv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            pop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eeeeee")));
            pop.setOutsideTouchable(false);
            pop.setFocusable(false);
            pop.showAsDropDown(reloadTextView, 15, 0);
        }
    }

    private void doTopAndBottomAnimation(int deltaY) {
        if (deltaY > 0 && isVisible) {
            isVisible = false;
            moveY = moveY - 0;
            //topViewInAnimation(350);
            bottomViewInAnimation(350);
        }
        if (deltaY < 0 && !isVisible) {
            isVisible = true;
            moveY = moveY + 0;
            //topViewOutAnimation();
            bottomViewOutAnimation();
        }
    }

    public void bottomViewInAnimation(int durationTime) {
        final ObjectAnimator animPushIn = ObjectAnimator.ofFloat(bottomView, "translationY", bottomView.getHeight(), 0);
        animPushIn.setInterpolator(new LinearInterpolator());
        animPushIn.setDuration(durationTime);
        animPushIn.start();
    }

    public void bottomViewOutAnimation() {
        final ObjectAnimator animPushOut = ObjectAnimator.ofFloat(bottomView, "translationY", 0, bottomView.getHeight());
        animPushOut.setInterpolator(new LinearInterpolator());
        animPushOut.setDuration(350);
        animPushOut.start();
    }

    private void bindData(boolean isReload) {

        if (isReload) {
            List<String> list = new ArrayList<>();
            list.add("张顺1:zzz");
            list.add("张顺2:zzz");
            list.add("张顺3:zzz");
            mAdapter.setDatas(list);
            mAdapter.notifyDataSetInvalidated();
        } else {
            mAdapter = new MyAdapter(datas, this);
            //showlistView.setLayoutAnimation(getAnimationController());
            showlistView.addHeaderView(View.inflate(this, R.layout.listheader, null));
            showlistView.setAdapter(mAdapter);

        }
    }

    private void initView() {
        mTopView = findViewById(R.id.ll_btn);
        reloadTextView = (TextView) findViewById(R.id.tv_reload);
        showlistView = (ListView) findViewById(R.id.lv_main);
        //showlistView.setSelector(R.drawable.itemselector);
        bottomView = (LinearLayout) findViewById(R.id.bottom_layout);
        getfirstVisibleTv = (TextView) findViewById(R.id.tv_getFirstVisible);
        chatTextView = (TextView) findViewById(R.id.tv_chat);
        emailTextView = (TextView) findViewById(R.id.tv_email);
        telTextView = (TextView) findViewById(R.id.tv_tel);
        fab = (FloatingActionButton) findViewById(R.id.bMain_Float);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {
        datas.clear();
        Random r = new Random();
        for (int i = 0; i < 30; i++) {
            int num = r.nextInt(50);
            if (i < 10) {
                datas.add("张三" + num + ":" + "张三 hello world" + num);
            } else if (i < 20) {
                datas.add("钱一" + num + ":" + "钱一 hello world" + num);
            } else {
                datas.add("王二" + num + ":" + "王二 hello world" + num);
            }
        }
        datas.add(0, "Z");
        datas.add(11, "Q");
        datas.add(22, "Q");
    }

    class MyAdapter extends BaseAdapter {
        private final int VIEW_TYPE_COUNT = 2;
        private final int TYPE_NORMAL = 0;
        private final int TYPE_CHAR = 1;
        private List<String> datas;
        private LayoutInflater inflater;
        private Context context;

        public MyAdapter(List datas, Context context) {
            this.datas = datas;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getItemViewType(int position) {
            if (!datas.get(position).contains(":")) {
                return TYPE_CHAR;
            } else {
                return TYPE_NORMAL;
            }
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE_COUNT;
        }

        public void setDatas(List<String> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            boolean hasConvertView = convertView != null;
            ViewHolder holder = null;
            if (getItemViewType(position) == TYPE_NORMAL) {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item, null, false);
                    holder = new ViewHolderNormal(convertView);
                    //findView(TYPE_NORMAL, convertView, holder);
                    convertView.setTag(holder);
                    //setItemAnim(convertView, position);
                } else {
                    holder = (ViewHolderNormal) convertView.getTag();
                }
                String[] data = datas.get(position).split(":");
                ((ViewHolderNormal)holder).tvName.setText(data[0]);
                ((ViewHolderNormal)holder).tvItem.setText(data[1]);
            } else {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.type, null, false);
                    holder = new ViewHolderType(convertView);
                   // findView(TYPE_CHAR, convertView, holderType);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderType) convertView.getTag();
                }
                ((ViewHolderType)holder).tvType.setText(datas.get(position));
            }
            setItemAnim(convertView, position,hasConvertView);
            return convertView;
        }

        private void findView(int type, View convertView, ViewHolder holder) {
            if (type == TYPE_NORMAL) {

                ((ViewHolderNormal) holder).tvName = (TextView) convertView.findViewById(R.id.tv_name);
                ((ViewHolderNormal) holder).tvItem = (TextView) convertView.findViewById(R.id.tv_item);
            } else {
                ((ViewHolderType) holder).tvType = (TextView) convertView.findViewById(R.id.tv_type);
            }
        }

        class ViewHolderNormal implements ViewHolder {
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_item)
            TextView tvItem;

            public ViewHolderNormal(View view) {
                ButterKnife.bind(this, view);
            }
        }

        class ViewHolderType implements ViewHolder {
            @BindView(R.id.tv_type)
            TextView tvType;

            ViewHolderType(View view) {
                ButterKnife.bind(this, view);
            }
        }

        private void setItemAnim(View convertView, int position,boolean hasConvertView) {
            if (!hasConvertView) {
                convertView.clearAnimation();
                Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top);
                animation1.setStartOffset(100 * position);
                animation1.setFillAfter(true);
                AnimationSet set = new AnimationSet(false);
     /*       if (position%2 == 0) {
                Animation animation2 = AnimationUtils.loadAnimation(context, R.anim.right_to_left);

                set.addAnimation(animation2);
            } else {
                Animation animation3 = AnimationUtils.loadAnimation(context, R.anim.left_to_right);
                set.addAnimation(animation3);
            }*/
                //set.setStartOffset(200*position);
                set.addAnimation(animation1);
                convertView.setAnimation(set);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
        Log.e("MainActivity", "onDestroy");
    }

    /**
     * Layout动画
     *
     * @return
     */
    protected LayoutAnimationController getAnimationController() {
        int duration = 500;
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;
    }

    interface ViewHolder {

    }

    public static void initFragment(FragmentManager fragmentManager, Fragment targetFragment, String tag, int postion) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(postion, targetFragment, tag);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 清楚listview动画集合
     */
    private void clearListTransitionAnims() {
        if (null != showlistView) {
            showlistView.clearDisappearingChildren();
        }
        for (View view : transitionViewList) {
            view.clearAnimation();
        }
        transitionViewList.clear();
    }


    /**
     * 进中间页或下一程的动画
     *
     * @param adapterView
     * @param position
     * @param duration
     */
    private void startExitTransition(AdapterView<?> adapterView, int position, int duration) {
        int firstPosition = adapterView.getFirstVisiblePosition();
        int endPosition = adapterView.getLastVisiblePosition();

        for (int i = firstPosition; i <= endPosition; i++) {
            int index = i - firstPosition;
            View itemView = adapterView.getChildAt(index);
            if (i < position) {
             CustomTranslateUtil.getInstance().hideViewWithAlpha(itemView, CustomTranslateUtil.DOWN_TO_TOP, duration, 200, true);
                transitionViewList.add(itemView);
            } else if (i > position) {
                CustomTranslateUtil.getInstance().hideViewWithAlpha(itemView, CustomTranslateUtil.TOP_TO_DOWN, duration, 200, true);
                transitionViewList.add(itemView);
            }
        }

        // fade out top and bottom view
        AlphaAnimation fadeAnim = new AlphaAnimation(1, 0);
        fadeAnim.setFillAfter(true);
        fadeAnim.setDuration(200);

        if (mTopView.getVisibility() == View.VISIBLE) {
            mTopView.startAnimation(fadeAnim);
            transitionViewList.add(mTopView);
        }

        if (bottomView.getVisibility() == View.VISIBLE) {
            bottomView.startAnimation(fadeAnim);
            transitionViewList.add(bottomView);
        }
    }
}
