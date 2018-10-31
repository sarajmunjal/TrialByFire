package com.saraj.trialbyfire;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FirebaseDataProvider implements DataProvider {

    private final FirebaseFirestore db;
    Map<Integer, ListData> map = new HashMap<>();
    String lastQuery = null;
    private DataLoadedCallback callback;

    public FirebaseDataProvider(DataLoadedCallback callback) {
        this.callback = callback;

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

    }

    @Override
    public ListData getData(int position) {
        return map.get(position);
    }



    private void arrangeLocalDataCopies(int startIdx, int endIdx, List<ListData> data) {
        for (int i = startIdx; i < endIdx; i++) {
            map.put(i, data.get(i - startIdx));
        }
    }

    @Override
    public void loadPage(final int pageNumber) {
        if (lastQuery == null) {
            db.collection("movies")
                    .orderBy("title")
                    .limit(PAGE_SIZE).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            QuerySnapshot result = task.getResult();
                            List<ListData> data = result.toObjects(ListData.class);
                            lastQuery = data.get(data.size() - 1).getTitle();
                            arrangeLocalDataCopies(0, PAGE_SIZE, data);
                            callback.onDataLoaded(0);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        } else {
            db.collection("movies")
                    .orderBy("title")
                    .startAfter(lastQuery)
                    .limit(PAGE_SIZE)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            QuerySnapshot result = task.getResult();
                            List<ListData> data = result.toObjects(ListData.class);
                            lastQuery = data.get(data.size() - 1).getTitle();
                            arrangeLocalDataCopies(pageNumber * PAGE_SIZE, (pageNumber + 1) * PAGE_SIZE, data);
                            callback.onDataLoaded(pageNumber);
                        }
                    });
        }
    }

    @Override
    public int getSize() {
        return map.size();
    }


}
