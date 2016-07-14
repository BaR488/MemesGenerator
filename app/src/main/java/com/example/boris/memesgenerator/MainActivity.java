package com.example.boris.memesgenerator;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.boris.memesgenerator.Fragments.ChooseLayoutFragment;
import com.example.boris.memesgenerator.Fragments.MakeLayoutFragment;

public class MainActivity extends AppCompatActivity implements  MakeLayoutFragment.IFindListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MakeLayoutFragment fragment = new MakeLayoutFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFind() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChooseLayoutFragment fragment = new ChooseLayoutFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
