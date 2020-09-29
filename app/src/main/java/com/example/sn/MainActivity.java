package com.example.sn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Color;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView text;
    private Boolean isClicked;
    /* Donald Hughes
     * Persistence - Assessment 3
     * */
    ListView lv_playerList;
    ArrayAdapter playerArrayAdapter;
    DataBaseHelper dataBaseHelper;
    EditText et_name;
    private static final String TAG = "dbTag";
    private static final String TAG2 = "fbTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Object[] scores = new Object[5];
        text = (TextView) findViewById(R.id.text);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        btn = (Button) findViewById(R.id.button);
        lv_playerList = findViewById(R.id.lv_playerList);
        et_name = findViewById(R.id.et_name);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSnake();
            }
        });
        anim(); // trigger the background animation
        /* Donald Hughes
         * Persistence - Assessment 3 * */
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        showPlayersOnListView(dataBaseHelper);


        // Read Players from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("players");

//        myRef.setValue("No high score yet...");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                  PlayerModel player;
                    for (DataSnapshot postSnap: dataSnapshot.getChildren()) {
                        player = postSnap.getValue(PlayerModel.class);
                        Log.d(TAG2, "Value is: " + player);
                        text.append(player.toString() + "\n");
                    }
//                    String value = dataSnapshot.getValue().toString();
//                    text.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void openSnake() { //method to start a game of snake
        Intent intent = new Intent(this, Snake.class);
        intent.putExtra("player_name", et_name.getText().toString());
        startActivity(intent);
    }

    public void anim() { //method for displaying a Drawable Animation on the start screen
        ImageView imageView = findViewById(R.id.imageView);
        final AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.rock1), 50);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.rock2), 50);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.rock3), 50);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.rock4), 50);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.rock5), 50);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.rock6), 50);
        animationDrawable.setOneShot(false);
        imageView.setImageDrawable(animationDrawable);
        animationDrawable.start();

        //to stop the animation, click it.
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClicked = true;
                if(isClicked)
                    animationDrawable.stop();
                    isClicked = false;
            }
        });
    }

    private void showPlayersOnListView(DataBaseHelper dataBaseHelper2) {
        playerArrayAdapter = new ArrayAdapter<PlayerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getPlayers());
        lv_playerList.setAdapter(playerArrayAdapter);
    }
}
