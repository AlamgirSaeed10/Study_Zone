package com.acuity.Splick.ui.fragments.authentication.signup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.acuity.Splick.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class Add_Portfolio_Fragment extends Fragment {


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
        mViewModel = ViewModelProviders.of(this).get(AddPortfolioViewModel.class);
        // TODO: Use the ViewModel
        btnUpload.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_add_Portfolio_Fragment_to_profile_Completed_Fragment);
        });
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
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {

            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                setImage(selectedImage);
                File imageFilePath = new File(String.valueOf(imageUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    private void setImage(Bitmap image) {
        switch (clickCode) {
            case 1:
                imgOne.setImageBitmap(image);
                break;
            case 2:
                imgTwo.setImageBitmap(image);
                break;
            case 3:
                imgThree.setImageBitmap(image);
                break;
            case 4:
                imgFour.setImageBitmap(image);
                break;
            case 5:
                imgFive.setImageBitmap(image);
                break;
            case 6:
                imgSix.setImageBitmap(image);
                break;
        }

    }
    private void setImage(File file){

    }
}
