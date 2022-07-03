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

package com.pranavpandey.android.dynamic.key;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * An interface to implement the app key.
 */
public interface DynamicKey {

    /**
     * Returns the intent for the app key.
     *
     * @return The intent for the app key.
     */
    @NonNull Intent getKeyIntent();

    /**
     * Returns the package name of the main app.
     *
     * @return The package name of the main app.
     */
    @NonNull String getAppPackage();

    /**
     * An interface to implement the app key receiver.
     */
    interface Receiver {

        /**
         * Returns the app key launcher activity class.
         *
         * @return The app key launcher activity class.
         */
        @Nullable Class<?> getLauncherActivity();
    }
}
