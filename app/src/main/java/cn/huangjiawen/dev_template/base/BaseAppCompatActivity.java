package cn.huangjiawen.dev_template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import cn.huangjiawen.dev_template.R;

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.center_title)
    TextView centerTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutId());
        ButterKnife.bind(this);

        this.initViews(savedInstanceState);
        this.initData(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);

}