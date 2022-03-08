package br.com.aui.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.ProgressDialog;
import android.icu.util.TimeZone;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.aui.R;
import br.com.util.ActionBarCuston;
import br.com.util.BackgroundTask;
import br.com.util.Task;
import br.com.util.Util;
import br.com.util.listeners.OnListnerAlertSimCancelar;
import br.com.util.listeners.OnListnerOk;

public class UtilActivity extends AppCompatActivity {

    Button button16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util);

        button16 = findViewById(R.id.button16);
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //executInBackground();

                new ATask().execute();
            }
        });
    }

    public void abaixateclado(View view) {
        Util.abaixaTeclado(getBaseContext(), view);
    }

    public void toastLong(View view) {
        Util.toastLong(getBaseContext(), "Long Toast");
    }

    public void toastShort(View view) {
        Util.toastShort(getBaseContext(), "Short Toast");
    }

    public void fadeIn(View view) {
        Util.fadeIn(getBaseContext(), view);
    }

    public void alertOk(View view) {
        Util.alertOk(UtilActivity.this, "Message");
    }

    public void alertOkListener(View view) {
        Util.alertOk(UtilActivity.this, "Message", new OnListnerOk() {
            @Override
            public void ok() {

            }
        });
    }

    public void alertSimCancelarListener(View view) {
        Util.alertSimCancelar(UtilActivity.this, "Message", new OnListnerAlertSimCancelar() {
            @Override
            public void sim() {
                // aqui
            }

            @Override
            public void cancelar() {

            }
        });
    }

    public void setActionBar(View view) {
        ActionBarCuston actionBarCuston = ActionBarCuston.getInstance(this);
        actionBarCuston.setBarColor(R.color.colorPrimary);
        actionBarCuston.setTitleColor("#E60000");

        actionBarCuston.setBar("Teste", "Subtitle");
    }

    int cont;
    private ProgressDialog progressDialog;

    public class Async extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private void executInBackground() {

        cont = 0;

        BackgroundTask.with(this) // Activity|FragmentActivity(v4)|Fragment|Fragment(v4)
                .doInBackground(new BackgroundTask.TaskDescription() {
                    @Override
                    public Object doInBackground() {
                        // Do what you want to do on background thread.
                        // If you want to post something to MainThread,
                        // just call SugarTask.post(YOUR_MESSAGE).

                        // Return your finally result(Nullable).

                        do {
                            cont++;

                            Message message = Message.obtain();
                            message.obj = cont;

                            BackgroundTask.post(message);

                            /*if (cont == 10) {
                                cont = 10 / 0;
                            }*/

                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } while (cont < 5);

                        return "Finalizado com sucesso!";
                    }
                })
                .onPreExecute(new BackgroundTask.PreExecuteListener() {
                    @Override
                    public void onPreExecute() {
                        progressDialog = ProgressDialog.show(UtilActivity.this, "Aguarde", "Carregando os clientes...", false, false);
                    }
                })
                .onProgressUpdate(new BackgroundTask.MessageListener() {
                    @Override
                    public void onProgressUpdate(@NonNull Message message) {
                        // Receive message in MainThread which sent from WorkerThread,
                        // update your UI just in time.
                        button16.setText("Message " + message.obj);
                    }
                })
                .onPostExecute(new BackgroundTask.FinishListener() {
                    @Override
                    public void onPostExecute(@Nullable Object result) {
                        // If WorkerThread finish without Exception and lifecycle safety,
                        // deal with your WorkerThread result at here.
                        button16.setText(result.toString());

                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                })
                .onException(new BackgroundTask.BrokenListener() {
                    @Override
                    public void onException(@NonNull Exception e) {
                        // If WorkerThread finish with Exception and lifecycle safety,
                        // deal with Exception at here.
                        e.printStackTrace();
                        button16.setText(e.getMessage());

                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                })
                .execute();
    }

    public class ATask extends Task<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cont = 0;
            progressDialog = ProgressDialog.show(UtilActivity.this, "Aguarde", "Carregando os clientes...", false, false);
        }

        @Override
        protected String doInBackground(Void... voids) {

            do {
                cont++;

                publishProgress(cont);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } while (cont < 5);

            return "Finalizado com sucesso!";
        }

        @Override
        protected void onPostExecute(String unused) {
            super.onPostExecute(unused);

            button16.setText(unused);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            button16.setText("Message " + values[0]);
        }
    }
}