package com.stefanhoth.ropasclisp.playing;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.stefanhoth.ropasclisp.R;

public class HandSignActivity extends AppCompatActivity implements HandSignFragment.OnFragmentInteractionListener, ViewPager.OnPageChangeListener {

    @Override
    public void onHandSignSelected(HandSign handSign) {
        Toast.makeText(this, nameOf(handSign), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_sign);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new HandSignAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // no op
    }

    @Override
    public void onPageSelected(int position) {
        HandSign handSign = HandSign.values()[position];
        setTitle(nameOf(handSign));
    }

    private String nameOf(HandSign handSign) {
        return getString(handSign.getNameResId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // no op
    }

}
