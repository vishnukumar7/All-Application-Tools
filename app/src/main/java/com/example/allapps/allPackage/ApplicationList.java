package com.example.allapps.allPackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.R;

import java.util.List;

public class ApplicationList extends RecyclerView.Adapter<ApplicationList.ApplicationviewHolder> {

    private final List<ResolveInfo> applicationInfos;
    private final Context context;
    public ApplicationList(Context context, List<ResolveInfo> applicationInfos){
        this.context=context;
        this.applicationInfos=applicationInfos;
    }
    @NonNull
    @Override
    public ApplicationviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ApplicationviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.application_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationviewHolder holder, int position) {
        final ResolveInfo resolveInfo=applicationInfos.get(position);
        final PackageManager packageManager=context.getPackageManager();
        holder.appName.setText(resolveInfo.loadLabel(packageManager).toString());
        holder.appImage.setImageDrawable(resolveInfo.activityInfo.loadIcon(packageManager));
        holder.applicationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName);
                if(intent==null){
                    intent=new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,resolveInfo.activityInfo.name));
                    intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationInfos.size();
    }

    class ApplicationviewHolder extends RecyclerView.ViewHolder{
        final LinearLayout applicationLayout;
        final ImageView appImage;
        final TextView appName;
        ApplicationviewHolder(@NonNull View itemView) {
            super(itemView);
            appImage=itemView.findViewById(R.id.appImageIcon);
            appName=itemView.findViewById(R.id.appName);
            applicationLayout=itemView.findViewById(R.id.application);
        }
    }
}
