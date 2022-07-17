package de.eyesonly5x5.brainstorm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Globals  extends ListActivity {
    @SuppressLint("StaticFieldLeak")
    private static final Globals instance = new Globals();
    private static MyDisplay metrics;

    // Beispiele für Daten...
    private byte[][] Tast = new byte[100][9];
    private int maxFelder = 0;
    private final boolean[] Flg = new boolean[100];
    private final int[][] Colors = new int[11][5];
    List<Integer> Color = new ArrayList<>();
    int[] BUTTON_IDS;
    private TextView Ausgabe;
    List<Button> buttons = new ArrayList<>();
    List<ImageView> imgButtons = new ArrayList<>();
    List<TextView> TextV = new ArrayList<>();
    private int Zuege = 0;
    private int Anzahl = 0;
    private int Activity=-1;
    private Context myContext;
    private Resources myRes;
    private boolean gewonnen = true;
    private SoundBib SoundW;
    private SoundBib SoundF;
    private int Buty = 90;
    private int[][] NonoG;
    List<String> piTron = new ArrayList<>();
    private GridLayout myBlock;
    private boolean geloest;
    private boolean dashEnde;
    private int istGedrueckt = 0;
    private int MemoryPic = 0;
    private ArrayList<Integer> MemoryPics = new ArrayList<>();
    private String woMischen = "Zauber";
    private boolean geMischt = false;
    List<RelativeLayout>viewButIDs = new ArrayList<>();

    // private Globals() { }

    public static Globals getInstance() {
        return instance;
    }

    public static void setMetrics( Resources hier ){
        metrics = new MyDisplay( hier );
    }
    public static MyDisplay getMetrics( ){
        return( metrics );
    }

    public void setAusgabe(TextView wert) {
        Ausgabe = wert;
    }

    public SoundBib getSoundBib(boolean s) {
        return( (s)?SoundW:SoundF );
    }
    public void setSoundBib(boolean s, SoundBib wert) {
        if( s ) SoundW = wert;
        else SoundF = wert;
    }

    public boolean getGewonnen() {
        return gewonnen;
    }

    public int getActivity(){
        return Activity;
    }
    public void setActivity(int act){
        Activity = act;
    }
    public void setMyContext( MainActivity c) {
        myContext = c;
        myRes = myContext.getResources();
    }
    public void addImgButton(ImageView button) {
        imgButtons.add(button);
    }

    public int getAnzahl(){ return Anzahl; }

    public void setNonoG( int vec, int pos, int wert ){ NonoG[vec][pos] = wert; }
    public int getNonoG( int vec, int pos ){ return( NonoG[vec][pos] ); }

    public RelativeLayout getViewButID( int pos ){ return( viewButIDs.get(pos) ); }
    public void addViewButIDs( RelativeLayout x ){
        viewButIDs.add( x );
    }

    public void setWoMischen( String wert ){
        woMischen = wert;
        metrics.setWoMischen(wert);
    }
    public String getWoMischen( ){ return( woMischen ); }
    public boolean getGeMischt(){ return( geMischt ); }
    public void setGeMischt( boolean wert ) { geMischt = wert; }

    public void setPiTron( int pos, String wert ){ piTron.set( pos, wert ); }
    public String getPiTron( int pos ){ return( piTron.get( pos ) ); }

    public List<Integer> getColor(){ return( Color ); }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void Mischer() {
        int id, id1, id2, tmp;
        Random r = new Random();
        Zuege = 0;
        gewonnen = false;
        Ausgabe.setText("Züge: " + Zuege);
        NonoG = new int[2][Anzahl*Anzahl];
        geMischt = true;
        piTron = null;
        piTron = new ArrayList<>();
        for( int i = 0; i < MemoryPics.size(); i++ ){
            NonoG[0][i] = MemoryPics.get(i);
            NonoG[1][i] = 0;
            ImageView button = imgButtons.get(i);
            button.setBackground( myRes.getDrawable( NonoG[0][i] ) );
        }
        for( int i = 0; i < (Anzahl*Anzahl); i++ ){
            viewButIDs.get(i).setBackgroundColor(myRes.getColor(R.color.gray2));
            piTron.add( ( (i/4)+1 )+"_"+( (i%4)+1 ) );
        }
    }

    public void DasIstEsPIT(){
        gewonnen = true;
        for( int j=0; j<Anzahl; j++ ) {
            for (int i = 0; i < Anzahl; i++) {
                viewButIDs.get(j + (i * Anzahl)).setBackgroundColor(myRes.getColor(R.color.gray2));
            }
        }
        for( int j=0; j<Anzahl; j++ ) {
            for (int i=0; i<Anzahl; i++) {
                String tmp[] = { piTron.get( i+(j*Anzahl) ), "" };
                String[] x = tmp[0].split("_");
                tmp[0] = "";
                tmp[1] = "";
                for (int ii=0; ii<Anzahl; ii++) {
                    if (i == ii) continue;
                    tmp[0] += ","+piTron.get( ii+(j*Anzahl) ).split("_")[0];
                    tmp[1] += ","+piTron.get( ii+(j*Anzahl) ).split("_")[1];
                }
                if( tmp[0].contains( x[0] ) || tmp[1].contains( x[1] ) ){
                    gewonnen = false;
                    viewButIDs.get(i+(j*Anzahl)).setBackgroundColor(myRes.getColor(R.color.color2));
                }
            }
        }

        for( int j=0; j<Anzahl; j++ ) {
            for (int i=0; i<Anzahl; i++) {
                String tmp[] = { piTron.get( j+(i*Anzahl) ), "" };
                String[] x = tmp[0].split("_");
                tmp[0] = "";
                tmp[1] = "";
                for (int ii=0; ii<Anzahl; ii++) {
                    if (i == ii) continue;
                    tmp[0] += ","+piTron.get( j+(ii*Anzahl) ).split("_")[0];
                    tmp[1] += ","+piTron.get( j+(ii*Anzahl) ).split("_")[1];
                }
                if( tmp[0].contains( x[0] ) || tmp[1].contains( x[1] ) ){
                    gewonnen = false;
                    viewButIDs.get(j+(i*Anzahl)).setBackgroundColor(myRes.getColor(R.color.color2));
                }
            }
        }
    }

    @SuppressLint("WrongConstant")
    private int anzahlButtons(){
        int AnzBut = (((metrics.getMaxPixels()) / (int)(this.Buty*metrics.getFaktor()))-3);
        if( AnzBut > 11 ) AnzBut = 11;
        AnzBut *= Anzahl;
        return( AnzBut );
    }

    public int[] getButtonIDs() {
        int wer = getWerWoWas();
        int Buttys = (wer==0)?9:(wer==1)?16:(wer==2)?25:(wer==3)?anzahlButtons():(wer==4)?100:(wer>=5)?Anzahl*Anzahl:0;
        int[] ret = new int[Buttys];

        if( wer < 3 ){
            for (int i = 0; i < ret.length; i++) {
                ret[i] = myRes.getIdentifier("b"+(i+1), "id", myContext.getPackageName());
            }
        } else {
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (i + 1);
            }
            if( wer == 5 ){
                NonoG = new int[2][Anzahl*Anzahl];
            }
        }
        BUTTON_IDS = ret;
        maxFelder = BUTTON_IDS.length;
        return (BUTTON_IDS);
    }

    @SuppressLint("NonConstantResourceId")
    private int getWerWoWas(){
        int ret = -1;
        switch( Activity ){
            case R.layout.activity_pit:
                ret = 1;
                break;
        }
        return( ret );
    }

    public void setGameData( String txt ) {
        Random r = new Random();
        Zuege = 0;
        gewonnen = true;
        buttons = null;
        buttons = new ArrayList<>();
        TextV = null;
        TextV = new ArrayList<>();
        imgButtons = null;
        imgButtons = new ArrayList<>();
        viewButIDs = null;
        viewButIDs = new ArrayList<>();
        istGedrueckt = 0;
        dashEnde = false;
        MemoryPics = new ArrayList<>();
        Anzahl = 4;
        MemoryPic = r.nextInt(2 );
        String dat = (MemoryPic == 0) ? "eye" : "face";
        for (int i = 1; i <= Anzahl; i++) {
            for (int j = 1; j <= Anzahl; j++) {
                MemoryPics.add(myRes.getIdentifier(dat +"_"+ i +"_"+ j, "drawable", "de.eyesonly5x5.brainstorm"));
            }
        }
        maxFelder = Anzahl*Anzahl;
        NonoG = new int[2][Anzahl*Anzahl];
    }

     static class SoundBib extends AppCompatActivity {
        private SoundPool soundPool;
        List<Integer> sound = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // setContentView(R.layout.activity_main);
        }

        public SoundBib(boolean s, Context context) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();

            if( s ) {
                sound.add(soundPool.load(context, R.raw.won1, 1));
                sound.add(soundPool.load(context, R.raw.won2, 1));
                sound.add(soundPool.load(context, R.raw.won3, 1));
                sound.add(soundPool.load(context, R.raw.won4, 1));
                sound.add(soundPool.load(context, R.raw.won5, 1));
            } else {
                sound.add(soundPool.load(context, R.raw.fail1, 1));
                sound.add(soundPool.load(context, R.raw.fail2, 1));
                sound.add(soundPool.load(context, R.raw.fail3, 1));
                sound.add(soundPool.load(context, R.raw.fail4, 1));
            }
        }

        // When users click on the button "Gun"
        public void playSound() {
            soundPool.autoPause();
            Random r = new Random();
            int id = r.nextInt(sound.size());
            soundPool.play(sound.get(id), 0.25F, 0.25F, 0, 0, 1);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            soundPool.release();
            soundPool = null;
        }
    }

    private String StrZ( int wert, int len ){
        String ret = ""+ wert;
        while( ret.length() < len ) ret = "0"+ ret;
        return( ret );
    }

    public void Anleitung( Context dasDA, int Wat ) {
        Dialog customDialog = new Dialog( dasDA );
        customDialog.setContentView(R.layout.anleitung);
        TextView oView = customDialog.findViewById( R.id.Anleitung );
        String str = myRes.getString( Wat, myRes.getString( R.string.Wunschliste ) );
        oView.setText( str );
        Button bView = customDialog.findViewById( R.id.Warte );
        bView.setOnClickListener(view -> customDialog.dismiss());
        customDialog.setCancelable(false);
        customDialog.show();
    }
}