package com.dicoding.picodiploma.barvolume;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText mEdtLength;
    private TextInputEditText mEdtWidth;
    private TextInputEditText mEdtHeight;
    private MaterialButton mBtnCalculate;
    private MaterialCardView mCdvResults;
    private TextView mTxtResult;
    private static final String STATE_RESULT = "state_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdtLength = findViewById(R.id.length_edit_text);
        mEdtHeight = findViewById(R.id.height_edit_text);
        mEdtWidth = findViewById(R.id.width_edit_text);
        mBtnCalculate = findViewById(R.id.material_button);
        mCdvResults = findViewById(R.id.card_view);
        mTxtResult = findViewById(R.id.result);

        mBtnCalculate.setOnClickListener(this);

        setTitle(R.string.calculate_volume);

        if (savedInstanceState != null) {
            String result = savedInstanceState.getString(STATE_RESULT);
            mTxtResult.setText(result);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.material_button) {
            String inputLength = mEdtLength.getText().toString().trim();
            String inputHeight = mEdtHeight.getText().toString().trim();
            String inputWidth = mEdtWidth.getText().toString().trim();

            boolean isEmptyFields = false;
            boolean isInvalidDouble = false;

            if (TextUtils.isEmpty(inputLength)) {
                isEmptyFields = true;
                mEdtLength.setError("This field can't be empty.");
            }

            if (TextUtils.isEmpty(inputWidth)) {
                isEmptyFields = true;
                mEdtWidth.setError("This field can't be empty.");
            }

            if (TextUtils.isEmpty(inputHeight)) {
                isEmptyFields = true;
                mEdtHeight.setError("This field can't be empty.");
            }

            if (!isEmptyFields) {

                Double length = toDouble(inputLength);
                Double height = toDouble(inputHeight);
                Double width = toDouble(inputWidth);

                if (length == null) {
                    isInvalidDouble = true;
                    mEdtLength.setError("Value must be valid numbers.");
                }

                if (height == null) {
                    isInvalidDouble = true;
                    mEdtHeight.setError("Value must be valid numbers");
                }

                if (width == null) {
                    isInvalidDouble = true;
                    mEdtWidth.setError("Value must be valid numbers");
                }

                if (!isInvalidDouble) {
                    double volume = length * width * height;
                    mCdvResults.setVisibility(View.VISIBLE);
                    mTxtResult.setText(String.valueOf(volume));
                }

                hideKeyboard(this);


            }

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_RESULT, mTxtResult.getText().toString());
    }

    private Double toDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException error) {
            return null;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
