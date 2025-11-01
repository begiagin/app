package com.halata.blueapp.data.models;

import com.google.gson.annotations.SerializedName;

public class BasicSetting {
    public BasicSetting(){}

    @SerializedName("bluetoothMACAddress")
    private String bluetoothMACAddress;
    @SerializedName("rightCounter")
    private int rightCounter;
    @SerializedName("leftCounter")
    private int leftCounter;
    @SerializedName("delayBeforeMark")
    private float delayBeforeMark;
    @SerializedName("delayAfterMark")
    private float delayAfterMark;
    @SerializedName("nextLineX")
    private int nextLineX;
    @SerializedName("nextLineY")
    private int nextLineY;
    @SerializedName("markSpeed")
    private int markSpeed;
    @SerializedName("rotationAngle")
    private int rotationAngle;
    @SerializedName("xCofficeintAxis")
    private float xCoffiecintAxis;
    @SerializedName("yConfficeintAxis")
    private float yConfficeintAxis;
    @SerializedName("startMarkX")
    private int startMarkX;
    @SerializedName("startMarkY")
    private int startMarkY;
    @SerializedName("pointsDistances")
    private float pointsDistances;
    @SerializedName("hitPower")
    private int hitPower;
    @SerializedName("penFrequency")
    private int penFrequency;
    @SerializedName("gotoHardwareZero")
    private boolean gotoHardwareZero;

    public float getxCoffiecintAxis() {
        return xCoffiecintAxis;
    }

    public void setxCoffiecintAxis(float xCoffiecintAxis) {
        this.xCoffiecintAxis = xCoffiecintAxis;
    }

    public boolean isSendExtraHeader() {
        return sendExtraHeader;
    }

    public void setSendExtraHeader(boolean sendExtraHeader) {
        this.sendExtraHeader = sendExtraHeader;
    }

    @SerializedName("sendExtraHeader")
    private boolean sendExtraHeader;

    public String getBluetoothMACAddress() {
        return bluetoothMACAddress;
    }
    public int getStartMarkX() {
        return startMarkX;
    }

    public void setStartMarkX(int startMarkX) {
        this.startMarkX = startMarkX;
    }

    public int getStartMarkY() {
        return startMarkY;
    }

    public void setStartMarkY(int startMarkY) {
        this.startMarkY = startMarkY;
    }
    public void setBluetoothMACAddress(String bluetoothMACAddress) {
        this.bluetoothMACAddress = bluetoothMACAddress;
    }

    public int getRightCounter() {
        return rightCounter;
    }

    public void setRightCounter(int rightCounter) {
        this.rightCounter = rightCounter;
    }

    public int getLeftCounter() {
        return leftCounter;
    }

    public void setLeftCounter(int leftCounter) {
        this.leftCounter = leftCounter;
    }

    public float getDelayBeforeMark() {
        return delayBeforeMark;
    }

    public void setDelayBeforeMark(float delayBeforeMark) {
        this.delayBeforeMark = delayBeforeMark;
    }

    public float getDelayAfterMark() {
        return delayAfterMark;
    }

    public void setDelayAfterMark(float delayAfterMark) {
        this.delayAfterMark = delayAfterMark;
    }

    public int getNextLineX() {
        return nextLineX;
    }

    public void setNextLineX(int nextLineX) {
        this.nextLineX = nextLineX;
    }

    public int getNextLineY() {
        return nextLineY;
    }

    public void setNextLineY(int nextLineY) {
        this.nextLineY = nextLineY;
    }

    public int getMarkSpeed() {
        return markSpeed;
    }

    public void setMarkSpeed(int markSpeed) {
        this.markSpeed = markSpeed;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public float getxCofficeintAxis() {
        return xCoffiecintAxis;
    }

    public void setxCofficeintAxis(float xCofficeintAxis) {
        this.xCoffiecintAxis = xCofficeintAxis;
    }

    public float getyConfficeintAxis() {
        return yConfficeintAxis;
    }

    public void setyConfficeintAxis(float yConfficeintAxis) {
        this.yConfficeintAxis = yConfficeintAxis;
    }

    public float getPointsDistances() {
        return pointsDistances;
    }

    public void setPointsDistances(float pointsDistances) {
        this.pointsDistances = pointsDistances;
    }

    public int getHitPower() {
        return hitPower;
    }

    public void setHitPower(int hitPower) {
        this.hitPower = hitPower;
    }

    public int getPenFrequency() {
        return penFrequency;
    }

    public void setPenFrequency(int penFrequency) {
        this.penFrequency = penFrequency;
    }

    public boolean isGotoHardwareZero() {
        return gotoHardwareZero;
    }

    public void setGotoHardwareZero(boolean gotoHardwareZero) {
        this.gotoHardwareZero = gotoHardwareZero;
    }

    @Override
    public String toString() {
        return "BasicSetting{" +
                "bluetoothMACAddress='" + bluetoothMACAddress + '\'' +
                ", rightCounter=" + rightCounter +
                ", leftCounter=" + leftCounter +
                ", delayBeforeMark=" + delayBeforeMark +
                ", delayAfterMark=" + delayAfterMark +
                ", nextLineX=" + nextLineX +
                ", nextLineY=" + nextLineY +
                ", markSpeed=" + markSpeed +
                ", rotationAngle=" + rotationAngle +
                ", xCoffiecintAxis=" + xCoffiecintAxis +
                ", yConfficeintAxis=" + yConfficeintAxis +
                ", startMarkX=" + startMarkX +
                ", startMarkY=" + startMarkY +
                ", pointsDistances=" + pointsDistances +
                ", hitPower=" + hitPower +
                ", penFrequency=" + penFrequency +
                ", gotoHardwareZero=" + gotoHardwareZero +
                ", sendExtraHeader=" + sendExtraHeader +
                '}';
    }
}
