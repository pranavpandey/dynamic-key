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

package com.pranavpandey.android.dynamic.key.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pranavpandey.android.dynamic.key.Key;
import com.pranavpandey.android.dynamic.preferences.DynamicPreferences;
import com.pranavpandey.android.dynamic.util.DynamicLinkUtils;

import java.util.List;

/**
 * Helper class to perform key related operations.
 */
public class DynamicKeyUtils {

    /**
     * Matches the signature of two packages.
     *
     * @param context The context to get the package manager.
     * @param targetPackage The package to be matched.
     *
     * @see PackageManager#checkSignatures(String, String)
     * @see PackageManager#SIGNATURE_MATCH
     *
     * @return {@code true} if the signature of two packages match.
     */
    public static boolean isMatches(@Nullable Context context, @Nullable String targetPackage) {
        if (context == null || targetPackage == null) {
            return false;
        }

        return context.getPackageManager().checkSignatures(context.getPackageName(),
                targetPackage) == PackageManager.SIGNATURE_MATCH;
    }

    /**
     * Checks whether the signature matches for at least one of the supplied packages.
     *
     * @param context The context to be used.
     * @param packages The key packages to be matched.
     * 
     * @return {@code true} if the signature matches for at least one of the supplied packages.
     */
    public static boolean isMatches(@Nullable Context context, @Nullable String... packages) {
        if (context == null || packages == null) {
            return false;
        }

        for (String key : packages) {
            if (isMatches(context, key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Launch a package according to the default launch intent.
     *
     * @param context The context to get the package manager.
     * @param targetPackage The package to be launched.
     *
     * @see PackageManager#getLaunchIntentForPackage(String)
     *
     * @return {@code true} if the package was launched successfully.
     */
    public static boolean launch(@Nullable Context context, @Nullable String targetPackage) {
        if (context == null || targetPackage == null) {
            return false;
        }

        Intent intent;
        if ((intent = context.getPackageManager()
                .getLaunchIntentForPackage(targetPackage)) != null) {
            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            return true;
        }

        return false;
    }

    /**
     * Set an app component state.
     *
     * @param context The context to get the package manager.
     * @param className The class name of the component.
     * @param state The component state to be set.
     *
     * @see PackageManager#setComponentEnabledSetting(ComponentName, int, int)
     * @see PackageManager#DONT_KILL_APP
     */
    public static void setComponentState(@Nullable Context context,
            @Nullable String className, int state) {
        if (context == null || className == null) {
            return;
        }

        context.getPackageManager().setComponentEnabledSetting(
                new ComponentName(context.getPackageName(), className),
                state, PackageManager.DONT_KILL_APP);
    }

    /**
     * Broadcast the key action to the supported apps.
     *
     * @param context The context to broadcast the key action.
     * @param keyPackage The name of the key package.
     * @param action The intent action to be broadcast.
     */
    public static void broadcast(@Nullable Context context,
            @Nullable String keyPackage, @Nullable String action) {
        if (context == null || keyPackage == null || action == null) {
            return;
        }

        List<ResolveInfo> receivers = context.getPackageManager()
                .queryBroadcastReceivers(new Intent(action), PackageManager.GET_META_DATA);

        for (ResolveInfo resolveInfo : receivers) {
            if (keyPackage.equals(resolveInfo.activityInfo.packageName)) {
                Intent intent = new Intent(action);
                intent.setPackage(resolveInfo.activityInfo.packageName);
                intent.setComponent(new ComponentName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name));

                context.sendOrderedBroadcast(intent.addFlags(
                        Intent.FLAG_INCLUDE_STOPPED_PACKAGES), null);
            }
        }
    }

    /**
     * Rate the app or key (if installed) on the Google Play.
     *
     * @param context The context to get the package name.
     * @param isKey {@code true} if the key package is installed.
     * @param keyPackage The key package to be rated.
     *
     * @return {@code true} on successful operation.
     *
     * @see DynamicLinkUtils#rateApp(Context)
     * @see DynamicLinkUtils#viewInGooglePlay(Context, String)
     */
    public static boolean rateAppOrKey(@Nullable Context context,
            boolean isKey, @Nullable String keyPackage) {
        if (context == null) {
            return false;
        }

        if (isKey && keyPackage != null) {
            return DynamicLinkUtils.viewInGooglePlay(context, keyPackage);
        } else {
            return DynamicLinkUtils.rateApp(context);
        }
    }

    /**
     * Returns the base to store and retrieve the data.
     *
     * @param key The custom key to be used.
     *
     * @return The base to store and retrieve the data.
     */
    public static @NonNull String getBaseKey(@Nullable String key) {
        return key != null ? key : Key.BASE;
    }

    /**
     * Returns the key concatenated with the base key.
     *
     * @param base The custom base key to be used.
     * @param key The key to be concatenated.
     *
     * @return The key concatenated with the base key.
     */
    public static @NonNull String getKey(@Nullable String base, @NonNull String key) {
        return getBaseKey(base) + key;
    }

    /**
     * Returns the activated state of the key from the shared preferences.
     *
     * @param base The optional base key to be used.
     *
     * @return The activated state of the key from the shared preferences.
     */
    public static boolean isActivated(@Nullable String base) {
        return DynamicPreferences.getInstance().load(
                getKey(base, Key.ACTIVATED), Key.Value.ACTIVATED);
    }

    /**
     * Returns the activated state of the key from the shared preferences.
     *
     * @return The activated state of the key from the shared preferences.
     *
     * @see #isActivated(String)
     */
    public static boolean isActivated() {
        return isActivated(null);
    }

    /**
     * Saves the activated state of the key to the shared preferences.
     *
     * @param base The optional base key to be used.
     * @param activated {@code true} if the key is activated.
     */
    public static void setActivated(@Nullable String base, boolean activated) {
        DynamicPreferences.getInstance().save(getKey(base, Key.ACTIVATED), activated);
    }

    /**
     * Saves the activated state of the key to the shared preferences.
     *
     * @param activated {@code true} if the key is activated.
     *
     * @see #setActivated(String, boolean)
     */
    public static void setActivated(boolean activated) {
        setActivated(null, activated);
    }

    /**
     * Returns the installed state of the key from the shared preferences.
     *
     * @param base The optional base key to be used.
     *
     * @return The installed state of the key from the shared preferences.
     */
    public static boolean isInstalled(@Nullable String base) {
        return DynamicPreferences.getInstance().load(
                getKey(base, Key.INSTALLED), Key.Value.INSTALLED);
    }

    /**
     * Returns the installed state of the key from the shared preferences.
     *
     * @return The installed state of the key from the shared preferences.
     *
     * @see #isInstalled(String)
     */
    public static boolean isInstalled() {
        return isInstalled(null);
    }

    /**
     * Saves the installed state of the key to the shared preferences.
     *
     * @param base The optional base key to be used.
     * @param installed {@code true} if the key is installed.
     */
    public static void setInstalled(@Nullable String base, boolean installed) {
        DynamicPreferences.getInstance().save(getKey(base, Key.INSTALLED), installed);
    }

    /**
     * Saves the installed state of the key to the shared preferences.
     *
     * @param installed {@code true} if the key is installed.
     *
     * @see #setInstalled(String, boolean)
     */
    public static void setInstalled(boolean installed) {
        setInstalled(null, installed);
    }

    /**
     * Returns the key status from the shared preferences.
     *
     * @param base The optional base key to be used.
     *
     * @return The key status from the shared preferences.
     */
    public static @Key.Status int getStatus(@Nullable String base) {
        return DynamicPreferences.getInstance().load(getKey(base, Key.STATUS), Key.Value.STATUS);
    }

    /**
     * Returns the key status from the shared preferences.
     *
     * @return The key status from the shared preferences.
     *
     * @see #getStatus(String)
     */
    public static @Key.Status int getStatus() {
        return getStatus(null);
    }

    /**
     * Saves the key status to the shared preferences.
     *
     * @param base The optional base key to be used.
     * @param status The key status to be saved.
     */
    public static void setStatus(@Nullable String base, @Key.Status int status) {
        DynamicPreferences.getInstance().save(getKey(base, Key.STATUS), status);
    }

    /**
     * Saves the key status to the shared preferences.
     *
     * @param status The key status to be saved.
     *
     * @see #setStatus(String, int)
     */
    public static void setStatus(@Key.Status int status) {
        setStatus(null, status);
    }

    /**
     * Checks whether the supplied status is activated.
     *
     * @param status The status to be checked.
     *
     * @return {@code true} if the supplied status is activated.
     *
     * @see Key.Status#ACTIVATED
     * @see Key.Status#AVAILABLE
     */
    public static boolean isStatusActivated(@Key.Status int status) {
        return status == Key.Status.ACTIVATED || status == Key.Status.AVAILABLE;
    }

    /**
     * Checks whether the signature matches for at least one of the supplied packages and sets 
     * the shared preferences accordingly.
     *
     * @param context The context to be used.
     * @param base The optional base key to be used.
     * @param debug {@code true} if running in debug mode.
     * @param packages The key packages to be matched.
     *
     * @return The key status according to the installed packages.
     */
    public static @Key.Status int checkWithBase(@Nullable Context context,
            @Nullable String base, boolean debug, @Nullable String... packages) {
        @Key.Status int status = getStatus(base);

        if (debug || isMatches(context, packages)) {
            setInstalled(base, true);

            if (!isActivated(base)) {
                setActivated(base, true);
            }

            if (status != Key.Status.AVAILABLE) {
                status = Key.Status.ACTIVATED;
            }
        } else {
            setInstalled(base, false);

            if (isActivated(base) && status != Key.Status.BUY) {
                status = Key.Status.REMOVED;
            }
        }

        setStatus(base, status);
        return status;
    }

    /**
     * Checks whether the signature matches for at least one of the supplied packages and sets
     * the shared preferences accordingly.
     *
     * @param context The context to be used.
     * @param debug {@code true} if running in debug mode.
     * @param packages The key packages to be matched.
     *
     * @return The key status according to the installed packages.
     */
    public static @Key.Status int check(@Nullable Context context,
            boolean debug, @Nullable String... packages) {
        return checkWithBase(context, null, debug, packages);
    }
}
