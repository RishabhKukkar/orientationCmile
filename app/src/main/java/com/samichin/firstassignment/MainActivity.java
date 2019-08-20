package com.samichin.firstassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.samichin.firstassignment.utils.Common;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.samichin.firstassignment.utils.Common.BACKGROUND_TASK;
import static com.samichin.firstassignment.utils.Common.COUNTER;


public class MainActivity extends AppCompatActivity {
    private Common common = new Common();

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.bt_Click)
    Button bt_Click;

    @BindView(R.id.textView2)
    TextView textView2;

    @BindView(R.id.textView4)
    TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            common.counter = savedInstanceState.getInt(COUNTER);
            common.backgroundCount = savedInstanceState.getInt(BACKGROUND_TASK);
            setTextview4(common.backgroundCount);
            setTextview2(common.counter);
        } else {
            setTextview2(Integer.parseInt(getString(R.string.constnumber)));
            setTextview4(Integer.parseInt(getString(R.string.constnumber)));
        }

        bt_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++common.counter;

                setTextview2(common.counter);
            }
        });
    }

    private void setTextview4(int i) {
        textView4.setText(String.valueOf(i));
    }

    private void setTextview2(int counter) {
        textView2.setText(String.valueOf(counter));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTER, common.counter);
        outState.putInt(BACKGROUND_TASK, common.backgroundCount);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Common.isAppWentToBg) {
            Common.isAppWentToBg = false;
            Common.isWindowFocused=false;
            Toast.makeText(getApplicationContext(), "App is in Background",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        if (!Common.isWindowFocused) {
            ++common.backgroundCount;
            Common.isAppWentToBg = true;
        }
        super.onStart();


    }
}
