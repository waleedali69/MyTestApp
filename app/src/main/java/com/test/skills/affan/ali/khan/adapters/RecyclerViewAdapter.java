package com.test.skills.affan.ali.khan.adapters;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.test.skills.affan.ali.khan.MainActivity;
import com.test.skills.affan.ali.khan.R;
import com.test.skills.affan.ali.khan.databinding.ItemLayoutBinding;
import com.test.skills.affan.ali.khan.model.Model;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Model> placeList;

    public RecyclerViewAdapter(Context context, List<Model> placeList) {
        this.context = context;
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemLayoutBinding itemLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()), R.layout.item_layout, viewGroup, false);

        return new MyViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Model model = placeList.get(i);
        myViewHolder.itemLayoutBinding.setModel(model);
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ItemLayoutBinding itemLayoutBinding;
        Button download;

        public MyViewHolder(@NonNull ItemLayoutBinding itemView) {
            super(itemView.getRoot());
            itemLayoutBinding = itemView;
            download = itemView.btnDownload;
            ProgressBar progressBar = itemView.downloadProgress;


            download.setOnClickListener(view -> {
                String url = itemView.getModel().getImageUrl();
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                String filename = url.substring(url.lastIndexOf('/') + 1);
                Log.i("onLoadResource()", "url = " + url + "\n" + filename);

                request.setDescription(itemView.getModel().getTitle());
                request.setTitle("Downloading");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

                final DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                final long downloadId = manager.enqueue(request);

                context.registerReceiver(onComplete,
                        new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                new Thread(() -> {

                    boolean downloading = true;

                    while (downloading) {

                        DownloadManager.Query q = new DownloadManager.Query();
                        q.setFilterById(downloadId);

                        Cursor cursor = manager.query(q);
                        cursor.moveToFirst();
                        int bytes_downloaded = cursor.getInt(cursor
                                .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false;
                        }

                        final int dl_progress = (int) ((double)bytes_downloaded / (double)bytes_total * 100f);
                        ((MainActivity)context).runOnUiThread((Runnable) () -> progressBar.setProgress((int) dl_progress));
                        cursor.close();
                    }

                }).start();
            });
        }
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                download.setEnabled(true);
                download.setText(context.getString(R.string.redownload));
            }
        };
    }

}
