package com.benjaminl4917.qrscanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BottomDialog extends BottomSheetDialogFragment {
    private TextView title, link, visitBtn, shareBtn;

    private ImageView close;
    private String fetchURL, fetchTitle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog, container, false);

        title = view.findViewById(R.id.titleTxt);
        link = view.findViewById(R.id.linkTxt);
        visitBtn = view.findViewById(R.id.visit);
        close = view.findViewById(R.id.close);
        shareBtn = view.findViewById(R.id.shareLink);

        link.setText(fetchURL);
        title.setText(fetchTitle);

        visitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse(fetchURL));
                startActivity(i);

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData();
            }
        });
        return view;
    }

    public void fetchURL(String url){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                fetchURL = url;
            }
        });
    }

    public void fetchTitle(String title){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                fetchTitle = title;
            }
        });
    }

    public void shareData(){
        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("text/plain");

        i.putExtra(Intent.EXTRA_TEXT, "QR Code Link: " + fetchURL);


        startActivity(Intent.createChooser(i,"Choose a platform"));

    }
}
