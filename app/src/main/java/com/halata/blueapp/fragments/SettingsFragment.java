package com.halata.blueapp.fragments;

import static android.bluetooth.BluetoothAdapter.getDefaultAdapter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.halata.blueapp.R;
import com.halata.blueapp.data.models.BasicSetting;
import com.halata.blueapp.utils.InputValidator;
import com.halata.blueapp.utils.SettingsHelper;
import com.halata.blueapp.utils.ValidationResult;
import com.halata.blueapp.viewmodels.LogViewModel;

import java.util.Set;

public class SettingsFragment extends Fragment {
    private Spinner spinnerDevices;
    private BluetoothAdapter bluetoothAdapter;
    private View rootView;
    private LogViewModel logvm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
         rootView = inflater.inflate(R.layout.setting_fragment, container, false);
         return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        spinnerDevices = (Spinner) rootView.findViewById(R.id.spinner);
        logvm = new ViewModelProvider(requireActivity()).get(LogViewModel.class);
        bluetoothAdapter = getDefaultAdapter();

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            showBluetoothDevices();
        } else {
            // Show Toast Messsae
        }

        // load all settings and put all values in appropriate places
        SettingsHelper.logViewModel = logvm;
        BasicSetting readSettingFromFile = SettingsHelper.loadFromFile(getView().getContext(),"settings.json", logvm);

        // 1-20 :1
        EditText txtRightCounter = (EditText)rootView.findViewById(R.id.txtRightCounter);
        //1-20 :1
        EditText txtLeftCounter = (EditText)rootView.findViewById(R.id.txtLeftCounter);
        //0.1-25.5 : 0.1
        EditText txtDelayBeforeMark = (EditText)rootView.findViewById(R.id.txtDelayBeforeMark);
        //0-25.5 : 0
        EditText txtDelayAfterMark = (EditText)rootView.findViewById(R.id.txtDelayAfterMark);
        //0-65535 : 30
        EditText txtNextLineX = (EditText)rootView.findViewById(R.id.txtNextLineX);
        //0-65535 : 30
        EditText txtNextLineY = (EditText)rootView.findViewById(R.id.txtNextLineY);
        //1-100 : 50
        EditText txtMarkVelocity = (EditText)rootView.findViewById(R.id.txtMarkVelocity);
        //0 90 180 270 : 0
        EditText txtRotationAngle = (EditText)rootView.findViewById(R.id.txtRotationAngle);
        // 1 -50 : 25
        EditText txtXAxisCoffieceint = (EditText)rootView.findViewById(R.id.txtXAxisCoffieceint);
        // 1 -50 : 25
        EditText txtYAxisCoffieceint = (EditText)rootView.findViewById(R.id.txtYAxisCoffieceint);
        // 0-65535 : 100
        EditText txtStartXPoint = (EditText)rootView.findViewById(R.id.txtStartXPoint);
        // 0-65535 : 100
        EditText txtStartYPoint = (EditText)rootView.findViewById(R.id.txtStartYPoint);
        // 0 - 255 : 8
        EditText txtPointDistance = (EditText)rootView.findViewById(R.id.txtPointsDistance);
        // 0 - 255 : 0
        EditText txtHitPower = (EditText)rootView.findViewById(R.id.txtHitPower);
        //0 - 255 : 0
        EditText txtPenFrequency = (EditText)rootView.findViewById(R.id.txtPenFrequency);
        CheckBox cbGotoZeroHardware = (CheckBox)rootView.findViewById(R.id.cbGotoZeroHardware);
        CheckBox cbSendExtraData = (CheckBox)rootView.findViewById(R.id.cbSendExtraData);
        Spinner  spBluetoothMAC = (Spinner)rootView.findViewById(R.id.spinner) ;

        if(readSettingFromFile != null) {
            SettingsHelper.SaveSettings(getView().getContext(), readSettingFromFile);

            txtRightCounter.setText(String.valueOf(readSettingFromFile.getRightCounter()));
            txtLeftCounter.setText(String.valueOf(readSettingFromFile.getLeftCounter()));
            txtDelayBeforeMark.setText(String.valueOf(readSettingFromFile.getDelayBeforeMark()));
            txtDelayAfterMark.setText(String.valueOf(readSettingFromFile.getDelayAfterMark()));
            txtNextLineX.setText(String.valueOf(readSettingFromFile.getNextLineX()));
            txtNextLineY.setText(String.valueOf(readSettingFromFile.getNextLineY()));
            txtMarkVelocity.setText(String.valueOf(readSettingFromFile.getMarkSpeed()));
            txtRotationAngle.setText(String.valueOf(readSettingFromFile.getRotationAngle()));
            txtXAxisCoffieceint.setText(String.valueOf(readSettingFromFile.getxCofficeintAxis()));
            txtYAxisCoffieceint.setText(String.valueOf(readSettingFromFile.getyConfficeintAxis()));
            txtStartXPoint.setText(String.valueOf(readSettingFromFile.getStartMarkX()));
            txtStartYPoint.setText(String.valueOf(readSettingFromFile.getStartMarkY()));
            txtPointDistance.setText(String.valueOf(readSettingFromFile.getPointsDistances()));
            txtHitPower.setText(String.valueOf(readSettingFromFile.getHitPower()));
            txtPenFrequency.setText(String.valueOf(readSettingFromFile.getPenFrequency()));
            cbGotoZeroHardware.setChecked(readSettingFromFile.isGotoHardwareZero());
            cbSendExtraData.setChecked(readSettingFromFile.isSendExtraHeader());

            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spBluetoothMAC.getAdapter();
            int foundIndex = -1;
            String inLoopItem = "";
            String currentMACAddr = readSettingFromFile.getBluetoothMACAddress();
            for (int i =0; i < adapter.getCount(); i++){
                inLoopItem = adapter.getItem(i).toString();
                if(inLoopItem.equals(currentMACAddr) )
                    foundIndex = i;
            }

            if(foundIndex != -1)
                spBluetoothMAC.setSelection(foundIndex);
        }

        Button btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(event -> {
            // Setting Paramenters validations
            if(txtRightCounter.getText().length() < 1 ||
                    txtLeftCounter.getText().length() < 1 ||
                    txtDelayBeforeMark.getText().length() < 1 ||
                    txtDelayAfterMark.getText().length() < 1 ||
                    txtNextLineX.getText().length() < 1 ||
                    txtNextLineY.getText().length() < 1 ||
                    txtMarkVelocity.getText().length() < 1 ||
                    txtRotationAngle.getText().length() < 1 ||
                    txtXAxisCoffieceint.getText().length() < 1 ||
                    txtYAxisCoffieceint.getText().length() < 1 ||
                    txtStartXPoint.getText().length() < 1 ||
                    txtStartYPoint.getText().length() < 1 ||
                    txtPointDistance.getText().length() < 1 ||
                    txtHitPower.getText().length() < 1 ||
                    txtPenFrequency.getText().length() < 1
            ) {
                Toast.makeText(rootView.getContext(), "ورودی های را به شکل صحیح وارد نماپید", Toast.LENGTH_SHORT).show();
                return;
            }


            // Spinner Selected validation
            if(spBluetoothMAC.getSelectedItem().toString().indexOf(':') < 0) {
                Toast.makeText(rootView.getContext(), "هیچ دستگاهی برای اتصال موجود نیست", Toast.LENGTH_SHORT).show();
                logvm.setText("No Device found for Joint with Terminal");
                return;
            }

            try {
                BasicSetting s = new BasicSetting();
                s.setBluetoothMACAddress(spBluetoothMAC.getSelectedItem().toString());
                s.setRightCounter(Integer.parseInt(String.valueOf(txtRightCounter.getText())));
                s.setLeftCounter(Integer.parseInt(String.valueOf(txtLeftCounter.getText())));
                s.setDelayBeforeMark(Float.parseFloat(String.valueOf(txtDelayBeforeMark.getText())));
                s.setDelayAfterMark(Float.parseFloat(String.valueOf(txtDelayAfterMark.getText())));
                s.setNextLineX(Integer.parseInt(String.valueOf(txtNextLineX.getText())));
                s.setNextLineY(Integer.parseInt(String.valueOf(txtNextLineY.getText())));
                s.setMarkSpeed(Integer.parseInt(String.valueOf(txtMarkVelocity.getText())));
                s.setRotationAngle(Integer.parseInt(String.valueOf(txtRotationAngle.getText())));
                s.setxCofficeintAxis(Float.parseFloat(String.valueOf(txtXAxisCoffieceint.getText())));
                s.setyConfficeintAxis(Float.parseFloat(String.valueOf(txtYAxisCoffieceint.getText())));
                s.setStartMarkX(Integer.parseInt(String.valueOf(txtStartXPoint.getText())));
                s.setStartMarkY(Integer.parseInt(String.valueOf(txtStartYPoint.getText())));
                s.setPointsDistances(Float.parseFloat(String.valueOf(txtPointDistance.getText())));
                s.setHitPower(Integer.parseInt(String.valueOf(txtHitPower.getText())));
                s.setPenFrequency(Integer.parseInt(String.valueOf(txtPenFrequency.getText())));
                s.setGotoHardwareZero(Boolean.parseBoolean(String.valueOf(cbGotoZeroHardware.isChecked())));
                s.setSendExtraHeader(Boolean.parseBoolean(String.valueOf(cbSendExtraData.isChecked())));




                float[] mins = {1, 1, 0.1f,0,0,0,1,0,1,1,0,0,0,0,0};
                float[] maxs = {20, 20, 25.5f,25.5f,65535,65535,100,270,50,50,65535,65535,255,255,255};
                String[] errors = {
                        "شمارنده راست باید بین 1 تا 20 باشد",
                        "شمارنده چپ باید بین 1 تا 20 باشد",
                        "تاخیر قبل از حک باید بین 0.1 تا 25.5 باشد",
                        "تاخیر بعد از حک باید بین 0 تا 25.5 باشد",
                        "X سطر بعد باید بین 0 تا 65535 باشد",
                        "Y سطر بعد باید بین 0 تا 65535 باشد",
                        "سرعت حک باید بین 1 تا 100 باشد",
                        "درجه چرخش باید بین 0 تا 270 باشد",
                        "ضریب محور X باید بین 1 تا 50 باشد",
                        "ضریب محور Y باید بین 1 تا 50 باشد",
                        "نقطه آغاز حک X باید بین 0 تا 65535 باشد",
                        "نقطه آغاز حک Y باید بین 1 تا 65535 باشد",
                        "فاصله نقاط باید بین 0 تا 255 باشد",
                        "توان ضربه باید بین 0 تا 255 باشد",
                        "بسامد قلم باید بین 0 تا 255 باشد"
                };
                EditText[] boxes = {txtRightCounter, txtLeftCounter, txtDelayBeforeMark,
                        txtDelayAfterMark, txtNextLineX, txtNextLineY,txtMarkVelocity,txtRotationAngle,txtXAxisCoffieceint,
                        txtYAxisCoffieceint,txtStartXPoint,txtStartYPoint,txtPointDistance,txtHitPower,txtPenFrequency
                };

                ValidationResult result = InputValidator.validateAll(boxes, mins, maxs, errors);

                if (result.isValid()) {
                    SettingsHelper.saveToFile(getView().getContext(), s, "settings.json", logvm);
                    SettingsHelper.SaveSettings(getView().getContext(), s);
                    Toast.makeText(getView().getContext(), "ذخیره جزییات انجام شد  ✅", Toast.LENGTH_SHORT).show();
                    logvm.setText("saved setting");
                } else {
                    Toast.makeText(getView().getContext(), result.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

            }catch (Exception ex){
                Log.e("PARSE SETTINGS", ex.getMessage());
                logvm.setText("Parse error in Save settings");
            }

        });

    }
    public SettingsFragment() {
        super(R.layout.setting_fragment);
    }

    private void showBluetoothDevices() {


        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item);

        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(devices != null && !devices.isEmpty()){
            for (BluetoothDevice device : devices){
                listAdapter.add(device.getAddress());
            }
        }else{
            listAdapter.add("هیچ دستگاهی توسط گوشی جفت نشده است");
        }
        spinnerDevices.setAdapter(listAdapter);
    }
}
