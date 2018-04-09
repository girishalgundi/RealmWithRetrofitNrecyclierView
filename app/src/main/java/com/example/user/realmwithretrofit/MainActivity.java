package com.example.user.realmwithretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.realmwithretrofit.Models.Examplerequest;
import com.example.user.realmwithretrofit.Models.Exampleresponse;
import com.example.user.realmwithretrofit.Models.UserDetailsList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    APIInterface apiInterface;
    RealmConfiguration realmConfiguration;
    Realm realm;
    TextView textView;
    RecyclerView rv_account_numbers;
    private LinearLayoutManager mLayoutManager;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        rv_account_numbers = (RecyclerView) findViewById(R.id.rv_account_numbers);

        mLayoutManager = new LinearLayoutManager(this);
        rv_account_numbers.setLayoutManager(mLayoutManager);

        inititalize();
        retrofitService();// calling retrofit service


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RealmResults<Exampleresponse> exampleresponseRealmResults = realm.where(Exampleresponse.class).findAll();// Retrieving the data
                Exampleresponse exampleresponse = exampleresponseRealmResults.get(0);
                Log.e("exampleresponse>", exampleresponse.getRemarks());

                RealmResults<UserDetailsList> realmList = realm.where(UserDetailsList.class).findAll();// Retrieving the data
                String contact = "";
                int size = realmList.size();
                Log.e("offline_result_", size + "");
                for (int i = 0; i < realmList.size(); i++) {
                    Log.e("offline_result_>", realmList.get(i).getEmailId());
                    listAdapter = new ListAdapter(MainActivity.this, realmList);
                    rv_account_numbers.setAdapter(listAdapter);
                }
            }
        });

    }

    private void inititalize() {

        /*Realm init*/
        Realm.init(this);
        realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfiguration);
        /*Retrofit init*/
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }


    private void retrofitService() {


        Call<Exampleresponse> call = apiInterface.doGetListResources(new Examplerequest("1"));
        call.enqueue(new Callback<Exampleresponse>() {
            @Override
            public void onResponse(Call<Exampleresponse> call, Response<Exampleresponse> response) {
                // Exampleresponse exampleresponse = response.body();


                if (response.body() != null) {
                    RealmList<UserDetailsList> realmList = response.body().getUserDetailsList();// getting data from server.
                    realm.beginTransaction();// init the realm
                    realm.delete(UserDetailsList.class);//deleting old data
                    realm.copyToRealm(realmList);// inserting new data
                    realm.commitTransaction();// commit and save the data to realDB

                    Exampleresponse exampleresponse = response.body();// getting data from server.
                    realm.beginTransaction();// init the realm
                    realm.delete(Exampleresponse.class);//deleting old data
                    realm.copyToRealm(exampleresponse);// inserting new data
                    realm.commitTransaction();// commit and save the data to realDB

                }

            }

            @Override
            public void onFailure(Call<Exampleresponse> call, Throwable t) {
                t.printStackTrace();

            }

        });


    }


}
