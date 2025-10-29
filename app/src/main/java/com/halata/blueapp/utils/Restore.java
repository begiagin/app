package com.halata.blueapp.utils;


import android.util.Log;
import android.widget.Toast;
import android.content.Context;

import java.io.IOException;
import java.io.OutputStream;

public class Restore {

    private static final String TAG = "Restore";
    private static final byte HEADER_CHAR = 0x20;

    private OutputStream btOutputStream;
    private Context context;

    public Restore(Context context) {
        this.context = context;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.btOutputStream = outputStream;
    }

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
