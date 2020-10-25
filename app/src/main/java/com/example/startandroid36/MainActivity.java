package com.example.startandroid36;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    final String LOG_TAG = "myLogs";

    String name[] = {"Китай", "США", "Бразилия", "Россия", "Япония",
            "Германия", "Египет", "Италия", "Франция", "Канада"};
    int people[] = {1400, 311, 195, 142, 128, 82, 80, 60, 66, 35};
    String region[] = {"Азия", "Америка", "Америка", "Европа", "Азия",
            "Европа", "Африка", "Европа", "Европа", "Америка"};

    Button btnAll, btnFunc, btnPeople, btnSort, btnGroup, btnHaving;
    EditText etFunc, etPeople, etRegionPeople;
    TextView textView;
    RadioGroup rgSort;

  /*  DBHelper dbHelper;
    SQLiteDatabase db;*/
    private DBJob JobBD; //работаем с бд

    /**
     * Called when the activity is first created.
     */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAll = findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);

        btnFunc = findViewById(R.id.btnFunc);
        btnFunc.setOnClickListener(this);

        btnPeople = findViewById(R.id.btnPeople);
        btnPeople.setOnClickListener(this);

        btnSort = findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        btnGroup = findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);

        btnHaving = findViewById(R.id.btnHaving);
        btnHaving.setOnClickListener(this);

        etFunc = findViewById(R.id.etFunc);
        etPeople = findViewById(R.id.etPeople);
        etRegionPeople = findViewById(R.id.etRegionPeople);

        textView = findViewById(R.id.textViewOutput);

        rgSort = findViewById(R.id.rgSort);
        JobBD = new DBJob(getApplicationContext());
        textView.setText("");
        String antwortet = JobBD.getData().toString();
        if (antwortet.equalsIgnoreCase("[База пуста]")) {
            ContentValues cv = new ContentValues();
            for (int i = 0; i < 10; i++) {
                cv.put("name", name[i]);
                cv.put("people", people[i]);
                cv.put("region", region[i]);
                JobBD.Insert(cv);
            }
            Toast.makeText(this, "Подготавливаем БД", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "База готова к работе", Toast.LENGTH_SHORT).show();

    }

    public void onClick(View v) {
        textView.setText("");

        // данные с экрана
        String sFunc = etFunc.getText().toString();
        String sPeople = etPeople.getText().toString();
        String sRegionPeople = etRegionPeople.getText().toString();

        // переменные для query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;


        // определяем нажатую кнопку
        switch (v.getId()) {
            // Все записи
            case R.id.btnAll:
                textView.setText(JobBD.getData().toString());
                break;
            // Функция
            case R.id.btnFunc:
                columns = new String[]{sFunc};
               // JobBD.getQuery(columns, selection, selectionArgs, groupBy, having, orderBy); //либо так
                JobBD.getQuery(columns, null, null, null, null, null); //либо так
                break;
            // Население больше, чем
            case R.id.btnPeople:
                selection = "people > ?";
                selectionArgs = new String[]{sPeople};
                JobBD.getQuery(null, selection, selectionArgs, null, null, null); //либо так
                break;
            // Население по региону
            case R.id.btnGroup:
                columns = new String[]{"region", "sum(people) as people"};
                groupBy = "region";
                JobBD.getQuery(columns, null, null, groupBy, null, null);
                break;
            // Население по региону больше чем
            case R.id.btnHaving:

                columns = new String[]{"region", "sum(people) as people"};
                groupBy = "region";
                having = "sum(people) > " + sRegionPeople;
                JobBD.getQuery( columns, null, null, groupBy, having, null);
                break;
            // Сортировка
            case R.id.btnSort:
                // сортировка по
                switch (rgSort.getCheckedRadioButtonId()) {
                    // наименование
                    case R.id.rName:

                        orderBy = "name";
                        break;
                    // население
                    case R.id.rPeople:

                        orderBy = "people";
                        break;
                    // регион
                    case R.id.rRegion:

                        orderBy = "region";
                        break;
                }
                JobBD.getQuery( null, null, null, null, null, orderBy);
                break;
        }

    }


}