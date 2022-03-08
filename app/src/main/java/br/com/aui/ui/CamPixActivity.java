package br.com.aui.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.aui.R;
import br.com.campix.Options;
import br.com.campix.Pix;
import br.com.campix.photoView.PhotoView;
import br.com.texto.storagesd.StorageSD;


public class CamPixActivity extends AppCompatActivity {

    private Options options;
    private int requestCodePicker = 100;
    private PhotoView photoView;
    private RequestManager glide;

    private static final String OPTIONS = "options";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_pix);


        photoView = findViewById(R.id.photoView);

        options = Options.init()
                .setRequestCode(requestCodePicker)
                .setFrontfacing(false)
                .setPath("pix/photo");

        Pix.start(capituraImageLaunch,this, options);
        //Pix.start(this, options);

    }


    ActivityResultLauncher<Intent> capituraImageLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

        try {

            Intent intent = result.getData();

            if (intent != null) {

                if (result.getResultCode() == requestCodePicker) {

                    String path = intent.getStringExtra(Pix.IMAGE_PATH);
                    File file = (File) intent.getExtras().get(Pix.IMAGE_FILE);

                    glide = Glide.with(CamPixActivity.this);
                    glide.load(path).into(photoView);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            StorageSD.info("onActivityResult");
            StorageSD.insereLog(e.getStackTrace());
        }


    });

    private String newFileName() {
        return "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmSS", Locale.ENGLISH).format(new Date()) + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestCodePicker) {

            String path = data.getStringExtra(Pix.IMAGE_PATH);
            File file = (File) data.getExtras().get(Pix.IMAGE_FILE);

            glide = Glide.with(CamPixActivity.this);
            glide.load(path).into(photoView);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        /*if (requestCode == PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Pix.start(this, options);
            } else {
                Toast.makeText(this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
            }
        }*/
    }
}
