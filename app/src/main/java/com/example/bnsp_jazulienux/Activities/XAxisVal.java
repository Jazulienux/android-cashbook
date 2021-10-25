package com.example.bnsp_jazulienux.Activities;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class XAxisVal implements IAxisValueFormatter {
    private String[] mvalues;
    public XAxisVal(String[] tempTgl) {
        this.mvalues = tempTgl;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return this.mvalues[(int)value];
    }
}
