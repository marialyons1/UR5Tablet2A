package com.example.tabletinterface2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class  SecondActivity extends MainActivity  {

//    private BoxInsetLayout boxInsetLayout;
    Socket socket = MainActivity.DoInBackground.getSocket();

    Button pickUpObject;
    //21313618687

    Button placeObject;
    //2131361868

    Button enterGCM;
    //2131361829

    Button exitGCM;
    //2131361830

    String str = "default"; //this allows you to test your str modifiers in the onClick method. str is active in both SecondActivity and SendMessage classes.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //boxInsetLayout = findViewById(R.id.second_activity);



        View.OnClickListener  listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonPressed = v.getId();
                Log.i("SecondActivity", "Button pressed: " + v.getId());

                // Creates a new SendMessage object each time a button is clicked. All of these run on the same socket, declared in MainActivity: this socket is only created once.
                SecondActivity.SendMessage asyncTask = new SecondActivity.SendMessage();
                asyncTask.execute();

                switch (buttonPressed){
                    // If you change the xml, check and make sure all the button ids are the same. You may also want to edit the comments beneath the button declarations.
                    case 2131165281:
                        str = "pick";
                        break;
                    case 2131165282:
                        str = "place";
                        break;
                    case 2131165241:
                        str = "gravcomp_on";
                        break;
                    case 2131165242:
                        str = "gravcomp_off";
                        break;
                }
            }
        };

        //assigns all button objects to their respective xml code
        pickUpObject = findViewById(R.id.pick_up_object);
        placeObject = findViewById(R.id.place_object);
        enterGCM = findViewById(R.id.enter_GCM);
        exitGCM = findViewById(R.id.exit_GCM);

        //assigns all button objects to the same onClickListener
        pickUpObject.setOnClickListener(listener);
        placeObject.setOnClickListener(listener);
        enterGCM.setOnClickListener(listener);
        exitGCM.setOnClickListener(listener);

    }

    // Code for switching back to the Main Activity.
    public void viewMainActivity (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.i("SecondActivity", "Switching Activities");
    }


    // Class to send message over socket connection
    class SendMessage extends AsyncTask<Void, Void, String> {

        @Override
        public void onPreExecute(){
        }

        @Override
        public String doInBackground(Void... voids) {

            try {
                // Sends message
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(str);
                Log.i("SecondActivity", "Message Sent");
            } catch (UnknownHostException e) {
                Log.i("SecondActivity", "caught UnknownHostException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("SecondActivity", "caught IOException");
                e.printStackTrace();
            } catch (Exception e) {
                Log.i("SecondActivity", "caught Exception");
                e.printStackTrace();
            }

            //the return type does not seem to matter for this program. The return value will never be used.
            return "";

        }
    }


}