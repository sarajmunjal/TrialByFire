package com.saraj.trialbyfire;

public interface DataProvider {
    int PAGE_SIZE = 10;

    ListData getData(int position);

    void loadPage(int pageNumber);

    int getSize();
}
