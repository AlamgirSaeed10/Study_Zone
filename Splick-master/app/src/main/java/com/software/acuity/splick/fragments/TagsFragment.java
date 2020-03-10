package com.software.acuity.splick.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.EditTagsActivity;
import com.software.acuity.splick.activities.SignUpActivity;
import com.software.acuity.splick.activities.discover.FiltersActivity;
import com.software.acuity.splick.adapters.TagsListViewCustomAdapter;
import com.software.acuity.splick.adapters.TagsRecyclerViewAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.interfaces.IChangeViewPagerItem;
import com.software.acuity.splick.interfaces.IChipsChange;
import com.software.acuity.splick.interfaces.IGetData;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;
import com.software.acuity.splick.interfaces.ISendTagsToActivity;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.TagsModel;
import com.software.acuity.splick.utils.AppController;
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

import static com.facebook.FacebookSdk.getApplicationContext;

public class TagsFragment extends Fragment implements IChipsChange, IVolleyResponse {


    IChangeViewPagerItem iChangeViewPagerItem;
    IGetData iGetData;

    @BindView(R.id.btnTagsNext)
    Button btnAboutNext;

    @BindView(R.id.add_more_tags)
    MaterialButton add_more_tags;

    TagsRecyclerViewAdapter tagsRecyclerViewAdapter;

    @BindView(R.id.tagsRecyclerView)
    RecyclerView recyclerView;

    List<TagsModel> mDataList = new ArrayList<>();
    List<TagsModel> mChipsDataList = new ArrayList<>();

    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "tags_request_tag";

    private StaggeredGridLayoutManager layoutManager;

    SharedPreferenceClass sharedPreferenceClass;

    ISendTagsToActivity iSendTagsToActivity;

    public TagsFragment() {
        super();

        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());
    }

    public TagsFragment(Context context, SignUpActivity activity) {
        // Required empty public constructor

        iChangeViewPagerItem = SignUpActivity.signUpActivityContext;
        iGetData = SignUpActivity.signUpActivityContext;
        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_tags, container, false);

        ButterKnife.bind(this, fragmentView);

        setHasOptionsMenu(true);

        btnAboutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder csvBuilder = new StringBuilder();
                for (TagsModel tag : mChipsDataList) {
                    if (!(tag.getTagName().trim().toLowerCase().contains("add tags"))) {
                        csvBuilder.append(tag.getTagName());
                        csvBuilder.append(",");
                    }
                }
                String tagsString = csvBuilder.toString();
                tagsString = tagsString.replaceAll(",$", "");
                System.out.println(tagsString);
                if (sharedPreferenceClass.getValues(Constants.LOGIN_STATUS).trim().equalsIgnoreCase("")) {
                    iGetData.getData(tagsString, "industry_tags");
                    iChangeViewPagerItem.changeViewPagerItem("bio");
                } else {
                    iSendTagsToActivity = (EditTagsActivity) EditTagsActivity.context;
                    iSendTagsToActivity.sendTags(tagsString);
                }
            }
        });
        requestData();
        return fragmentView;
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);

        tagsRecyclerViewAdapter = new TagsRecyclerViewAdapter(getActivity(), mChipsDataList,
                TagsFragment.this);
        recyclerView.setAdapter(tagsRecyclerViewAdapter);

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
            case R.id.menuItemSkipTagFragment:
                iChangeViewPagerItem.changeViewPagerItem("bio");
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tags_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void chipsChanged(View view, int position) {
//        Toast.makeText(getActivity(), "Chips Changed", Toast.LENGTH_SHORT).show();
            if (((Chip) view).getText().toString().toLowerCase().contains("add")) {
                Dialog dialog = new Dialog(getActivity(),
                        android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.tags_dialog_view);

                TagsListViewCustomAdapter tagsListViewCustomAdapter =
                        new TagsListViewCustomAdapter(getActivity(), mDataList, TagsFragment.this);

                ListView listView = dialog.findViewById(R.id.tagsListView);
                listView.setAdapter(tagsListViewCustomAdapter);


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
                dialog.show();}

    }

    @Override
    public void chipsRemoved(View view, int position) {


        if ((!((Chip) view).getText().toString().toLowerCase().equalsIgnoreCase("#add tags"))) {
            mChipsDataList.remove(position);
            mDataList.get(getIndex(((Chip) view).getText().toString().trim())).setTagStatus(false);
            tagsRecyclerViewAdapter.dataChanged(mChipsDataList);
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
//        mDataList.remve(tagsModel);
        mChipsDataList.add(tagsModel);
        mChipsDataList.remove(findObject());
        //mChipsDataList.add(new TagsModel("#ADD TAGS", true));
        tagsRecyclerViewAdapter.dataChanged(mChipsDataList);
    }

    public int findObject() {

        for (int i = 0; i < mChipsDataList.size(); i++) {
            if (mChipsDataList.get(i).getTagName().toLowerCase().contains("add")) {
                return i;
            }
        }

        return 0;
    }

    public void requestData() {

        volleyRequestClass = new VolleyRequestClass(getActivity(), TagsFragment.this,
                "Tags", "Loading tags!", requestTag, Request.Method.POST);

        volleyRequestClass.volleyServiceCallWithoutLoading(Constants.BASE_URL + Constants.API_GET_AFFILIATE_TAGS);
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
                initRecyclerView();
            } else {
                new MaterialAlertDialogBuilder(getActivity(),
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
        add_more_tags.setOnClickListener(v->
                {
                }
                );
    }
}
