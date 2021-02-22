package com.dmitrysukhov.secondhomeworkapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences oldPrefs;
    SharedPreferences newPrefs;
    Fragment hostFragment;
    Fragment1 fragment1;
    Fragment2 fragment2;
    String login;
    Boolean showLogin;
    Boolean nightMode;
    String whoWillSeeNumber;
    String messageForAlert;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                //не получилось чего-то через интент, решил сделать следующим образом
                    showChangedPreferences();
                }
            });



    public interface AboutFragmentCallback {
        void passTextToToast();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hostFragment = new HostFragment();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_main_forFragments, hostFragment, "host_fragment")
                .commit();
    }

    @Override
    protected void onResume() {
        oldPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        login = oldPrefs.getString("login", "");
        showLogin = oldPrefs.getBoolean("show login", true);
        nightMode = oldPrefs.getBoolean("switch_night_mode", false);
        whoWillSeeNumber = oldPrefs.getString("who_will_see_number", "my_contacts");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.about): {
                new AboutFragment().show(getSupportFragmentManager(), AboutFragment.TAG);
                break;
            }
            case (R.id.settings): {
                mStartForResult.launch(new Intent(this, SettingsActivity.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hostFragment = null;
        fragment1 = null;
        fragment2 = null;
    }

    public void showFragment(View view) {
        switch (view.getId()) {
            case (R.id.button_main_frag1): {
                hostFragment.getChildFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout_host_container, fragment1).commit();
                break;
            }
            case (R.id.button_main_frag2): {
                hostFragment.getChildFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout_host_container, fragment2, "fragment2").commit();
                break;
            }
        }
    }

    public void sendTextToFragment(View view) {
        switch (view.getId()) {
            case (R.id.button_sendToFragment2): {
                EditText editText = findViewById(R.id.editText_fragment1);
                String ourTextF1 = editText.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("text_from_fragment1", ourTextF1);
                fragment2.setArguments(bundle);
                editText.setText("");
                hostFragment.getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_host_container, fragment2).commit();
                break;
            }
            case (R.id.button_sendToFragment1): {
                EditText editText = findViewById(R.id.editText_fragment2);
                String ourText = editText.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("text_from_fragment2", ourText);
                fragment1.setArguments(bundle);
                editText.setText("");
                hostFragment.getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_host_container, fragment1).commit();
                break;
            }
        }
    }

    private void showChangedPreferences() {
        //                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
//                        Intent data = result.getData();
        //тут не очень понял как это сделать, дата у меня была нулл
//                    }
        //пока сделал следующим образом
        messageForAlert = "Изменённые настройки:\n";
        newPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String newLogin = newPrefs.getString("login", "");
        boolean newShowLogin = newPrefs.getBoolean("show login", true);
        boolean newNightMode = newPrefs.getBoolean("switch_night_mode", false);
        String newWhoWillSeeNumber = newPrefs.getString("who_will_see_number", "my_contacts");

        if (newLogin != login)
            messageForAlert += "Ваш логин: " + newLogin + "\n";
        if (newShowLogin != showLogin)
            messageForAlert += "Отображать логин: " + newShowLogin + "\n";
        if (newNightMode != nightMode)
            messageForAlert += "Night mode: " + newNightMode + "\n";
        if (newWhoWillSeeNumber != whoWillSeeNumber)
            messageForAlert += "Кто видит мой номер телефона?: " + newWhoWillSeeNumber + "\n";
        if (messageForAlert == "Изменённые настройки:\n")
            return;

        new AlertDialog.Builder(MainActivity.this)
                .setMessage(messageForAlert)
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                })
                .create().show();
        oldPrefs = newPrefs;
        login = newLogin;
        showLogin = newShowLogin;
        nightMode = newNightMode;
        whoWillSeeNumber = newWhoWillSeeNumber;
    }
}
