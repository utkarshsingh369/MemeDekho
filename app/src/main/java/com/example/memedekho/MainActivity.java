package com.example.memedekho;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static String subReddit;
    public ImageView imageView;
    public TextView textView;
    ProgressBar progressBar;
    String tempUrl="";
    BitmapDrawable bd;
    Bitmap bmp;
    static String title="";
    public TextView textViewTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent1=getIntent();
        title=intent1.getStringExtra(title);
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        imageView=(ImageView) findViewById(R.id.imageView3);
        textView=(TextView) findViewById(R.id.textView);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        textViewTitle=(TextView)findViewById(R.id.textViewTitle);
        textViewTitle.setText(title);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        Intent intent2=getIntent();
        subReddit=intent2.getStringExtra(subReddit);
        showMeme();
    }
    public void showMeme(){
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://meme-api.herokuapp.com/gimme/"+subReddit;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            tempUrl= response.getString("url");
                            Glide.with(MainActivity.this).load(tempUrl).addListener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "A Problem occurred! Try Again!", Toast.LENGTH_SHORT).show();
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(imageView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Net chalu kar");
                    }
                });
        queue.add(jsonObjectRequest);
    }


    public void onNext(View view)
    {
        showMeme();
    }


    public void onShareLink(View view){
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.setType("text/plane");
        intent.putExtra(Intent.EXTRA_TEXT,"Check this out from MemeDekho::\n"+tempUrl);
        Intent chooser=Intent.createChooser(intent,"Share Using?");
        startActivity(chooser);
    }


    public void shareImage(){
        //nothing to add extra
        FileOutputStream outputStream;
        Intent intent=new Intent(Intent.ACTION_SEND);
        StrictMode.VmPolicy.Builder b=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(b.build());
        bd=(BitmapDrawable) imageView.getDrawable();
        bmp=bd.getBitmap();
        File file=new File(getExternalCacheDir()+"/MemeDekhoImg.png");
        try{
            outputStream=new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Intent chooser=Intent.createChooser(intent,"Share Using?");
        startActivity(chooser);
    }
    public void onShareMeme(View view){
        try{
            shareImage();
        }catch (Exception ex){
            Toast.makeText(this, "GIF share option is not available, wait for next update", Toast.LENGTH_SHORT).show();
        }
    }
    public void saveToGallery(View view) throws Exception{
        //first add in androidmanifest.xml
        // <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        //    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
        //and alsooo:
        //android:requestLegacyExternalStorage="true"
        BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = draw.getBitmap();

        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/DCIM/Screenshots");
        boolean isCreated=dir.mkdirs();
//        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        String fileName=System.currentTimeMillis()+".jpg";
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        Toast.makeText(this, "Meme Saved in /Storage/MemeDekho ", Toast.LENGTH_SHORT).show();
        try {
            assert outStream != null;
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUpdate(View view) {
        Intent intent=new Intent(this,UpdateLogs.class);
        startActivity(intent);
    }
}