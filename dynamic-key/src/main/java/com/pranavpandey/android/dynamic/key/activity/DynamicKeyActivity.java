/*
 * Copyright 2022-2024 Pranav Pandey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pranavpandey.android.dynamic.key.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.pranavpandey.android.dynamic.key.DynamicKey;
import com.pranavpandey.android.dynamic.key.util.DynamicKeyUtils;
import com.pranavpandey.android.dynamic.util.DynamicIntentUtils;
import com.pranavpandey.android.dynamic.util.DynamicLinkUtils;
import com.pranavpandey.android.dynamic.util.product.DynamicFlavor;

/**
 * Base activity to implement the basic key operations.
 *
 * <p>Extend this activity and implement the required methods to perform operations
 * automatically on key app launch.
 */
public abstract class DynamicKeyActivity extends Activity implements DynamicKey {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (DynamicIntentUtils.isActivityResolved(this, getKeyIntent())) {
                startActivity(getKeyIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                onLaunchFallbackIntent();
            }
        } catch (Exception e) {
            onLaunchFallbackIntent();
        }

        finish();
    }

    /**
     * Returns the build flavor to support third-party app stores.
     *
     * @return The build flavor to support third-party app stores.
     *
     * @see DynamicFlavor
     */
    public @DynamicFlavor String getProductFlavor() {
        return DynamicFlavor.DEFAULT;
    }

    /**
     * Launch the fallback intent if the supported intent was unsuccessful.
     */
    protected void onLaunchFallbackIntent() {
        if (!DynamicKeyUtils.launch(this, getAppPackage())) {
            DynamicLinkUtils.viewApp(this, getAppPackage(), getProductFlavor());
        }
    }
}
