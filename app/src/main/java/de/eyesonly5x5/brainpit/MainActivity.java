package de.eyesonly5x5.brainpit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Globals daten = Globals.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        daten.setMyContext( this );
        daten.setMetrics(getResources());

        TextView AusG = findViewById(R.id.Kopf);
        AusG.setText(getString(R.string.title1));
        AusG.setTextSize( daten.getMetrics().pxToDp((int)(AusG.getTextSize()*daten.getMetrics().getFaktor())) );
        daten.setSoundBib(true,new Globals.SoundBib( true,this));
        daten.setSoundBib(false,new Globals.SoundBib( false,this));

        Button PIT = findViewById(R.id.PIT);
        PIT.setTextSize( daten.getMetrics().pxToDp((int)(PIT.getTextSize()*daten.getMetrics().getFaktor())) );

        PIT.setOnClickListener(view -> {
            PIT.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_pit);
            daten.setWoMischen( "PIT" );
            daten.setGameData( "PIT" );
            startActivity(new Intent(getApplicationContext(), PITActivity.class));
            PIT.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
    }
}