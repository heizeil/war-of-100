package com.practice.event_car;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.practice.event_car.annotation.Function;
import com.practice.event_car.thread.ThreadMode;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EventCar.getDefaultInstance().register(this);
    }

    @Function(threadMod = ThreadMode.MAIN)
    public void onEvent(String s){
        Toast.makeText(this, "main2" + s, Toast.LENGTH_SHORT).show();
        Student student = new Student();
        student.name = s;
        student.age = 12;
        student.isMan = false;
        EventCar.getDefaultInstance().post(student);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCar.getDefaultInstance().unregister(this);
    }
}
