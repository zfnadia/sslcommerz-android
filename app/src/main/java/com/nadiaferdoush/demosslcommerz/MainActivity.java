package com.nadiaferdoush.demosslcommerz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sslwireless.sslcommerzlibrary.model.initializer.AdditionalInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.CustomerInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.TransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.CurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.TransactionResponseListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements TransactionResponseListener {

    EditText amountET;
    TextView transactionMessage;
    String TAG = "Payment info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountET = findViewById(R.id.amount);
        transactionMessage = findViewById(R.id.transaction_message);

        ((Button) findViewById(R.id.pay_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double amount = Double.parseDouble(amountET.getText().toString());
                    if (amount > 0.0) {
                        makePayment(amount, view);
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter valid amount",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void makePayment(double amount, View view) {
        int randomPrefix = new Random().nextInt();

        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization(
                "bdjob5f0ad29f35834", "bdjob5f0ad29f35834@ssl",
                amount, CurrencyType.BDT, "transactionID" + randomPrefix,
                "Payment", SdkType.TESTBOX);

        final CustomerInfoInitializer customerInfoInitializer = new CustomerInfoInitializer(
                "Nadia", "ferdoushnadia@gmail.com",
                "Sky View Arena", "Dhaka",
                "1200", "Bangladesh", "123456789");

        final AdditionalInitializer additionalInitializer = new AdditionalInitializer();
        additionalInitializer.setValueA("User id: 1234");

        IntegrateSSLCommerz
                .getInstance(view.getContext())
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addCustomerInfoInitializer(customerInfoInitializer)
                .addAdditionalInitializer(additionalInitializer)
                .buildApiCall(this);
    }

    @Override
    public void transactionSuccess(TransactionInfoModel transactionInfoModel) {
        // Get risk-level for this transaction. The return value will be 0 or 1.
        // If 0 then the transaction is safe . If 1 then the transaction is risky.
        if (transactionInfoModel.getRiskLevel().equals("0")) {
            Log.d(TAG, "Transaction Successfully completed");
            Log.d(TAG, transactionInfoModel.getTranId());
            Log.d(TAG, transactionInfoModel.getBankTranId());
            Log.d(TAG, transactionInfoModel.getAmount());
            Log.d(TAG, transactionInfoModel.getTranDate());
        } else
            Log.d(TAG, "Risk message: " + transactionInfoModel.getRiskTitle());
        transactionMessage.setText("Transaction status: successful (" + transactionInfoModel.getRiskTitle() + ")");
        amountET.setText(null);
    }

    @Override
    public void transactionFail(String s) {
        Log.e(TAG, "Transaction Failed");
        transactionMessage.setText("Transaction status: " + s);
        amountET.setText(null);
        Toast.makeText(this, "Transaction status:" + s, Toast.LENGTH_SHORT).show();
        // go back to main screen on failure
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void merchantValidationError(String s) {
        Log.e(TAG, "Transaction Failed: " + s);
        transactionMessage.setText("Transaction status: " + s);
        amountET.setText(null);
        Toast.makeText(this, "Transaction status:" + s, Toast.LENGTH_SHORT).show();
    }

}
