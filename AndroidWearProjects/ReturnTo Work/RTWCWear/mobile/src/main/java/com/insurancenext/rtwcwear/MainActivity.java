/*
 * Copyright (C) 2014 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.insurancenext.rtwcwear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.cognizant.workactivate.R;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

import adapter.CardItemData;
import adapter.cardAdapter;
import common.logger.Log;

public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    String[] txt_header = new String[]{"Side Raise", "Front Raise", "Biceps", "Step Countdown"};
    String[] sub_header = new String[]{"This will Track Your Side Raise and Update Your Health Data",
            "This will Track Your Side Raise and Update Your Health Data",
            "This will Track Your Elbow Exercise and Update Your Health Data", "This will Track Your Step Count and Update Your Health Data"};
    ListView cardslist;
    String WEARABLE_DATA_PATH = "/wearable_data";
    Context con;
    ArrayList<CardItemData> carddetails;
    CardItemData details1, details2, details3, details4, details5;
    cardAdapter cardadpter;
    GoogleApiClient googleClient;
    DataMap datamap;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        Firebase.setAndroidContext(this);
        cardslist = (ListView) findViewById(R.id.list_view);
        cardslist.addHeaderView(new View(this));
        cardslist.addFooterView(new View(this));
        con = this;
        datamap = new DataMap();
        carddetails = new ArrayList<CardItemData>();
        buildingArray();
        setadapter();
         try {
             // Build a new GoogleApiClient
             googleClient = new GoogleApiClient.Builder(this)
                     .addApi(Wearable.API)
                     .addConnectionCallbacks(this)
                     .addOnConnectionFailedListener(this)
                     .build();
             if (!googleClient.isConnected()) {
                 googleClient.connect();
             }
         }catch (Exception e)
         {
             Toast.makeText(con,e.getMessage()+"",Toast.LENGTH_SHORT).show();
         }
         cardslist.setOnItemClickListener(tracklistener);
    }

    private OnItemClickListener tracklistener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            // Toast.makeText(con, "" + position, Toast.LENGTH_SHORT).show();
            datamap.clear();
            switch (position) {
                case 1:
                    datamap.putString("activity_key", "Side");
                    sendActivity();
                    startActivity(new Intent(con, SideRaiseView.class));
                    break;
                case 2:
                    datamap.putString("activity_key", "Front");
                    sendActivity();
                    startActivity(new Intent(con, FrontRaiseView.class));

                    break;
                case 3:
                    datamap.putString("activity_key", "Elbow");
                    sendActivity();
                    startActivity(new Intent(con, BicepsView.class));
                    break;
                case 4:
                    datamap.putString("activity_key", "Step");
                    sendActivity();
                    startActivity(new Intent(con, StepcountdownView.class));

                    break;
                default:
                    break;
            }


        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        googleClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void  sendActivity()
    {
        new MobileSendToDataLayerThread(WEARABLE_DATA_PATH, datamap, googleClient,con).start();
    }

    @Override
    public void onDestroy() {
        if (googleClient.isConnected()) {
            googleClient.disconnect();
        }

        super.onDestroy();
    }

    private void buildingArray() {
        // TODO Auto-generated method stub
        for (int i = 0; i < txt_header.length; i++) {
            switch (i) {
                case 0:
                    details1 = new CardItemData();
                    details1.setText1(txt_header[i]);
                    details1.setText2(sub_header[i]);
                    carddetails.add(details1);
                    break;
                case 1:
                    details2 = new CardItemData();
                    details2.setText1(txt_header[i]);
                    details2.setText2(sub_header[i]);
                    carddetails.add(details2);
                    break;

                case 2:
                    details3 = new CardItemData();
                    details3.setText1(txt_header[i]);
                    details3.setText2(sub_header[i]);
                    carddetails.add(details3);
                    break;

                case 3:
                    details4 = new CardItemData();
                    details4.setText1(txt_header[i]);
                    details4.setText2(sub_header[i]);
                    carddetails.add(details4);
                    break;
                case 4:
                    details5 = new CardItemData();
                    details5.setText1(txt_header[i]);
                    details5.setText2(sub_header[i]);
                    carddetails.add(details5);
                    break;

                default:
                    break;
            }
        }
    }

    private void setadapter() {
        cardadpter = new cardAdapter(carddetails, con);
        cardslist.setAdapter(cardadpter);

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("Connected", "Google API Client Was Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
