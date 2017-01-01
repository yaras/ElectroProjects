package pl.yaras.android.raspberrypitest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.http.Field;
import retrofit2.http.Query;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((SeekBar)this.findViewById(R.id.seek)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                send(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void send(final int n) {
        final String ip = ((EditText)this.findViewById(R.id.ip)).getText().toString();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void[] params) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://" + ip)
                        .build();

                RpiService service = retrofit.create(RpiService.class);

                try {
                    service.send(n).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    private interface RpiService {
        @GET("x")
        Call<Void> send(@Query("n") int n);
    }
}
