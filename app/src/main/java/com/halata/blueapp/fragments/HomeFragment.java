package com.halata.blueapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.halata.blueapp.R;
import com.halata.blueapp.utils.Restore;
import com.halata.blueapp.viewmodels.LogViewModel;

public class HomeFragment extends Fragment {

    private LogViewModel logvm;
    private Button btnSendToBluetooth;
    private TextView tvAlert;
    private View rootView;
    private Button btnCheckBluetoothConnectivity;
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
        tvAlert = (TextView)view.findViewById(R.id.tvAlert);
        btnCheckBluetoothConnectivity = (Button)view.findViewById(R.id.btnCheckBluetooth);
        logvm.connectToBluetoothDevice(view.getContext());
        btnSendToBluetooth = (Button) view.findViewById(R.id.btnSendToBluetooth);
        if(Boolean.TRUE.equals(logvm.getConnectedStatus().getValue())) {
            logvm.startReadThread();

            tvAlert.setTextColor(Color.parseColor("#4CAF50"));
            tvAlert.setText("اتصال برقرار است \uD83D\uDC9A");
            btnCheckBluetoothConnectivity.setText("\uD83D\uDFE0");

            btnSendToBluetooth.setOnClickListener(event -> {
                Restore halataConfig = new Restore(view.getContext());
                halataConfig.setOutputStream(logvm.getOutputStream());
                halataConfig.SendEX(0x62, Float.floatToIntBits(0.2f), false, "RAM");
            });
        }else {
            tvAlert.setTextColor(Color.parseColor("#FF1744"));
            btnCheckBluetoothConnectivity.setText("\uD83D\uDC9A");
            tvAlert.setText("اتصال به دستگاه برقرار نیست \uD83D\uDFE0");
        }

        btnCheckBluetoothConnectivity.setOnClickListener(event -> {

            if(logvm.refreshBluetoothConnectivity(view.getContext())){
                tvAlert.setText("اتصال برقرار است \uD83D\uDC9A");
                tvAlert.setTextColor(Color.parseColor("#4CAF50"));
                btnCheckBluetoothConnectivity.setText("\uD83D\uDFE0");
                logvm.startReadThread();
            }else{
                tvAlert.setTextColor(Color.parseColor("#FF1744"));
                btnCheckBluetoothConnectivity.setText("\uD83D\uDC9A");
                tvAlert.setText("اتصال به دستگاه برقرار نیست \uD83D\uDFE0");
            }
        });
    }
}
