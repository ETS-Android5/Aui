package br.com.aui.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.aui.R;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        //new UnCaughtException.Builder(this).build();

        int a = 10 / 0;
    }
}