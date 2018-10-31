package com.saraj.trialbyfire;

final class DataProviderFactory {

    private DataProviderFactory() {
    }

    static DataProvider getDataProviderInstance(DataLoadedCallback callback) {
        return new FirebaseDataProvider(callback);
    }
}
