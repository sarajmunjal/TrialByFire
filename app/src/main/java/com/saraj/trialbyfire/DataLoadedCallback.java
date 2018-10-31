package com.saraj.trialbyfire;

public interface DataLoadedCallback {
    void onDataLoaded(int pageNumber);

    void onDataLoadFailed(int pageNumber);

    void onDataLoadCancelled();
}
