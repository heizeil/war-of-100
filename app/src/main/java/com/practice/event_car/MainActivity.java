package com.practice.event_car;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.practice.event_car.annotation.Function;
import com.practice.event_car.thread.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.btn_main);
        button.setOnClickListener(this);
        EventCar.getDefaultInstance().register(this);
    }
    @Function(threadMod = ThreadMode.MAIN)
    public void onEvent(Student s){
        Toast.makeText(this, s.name + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCar.getDefaultInstance().unregister(this);
    }

    public boolean one = true;
    @Override
    public void onClick(View v) {
        if (one) {
            startActivity(new Intent(this,Main2Activity.class));
            one = false;
        }else {
            Student s = new Student();
            s.name = "haha";
            EventCar.getDefaultInstance().post(s);
            EventCar.getDefaultInstance().post("kaishila");
        }
    }
}
