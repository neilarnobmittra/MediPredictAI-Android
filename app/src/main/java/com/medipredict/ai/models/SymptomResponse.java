package com.medipredict.ai.models;

import java.util.List;

public class SymptomResponse {
    public List<PredictionResult> predictions;
    public List<String> selected_symptoms;
    public String disclaimer;
    public String error;
}
