package com.acuity.Splick.ui.fragments.authentication.signup.portfolio;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.acuity.Splick.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class Add_Portfolio_Fragment extends Fragment {
    private static final String TAG = "Add_Portfolio_Fragment";

    @BindView(R.id.portfolio_upload_btn)
    Button btnUpload;
    @BindView(R.id.skip_bio_tv)
    TextView tvSkip;
    @BindView(R.id.back_image)
    ImageView imgBack;
    @BindView(R.id.portfolio_image_1)
    ImageView imgOne;
    @BindView(R.id.portfolio_image_2)
    ImageView imgTwo;
    @BindView(R.id.portfolio_image_3)
    ImageView imgThree;
    @BindView(R.id.portfolio_image_4)
    ImageView imgFour;
    @BindView(R.id.portfolio_image_5)
    ImageView imgFive;
    @BindView(R.id.portfolio_image_6)
    ImageView imgSix;
    private AddPortfolioViewModel mViewModel;
    private int requestCode = 200;
    private int clickCode = 0;
    HashMap<String,Uri> imgMap=new HashMap<>();

    public static Add_Portfolio_Fragment newInstance() {
        return new Add_Portfolio_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add__portfolio__fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(AddPortfolioViewModel.class);

        tvSkip.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_add_Portfolio_Fragment_to_profile_Completed_Fragment);
        });
        imgBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_add_Portfolio_Fragment_to_social_Reach_Fragment);
        });
    }

    @OnClick({R.id.portfolio_image_1, R.id.portfolio_image_2, R.id.portfolio_image_3, R.id.portfolio_image_4, R.id.portfolio_image_5, R.id.portfolio_image_6})
    public void photoPicker(View v) {
        switch (v.getId()) {
            case R.id.portfolio_image_1:
                clickCode = 1;
                break;
            case R.id.portfolio_image_2:
                clickCode = 2;
                break;
            case R.id.portfolio_image_3:
                clickCode = 3;
                break;
            case R.id.portfolio_image_4:
                clickCode = 4;
                break;
            case R.id.portfolio_image_5:
                clickCode = 5;
                break;
            case R.id.portfolio_image_6:
                clickCode=6;
                break;
            default:
                Toast.makeText(getActivity(), "Error occur", Toast.LENGTH_SHORT).show();
        }

            // Permission has already been granted
        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.
                permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()){
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, requestCode);
                }
                else {
                    Toast.makeText(getActivity(),"You must grant all permissions ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).onSameThread().check();}

    @OnClick(R.id.portfolio_upload_btn)
    public void setBtnUpload(){
        Log.d(TAG, "setBtnUpload: "+imgMap.size());
    }
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (resultCode == RESULT_OK) {

                final Uri imageUri = data.getData();

                setImage(imageUri);

            }
            else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    //display image
    private void setImage(Uri image) {
        switch (clickCode) {
            case 1:
                Picasso.with(getActivity()).load(image).centerCrop().fit().into(imgOne);
                imgMap.put("1",image);
                break;
            case 2:
                Picasso.with(getActivity()).load(image).centerCrop().fit().into(imgTwo);
                imgMap.put("2",image);
                break;
            case 3:
                Picasso.with(getActivity()).load(image).centerCrop().fit().into(imgThree);
                imgMap.put("3",image);
                break;
            case 4:
                Picasso.with(getActivity()).load(image).centerCrop().fit().into(imgFour);
                imgMap.put("4",image);
                break;
            case 5:
                Picasso.with(getActivity()).load(image).centerCrop().fit().into(imgFive);
                imgMap.put("5",image);
                break;
            case 6:
                Picasso.with(getActivity()).load(image).centerCrop().fit().into(imgSix);
                imgMap.put("6",image);
                break;
        }

    }
    //send to server
    private void addImage(Uri urifile){
        File file=new File(getRealPathFromUri(getActivity(),urifile));
     mViewModel.addImage(134, file);
     mViewModel.getMutableLiveMedia().observe(getViewLifecycleOwner(),register -> {
         if(register.getSuccess()){
             Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
         }
         else{
             Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
         }
     });

    }

    //change Uri to file path
    public static String getRealPathFromUri(Activity activity, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
