package cc.xaabb.dynamicschedule.module.setup;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.base.BaseActivity;
import cc.xaabb.dynamicschedule.utils.ACache;

public class SetupActivity extends BaseActivity {


    @Bind(R.id.btn_back)
    ImageView mBtnBack;
    @Bind(R.id.txt_title)
    TextView mTxtTitle;
    @Bind(R.id.cur_week_spinner)
    Spinner mCurWeekSpinner;

    private Context mContext;
    private Resources mResources;
    private ACache mACache;
    private String[] spinnerData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        mContext = this;
        mResources = getResources();
        mACache = ACache.get(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTxtTitle.setText("设置");
        setSpinner(20);
    }

    private void setSpinner(int length) {
        mCurWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mACache.put("curWeek", i + 1 + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int alllength = length;
        if (alllength < 20) alllength = 20;
        spinnerData = new String[alllength];
        int a;
        for (a = 0; a < alllength; a++) {
            spinnerData[a] = mResources.getString(R.string.course_no) + (a + 1) + mResources.getString(R.string.course_week);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.item_spinner_course_week, spinnerData);
        adapter.setDropDownViewResource(R.layout.item_spinner_course_week);
        mCurWeekSpinner.setAdapter(adapter);
        String curWeek1 = mACache.getAsString("curWeek");
        int curWeek = curWeek1==null? 0 : Integer.parseInt(curWeek1);
        mCurWeekSpinner.setSelection(curWeek-1);
    }
    @OnClick({R.id.btn_back})
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
