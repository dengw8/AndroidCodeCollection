package com.example.dropdownmenu;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dropdownmenu.adapter.AdapterForRecylerView;
import com.example.dropdownmenu.adapter.AnimAdapterForRecyclerView;
import com.example.dropdownmenu.bean.AnimationModel;
import com.example.dropdownmenu.bean.FirstMenuModel;
import com.example.dropdownmenu.bean.SecondMenuModel;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView switcher;
    private ImageButton fixedButton;
    private RecyclerView firstMenu;
    private RecyclerView secondMenu;
    private AdapterForRecylerView firstAdapter;
    private AnimAdapterForRecyclerView secondAdapter;
    private List<FirstMenuModel> firstMenuModelList;

    private List<AnimationModel> mAnimationItems;

    private boolean isHidden = true;  // 判断当前浮动菜单是隐藏还是显示的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
        //addAnimationItems();
        setClickListenner();
    }

    private void initViews() {
        switcher = findViewById(R.id.switcher);
        fixedButton = findViewById(R.id.fixedButton);
        firstMenu = findViewById(R.id.menu1);
        secondMenu = findViewById(R.id.menu2);
    }

    private void initData() {
        firstMenuModelList = new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
            List<SecondMenuModel> secondMenuModelList = new ArrayList<>();
            for(int j = 1; j <= 10; j++) {
                secondMenuModelList.add(new SecondMenuModel("滤镜" + i + "-" + j));
            }
            firstMenuModelList.add(new FirstMenuModel("滤镜" + i, secondMenuModelList));
        }
        firstAdapter = new AdapterForRecylerView(firstMenuModelList);
        setParamsForRecyclerView(firstMenu, firstAdapter);
    }

    private void setClickListenner() {
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHidden) {
                    isHidden = false;
                    if(firstMenu.getVisibility() == View.VISIBLE) {
                        moveViewSmooth(firstMenu, false);
                    } else {
                        moveViewSmooth(secondMenu, false);
                        moveViewSmooth(fixedButton, false);
                    }
                } else {
                    isHidden = true;
                    if(firstMenu.getVisibility() == View.VISIBLE) {
                        moveViewSmooth(firstMenu, true);
                    } else {
                        moveViewSmooth(secondMenu, true);
                        moveViewSmooth(fixedButton, true);
                    }
                }
            }
        });

        fixedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                firstMenu.setVisibility(View.VISIBLE);
                moveView(secondMenu, true);
                moveView(fixedButton, true);
            }
        });

        firstAdapter.setOnItemClickListener(new AdapterForRecylerView.OnItemClickListener() {
            @Override
            public void onItemClick(final int pos) {
                secondAdapter = new AnimAdapterForRecyclerView(secondMenu, firstMenuModelList.get(pos).getmList());
                secondAdapter.setOnItemClickListener(new AnimAdapterForRecyclerView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getApplicationContext(), firstMenuModelList.get(pos).getmList().get(position).getName(), Toast.LENGTH_SHORT).show();
                    }
                });

                setParamsForRecyclerView(secondMenu, secondAdapter);

                firstMenu.setVisibility(View.INVISIBLE);

                moveView(secondMenu, false);
                moveView(fixedButton, false);
                //runLayoutAnimation(secondMenu, mAnimationItems.get(0));
            }
        });
    }

    /*
     * 为RecyclerView设置布局水平的方式，以及setAdapter
     * @param1 RecyclerView 对象
     * @param2 继承自RecyclerView.Adapter类的adapter
     */
    private void setParamsForRecyclerView(RecyclerView view, RecyclerView.Adapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        view.setLayoutManager(linearLayoutManager);
        view.setAdapter(adapter);
    }

    /*
     * 使用动画平移view指定的距离
     * @param1 要移动的view
     * @param2 标志位，值为true的时候下移，值为false的时候上移
     */
    private void moveViewSmooth(View view, boolean sign) {
        float from = view.getTranslationY(), to;
        if(sign) {
            to = from + fromDpToPx( 80);
        } else {
            to = from - fromDpToPx(80);
        }
        ObjectAnimator.ofFloat(view,"translationY", from, to).setDuration(400).start();
    }

    /*
     * 修改位置参数瞬移view 指定的距离
     * @param1 要移动的view
     * @param2 标志位，值为true的时候下移，值为false的时候上移
     */
    private void moveView(View view, boolean sign) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if(sign) {
            params.topMargin += fromDpToPx(80);
        } else {
            params.topMargin -= fromDpToPx(80);
        }
        view.requestLayout();
    }

    /*
     * 将dp值转化为px值
     *  @param dp值
     */
    private float fromDpToPx(float dpValue) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    private void addAnimationItems() {
        mAnimationItems = new ArrayList<>();
        mAnimationItems.add(new AnimationModel(R.anim.from_left_to_right));
    }

    private void runLayoutAnimation(RecyclerView recyclerView, final AnimationModel item) {
        final Context context = recyclerView.getContext();

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, item.getResourceId());
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    /*
     * 为ViewGroup增加弹性动画
     */
    private void chainAnim(ViewGroup viewGroup){
        SpringChain springChain = SpringChain.create();

        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View view = viewGroup.getChildAt(i);

            springChain.addSpring(new SimpleSpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    view.setTranslationX((float) spring.getCurrentValue());
                }
            });
        }

        List<Spring> springs = springChain.getAllSprings();
        for (int i = 0; i < springs.size(); i++) {
            springs.get(i).setCurrentValue(1080);
        }
        springChain.setControlSpringIndex(0).getControlSpring().setEndValue(0);
    }
}
