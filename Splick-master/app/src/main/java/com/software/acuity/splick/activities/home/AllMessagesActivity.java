package com.software.acuity.splick.activities.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.partnerships.PartnershipDetailsActivity;
import com.software.acuity.splick.adapters.MessagesAdapter;
import com.software.acuity.splick.adapters.RecyclerViewDealsCustomAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AllMessagesActivity extends AppCompatActivity {

    @BindView(R.id.btnAllMessagesBack)
    MaterialButton btnBack;

    @BindView(R.id.search_messagesEt)
    EditText search_messagesEt;

    @BindView(R.id.recyclerViewMessages)
    RecyclerView recyclerView;

    MessagesAdapter messagesAdapter;

    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_messages);

        ButterKnife.bind(this);

        initRecyclerView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        messagesAdapter = new MessagesAdapter(getApplicationContext(), getData());
        recyclerView.setAdapter(messagesAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new IRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    public List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("Test 1");
        data.add("Test 2");
        data.add("Test 1");
        data.add("Test 2");
        data.add("Test 1");
        data.add("Test 2");
        data.add("Test 1");
        data.add("Test 2");
        data.add("Test 1");
        data.add("Test 2");
        data.add("Test 1");
        data.add("Test 2");
        data.add("Test 1");
        data.add("Test 2");
        data.add("Test 1");
        data.add("Test 2");
        data.add("Test 1");
        data.add("Test 2");

        return data;
    }
}
