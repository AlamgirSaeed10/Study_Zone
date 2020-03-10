package com.software.acuity.splick.fragments.business;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.software.acuity.splick.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Business_EditMarketingMaterial_ViewBinder implements Unbinder {
    private Business_EditMarketingMaterial target;

    @UiThread
    public Business_EditMarketingMaterial_ViewBinder(Business_EditMarketingMaterial target) {
        this(target, target.getWindow().getDecorView());
    }

    @UiThread
    public Business_EditMarketingMaterial_ViewBinder(Business_EditMarketingMaterial target, View source) {
        this.target = target;
        target.insta_marketing_material = Utils.findRequiredViewAsType(source, R.id.insta_marketing_material,"field 'insta_marketing_material'", ImageButton.class);
        target.insta_story_marketing_material = Utils.findRequiredViewAsType(source,R.id.insta_story_marketing_material,"field 'insta_story_marketing_material'",ImageButton.class);
        target.facebook_marketing_material = Utils.findRequiredViewAsType(source,R.id.facebook_marketing_material,"field 'facebook_marketing_material'",ImageButton.class);
        target.create_new_folder = Utils.findRequiredViewAsType(source,R.id.create_new_folder,"field 'create_new_folder'", TextView.class);
        target.cloud_upload = Utils.findRequiredViewAsType(source,R.id.cloud_upload,"field 'cloud_upload'",ImageButton.class);
        target.back_btn = Utils.findRequiredViewAsType(source,R.id.back_btn,"field 'back_btn'",ImageButton.class);
    }

    @Override
    @CallSuper
    public void unbind() {
        Business_EditMarketingMaterial target =this.target;
        if (target == null) throw new IllegalStateException("Binding already Created.");
        this.target = null;

        target.insta_marketing_material =null;
        target.insta_story_marketing_material =null;
        target.facebook_marketing_material =null;
        target.create_new_folder =null;
        target.cloud_upload =null;
        target.back_btn =null;



    }
}
