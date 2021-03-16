package com.dmitrysukhov.collectionsspeedtest.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dmitrysukhov.collectionsspeedtest.R;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements MainContract.ViewContract {

    private MainContract.PresenterContract mainPresenter;
    private TextView textViewArrayListAddMid;
    private TextView textViewLinkedListAddMid;
    private TextView textViewCopyOnWriteArrayListAddMid;
    private TextView textViewArrayListRemoveMid;
    private TextView textViewLinkedListRemoveMid;
    private TextView textViewCopyOnWriteArrayListRemoveMid;
    private TextView textViewArrayListSearch;
    private TextView textViewLinkedListSearch;
    private TextView textViewCopyOnWriteArrayListSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this);
        textViewArrayListAddMid = findViewById(R.id.text_view_main_array_list_add_mid);
        textViewLinkedListAddMid = findViewById(R.id.text_view_main_linked_list_add_mid);
        textViewCopyOnWriteArrayListAddMid = findViewById(R.id.text_view_main_copy_on_write_array_list_add_mid);
        textViewArrayListRemoveMid = findViewById(R.id.text_view_main_array_list_remove_mid);
        textViewLinkedListRemoveMid = findViewById(R.id.text_view_main_linked_list_remove_mid);
        textViewCopyOnWriteArrayListRemoveMid = findViewById(R.id.text_view_main_copy_on_write_array_list_remove_mid);
        textViewArrayListSearch = findViewById(R.id.text_view_main_array_list_search);
        textViewLinkedListSearch = findViewById(R.id.text_view_main_linked_list_search);
        textViewCopyOnWriteArrayListSearch = findViewById(R.id.text_view_main_copy_on_write_array_list_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_button_calculate) {
            mainPresenter.onButtonWasClicked();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void showTimeInMillis(byte operationTag, int resultInMillis) {
        Log.i(MainPresenter.TAG,"operationTag is "+operationTag+" and resultInMillis is "+resultInMillis);
        switch (operationTag) {
            case (MainPresenter.ADD_MID_ARRAY_LIST_TAG): {
                runOnUiThread(() -> textViewArrayListAddMid.setText(String.valueOf(resultInMillis)));
                break;
            }
            case (MainPresenter.ADD_MID_LINKED_LIST_TAG): {
                runOnUiThread(() -> textViewLinkedListAddMid.setText(String.valueOf(resultInMillis)));
                break;
            }
            case (MainPresenter.ADD_MID_COPY_ON_WRITE_ARRAY_LIST_TAG): {
                runOnUiThread(() -> textViewCopyOnWriteArrayListAddMid.setText(String.valueOf(resultInMillis)));
                break;
            }
            case (MainPresenter.REMOVE_MID_ARRAY_LIST_TAG): {
                runOnUiThread(() -> textViewArrayListRemoveMid.setText(String.valueOf(resultInMillis)));
                break;
            }
            case (MainPresenter.REMOVE_MID_LINKED_LIST_TAG): {
                runOnUiThread(() -> textViewLinkedListRemoveMid.setText(String.valueOf(resultInMillis)));
                break;
            }
            case (MainPresenter.REMOVE_MID_COPY_ON_WRITE_ARRAY_LIST_TAG): {
                runOnUiThread(() -> textViewCopyOnWriteArrayListRemoveMid.setText(String.valueOf(resultInMillis)));
                break;
            }

            case (MainPresenter.SEARCH_ARRAY_LIST_TAG): {
                runOnUiThread(() -> textViewArrayListSearch.setText(String.valueOf(resultInMillis)));
                break;
            }
            case (MainPresenter.SEARCH_LINKED_LIST_TAG): {
                runOnUiThread(() -> textViewLinkedListSearch.setText(String.valueOf(resultInMillis)));
                break;
            }
            case (MainPresenter.SEARCH_COPY_ON_WRITE_ARRAY_LIST_TAG): {
                runOnUiThread(() -> textViewCopyOnWriteArrayListSearch.setText(String.valueOf(resultInMillis)));
                break;
            }
        }
    }
}