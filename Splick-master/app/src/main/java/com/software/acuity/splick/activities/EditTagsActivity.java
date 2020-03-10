package com.software.acuity.splick.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.software.acuity.splick.R;
import com.software.acuity.splick.interfaces.ISendTagsToActivity;

public class EditTagsActivity extends FragmentActivity implements ISendTagsToActivity {

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tags);

        context = EditTagsActivity.this;

        findViewById(R.id.cancelUpdateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    @Override
    public void sendTags(String tags) {
        Intent intent = new Intent();
        intent.putExtra("industry_tags", tags + "");
        setResult(RESULT_OK, intent);
        EditTagsActivity.this.finish();
    }
}
