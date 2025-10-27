package com.halata.blueapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.halata.blueapp.R;
import com.halata.blueapp.utils.Restore;
import com.halata.blueapp.viewmodels.LogViewModel;

public class HomeFragment extends Fragment {

    private LogViewModel logvm;
    private Button btnSendToBluetooth;
    private View rootView;
    public HomeFragment(){
        super(R.layout.home_fragment);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        logvm = new ViewModelProvider(requireActivity()).get(LogViewModel.class);

        logvm.connectToBluetoothDevice(view.getContext());
        logvm.startReadThread();

        btnSendToBluetooth.setOnClickListener(event -> {
            Restore halataConfig = new Restore(view.getContext());
            halataConfig.setOutputStream(logvm.getOutputStream());
            halataConfig.SendEX(0x62,Float.floatToIntBits(0.2f),false,"RAM");
        });

    }
}
