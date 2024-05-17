package com.example.uspproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PhotosPermission extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_permission);

        TextView headerTextView = findViewById(R.id.textViewHeader);
        ListView listViewApps = findViewById(R.id.listViewApps);
        TextView emptyTextView = findViewById(R.id.textViewEmpty);

        PackageManager pm = getPackageManager();
        List<AppInfo> appsWithPhotoPermission = new ArrayList<>();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            try {
                PackageInfo packageInfoWithPermissions = pm.getPackageInfo(
                        packageInfo.packageName, PackageManager.GET_PERMISSIONS);

                if (packageInfoWithPermissions.requestedPermissions != null) {
                    for (String permission : packageInfoWithPermissions.requestedPermissions) {
                        if (permission.equals(android.Manifest.permission.READ_EXTERNAL_STORAGE) ||
                                permission.equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            AppInfo appInfo = new AppInfo();
                            appInfo.packageName = packageInfo.packageName;
                            appInfo.appName = pm.getApplicationLabel(packageInfo).toString();
                            appInfo.appIcon = pm.getApplicationIcon(packageInfo);
                            appInfo.hasPhotoPermission = true; // Assume initially granted
                            appsWithPhotoPermission.add(appInfo);
                            break;
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (!appsWithPhotoPermission.isEmpty()) {
            AppArrayAdapter adapter = new AppArrayAdapter(this, appsWithPhotoPermission);
            listViewApps.setAdapter(adapter);
        } else {
            listViewApps.setVisibility(ListView.GONE);
            emptyTextView.setVisibility(TextView.VISIBLE);
        }

    }


    private static class AppInfo {
        String packageName;
        String appName;
        android.graphics.drawable.Drawable appIcon;
        boolean hasPhotoPermission;
    }

    private class AppArrayAdapter extends ArrayAdapter<AppInfo> {
        public AppArrayAdapter(Activity context, List<AppInfo> apps) {
            super(context, R.layout.list_item_app, apps);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_app, parent, false);
            }

            ImageView imageViewIcon = convertView.findViewById(R.id.imageViewIcon);
            TextView textViewAppName = convertView.findViewById(R.id.textViewAppName);
            Switch switchPhotoPermission = convertView.findViewById(R.id.switchbtn);

            final AppInfo appInfo = getItem(position);

            if (appInfo != null) {
                imageViewIcon.setImageDrawable(appInfo.appIcon);
                textViewAppName.setText(appInfo.appName);

                switchPhotoPermission.setChecked(appInfo.hasPhotoPermission);

                switchPhotoPermission.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    appInfo.hasPhotoPermission = isChecked;

                    if (!isChecked) {
                        removePhotoPermission(appInfo.packageName);
                    }
                });
            }

            return convertView;
        }

        private void removePhotoPermission(String packageName) {
            try {
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);

                Toast.makeText(getContext(), "Please manually revoke photo permissions for " + packageName, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error opening settings for: " + packageName, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}