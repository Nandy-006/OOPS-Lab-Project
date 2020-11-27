package oops.oops_project.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import oops.oops_project.R;

public class InventoryFragment extends Fragment
{
    private final String TAG = "DATABASE TEST";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }
}