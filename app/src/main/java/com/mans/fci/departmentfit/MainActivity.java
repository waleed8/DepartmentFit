package com.mans.fci.departmentfit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import at.grabner.circleprogress.CircleProgressView;

public class MainActivity extends AppCompatActivity {

    RecyclerView m_recycler;
    DepartmentCalculator m_departmentCalculator ;
    CircleProgressView[] m_DepartmentCircularBars = new CircleProgressView[3];
    ImageButton m_helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_departmentCalculator = new DepartmentCalculator(this);
        //The recycler view
        m_recycler= (RecyclerView)findViewById(R.id.rv);

       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       // GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        m_recycler.setLayoutManager(layoutManager);

        CoursesAdapter adapter = new CoursesAdapter(this, m_departmentCalculator);
        m_recycler.setAdapter(adapter);

        m_DepartmentCircularBars[0] = (CircleProgressView) findViewById(R.id.circularBar_cs);
        m_DepartmentCircularBars[1] = (CircleProgressView) findViewById(R.id.circularBar_it);
        m_DepartmentCircularBars[2] = (CircleProgressView) findViewById(R.id.circularBar_is);

        //_______________________
        m_helpButton = (ImageButton) findViewById (R.id.imgBtn_Help);
        m_helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHelpDialog();
            }
        });
        //-------------------show help on load
       // ShowHelpDialog();
    }



    private void ShowHelpDialog() {
        //based on https://www.mkyong.com/android/android-custom-dialog-example/
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.start_dialog_content, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Help Instructions");

        WebView webView = (WebView)dialogView.findViewById(R.id.webView);
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
 //       TextView helpText = (TextView) findViewById(R.id.txtHelpMessage);

        webView.loadUrl("file:///android_asset/welcome.html");
//        try {
//            AssetManager assetManager = getApplicationContext().getAssets();
//            InputStream stream = assetManager.open("welcome.html");
//            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
//            StringBuilder total = new StringBuilder();
//            String line;
//            while ((line = r.readLine()) != null) {
//                total.append(line).append("\n");
//            }
//            helpText.setText( total.toString());
//        } catch (Exception xxx) {
//            Log.e("text error", "Load assets/page.html", xxx);
//        }

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });


        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void UpdateCirclarProgressBars() {
        DepartmentFitness[] fitnesses = m_departmentCalculator.m_departments;
        for (int i= 0;i<fitnesses.length && i< m_DepartmentCircularBars.length; i++)
        {
            m_DepartmentCircularBars[i].setValue(fitnesses[i].m_departmentFitness);
        }
    }

    //http://stackoverflow.com/questions/9791714/take-a-screenshot-of-a-whole-view
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }
}
