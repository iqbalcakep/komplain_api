package com.iqbalfahrul.com.administrasi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.iqbalfahrul.com.administrasi.Adapter.KomplainAdapter;
import com.iqbalfahrul.com.administrasi.Model.Komplain;
import com.iqbalfahrul.com.administrasi.Model.GetKomplain;
import com.iqbalfahrul.com.administrasi.Rest.ApiClient;
import com.iqbalfahrul.com.administrasi.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListKomplain extends OpsiMenu {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    ApiInterface mApiInterface;
    Button btGet,btAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_komplain);

        mContext = this.getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btGet = (Button) findViewById(R.id.btGet);
        btAddData = (Button) findViewById(R.id.btAddData);

        // langsung tampilkan data
        TampilData();

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // refresh data
                TampilData();
            }
        });
    }

    public void TampilData(){
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetKomplain> mKomplainCall = mApiInterface.getKomplain();

        (mKomplainCall).enqueue(new Callback<GetKomplain>() {
            @Override
            public void onResponse(Call<GetKomplain> call, Response<GetKomplain> response) {
                Log.d("Get Komplain",response.body().getStatus());
                List<Komplain> listKomplain = response.body().getResult();
                mAdapter = new KomplainAdapter(listKomplain,ListKomplain.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetKomplain> call, Throwable t) {
                Log.d("Get Komplain",t.getMessage());
            }
        });
    }
}
