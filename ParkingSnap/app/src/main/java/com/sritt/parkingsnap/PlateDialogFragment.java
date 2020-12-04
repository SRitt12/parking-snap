package com.sritt.parkingsnap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlateDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlateDialogFragment extends DialogFragment {
    String plate = PlateReaderActivity.getPlatenum();
    public PlateDialogFragment() {
        // Required empty public constructor
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Plate Not Found");
        builder.setMessage(plate + " is not in the database, would you like to add it?");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // send the user to the new vehicle activity
                Intent vehicleIntent = new Intent(getActivity(), NewVehicleActivity.class);
                startActivity(vehicleIntent);
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        return builder.create();
    }



    public static PlateDialogFragment newInstance() {
        PlateDialogFragment fragment = new PlateDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plate_dialog, container, false);
    }
}