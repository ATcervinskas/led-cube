package com.application.ledcubeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private BluetoothAdapter bluetoothAdapter; //pridedamas bluetooth adapteris
    private CubeUtils cubeUtils;

    private final int LOCATION_PERMISSION_REQUEST=101;//
    private final int SELECT_DEVICE=102;

    public static final int MESSAGE_STATE_CHANGED=0;
    public static final int MESSAGE_READE=1;
    public static final int MESSAGE_WRITE=2;
    public static final int MESSAGE_DEVICE_NAME=3;
    public static final int MESSAGE_TOAST=4;
    private Button turnAllOff,turnAllON,oneByOne,cicrleAround,layerByLayerUp,layerByLayerDown,layerUpAndDown,edges, center,inAndOut;
    public static final String DEVICE_NAME="deviceName";
    public static final String TOAST ="TOAST";
    private String connectedDevice;



    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case MESSAGE_STATE_CHANGED:
                    switch (message.arg1){
                        case CubeUtils.STATE_NONE:
                            setState("Neprisijungęs");
                            break;
                        case CubeUtils.STATE_LISTEN:
                            setState("Neprisijungęs");
                            break;
                        case CubeUtils.STATE_CONNECTING:
                            setState("Jungiasi..");
                            break;
                        case CubeUtils.STATE_CONNECTED:
                            setState("Prisijungęs prie " + connectedDevice );
                            break;
                    }
                    break;
                case MESSAGE_READE:
                    break;
                case MESSAGE_WRITE:
                    break;
                case MESSAGE_DEVICE_NAME:
                    connectedDevice = message.getData().getString(DEVICE_NAME);
                    Toast.makeText(context,connectedDevice,Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(context, message.getData().getString(TOAST), Toast.LENGTH_SHORT).show();;
                    break;

            }

            return false;
        }
    });
    private void setState(CharSequence subTitle){
        getSupportActionBar().setSubtitle(subTitle);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        initBluetooth();//iškviečiamas bluetooth tikrinimo metodas
        cubeUtils=new CubeUtils(context,handler);
        init();

    }
    private void init(){
        turnAllOff=findViewById(R.id.btn_TurnAllOff);
        turnAllON=findViewById(R.id.btn_TurnAllOn);
        oneByOne=findViewById(R.id.btn_OneByOne);
        cicrleAround=findViewById(R.id.btn_CircleAround);
        layerByLayerUp=findViewById(R.id.btn_LayerByLayerUp);
        layerByLayerDown=findViewById(R.id.btn_LayerByLayerDown);
        layerUpAndDown=findViewById(R.id.btn_LayerUpAndDown);
        edges=findViewById(R.id.btn_Edges);
        center=findViewById(R.id.btn_Centre);
        inAndOut=findViewById(R.id.btn_InAndOut);

        turnAllOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String turnAlloff="0";
                cubeUtils.write(turnAlloff.getBytes());
            }
        });
        turnAllON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String turnAllOn="1";
                 cubeUtils.write(turnAllOn.getBytes());
            }
        });

        oneByOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oneByOne="2";
                cubeUtils.write(oneByOne.getBytes());
            }
        });
        cicrleAround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cicrleAround="3";
                cubeUtils.write(cicrleAround.getBytes());
            }
        });
        layerByLayerUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String layerByLayerUp="4";
                cubeUtils.write(layerByLayerUp.getBytes());
            }
        });
        layerByLayerUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String layerByLayerUp="4";
                cubeUtils.write(layerByLayerUp.getBytes());
            }
        });
        layerByLayerDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String layerByLayerDown="5";
                cubeUtils.write(layerByLayerDown.getBytes());
            }
        });
        layerUpAndDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String layerUpAndDown="6";
                cubeUtils.write(layerUpAndDown.getBytes());
            }
        });

        edges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edges="7";
                cubeUtils.write(edges.getBytes());
            }
        });
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String center="8";
                cubeUtils.write(center.getBytes());
            }
        });
        inAndOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inAndOut="9";
                cubeUtils.write(inAndOut.getBytes());
            }
        });

    }

// deklaruojamas bluetooth įrenginys
    private void initBluetooth(){
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null){ // tikrina ar įrenginyje yra bluetooth, jei atsakymas neigiamas išvedama žinutė
            Toast.makeText(context,"Bluetooth nerastas", Toast.LENGTH_SHORT).show();
        }
    }
// Pridedamas meniu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }
//Pridedami meniu elementai

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_search_devices:
                Toast.makeText(context,"Paspaudėte pridėti įrenginį", Toast.LENGTH_SHORT).show();
                checkPermissions();
                return true;
            case R.id.menu_enable_bluetooth:
                Toast.makeText(context,"Paspaudėte įjungti bluetooth", Toast.LENGTH_SHORT).show();
                enableBluetooth();//iškviečiama bluetooth įjungimo funkcija
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
// Teisių tikrinimo funkcija
private void checkPermissions() {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
    } else {
        Intent intent = new Intent(context, DeviceListActivity.class);
        startActivityForResult(intent, SELECT_DEVICE);
    }
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_DEVICE && resultCode == RESULT_OK) {
            String address = data.getStringExtra("deviceAddress");
            cubeUtils.connect(bluetoothAdapter.getRemoteDevice(address));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==LOCATION_PERMISSION_REQUEST){
            if(grantResults.length> 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(context,DeviceListActivity.class);
                startActivityForResult(intent,SELECT_DEVICE);

            }else {
                new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setMessage("Vietovės teisė nėra įjungta .\nPrašome įjungti")
                        .setPositiveButton("Įjungti", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkPermissions();//iškviečiama funkcija teisėms suteikti
                            }
                        })
                        .setNegativeButton("Atštaukti", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                        .show();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    // bluetooth įjungimo funkcija
    private void enableBluetooth(){
        if (bluetoothAdapter.isEnabled()){
            Toast.makeText(context,"Bluetooth jau yra įjungtas", Toast.LENGTH_SHORT).show();// tikrina ar bluetooth įjungtas, jei įjungtas atvaziduoja žinute
            bluetoothAdapter.enable();// jei bluetooth išjungtas, įjungiamas
        }
        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoveryIntent);
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if(cubeUtils!=null){
            cubeUtils.stop();
        }
    }
}


