package aires.com.fitcook;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import aires.com.fitcook.util.SyncUtils;

public class SplashActivity extends AppCompatActivity {

    public static String FIRST_ACCESS="FIRST_ACCESS";

    ProgressDialog pd;

    SyncUtils.CallBack callBack=new SyncUtils.CallBack() {

        @Override
        public void onStart() {

            pd =ProgressDialog.show(SplashActivity.this,
                    getResources().getString(R.string.first_access_title),
                    getResources().getString(R.string.first_access_msg),
                    true);

        }

        @Override
        public void onFinished(boolean error) {

            pd.dismiss();

            if(error){

                showErrorDialog();

            }else{
                FitCookApp.putSharedPreferencesValue(getApplicationContext(), FIRST_ACCESS, false);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(FitCookApp.getSharedPreferencesValue(this, FIRST_ACCESS, true)){

            firstAccess();

        }else{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }
            }, 1500);
        }
    }

    private void firstAccess() {

        SyncUtils.CreateSyncAccount(this);

        SyncUtils.TriggerRefresh(this,callBack);

    }

    private void showErrorDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.dialog_first_access_message).setTitle(R.string.dialog_first_access_title);

        builder.setPositiveButton(R.string.first_access_try_again, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                SyncUtils.TriggerRefresh(getApplicationContext(), callBack);
            }
        });


       builder.create().show();

    }

}
