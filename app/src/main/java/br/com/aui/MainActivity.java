package br.com.aui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;

import java.util.ArrayList;

import br.com.aui.ui.CalculadoraDialogActivity;
import br.com.aui.ui.CamPixActivity;
import br.com.aui.ui.ComponentesActivity;
import br.com.aui.ui.ComponentesDoisActivity;
import br.com.aui.ui.ComponentesTresActivity;
import br.com.aui.ui.EasyFontsActivity;
import br.com.aui.ui.ErrorActivity;
import br.com.aui.ui.HawkActivity;
import br.com.aui.ui.HttpAgentActivity;
import br.com.aui.ui.LerQrBarCodeActivity;
import br.com.aui.ui.ManipulaTextoActivity;
import br.com.aui.ui.SignatureActivity;
import br.com.aui.ui.SoapActionActivity;
import br.com.aui.ui.SwipLayoutActivity;
import br.com.aui.ui.UtilActivity;
import br.com.aui.ui.ZoomFrameImageViewActivity;
import br.com.error.UnCaughtException;
import br.com.permision.PermissionHandler;
import br.com.permision.Permissions;
import br.com.permision.Premisao;
import br.com.shortcut.IReceiveStringExtra;
import br.com.shortcut.Shortcut;
import br.com.shortcut.ShortcutUtils;
import br.com.util.ActionBarCuston;


public class MainActivity extends AppCompatActivity {

    private int REQUEST_CODE = 5463 & 0xffffff00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new UnCaughtException.Builder(this)
                .setMailSuport("concyline@hotmail.com", "concyinfo@gmail.com")
                .setTrackActivitiesEnabled(true)
                .setBackgroundModeEnabled(true)
                .build();

        checkExternalStorageManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            addShortCut();
        }

        ActionBarCuston actionBarCuston = ActionBarCuston.getInstance(this);
        actionBarCuston.setBarColor(R.color.colorPrimary);
        actionBarCuston.setTitleColor(R.color.teste);

        actionBarCuston.setBar("Teste", "Subtitle");
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void addShortCut() {
        ShortcutUtils shortcutUtils = new ShortcutUtils(this);

        Shortcut dynamicShortcut = new Shortcut.ShortcutBuilder()
                .setShortcutIcon(R.drawable.round_device_hub_white_48dp)
                .setShortcutId("dynamicShortcutId")
                .setShortcutLongLabel("ALL Devices")
                .setShortcutShortLabel("ALL Devices")
                .setIntentAction("dynamicShortcutIntentAction")
                .setIntentStringExtraKey("dynamicShortcutKey")
                .setIntentStringExtraValue("all")
                .build();


        shortcutUtils.addDynamicShortCut(dynamicShortcut, new IReceiveStringExtra() {
            @Override
            public void onReceiveStringExtra(String stringExtraKey, String stringExtraValue) {
                String intent = getIntent().getStringExtra(stringExtraKey);
                if (intent != null) {
                    if (intent.equals("all")) {
                        System.out.println("OKOKOKOKOKOKO");
                    }
                }
            }
        });
    }

    public void checkExternalStorageManager(){

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            validaPermisoes();
            return;
        }

        if (Environment.isExternalStorageManager()) {
            validaPermisoes();
            return;
        }

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Atenção")
                .setMessage("Dar permissão de acesso aos arquivos!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
                            startActivityForResult(intent, REQUEST_CODE);
                        } catch (Exception ex) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                            startActivityForResult(intent, REQUEST_CODE);
                        }

                    }
                })
                .setIcon(R.drawable.round_folder_open_24
                )
                .show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    validaPermisoes();
                } else {
                    checkExternalStorageManager();
                }
            }
        }
    }

    private void validaPermisoes() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION};

        Permissions.check(MainActivity.this, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {

                try {

                    findViewById(R.id.permision).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Premisao premisao = new Premisao();

                            premisao.validaPermisoes(getApplicationContext(), new String[]{Manifest.permission.CAMERA}, new Premisao.onOk() {
                                @Override
                                public void ok(boolean flag) {
                                    if (flag) {
                                        System.out.println("aqui");
                                    }
                                }
                            });

                        }
                    });

                   findViewById(R.id.componentesButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), ComponentesActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.calculadoraButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), CalculadoraDialogActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.campix).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), CamPixActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.button19).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), ErrorActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.halkButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), HawkActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.httpAgentButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), HttpAgentActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.leitorButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), LerQrBarCodeActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.componentesDoisButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), ComponentesDoisActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.componentesTresButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), ComponentesTresActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.abaixaTeclado).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), UtilActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.zoomFrameButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), ZoomFrameImageViewActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.textoButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), ManipulaTextoActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.SwipeLayoutButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), SwipLayoutActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.button17).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), EasyFontsActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.button18).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), SignatureActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.button20).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), SoapActionActivity.class);
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Atenção")
                        .setMessage("O aplicativo não tem as permições necessárias para prosseguir!")
                        .setPositiveButton("Pegar as permições?", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                validaPermisoes();


                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setIcon(R.drawable.ic_error_outline
                        )
                        .show();
            }
        });
    }

}
