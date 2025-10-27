package com.halata.blueapp.viewmodels;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.halata.blueapp.data.models.BasicSetting;
import com.halata.blueapp.utils.SettingsHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LogViewModel extends ViewModel {
    private MutableLiveData<List<String>> text = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<BluetoothAdapter> bluetoothAdapter = new MutableLiveData<>();
    private MutableLiveData<List<Byte>> receivedBluetoothBuffer = new MutableLiveData<>(new ArrayList<>());
    private BluetoothSocket socket;
    private boolean threadStarted;
    private OutputStream outputStream;
    private InputStream inputStream;
    private static final UUID HC05_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public void setText(String value) {
        List<String> logData = text.getValue();
        logData.add(value);
        text.setValue(logData);
    }

    public LiveData<List<String>> getText() {
        return text;
    }

    public boolean connectToBluetoothDevice(Context context) {
        if(socket.isConnected())
            return true;
        try {
            BasicSetting readSettingFromFile = SettingsHelper.loadFromFile(context, "settings.json");
            if (readSettingFromFile != null) {
                String bluetoothMAC = readSettingFromFile.getBluetoothMACAddress();


                bluetoothAdapter.setValue(BluetoothAdapter.getDefaultAdapter());
                BluetoothDevice device = bluetoothAdapter.getValue().getRemoteDevice(bluetoothMAC);

                socket = device.createRfcommSocketToServiceRecord(HC05_UUID);
                socket.connect();
                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean sendToBluetooth(String data){
        try {
            outputStream.write(data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public  OutputStream getOutputStream(){
        return this.outputStream;
    }
    public void startReadThread(){
        if(threadStarted)
            return;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            while (true) {
                try {
                    byte[] buffer = new byte[64];
                    int bytes;

                    bytes = inputStream.read(buffer);
                    for (int i=0; i< bytes; i++)
                        receivedBluetoothBuffer.getValue().add(buffer[i]);
                    String incomingData = new String(buffer, 0, bytes);
                    text.getValue().add("Received : " + incomingData);

                    threadStarted = true;
                } catch (IOException e) {
                    Log.e("BT_ERROR", "Disconnected / Error reading");
                    threadStarted = false;
                    break;
                }
            }
        }).start();

    }

}
