package com.halata.blueapp.fragments;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.halata.blueapp.R;
import com.halata.blueapp.utils.Restore;
import com.halata.blueapp.viewmodels.LogViewModel;

import java.io.IOException;

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
        boolean equalityTest= Boolean.TRUE.equals(logvm.getConnectedStatus().getValue()) ;
        if(equalityTest) {
            logvm.startReadThread();

            tvAlert.setTextColor(Color.parseColor("#4CAF50"));
            tvAlert.setText("اتصال برقرار است \uD83D\uDC9A");
            btnCheckBluetoothConnectivity.setText("\uD83D\uDFE0");

            btnSendToBluetooth.setOnClickListener(event -> {

                boolean connectionStat = Boolean.TRUE.equals(logvm.getConnectedStatus().getValue());
                if(connectionStat){
                    Restore halataConfig = new Restore(view.getContext());
                    halataConfig.setOutputStream(logvm.getOutputStream());
                    EditText txtBarcode = (EditText)view.findViewById(R.id.txtBarcode);
                    if(txtBarcode.getText().length() > 0){
                        byte[] dataToSend = txtBarcode.getText().toString().getBytes();
                        try {
                            logvm.getOutputStream().write(dataToSend);
                            logvm.setText("Send data to bluetooth : " + txtBarcode.getText().toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                //halataConfig.SendEX(0x62, Float.floatToIntBits(0.2f), false, "RAM");
            });
        }else {
            tvAlert.setTextColor(Color.parseColor("#FF1744"));
            btnCheckBluetoothConnectivity.setText("\uD83D\uDC9A");
            tvAlert.setText("اتصال به دستگاه برقرار نیست \uD83D\uDFE0");
        }

        btnCheckBluetoothConnectivity.setOnClickListener(event -> {
            boolean connectionStat = Boolean.TRUE.equals(logvm.getConnectedStatus().getValue());
            if(connectionStat){
                logvm.refreshBluetoothConnectivity(view.getContext());

                tvAlert.setTextColor(Color.parseColor("#FF1744"));
                btnCheckBluetoothConnectivity.setText("\uD83D\uDC9A");
                tvAlert.setText("اتصال به دستگاه برقرار نیست \uD83D\uDFE0");
            }else{
                logvm.refreshBluetoothConnectivity(view.getContext());
                tvAlert.setText("اتصال برقرار است \uD83D\uDC9A");
                tvAlert.setTextColor(Color.parseColor("#4CAF50"));
                btnCheckBluetoothConnectivity.setText("\uD83D\uDFE0");
                logvm.startReadThread();


            }
        });
    }
}
