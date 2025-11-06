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
import com.halata.blueapp.utils.Restore;
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

                    boolean connectionStat = false;
                    connectionStat = Boolean.TRUE.equals(logvm.getConnectedStatus().getValue());

                    if(connectionStat){

                        Restore halataConfig = new Restore(view.getContext());
                        halataConfig.setOutputStream(logvm.getOutputStream());
                        //halataConfig.SendEX(0x24,s.getRightCounter(),false,"RAM"); //
                        String MotorsIntCof = "1";
                        String MinMotortime = "1";
                        String Config1 = String.valueOf(0x80);
                        String Config2 = String.valueOf(0x21);
                        String PenDelay = String.valueOf(0x4);
                        String DefaultMode = String.valueOf(0x20);
                        String MotorXHalfDelay = String.valueOf(0x20);
                        String MotorYHalfDelay  = String.valueOf(0x20);
                        String MotorXextent = String.valueOf(0x2000);
                        String MotorYextent = String.valueOf(0x2000);
                        String Pen_Duty_On = String.valueOf(0x0);
                        String Pen_Duty_Off = String.valueOf(0x0);
                        String characterSpace = String.valueOf(0x8);
                        String[] SendToDevice = {
                                // ===== MotorsIntCof =====
                                "40", MotorsIntCof, "false", "RAM",          // MotorsIntCof
                                "10", MotorsIntCof, "false", "FLACK",        // MotorsIntCof_FLACK

                                // ===== MinMotorTime =====
                                "41", MinMotortime, "false", "RAM",          // MinMotortime
                                "11", MinMotortime, "false", "FLACK",        // MinMotortime_FLACK

                                // ===== Config1 =====
                                "2E", Config1, "false", "RAM",               // Config1
                                "30", Config1, "false", "FLACK",             // Config1_FLACK

                                // ===== Config2 =====
                                "2F", Config2, "false", "RAM",               // Config2
                                "31", Config2, "false", "FLACK",             // Config2_FLACK

                                // ===== Pen Delay =====
                                "62", PenDelay, "false", "RAM",              // PenDelay
                                "32", PenDelay, "false", "FLACK",            // PenDelay_FLACK

                                // ===== Default Mode =====
                                "63", DefaultMode, "false", "RAM",           // DefaultMode
                                "33", DefaultMode, "false", "FLACK",         // DefaultMode_FLACK

                                // ===== MotorXHalfDelay =====
                                "64", MotorXHalfDelay, "false", "RAM",
                                "34", MotorXHalfDelay, "false", "FLACK",

                                // ===== MotorYHalfDelay =====
                                "65", MotorYHalfDelay, "false", "RAM",
                                "35", MotorYHalfDelay, "false", "FLACK",

                                // ===== MotorXExtent =====
                                "66", String.valueOf(Integer.parseInt(MotorXextent) & 0xFF), "false", "RAM",
                                "36", String.valueOf(Integer.parseInt(MotorXextent) & 0xFF), "false", "FLACK",
                                "67", String.valueOf(Integer.parseInt(MotorXextent) / 0x100), "false", "RAM",
                                "37", String.valueOf(Integer.parseInt(MotorXextent) / 0x100), "false", "FLACK",

                                // ===== MotorYExtent =====
                                "68", String.valueOf(Integer.parseInt(MotorYextent) & 0xFF), "false", "RAM",
                                "38", String.valueOf(Integer.parseInt(MotorYextent) & 0xFF), "false", "FLACK",
                                "69", String.valueOf(Integer.parseInt(MotorYextent) / 0x100), "false", "RAM",
                                "39", String.valueOf(Integer.parseInt(MotorYextent) / 0x100), "false", "FLACK",

                                // ===== SERNO_PDSPEED =====
                                "5D", String.valueOf(Integer.parseInt(String.valueOf(s.getMarkSpeed()), 16)), "false", "RAM",
                                "2D", String.valueOf(Integer.parseInt(String.valueOf(s.getMarkSpeed()), 16)), "false", "FLACK",

                                // ===== SCALE X / Y =====
                                "6A", String.valueOf((int)(s.getxCofficeintAxis() * 10)), "false", "RAM",
                                "3C", String.valueOf((int)(s.getxCofficeintAxis() * 10)), "false", "FLACK",
                                "6B", String.valueOf((int)(s.getyConfficeintAxis() * 10)), "false", "RAM",
                                "3D", String.valueOf((int)(s.getyConfficeintAxis() * 10)), "false", "FLACK",

                                // ===== Start Mark =====
                                "40", String.valueOf(s.getStartMarkX() & 0xFF), "false", "FLACK",
                                "41", String.valueOf(s.getStartMarkX() / 0x100), "false", "FLACK",
                                "42", String.valueOf(s.getStartMarkY() & 0xFF), "false", "FLACK",
                                "43", String.valueOf(s.getStartMarkY() / 0x100), "false", "FLACK",

                                // ===== Next Line =====
                                "50", String.valueOf(s.getNextLineX() & 0xFF), "false", "FLACK",
                                "51", String.valueOf(s.getNextLineX() / 0x100), "false", "FLACK",
                                "52", String.valueOf(s.getNextLineY() & 0xFF), "false", "FLACK",
                                "53", String.valueOf(s.getNextLineY() / 0x100), "false", "FLACK",

                                // ===== Counters =====
                                "28", String.valueOf(s.getRightCounter()), "false", "FLACK",
                                "29", String.valueOf(s.getLeftCounter()),  "false", "FLACK",
                                "58", String.valueOf(s.getRightCounter()), "false", "RAM",
                                "59", String.valueOf(s.getLeftCounter()),  "false", "RAM",

                                // ===== Character Space =====
                                "44", characterSpace, "false", "FLACK",

                                // ===== Delay Before Mark =====
                                "47", String.valueOf((int)(s.getDelayBeforeMark() * 10)), "false", "FLACK",

                                // ===== Pen Duty On/Off =====
                                "55", Pen_Duty_On,  "false", "RAM",
                                "25", Pen_Duty_On,  "false", "FLACK",
                                "56", Pen_Duty_Off, "false", "RAM",
                                "26", Pen_Duty_Off, "false", "FLACK",

                                // ===== Dot Space =====
                                "5A", String.valueOf((int)(s.getPointsDistances() * 10) & 0xFF), "false", "RAM",
                                "2A", String.valueOf((int)(s.getPointsDistances() * 10) & 0xFF), "false", "FLACK",
                                "5B", String.valueOf((int)(s.getPointsDistances() * 10) / 0x100), "false", "RAM",
                                "2B", String.valueOf((int)(s.getPointsDistances() * 10) / 0x100), "false", "FLACK",

                                // ===== Delay After Mark =====
                                "5E", String.valueOf((int)(s.getDelayAfterMark() * 10) & 0xFF), "false", "RAM",
                                "2E", String.valueOf((int)(s.getDelayAfterMark() * 10) & 0xFF), "false", "FLACK",
                                "5F", String.valueOf((int)(s.getDelayAfterMark() * 10) / 0x100), "false", "RAM",
                                "2F", String.valueOf((int)(s.getDelayAfterMark() * 10) / 0x100), "false", "FLACK",

                                // ===== Pen Server =====
                                "26", String.valueOf((byte)(0)), "true", "RAM"
                        };

                        logvm.setText("Total String should be saved is : " + SendToDevice.length);
                        for (int k = 0; k < SendToDevice.length; k += 4) {

                            int adrs = Integer.parseInt(SendToDevice[k], 16) & 0xFF;



                            int dt;
                            String value = SendToDevice[k + 1];

                            if (value.matches("^[0-9A-Fa-f]+$") && value.length() <= 2) {
                                dt = Integer.parseInt(value, 16) & 0xFF;
                            } else {
                                dt = (int) (Long.parseLong(value) & 0xFFFFFFFFL);
                            }


                            boolean rd = Boolean.parseBoolean(SendToDevice[k + 2]);
                            String mm = SendToDevice[k + 3];

                            logvm.setText(adrs+ " " + dt + " " +
                                    rd + " " + mm + " index=" + k);

                            halataConfig.SendEX(adrs, dt, rd, mm);
                            Thread.sleep(10);
                        }



                    }

                    Toast.makeText(getView().getContext(), "ذخیره جزییات انجام شد  ✅", Toast.LENGTH_SHORT).show();
                    logvm.setText("saved setting");
                } else {
                    Toast.makeText(getView().getContext(), result.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

            }catch (Exception ex){
                Log.e("PARSE SETTINGS", ex.getMessage());
                logvm.setText(ex.getMessage());
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
