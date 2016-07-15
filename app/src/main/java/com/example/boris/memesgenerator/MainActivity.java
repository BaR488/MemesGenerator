package com.example.boris.memesgenerator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.boris.memesgenerator.Fragments.ChooseLayoutFragment;
import com.example.boris.memesgenerator.Fragments.MakeLayoutFragment;

public class MainActivity extends AppCompatActivity implements  MakeLayoutFragment.IFindListener,
        ChooseLayoutFragment.IChooseMeme {

    Fragment saved;

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
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack("mkFragment");
        fragmentTransaction.commit();
        saved = fragment;
    }

    @Override
    public void onFind() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChooseLayoutFragment fragment = new ChooseLayoutFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack("mkFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onChooseMeme(Drawable drawable) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MakeLayoutFragment fragment = new MakeLayoutFragment();
        fragment.drawable = drawable;
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack("chFragment");
        fragmentTransaction.commit();
    }
}
