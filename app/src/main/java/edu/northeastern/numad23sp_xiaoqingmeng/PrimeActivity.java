package edu.northeastern.numad23sp_xiaoqingmeng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class PrimeActivity extends AppCompatActivity {

    private TextView currentNumberView;
    private TextView latestPrimeView;
    private FindPrimeRunnable findPrimeRunnable;
    Button findPrimeButton;
    Button terminateSearchButton;
    CheckBox pacifier;

    private volatile boolean isSearching = true;
    int currentNumber;
    int latestPrime;
    private Handler handler;

    private static final String TAG = "MainActivity";
/*    private static final String KEY_PRIME_FOUND = "prime_found";
    private static final String KEY_CURRENT_NUMBER = "current_number";
    private static final String KEY_SEARCH_RUNNING = "search_running";
    private static final String KEY_SWITCH_CHECKED = "switch_checked";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime);

        currentNumberView = findViewById(R.id.current_number);
        latestPrimeView = findViewById(R.id.latest_prime);
        findPrimeButton = findViewById(R.id.find_prime);
        terminateSearchButton = findViewById(R.id.terminate_search);
        pacifier = findViewById(R.id.pacifier_switch);
        boolean isChecked = pacifier.isChecked();
        handler = new Handler();

        if (savedInstanceState != null) {
            currentNumber = savedInstanceState.getInt("currentNumber");
            latestPrime = savedInstanceState.getInt("latestPrime");
            pacifier.setChecked(savedInstanceState.getBoolean("isPacifierOn"));
        }
        findPrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPrimeRunnable = new FindPrimeRunnable();
                new Thread(findPrimeRunnable).start();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt("currentNumber", currentNumber);
        outState.putInt("latestPrime", latestPrime);
        outState.putBoolean("isPacifierOn", pacifier.isChecked());
        super.onSaveInstanceState(outState);
    }

    public void onTerminateSearchClick(View view) {
        isSearching = false;
    }

    class FindPrimeRunnable implements Runnable {
        @Override
        public void run() {
            findPrime();
        }

        public void findPrime() {
            int currentNumber = 3;
            while (isSearching) {
                boolean isPrime = true;
                for (int i = 2; i < currentNumber; i++) {
                    if (currentNumber % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime) {
                    final int primeNumber = currentNumber;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            latestPrimeView.setText("Latest prime found: " + primeNumber);
                        }
                    });
                }
                final int currentNumberToCheck = currentNumber;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentNumberView.setText("Current number being checked: " + currentNumberToCheck);
                    }
                });
                currentNumber += 2;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (findPrimeRunnable != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Terminate search")
                    .setMessage("Are you sure you want to terminate the search?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isSearching = false;
                            PrimeActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }

}