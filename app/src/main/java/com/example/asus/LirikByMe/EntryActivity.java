package com.example.asus.LirikByMe;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class EntryActivity extends AppCompatActivity {
    final Context context = this;
    private SQLiteAdapter sqLiteAdapter;
    private EditText titleView, linkView, lyricView;
    private Button submitButton;
    private Spinner typeView, languageView;
    private String other;
    private CheckBox kpop, metal, pop, dangdut, rock, hiphop, rap, jazz;
    ArrayAdapter<CharSequence> adapterBahasa, adapterType;
    private String title, link, language, type, lyric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_entry);
        Bundle extras = getIntent().getExtras();
        if (!(extras == null)){
            AppCompatDelegate.setDefaultNightMode(extras.getInt("night_mode"));
            if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
                setTheme(R.style.darktheme);
            }else
                setTheme(R.style.AppTheme);
            restartApp();
        }
        sqLiteAdapter = new SQLiteAdapter(context);

        titleView = (EditText) findViewById(R.id.title);
        linkView = (EditText) findViewById(R.id.link);
        lyricView = (EditText) findViewById(R.id.lyric);
        kpop = (CheckBox) findViewById(R.id.checkBoxKPop);
        metal = (CheckBox) findViewById(R.id.checkBoxMetal);
        pop = (CheckBox) findViewById(R.id.checkBoxPop);
        dangdut = (CheckBox) findViewById(R.id.checkBoxDangdut);
        rock = (CheckBox) findViewById(R.id.checkBoxRock);
        hiphop = (CheckBox) findViewById(R.id.checkBoxHipHop);
        rap = (CheckBox) findViewById(R.id.checkBoxRap);
        jazz = (CheckBox) findViewById(R.id.checkBoxJazz);

//        Populate Type Spinner
        typeView = (Spinner) findViewById(R.id.spinnerType);
        adapterType = ArrayAdapter.createFromResource(context,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeView.setAdapter(adapterType);
//        Populate Bahasa Spinner
        languageView = (Spinner) findViewById(R.id.spinnerBahasa);
        adapterBahasa = ArrayAdapter.createFromResource(context,
                R.array.bahasa_array, android.R.layout.simple_spinner_item);
        adapterBahasa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageView.setAdapter(adapterBahasa);
        languageView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Other")){
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View promptsView = inflater.inflate(R.layout.input_prompts, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(promptsView);
                    final EditText input = (EditText) promptsView.findViewById(R.id.prompt_edit_text);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            other = input.getText().toString();
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton = (Button) findViewById(R.id.submitEntry);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteAdapter.openToWrite();
                title = titleView.getText().toString();
                link = linkView.getText().toString();
                type = typeView.getSelectedItem().toString();
                lyric = lyricView.getText().toString();
                if (!(languageView.getSelectedItem().equals("Other"))){
                    language = languageView.getSelectedItem().toString();
                } else {
                    language = other;
                }
                sqLiteAdapter.insertEntry(title, link, language, type, lyric);

                if (kpop.isChecked()){
                    sqLiteAdapter.insertGenre(title, kpop.getText().toString());
                }
                if (metal.isChecked()){
                    sqLiteAdapter.insertGenre(title, metal.getText().toString());
                }
                if (pop.isChecked()){
                    sqLiteAdapter.insertGenre(title, pop.getText().toString());
                }
                if (dangdut.isChecked()){
                    sqLiteAdapter.insertGenre(title, dangdut.getText().toString());
                }
                if (rock.isChecked()){
                    sqLiteAdapter.insertGenre(title, rock.getText().toString());
                }
                if (jazz.isChecked()){
                    sqLiteAdapter.insertGenre(title, jazz.getText().toString());
                }
                if (hiphop.isChecked()){
                    sqLiteAdapter.insertGenre(title, hiphop.getText().toString());
                }
                if (rap.isChecked()){
                    sqLiteAdapter.insertGenre(title, rap.getText().toString());
                }
                sqLiteAdapter.close();
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("link", link);
                intent.putExtra("language", language);
                intent.putExtra("type", type);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void restartApp(){
        Intent i = new Intent(getApplicationContext(), EntryActivity.class);
        startActivity(i);
        finish();
    }
}
