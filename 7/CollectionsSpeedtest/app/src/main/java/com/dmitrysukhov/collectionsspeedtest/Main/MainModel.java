package com.dmitrysukhov.collectionsspeedtest.Main;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainModel implements MainContract.ModelContract {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final MainContract.PresenterContract mainPresenter;

    public MainModel(MainContract.PresenterContract mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void calculateCollectionsSpeed() {
        executorService.execute(() -> {
            ArrayList<Byte> arrayListForAddMid = new ArrayList<>(Collections.nCopies(10000000,(byte)0));
            long start = System.currentTimeMillis();
            arrayListForAddMid.add(arrayListForAddMid.size() / 2, (byte) 11);
            int duration = (int) (System.currentTimeMillis() - start);
            mainPresenter.sendDuration(MainPresenter.ADD_MID_ARRAY_LIST_TAG, duration);
        });
        executorService.execute(() -> {
            LinkedList<Byte> linkedListForAddMid = new LinkedList<>(Collections.nCopies(10000000,(byte)0));
            long start = System.currentTimeMillis();
            linkedListForAddMid.add(linkedListForAddMid.size() / 2, (byte) 11);
            int duration = (int) (System.currentTimeMillis() - start);
            mainPresenter.sendDuration(MainPresenter.ADD_MID_LINKED_LIST_TAG, duration);
        });
        executorService.execute(() -> {
            CopyOnWriteArrayList<Byte> copyOnWriteArrayListForAddMid = new CopyOnWriteArrayList<>(Collections.nCopies(10000000,(byte)1));
            long start = System.currentTimeMillis();
            copyOnWriteArrayListForAddMid.add(copyOnWriteArrayListForAddMid.size() / 2, (byte) 11);
            int duration = (int) (System.currentTimeMillis() - start);
            mainPresenter.sendDuration(MainPresenter.ADD_MID_COPY_ON_WRITE_ARRAY_LIST_TAG, duration);
        });
        executorService.execute(() -> {
            ArrayList<Byte> arrayListForRemoveMid = new ArrayList<>(Collections.nCopies(10000000,(byte)0));
            long start = System.currentTimeMillis();
            arrayListForRemoveMid.remove(5000000);
            int duration = (int) (System.currentTimeMillis() - start);
            mainPresenter.sendDuration(MainPresenter.REMOVE_MID_ARRAY_LIST_TAG, duration);
        });
        executorService.execute(() -> {
            LinkedList<Byte> linkedListForRemoveMid = new LinkedList<>(Collections.nCopies(10000000,(byte)0));
            long start = System.currentTimeMillis();
            linkedListForRemoveMid.remove(5000000);
            int duration = (int) (System.currentTimeMillis() - start);
            mainPresenter.sendDuration(MainPresenter.REMOVE_MID_LINKED_LIST_TAG, duration);
        });
        executorService.execute(() -> {
            CopyOnWriteArrayList<Byte> copyOnWriteArrayListForRemoveMid = new CopyOnWriteArrayList<>(Collections.nCopies(10000000,(byte)1));
            long start = System.currentTimeMillis();
            copyOnWriteArrayListForRemoveMid.remove(5000000);
            int duration = (int) (System.currentTimeMillis() - start);
            mainPresenter.sendDuration(MainPresenter.REMOVE_MID_COPY_ON_WRITE_ARRAY_LIST_TAG, duration);
        });
        executorService.execute(() -> {
            ArrayList<Byte> arrayListForSearch = new ArrayList<>(Collections.nCopies(10000000,(byte)0));
            arrayListForSearch.add(arrayListForSearch.size() / 2, (byte) 11);
            long start = System.currentTimeMillis();
            arrayListForSearch.indexOf((byte)11);
            int duration = (int) (System.currentTimeMillis() - start);
            mainPresenter.sendDuration(MainPresenter.SEARCH_ARRAY_LIST_TAG, duration);
        });
        executorService.execute(() -> {
            LinkedList<Byte> linkedListForSearch = new LinkedList<>(Collections.nCopies(10000000,(byte)0));
            linkedListForSearch.add(linkedListForSearch.size() / 2, (byte) 11);
            long start = System.currentTimeMillis();
            linkedListForSearch.indexOf((byte)11);
            int duration = (int) (System.currentTimeMillis() - start);
            mainPresenter.sendDuration(MainPresenter.SEARCH_LINKED_LIST_TAG, duration);
        });
        executorService.execute(() -> {
            CopyOnWriteArrayList<Byte> copyOnWriteArrayListForSearch = new CopyOnWriteArrayList<>(Collections.nCopies(10000000,(byte)1));
            copyOnWriteArrayListForSearch.add(copyOnWriteArrayListForSearch.size() / 2, (byte) 11);
            long start = System.currentTimeMillis();
            copyOnWriteArrayListForSearch.indexOf((byte)11);
            int duration = (int) (System.currentTimeMillis() - start);
            mainPresenter.sendDuration(MainPresenter.SEARCH_COPY_ON_WRITE_ARRAY_LIST_TAG, duration);
        });
        executorService.shutdown();
    }
}
