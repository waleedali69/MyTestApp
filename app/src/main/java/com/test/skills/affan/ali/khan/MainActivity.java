package com.test.skills.affan.ali.khan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.skills.affan.ali.khan.adapters.RecyclerViewAdapter;
import com.test.skills.affan.ali.khan.databinding.ActivityMainBinding;
import com.test.skills.affan.ali.khan.viewmodel.ModelViewModel;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ModelViewModel viewModel;
    private RecyclerViewAdapter adapter;

    private final String RECYCLER_POSITION_KEY = "recycler_position";
    private int mPosition = RecyclerView.NO_POSITION;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private static Bundle mBundleState;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        mRecyclerView = binding.recyclerView;

        viewModel = new ViewModelProvider(this).get(ModelViewModel.class);
        viewModel.init();
        viewModel.getModels().observe(this, nicePlaces -> {
            adapter.notifyDataSetChanged();
            binding.recyclerView.smoothScrollToPosition(Objects.requireNonNull(viewModel.getModels().getValue()).size() - 1);

        });

        adapter = new RecyclerViewAdapter(this, viewModel.getModels().getValue());
        binding.recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onPause()
    {
        super.onPause();

        // Save RecyclerView state
        mBundleState = new Bundle();
        mPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        mBundleState.putInt(RECYCLER_POSITION_KEY, mPosition);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Restore RecyclerView state
        if (mBundleState != null) {
            mPosition = mBundleState.getInt(RECYCLER_POSITION_KEY);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            // Scroll the RecyclerView to mPosition
            mRecyclerView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save RecyclerView state
        outState.putInt(RECYCLER_POSITION_KEY,  mLayoutManager.findFirstCompletelyVisibleItemPosition());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore RecyclerView state
        if (savedInstanceState.containsKey(RECYCLER_POSITION_KEY)) {
            mPosition = savedInstanceState.getInt(RECYCLER_POSITION_KEY);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            // Scroll the RecyclerView to mPosition
            mRecyclerView.smoothScrollToPosition(mPosition);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }
}