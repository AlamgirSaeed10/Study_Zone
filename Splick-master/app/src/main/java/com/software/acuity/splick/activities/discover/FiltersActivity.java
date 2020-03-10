package com.software.acuity.splick.activities.discover;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nex3z.flowlayout.FlowLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.FiltersActivityTagsGridAdapter;
import com.software.acuity.splick.adapters.TagsListViewCustomAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.interfaces.IChipsChange;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.TagsModel;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.VolleyRequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FiltersActivity extends AppCompatActivity implements IVolleyResponse, IChipsChange {

    @BindView(R.id.filterActivityToolbar)
    Toolbar toolbar;

    @BindView(R.id.clearBtn)
    MaterialButton clearBtn;

    @BindView(R.id.applyFiltersBtn)
    MaterialButton applyFilterBtn;

    SharedPreferenceClass sharedPreferenceClass;
    TagsListViewCustomAdapter tagsListViewCustomAdapter=null;

    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "tags_request_tag";
    private List<TagsModel> mDataList = new ArrayList<>();
    private List<TagsModel> mChipsDataList = new ArrayList<>();
    private List<TagsModel> selectedTagsList = new ArrayList<>();

    private StaggeredGridLayoutManager layoutManager;
    FiltersActivityTagsGridAdapter filtersActivityTagsGridAdapter;

    @BindView(R.id.tagsRecyclerView)
    RecyclerView recyclerView;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("");

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        applyFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("tags", convertArrayIntoString() + "");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("tags", "");
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        requestData();
    }

    public String convertArrayIntoString() {
        StringBuilder csvBuilder = new StringBuilder();
        for (TagsModel tag : selectedTagsList) {
            if (!(tag.getTagName().trim().toLowerCase().contains("add tags"))) {
                csvBuilder.append(tag.getTagName());
                csvBuilder.append(",");
            }
        }
        String tagsString = csvBuilder.toString();
        tagsString = tagsString.replaceAll(",$", "");
        return tagsString;
    }

    public void initializeRecyclerView() {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);

        filtersActivityTagsGridAdapter = new FiltersActivityTagsGridAdapter(FiltersActivity.this, mChipsDataList, FiltersActivity.this);
        recyclerView.setAdapter(filtersActivityTagsGridAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new IRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

            case R.id.close_filter_activity:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filters_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void requestData() {

        try {
            if (new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business")) {
                volleyRequestClass = new VolleyRequestClass(FiltersActivity.this, FiltersActivity.this,
                        "Tags", "Loading tags!", requestTag, Request.Method.POST);

                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_GET_AFFILIATE_TAGS);
            } else {
                volleyRequestClass = new VolleyRequestClass(FiltersActivity.this, FiltersActivity.this,
                        "Tags", "Loading tags!", requestTag, Request.Method.POST);

                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_GET_BUSINESS_TAGS);
            }
        } catch (JSONException e) {

        }
    }

    @Override
    public void networkResponse(String response) {

        mChipsDataList.removeAll(mChipsDataList);
        mDataList.removeAll(mDataList);
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean("success")) {
                JSONObject jsonDataObject = jsonObject.getJSONObject("data");

                for (Iterator<String> it = jsonDataObject.keys(); it.hasNext(); ) {
                    String key = it.next();

                    mDataList.add(new TagsModel(jsonDataObject.getString(key + ""), true));
                }

                mChipsDataList = mDataList;

                generateValidTagsList();

                //Setup Recycler View
                initializeRecyclerView();
            } else {
                new MaterialAlertDialogBuilder(FiltersActivity.this,
                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Posts")
                        .setMessage(jsonObject.getString("msg"))
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void generateValidTagsList() {

        mChipsDataList = new ArrayList<>();

        for (TagsModel tagsModel : mDataList) {
            if (tagsModel.getTagStatus()) {
                mChipsDataList.add(tagsModel);
            }
        }

     //   mChipsDataList.add(new TagsModel("#ADD TAGS", true));
    }

    @Override
    public void chipsChanged(View view, int position) {
//        Toast.makeText(getActivity(), "Chips Changed", Toast.LENGTH_SHORT).show();

        if (((Chip) view).getText().toString().toLowerCase().contains("add")) {
            Dialog dialog = new Dialog(FiltersActivity.this,
                    android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.tags_dialog_view);

            tagsListViewCustomAdapter = new TagsListViewCustomAdapter(FiltersActivity.this, mDataList, FiltersActivity.this);

            listView = dialog.findViewById(R.id.tagsListView);
            listView.setAdapter(tagsListViewCustomAdapter);
            listView.setTextFilterEnabled(true);

            dialog.findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {

            if (isExists(mDataList.get(position).getTagName())) {
                selectedTagsList.remove(findIndex(mDataList.get(position).getTagName()));
            } else {
                selectedTagsList.add(mDataList.get(position));
            }

//            for (int i = 0; i < selectedTagsList.size(); i++) {
//                flowLayout.removeAllViews();
//
//                View chip = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row_tags_chip, null);
//
//                flowLayout.addView(chip);
//            }
        }
    }

    public int findIndex(String tag) {
        for (int i = 0; i < selectedTagsList.size(); i++) {
            if (selectedTagsList.get(i).getTagName().equalsIgnoreCase(tag)) {
                return i;
            }
        }
        return 0;
    }

    public boolean isExists(String tag) {
        for (TagsModel tagsModel : selectedTagsList) {
            if (tagsModel.getTagName().equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void chipsRemoved(View view, int position) {
        if ((!((Chip) view).getText().toString().toLowerCase().equalsIgnoreCase("#add tagss"))) {
            mChipsDataList.remove(position);
            mDataList.get(getIndex(((Chip) view).getText().toString().trim())).setTagStatus(false);
            filtersActivityTagsGridAdapter.dataChanged(mChipsDataList);
        }
    }

    public int getIndex(String tag) {
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getTagName().trim().equals(tag + "")) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void chipsAdded(TagsModel tagsModel) {
        mDataList.remove(tagsModel);
        mChipsDataList.add(tagsModel);
        mChipsDataList.remove(findObject());
 //       mChipsDataList.add(new TagsModel("#ADD TAGS", true));
        filtersActivityTagsGridAdapter.dataChanged(mChipsDataList);
    }

    public int findObject() {

        for (int i = 0; i < mChipsDataList.size(); i++) {
            if (mChipsDataList.get(i).getTagName().toLowerCase().contains("add")) {
                return i;
            }
        }

        return 0;
    }
}
