package com.example.uspproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PermissionsManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_manager);

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        ArrayAdapter<ApplicationInfo> adapter = new ArrayAdapter<ApplicationInfo>(this, android.R.layout.simple_list_item_2, android.R.id.text1, packages) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder holder;

                if (convertView == null) {
                    RelativeLayout layout = new RelativeLayout(PermissionsManagerActivity.this);
                    layout.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

                    TextView appName = new TextView(PermissionsManagerActivity.this);
                    appName.setId(View.generateViewId());
                    appName.setTextSize(16);
                    appName.setTextColor(getResources().getColor(android.R.color.black));

                    Button managePermissions = new Button(PermissionsManagerActivity.this, null, 0, R.style.AppButton);
                    managePermissions.setId(View.generateViewId());
                    managePermissions.setText("Manage");

                    RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    textParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                    textParams.addRule(RelativeLayout.CENTER_VERTICAL);

                    RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    buttonParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                    buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);

                    layout.addView(appName, textParams);
                    layout.addView(managePermissions, buttonParams);

                    convertView = layout;

                    holder = new ViewHolder();
                    holder.appName = appName;
                    holder.managePermissions = managePermissions;
                    convertView.setTag(holder);
                } else {
                    // Reuse ViewHolder from the recycled view
                    holder = (ViewHolder) convertView.getTag();
                }

                ApplicationInfo packageInfo = packages.get(position);
                holder.appName.setText(packageInfo.loadLabel(pm));
                holder.managePermissions.setOnClickListener(v -> openAppSettings(packageInfo.packageName));

                return convertView;
            }
        };


        ListView listViewApps = findViewById(R.id.listViewApps);
        listViewApps.setAdapter(adapter);
    }

    private class ViewHolder {
        TextView appName;
        Button managePermissions;
    }

    private void openAppSettings(String packageName) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        startActivity(intent);
    }
}