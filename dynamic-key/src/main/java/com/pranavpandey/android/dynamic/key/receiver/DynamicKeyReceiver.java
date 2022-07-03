/*
 * Copyright 2022 Pranav Pandey
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

package com.pranavpandey.android.dynamic.key.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pranavpandey.android.dynamic.key.DynamicKey;
import com.pranavpandey.android.dynamic.key.Key;
import com.pranavpandey.android.dynamic.key.util.DynamicKeyUtils;

/**
 * A {@link BroadcastReceiver} to receive the key data.
 *
 * <p>Extend this broadcast receiver and declare it in the project's manifest to
 * disable the launcher activity on successful activation.
 *
 * @see Key.Intent#ACTION_ACTIVATE
 */
public abstract class DynamicKeyReceiver extends BroadcastReceiver implements DynamicKey.Receiver {

    @Override
    public void onReceive(final @NonNull Context context, @Nullable Intent intent) {
        if (getLauncherActivity() == null) {
            return;
        }

        if (intent != null && Key.Intent.ACTION_ACTIVATE.equals(intent.getAction())) {
            DynamicKeyUtils.setComponentState(context, getLauncherActivity().getName(),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
        }
    }
}
