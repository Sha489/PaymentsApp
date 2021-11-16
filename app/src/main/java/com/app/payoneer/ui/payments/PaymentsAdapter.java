package com.app.payoneer.ui.payments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.payoneer.R;
import com.app.payoneer.databinding.ItemPaymentsBinding;
import com.app.payoneer.model.Payment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.MyViewHolder> {

    ArrayList<Payment> paymentArrayList;
    private LayoutInflater layoutInflater;
    Context context;

    public PaymentsAdapter(Context context, ArrayList<Payment> paymentArrayList) {
        this.paymentArrayList = paymentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PaymentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemPaymentsBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_payments, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentsAdapter.MyViewHolder holder, int position) {
        holder.binding.setPayment(paymentArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return paymentArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemPaymentsBinding binding;

        public MyViewHolder(final ItemPaymentsBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
