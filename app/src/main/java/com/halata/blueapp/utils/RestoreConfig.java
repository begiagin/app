package com.halata.blueapp.utils;

public class RestoreConfig {

    public static int SerNoYekan = 0;
    public static int SerNoEnd = 0;
    public static int SerNoCount = 0;
    public static int SerNoSpeed = 40;
    public static int SerNoSpace = 12;
    public static int SerNoAngel = 90;

    public static byte PenDelayTime = 6;

    public static double MarkerExtentX = 100;  // میلی‌متر
    public static double MarkerExtentY = 100;  // میلی‌متر
    public static double MarkerPixelsX = 100;  // میلی‌متر
    public static double MarkerPixelsY = 100;  // میلی‌متر

    public static byte SERNO_X0_FLACK = (byte) 0x40;
    public static byte SERNO_X1_FLACK = (byte) 0x41;
    public static byte SERNO_Y0_FLACK = (byte) 0x42;
    public static byte SERNO_Y1_FLACK = (byte) 0x43;
    public static byte SERNO_FEEDBACK = (byte) 0x31;
    public static byte SERNO_SPACE_FLACK = (byte) 0x44;

    public static final byte Header_char = 0x06;
    public static final byte Return_char = 0x0D;
    public static final byte LineFeed_char = 0x0A;
    public static final byte XON_char = 0x01;
    public static final byte XOFF_char = 0x02;
    public static final byte IN_XOn_Char = 0x11;
    public static final byte IN_XOff_Char = 0x13;
    public static final byte START_Char = 0x05;
    public static final byte COMMANDS = 0x23;
    public static final byte CONFIGUR2 = 0x2F;
    public static final byte OPERATE = 0x22;

    public static byte EOF_Char = (byte) '=';
    /*
      void sendStartCommand() {
    Uint8List listOfData = Uint8List.fromList([0x06, 0x20, 0x00, 0x24, 0x04]);
    if (_isConnected) {
      _connection?.output.add(listOfData);
    }
  }

  void sendStopCommand() async {
    Uint8List listOfData = Uint8List.fromList([0x06, 0x20, 0x00, 0x24, 0x01]);
    if (_isConnected) {
      _connection?.output.add(listOfData);
    }
  }

  void sendUpCommand() async {
    Uint8List listOfData = Uint8List.fromList([0x06, 0x20, 0x00, 0x24, 0x02]);
    if (_isConnected) {
      _connection?.output.add(listOfData);
    }
  }

  void sendDownCommand() async {
    Uint8List listOfData = Uint8List.fromList([0x06, 0x20, 0x00, 0x24, 0x08]);
    if (_isConnected) {
      _connection?.output.add(listOfData);
    }
  }

  void sendPenCnommand() async {
    Uint8List listOfData = Uint8List.fromList([0x06, 0x28, 0x00, 0x22, 0x00]);
    if (_isConnected) {
      _connection?.output.add(listOfData);
    }
  }

  void sendSetCnommand() async {
    Uint8List listOfData = Uint8List.fromList([0x06, 0x20, 0x00, 0x24, 0x80]);
    if (_isConnected) {
      _connection?.output.add(listOfData);
    }
  }

  Future<bool> readPenStatus() async {
    Uint8List listOfData = Uint8List.fromList([0x06, 0x28, 0x00, 0x22, 0x00]);
    if (_isConnected) {
      _connection?.output.add(listOfData);
    }
    var test = _connection?.input;
    logger.d(test);
    return false;
  }

  void sendBarcodeContent(String barcode) async {
    String tempBarcode = barcode; //+ "\r";

    List<int> list = utf8.encode(tempBarcode);
    Uint8List bytes = Uint8List.fromList(list);

    if (_isConnected) {
      _connection?.output.add(bytes);
    }

    Uint8List readMarkerRAM() {
      Uint8List RAM_MEMORY = Uint8List(256);
      return RAM_MEMORY;
    }

    Uint8List readFALCK_MEM() {
      Uint8List FLACK_MEM = Uint8List(0x10000);
      return FLACK_MEM;
    }

    Uint8List readFALASH_MEM() {
      Uint8List FLASH_MEM = Uint8List(0x20000);
      return FLASH_MEM;
    }
     */
}
