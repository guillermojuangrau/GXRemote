package com.gollix.gxremote;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyActivity extends Activity {
    public static final String IPADDRESS = "ipaddress";
    public static final int PORT = 15032;
    public String ipaddress;
    String[] line = new String[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

    }

    public void onClick(View v) {
        EditText ipaddressinput = (EditText) findViewById(R.id.ipaddress_input);
        ipaddress = ipaddressinput.getText().toString();
        line[0] = "192.168.1." +  ipaddress;
        switch (v.getId()) {

            case R.id.vol_up_button:
                line [1] = "volup";
                new AsyncAction().execute(line);
                break;
            case R.id.vol_down_button:
                line [1] = "voldown";
                new AsyncAction().execute(line);
                break;
            case R.id.mute_button:
                line [1] = "mute";
                new AsyncAction().execute(line);
                break;
            case R.id.pause_button:
                line [1] = "pause";
                new AsyncAction().execute(line);
                break;
            case R.id.shutdown_button:
                line [1] = "shutdown";
                new AsyncAction().execute(line);
                finish();
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }
    }
    private class AsyncAction extends AsyncTask<String, Void, String> {
        @Override
        protected String  doInBackground(String... ipaddressToConnectTo){
            try {
                String thisip = ipaddressToConnectTo[0];
                String message = ipaddressToConnectTo[1];
                Socket socket = new Socket( thisip, PORT);

                DataOutputStream streamOut = new DataOutputStream(socket.getOutputStream());

                streamOut.writeUTF(message);
                streamOut.writeUTF(".bye");
                streamOut.flush();
                Log.i("TcpClient", "sent: " + message);

                streamOut.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



    }}