package com.example.a222.FragmentosPP;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a222.R;

public class HomeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState){

        getActivity().setTitle("Inicio");
        View layout = inflater.inflate(R.layout.fragment_homee, container, false);
       // layout.findViewById(R.id.imagenView).setBackgroundResource(getArguments().getInt(ARG_ICON));
        return layout;


    }


}

