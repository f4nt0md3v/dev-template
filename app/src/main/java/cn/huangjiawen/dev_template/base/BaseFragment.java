package cn.huangjiawen.dev_template.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lufficc.stateLayout.StateLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.huangjiawen.dev_template.R;

public abstract class BaseFragment extends Fragment {
    @Nullable
//    @BindView(R.id.state_layout)
    protected StateLayout stateLayout;

    protected View self;

    private boolean isVisible;

    private boolean isPrepared;

    private boolean isFirstLoad = true;

    protected Context attachedActivity;


    @Override
    public void onDetach() {
        super.onDetach();
        this.attachedActivity = null;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.attachedActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (this.self == null) {
            this.self = inflater.inflate(this.getLayoutId(), container, false);
        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }

        this.initViews(this.self, savedInstanceState);
        this.initNoLazyData();
        return this.self;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected abstract int getLayoutId();

    protected void initViews(View self, Bundle savedInstanceState) {
        ButterKnife.bind(this, self);
        isPrepared = true;
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        initLazyData();
    }

    /**
     * 延迟加载时使用该方法
     */
    protected void initLazyData() {

    }

    protected void initNoLazyData() {

    }

    protected <V extends View> V findView(int id) {
        return (V) ButterKnife.findById(this.self, id);
    }
}
