package com.example.tabletinterface2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


//This activity holds a TextView for the watch's IP address, a button to connect to the robot, and a button to switch to the second (control) activity.


public class MainActivity extends Activity {


    //private BoxInsetLayout boxInsetLayout;
    Button button;

    // This will display the IP address as entered into SERVER_IP. It cannot be changed from the watch interface.
    TextView ipAddress;

    // Change this to the address of the server you're connecting to.
    static final String SERVER_IP = "169.254.152.5";

    // Returns SERVER_IP so it can be accessed in the DoInBackground class.
    public static String getSERVER_IP(){
        return SERVER_IP;
    }


    static final int serverport = 20602;

    // Returns serverport so it can be accessed in the DoInBackground class. (It doesn't need to be declared in the main class but I find it's easier to spot the IP and port fields when they're near the top.
    public static int getServerport(){
        return serverport;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets the TextView to SERVER_IP
        ipAddress = findViewById(R.id.ipAddress); //Try commenting out this line
        ipAddress.setText(SERVER_IP);


        //boxInsetLayout = findViewById(R.id.main_activity);
        button = findViewById(R.id.connect);

        button.setOnClickListener(new View.OnClickListener() {

            // Connects when (and only when!) the "connect" button is clicked.
            @Override
            public void onClick(View v) {
                connect();
            }
        });


    }

    // Starts the DoInBackground thread. This should ony need to be done once per session: the connection should not be renewed every time a message is sent.
    public void connect(){
        MainActivity.DoInBackground asyncTask = new MainActivity.DoInBackground();
        asyncTask.execute();
    }

    // Class to open socket and connect to computer.
    static class DoInBackground extends AsyncTask<Void, Void, String> {

        public static Socket getSocket(){
            Log.i("MainActivity", "Returning socket");
            return socket;
        }

        public static Socket socket;


        @Override
        public void onPreExecute(){
            Log.i("MainActivity", "onPreExecute"); // This is just for documentation
        }

        @Override
        public String doInBackground(Void... voids) {

            final int serverport = getServerport(); //20602 is the default for the Unity engine I was using at the time
            final String SERVER_IP = getSERVER_IP();
            Log.i("MainActivity", "Server_IP = " + SERVER_IP);

            try {
                Log.i("MainActivity", "'Try' loop started in onClick");

                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                Log.i("MainActivity", "serverAddr: " + serverAddr);

                socket = new Socket(serverAddr, serverport);

            } catch (UnknownHostException e) {
                Log.i("MainActivity", "caught UnknownHostException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("MainActivity", "caught IOException");
                e.printStackTrace();
            } catch (Exception e) {
                Log.i("MainActivity", "caught Exception");
                e.printStackTrace();
            }

            // Returned value isn't important, since it is never used.
            Log.i("MainActivity", "returning doInBackground");
            return "";
        }
    }

    // Switches to the Second Activity
    public void viewSecondActivity (View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        Log.i("MainActivity", "Switching Activities");
    }

}