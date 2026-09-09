package com.medipredict.ai.models;

import java.util.List;

public class PredictionResult {
    public String disease;
    public double confidence;
    public String risk;
    public String severity;
    public String specialist;
    public String precautions;
    public List<String> matched_symptoms;
    public String date;
}
