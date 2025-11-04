package com.halata.blueapp.utils;


import android.util.Log;
import android.widget.Toast;
import android.content.Context;

import java.io.IOException;
import java.io.OutputStream;

public class Restore {

    private static final String TAG = "Restore";
    private static final byte HEADER_CHAR = 0x06;

    private OutputStream btOutputStream;
    private Context context;

    public Restore(Context context) {
        this.context = context;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.btOutputStream = outputStream;
    }
    public OutputStream getBtOutputStream(){return this.btOutputStream;}
    public void SendEX(int Address, int Data, boolean IsRead, String Memory) {

        try {
            byte tmpReadStr = 0x20;
            byte[] SendByteArr = new byte[0xFF];
            int I = 0;

            SendByteArr[I++] = HEADER_CHAR;

            if (IsRead)
                tmpReadStr |= 0x08;

            switch (Memory) {

                case "RAM":
                    SendByteArr[I++] = tmpReadStr;
                    SendByteArr[I++] = 0;
                    SendByteArr[I++] = (byte) (Address & 0xff);
                    SendByteArr[I++] = (byte) (Data & 0xff);
                    break;

                case "CLOCK":
                    SendByteArr[I++] = (byte) (tmpReadStr | 0xA0);
                    SendByteArr[I++] = 0;
                    SendByteArr[I++] = (byte) (Address & 0xff);
                    SendByteArr[I++] = (byte) (Data & 0xff);
                    break;

                case "FLACK":
                    SendByteArr[I++] = (byte) (tmpReadStr | 0xA1);
                    SendByteArr[I++] = (byte) (Address / 0x100);
                    SendByteArr[I++] = (byte) (Address & 0xff);
                    SendByteArr[I++] = (byte) (Data & 0xff);
                    break;

                case "FLASH":
                    byte DCHH = (byte) (Address / 0x10000);
                    DCHH &= 1;
                    SendByteArr[I++] = (byte) (tmpReadStr | 0xA2 | DCHH);
                    SendByteArr[I++] = (byte) (Address / 0x100);
                    SendByteArr[I++] = (byte) (Address & 0xff);
                    SendByteArr[I++] = (byte) (Data & 0xff);
                    break;
            }

            if (btOutputStream != null) {
                btOutputStream.write(SendByteArr, 0, I);
                btOutputStream.flush();
            }

        } catch (IOException ex) {
            Log.e(TAG, "SendEX failed: " + ex.getMessage());
            Toast.makeText(context, "SendEX failed:\n" + ex, Toast.LENGTH_LONG).show();
        }
    }
}


/*
            string[] SendToDevice =
            {
            "40",((byte)Markers[n].MotorsIntCof).ToString("X"),                            "false","RAM",           //MotorsIntCof
            "10",((byte)Markers[n].MotorsIntCof).ToString("X"),                            "false","FLACK",         //MotorsIntCof_FLACK
            "41",((byte)Markers[n].MinMotortime).ToString("X"),                            "false","RAM",           //MinMotortime
            "11",((byte)Markers[n].MinMotortime).ToString("X"),                            "false","FLACK",         //MinMotortime_FLACK
            "2E",((byte)Markers[n].Config1).ToString("X"),                                 "false","RAM",           //Config1
            "30",((byte)Markers[n].Config1).ToString("X"),                                 "false","FLACK",         //Config1_FLACK
            "2F",((byte)Markers[n].Config2).ToString("X"),                                 "false","RAM",           //Config2
            "31",((byte)Markers[n].Config2).ToString("X"),                                 "false","FLACK",         //Config2_FLACK
            "62",((byte)Markers[n].PenDelay).ToString("X"),                                "false","RAM",           //PenDelay
            "32",((byte)Markers[n].PenDelay).ToString("X"),                                "false","FLACK",         //PenDelay_FLACK
            "63",((byte)Markers[n].DefaultMode).ToString("X"),                             "false","RAM",           //DefaultMode
            "33",((byte)Markers[n].DefaultMode).ToString("X"),                             "false","FLACK",         //DefaultMode_FLACK
            "64",((byte)Markers[n].MotorXHalfDelay).ToString("X"),                         "false","RAM",           //MotorXHalf
            "34",((byte)Markers[n].MotorXHalfDelay).ToString("X"),                         "false","FLACK",         //MotorXHalf_FLACK
            "65",((byte)Markers[n].MotorYHalfDelay).ToString("X"),                         "false","RAM",           //MotorXHalf
            "35",((byte)Markers[n].MotorYHalfDelay).ToString("X"),                         "false","FLACK",         //MotorXHalf_FLACK

            "66",((byte)(Markers[n].MotorXextent & 0xFF)).ToString("X"),                   "false","RAM",           //MotorXHalf
            "36",((byte)(Markers[n].MotorXextent & 0xFF)).ToString("X"),                   "false","FLACK",         //MotorXHalf_FLACK
            "67",((byte)(Markers[n].MotorXextent / 0x100)).ToString("X"),                  "false","RAM",           //MotorXHalf
            "37",((byte)(Markers[n].MotorXextent / 0x100)).ToString("X"),                  "false","FLACK",         //MotorXHalf_FLACK
            "68",((byte)(Markers[n].MotorYextent & 0xFF)).ToString("X"),                   "false","RAM",           //MotorXHalf
            "38",((byte)(Markers[n].MotorYextent & 0xFF)).ToString("X"),                   "false","FLACK",         //MotorXHalf_FLACK
            "69",((byte)(Markers[n].MotorYextent / 0x100)).ToString("X"),                  "false","RAM",           //MotorXHalf
            "39",((byte)(Markers[n].MotorYextent / 0x100)).ToString("X"),                  "false","FLACK",         //MotorXHalf_FLACK

            "5D",((byte)Markers[n].SERNOPenSpeed).ToString("X"),                           "false","RAM",           //SERNO_PDSPEED
            "2D",((byte)Markers[n].SERNOPenSpeed).ToString("X"),                           "false","FLACK",         //SERNO_PDTIME_FLACK
            //"2F",((byte)((Markers[n].SERNORotation * 64 / 90) + 32 + 1)).ToString("X"),    "false","RAM",           //CONFIGUR2
            //"31",((byte)((Markers[n].SERNORotation * 64 / 90) + 32 + 1)).ToString("X"),    "false","FLACK",         //CONFIGUR2_FLACK
            "6A",((byte)Markers[n].SERNOscaleX).ToString("X"),                             "false","RAM",           //SCALEX
            "3C",((byte)Markers[n].SERNOscaleX).ToString("X"),                             "false","FLACK",         //SERNO_XSIZE_FLACK
            "6B",((byte)Markers[n].SERNOscaleY).ToString("X"),                             "false", "RAM",          //SCALEY
            "3D",((byte)Markers[n].SERNOscaleY).ToString("X"),                             "false", "FLACK",        //SERNO_YSIZE_FLACK
            "40",((byte)((int)Markers[n].SERNOoriginX & 0XFF)).ToString("X"),              "false", "FLACK",        //SERNO_X0_FLACK
            "41",((byte)(Markers[n].SERNOoriginX / 0x100)).ToString("X"),                  "false", "FLACK",        //SERNO_X1_FLACK
            "42",((byte)((int)Markers[n].SERNOoriginY & 0XFF)).ToString("X"),              "false", "FLACK",        //SERNO_Y0_FLACK
            "43",((byte)(Markers[n].SERNOoriginY / 0x100)).ToString("X"),                  "false", "FLACK",        //SERNO_Y1_FLACK
            "50",((byte)((int)Markers[n].SERNOnextLineX & 0XFF)).ToString("X"),            "false", "FLACK",        //NEXT_LINEX0_FLACK
            "51",((byte)(Markers[n].SERNOnextLineX / 0x100)).ToString("X"),                "false", "FLACK",        //NEXT_LINEX1_FLACK
            "52",((byte)((int)Markers[n].SERNOnextLineY & 0XFF)).ToString("X"),            "false", "FLACK",        //NEXT_LINEY0_FLACK
            "53",((byte)(Markers[n].SERNOnextLineY / 0x100)).ToString("X"),                "false", "FLACK",        //NEXT_LINEY1_FLACK
            "28",((byte)(Markers[n].SERNO_LSB)).ToString("X"),                             "false", "FLACK",        //SERNO_LSB_FLACK
            "29",((byte)(Markers[n].SERNO_MSB)).ToString("X"),                             "false", "FLACK",        //SERNO_MSB_FLACK
            "58",((byte)(Markers[n].SERNO_LSB)).ToString("X"),                             "false", "RAM",        //SERNO_LSB_FLACK
            "59",((byte)(Markers[n].SERNO_MSB)).ToString("X"),                             "false", "RAM",        //SERNO_MSB_FLACK
            "44",((byte)(Markers[n].SERNOspace)).ToString("X"),                            "false", "FLACK",        //serno space
            "45",((byte)(1)).ToString("X"),                                                "false", "FLACK",        //serno rate
            "46",((byte)(0)).ToString("X"),                                                "false", "FLACK",        //serno end
            "47",((byte)(Markers[n].SERNOdelay * 10)).ToString("X"),                       "false", "FLACK",        //SERNO_DELAY_FLACK
            "55",((int)(Markers[n].Pen_Duty_On)).ToString("X"),                            "false", "RAM",          //Pen_Duty_On
            "25",((int)(Markers[n].Pen_Duty_On)).ToString("X"),                            "false", "FLACK",        //Pen_Duty_On
            "56",((int)(Markers[n].Pen_Duty_Off)).ToString("X"),                           "false", "RAM",          //Pen_Duty_Off
            "26",((int)(Markers[n].Pen_Duty_Off)).ToString("X"),                           "false", "FLACK",        //Pen_Duty_Off
            "5A",((int)(Markers[n].DotSpace) & 0XFF).ToString("X"),                        "false", "RAM",          //DotSpace
            "2A",((int)(Markers[n].DotSpace) & 0XFF).ToString("X"),                        "false", "FLACK",        //DotSpace
            "5B",((int)(Markers[n].DotSpace)/0x100).ToString("X"),                         "false", "RAM",          //DotSpace
            "2B",((int)(Markers[n].DotSpace)/0x100).ToString("X"),                         "false", "FLACK",        //DotSpace
            "5E",((int)(Markers[n].SERNOIntervalTime*10) & 0XFF).ToString("X"),            "false", "RAM",          //AUTO_TIMEth
            "2E",((int)(Markers[n].SERNOIntervalTime*10) & 0XFF).ToString("X"),            "false", "FLACK",        //AUTO_TIMEth_FLACK
            "5F",((int)(Markers[n].SERNOIntervalTime*10)/0x100).ToString("X"),             "false", "RAM",          //AUTO_TIME
            "2F",((int)(Markers[n].SERNOIntervalTime*10)/0x100).ToString("X"),             "false", "FLACK",        //AUTO_TIME_FLACK
            "26",((byte)(0)).ToString("X"),                                                 "true", "RAM",          //PEN_SERVER
            };

* */