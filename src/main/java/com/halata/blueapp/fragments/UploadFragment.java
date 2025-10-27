package com.halata.blueapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.halata.blueapp.R;
import com.halata.blueapp.viewmodels.LogViewModel;

public class UploadFragment extends Fragment {

    private LogViewModel logvm;
    private TextView txtLogView;
    private View rootView;
    public UploadFragment(){
        super(R.layout.upload_fragment);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.upload_fragment, container, false);
        return rootView;
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        txtLogView = rootView.findViewById(R.id.txtLogView);
        logvm = new ViewModelProvider(requireActivity()).get(LogViewModel.class);

        logvm.getText().observe(getViewLifecycleOwner(), value -> {
            StringBuilder sb = new StringBuilder();
            for (String s : value){
                sb.append(s+"\n");
            }
            txtLogView.setText(sb.toString());
        });

    }
}
