package com.app.payoneer.ui.payments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.payoneer.MyApplication;
import com.app.payoneer.R;
import com.app.payoneer.databinding.ActivityMainBinding;
import com.app.payoneer.model.Payment;
import com.app.payoneer.network.ApiResponse;
import com.app.payoneer.network.Urls;
import com.app.payoneer.ui.BaseActivity;
import com.app.payoneer.utils.ViewModelFactory;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements OnClickListener{

    @Inject
    ViewModelFactory viewModelFactory;

    PaymentsAdapter paymentsAdapter;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    ActivityMainBinding binding;
    TextView noData;
    PaymentsViewModel paymentsViewModel;
    ArrayList<Payment> paymentArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setListener(this);

        shimmerFrameLayout = binding.shimmerLayout;
        noData = binding.noData;

        ((MyApplication) getApplication()).getAppComponent().doInjection(this);
        paymentsViewModel = ViewModelProviders.of(this, viewModelFactory).get(PaymentsViewModel.class);
        paymentsViewModel.paymentsResponse().observe(this, this::consumeResponse);

        paymentsViewModel.hitLoginApi();

        setUpAdapter();
    }

    public void setUpAdapter(){
        recyclerView = binding.recyclerView;
        paymentsAdapter = new PaymentsAdapter(MainActivity.this, paymentArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(paymentsAdapter);
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();
                recyclerView.setVisibility(View.GONE);
                break;

            case SUCCESS:
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.alert))
                        .setMessage(getString(R.string.error_message))
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> finish())
                        .show();
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(JsonElement response) {
        if (!response.isJsonNull()) {
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONObject networks = jsonObject.getJSONObject(Urls.Constants.NETWORK);
                JSONArray applicablesArray = networks.getJSONArray(Urls.Constants.APPLICABLE);

                for(int i = 0; i < applicablesArray.length(); i++){
                    JSONObject applicableObject = applicablesArray.getJSONObject(i);
                    Payment payment = new Payment();
                    payment.setCode(applicableObject.optString(Urls.Constants.CODE));
                    JSONObject linkObject = applicableObject.getJSONObject(Urls.Constants.LINKS);
                    payment.setLogo(linkObject.optString(Urls.Constants.LOGO));
                    payment.setLabel(applicableObject.optString(Urls.Constants.LABEL));
                    paymentArrayList.add(payment);
                }

                setUpAdapter();
                noData.setVisibility(paymentArrayList.isEmpty() ? View.VISIBLE : View.GONE);
                recyclerView.setVisibility(paymentArrayList.isEmpty() ? View.GONE : View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view){
        if(view == binding.backBtn){
            finish();
        }
    }

}