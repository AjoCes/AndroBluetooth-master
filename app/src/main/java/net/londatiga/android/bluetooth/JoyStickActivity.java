package net.londatiga.android.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;


public class JoyStickActivity extends Activity {

    RelativeLayout layout_joystick, layout_joystick1;
    ImageView image_joystick, image_border;
    TextView textView1, textView2, textView3, textView4, textView5;
    JoyStickClass js;
    JoyStickClass1 js1;

    private static final String TAG = "bluetooth1";

    Button btnOn, btnOff;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "20:15:08:13:86:51";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joy_stick);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();

        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        textView4 = (TextView)findViewById(R.id.textView4);
        textView5 = (TextView)findViewById(R.id.textView5);

        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);

        js = new JoyStickClass(getApplicationContext()
                , layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(200);//view
        js.setOffset(90);
        js.setMinimumDistance(50);
        js.drawStickWithoutMotion();

        layout_joystick1 = (RelativeLayout)findViewById(R.id.layout_joystick1);

        js1 = new JoyStickClass1(getApplicationContext(), layout_joystick1, R.drawable.image_button);
        js1.setStickSize(150, 150);
        js1.setLayoutSize(500, 500);
        js1.setLayoutAlpha(150);
        js1.setStickAlpha(200);
        js1.setOffset(90);
        js1.setMinimumDistance(50);
        js1.drawStickWithoutMotion();




        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    textView1.setText("X : " + String.valueOf(js.getX()));
                    textView2.setText("Y : " + String.valueOf(js.getY()));
                    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
                    textView4.setText("Distance : " + String.valueOf(js.getDistance()));

                    int direction = js.get8Direction();
                    if (direction == JoyStickClass.STICK_UP) {
                        textView5.setText("Direction : Up");
                        sendData("w");
                  //  } else if (direction == JoyStickClass.STICK_UPRIGHT) {
                    //    textView5.setText("Direction : Up Right");
                   //     sendData("e");
                 //   } else if (direction == JoyStickClass.STICK_RIGHT) {
                 //       textView5.setText("Direction : Right");
                 //       sendData("d");
                 //   } else if (direction == JoyStickClass.STICK_DOWNRIGHT) {
                //        textView5.setText("Direction : Down Right");
                //        sendData("x");
                    } else if (direction == JoyStickClass.STICK_DOWN) {
                        textView5.setText("Direction : Down");
                        sendData("s");
                 //   } else if (direction == JoyStickClass.STICK_DOWNLEFT) {
                 //       textView5.setText("Direction : Down Left");
                 //       sendData("z");
                //    } else if (direction == JoyStickClass.STICK_LEFT) {
                 //       textView5.setText("Direction : Left");
                //        sendData("a");
                //    } else if (direction == JoyStickClass.STICK_UPLEFT) {
                 //       textView5.setText("Direction : Up Left");
                 //       sendData("q");
              //      } else if (direction == JoyStickClass.STICK_NONE) {
               //         textView5.setText("Direction : Center");
                        //sendData("9");
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    //  textView1.setText("X :");
                    //   textView2.setText("Y :");
                    //   textView3.setText("Angle :");
                    //    textView4.setText("Distance :");
                    //   textView5.setText("Direction :");
                    textView1.setText("X : " + String.valueOf(js.getX()));
                    textView2.setText("Y : " + String.valueOf(js.getY()));
                    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
                    textView4.setText("Distance : " + String.valueOf(js.getDistance()));

                    int direction = js.get8Direction();
                    if (direction == JoyStickClass.STICK_UP) {
                        textView5.setText("Direction : Up");
                        sendData("w");
                    } else if (direction == JoyStickClass.STICK_UPRIGHT) {
                        textView5.setText("Direction : Up Right");
                        sendData("e");
                    } else if (direction == JoyStickClass.STICK_RIGHT) {
                        textView5.setText("Direction : Right");
                        sendData("d");
                    } else if (direction == JoyStickClass.STICK_DOWNRIGHT) {
                        textView5.setText("Direction : Down Right");
                        sendData("x");
                    } else if (direction == JoyStickClass.STICK_DOWN) {
                        textView5.setText("Direction : Down");
                        sendData("s");
                    } else if (direction == JoyStickClass.STICK_DOWNLEFT) {
                        textView5.setText("Direction : Down Left");
                        sendData("z");
                    } else if (direction == JoyStickClass.STICK_LEFT) {
                        textView5.setText("Direction : Left");
                        sendData("a");
                    } else if (direction == JoyStickClass.STICK_UPLEFT) {
                        textView5.setText("Direction : Up Left");
                        sendData("q");
                    } else if (direction == JoyStickClass.STICK_NONE) {
                        textView5.setText("Direction : Center");
                        //sendData("9");
                    }
                }
                return true;
            }
        });

        layout_joystick1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                  //  textView1.setText("X : " + String.valueOf(js.getX()));
                  //  textView2.setText("Y : " + String.valueOf(js.getY()));
                  //  textView3.setText("Angle : " + String.valueOf(js.getAngle()));
                  //  textView4.setText("Distance : " + String.valueOf(js.getDistance()));

                    int direction = js.get8Direction();
                  //  if (direction == JoyStickClass.STICK_UP) {
                       // textView5.setText("Direction : Up");
                      //  sendData("w");
                 //   } else if (direction == JoyStickClass.STICK_UPRIGHT) {
                       // textView5.setText("Direction : Up Right");
                      //  sendData("e");
                  //  } else
                if (direction == JoyStickClass.STICK_RIGHT) {
                        textView5.setText("Direction : Right");
                        sendData("d");
                //    } else if (direction == JoyStickClass.STICK_DOWNRIGHT) {
                   //     textView5.setText("Direction : Down Right");
                    //    sendData("x");
                //    } else if (direction == JoyStickClass.STICK_DOWN) {
                 //       textView5.setText("Direction : Down");
                 //       sendData("s");
                //    } else if (direction == JoyStickClass.STICK_DOWNLEFT) {
                  //      textView5.setText("Direction : Down Left");
                  //      sendData("z");
                    } else if (direction == JoyStickClass.STICK_LEFT) {
                        textView5.setText("Direction : Left");
                        sendData("a");
                  //  } else if (direction == JoyStickClass.STICK_UPLEFT) {
                   //     textView5.setText("Direction : Up Left");
                  //      sendData("q");
                  //  } else if (direction == JoyStickClass.STICK_NONE) {
                 //       textView5.setText("Direction : Center");
                   //     //sendData("9");
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    //  textView1.setText("X :");
                    //   textView2.setText("Y :");
                    //   textView3.setText("Angle :");
                    //    textView4.setText("Distance :");
                    //   textView5.setText("Direction :");
                  //  textView1.setText("X : " + String.valueOf(js.getX()));
                 //   textView2.setText("Y : " + String.valueOf(js.getY()));
                //    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
                  //  textView4.setText("Distance : " + String.valueOf(js.getDistance()));

                    int direction = js.get8Direction();
                 //   if (direction == JoyStickClass.STICK_UP) {
                   //     textView5.setText("Direction : Up");
                   //     sendData("w");
                  //  } else if (direction == JoyStickClass.STICK_UPRIGHT) {
                    //    textView5.setText("Direction : Up Right");
                    //    sendData("e");
                  //  } else
                if (direction == JoyStickClass.STICK_RIGHT) {
                        textView5.setText("Direction : Right");
                        sendData("d");
                  //  } else if (direction == JoyStickClass.STICK_DOWNRIGHT) {
                  //      textView5.setText("Direction : Down Right");
                   //     sendData("x");
                //    } else if (direction == JoyStickClass.STICK_DOWN) {
                    //    textView5.setText("Direction : Down");
                   //     sendData("s");
                 //   } else if (direction == JoyStickClass.STICK_DOWNLEFT) {
                //        textView5.setText("Direction : Down Left");
                 //       sendData("z");
                    } else if (direction == JoyStickClass.STICK_LEFT) {
                        textView5.setText("Direction : Left");
                        sendData("a");
                 //   } else if (direction == JoyStickClass.STICK_UPLEFT) {
                 //       textView5.setText("Direction : Up Left");
                  //      sendData("q");
                //    } else if (direction == JoyStickClass.STICK_NONE) {
                //        textView5.setText("Direction : Center");
                        //sendData("9");
                    }
                }
                return true;
            }
        });
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection", e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "...onResume - try connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e1) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e1.getMessage() + ".");
        }

    /*try {
      btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
    } catch (IOException e) {
      errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
    }*/

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
            Log.d(TAG, "...Connection ok...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "...In onPause()...");

        if (outStream != null) {
            try {
                outStream.flush();
            } catch (IOException e) {
                errorExit("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
            }
        }

        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }
    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    private void sendData(String message) {
        byte[] msgBuffer = message.getBytes();

        Log.d(TAG, "...Send data: " + message + "...");

        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
            if (address.equals("00:00:00:00:00:00"))
                msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 35 in the java code";
            msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";

         //   errorExit("Fatal Error", msg);
        }
    }


}
