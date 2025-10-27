package com.halata.blueapp.utils;

import android.widget.EditText;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

public class InputValidator {


    @NonNull
    @Contract("_, _, _, _ -> new")
    public static ValidationResult validateRange(@NonNull EditText editText, float min, float max, String errorMessage) {
        String text = editText.getText().toString().trim();

        if (text.isEmpty()) {
            return new ValidationResult(false, errorMessage);
        }

        try {
            float value = Float.parseFloat(text);
            if (value >= min && value <= max) {
                return new ValidationResult(true, null);
            } else {
                return new ValidationResult(false, errorMessage);
            }
        } catch (NumberFormatException e) {
            return new ValidationResult(false, errorMessage);
        }
    }


    @NonNull
    public static ValidationResult validateAll(@NonNull EditText[] editTexts, @NonNull float[] mins, float[] maxs, String[] errorMessages) {
        if (editTexts.length != mins.length || editTexts.length != maxs.length || editTexts.length != errorMessages.length) {
            throw new IllegalArgumentException("طول آرایه‌ها باید یکسان باشد!");
        }

        for (int i = 0; i < editTexts.length; i++) {
            ValidationResult result = validateRange(editTexts[i], mins[i], maxs[i], errorMessages[i]);
            if (!result.isValid()) {
                return result;
            }
        }
        return new ValidationResult(true, null);
    }
}
