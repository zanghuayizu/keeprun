package com.example.liaodh.keeprun.view;

import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.FragmentPlanBinding;

import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class PlanFragment extends Fragment {
    private FragmentPlanBinding planBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return initView(inflater, container);

    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        planBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_plan, container, false);
        querySensor();
        return planBinding.getRoot();
    }

    private void querySensor() {
        SensorManager sensorManager;
        sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder stringBuilder = new StringBuilder();
        for (Sensor sensor : sensors) {
            stringBuilder.append(sensor.getName() + "\n");
            planBinding.textview.append(sensor.getName() + "\n");
        }
        Log.e("sensor",stringBuilder.toString());
    }
}
