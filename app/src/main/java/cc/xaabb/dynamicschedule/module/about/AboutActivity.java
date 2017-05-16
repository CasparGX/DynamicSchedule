package cc.xaabb.dynamicschedule.module.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.textView)
    TextView mTextView;
    @Bind(R.id.img_logo)
    ImageView mImgLogo;
    @Bind(R.id.txt_title)
    TextView mTxtTitle;
    @Bind(R.id.imageView2)
    ImageView mImageView2;
    @Bind(R.id.textView2)
    TextView mTextView2;
    @Bind(R.id.textView3)
    TextView mTextView3;
    @Bind(R.id.cv_dev)
    CardView mCvDev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }
    
    @OnClick({R.id.cv_dev})
    public void btnClick(View v) {
        switch(v.getId()) {
            case R.id.cv_dev:
                Uri uri = Uri.parse("https://github.com/CasparGX");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            break;
        }
    }
}
