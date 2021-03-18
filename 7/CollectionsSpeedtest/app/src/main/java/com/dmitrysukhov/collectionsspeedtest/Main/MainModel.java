package com.dmitrysukhov.collectionsspeedtest.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainModel implements MainContract.ModelContract {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final MainContract.PresenterContract mainPresenter;
    private final int size = 2000000;
    private ArrayList<Byte> arrayListForAddMid;
    private ArrayList<Byte> arrayListForRemoveMid;
    private ArrayList<Byte> arrayListForSearch;
    private LinkedList<Byte> linkedListForAddMid;
    private LinkedList<Byte> linkedListForRemoveMid;
    private LinkedList<Byte> linkedListForSearch;
    private CopyOnWriteArrayList<Byte> copyOnWriteArrayListForAddMid;
    private CopyOnWriteArrayList<Byte> copyOnWriteArrayListForRemoveMid;
    private CopyOnWriteArrayList<Byte> copyOnWriteArrayListForSearch;

    public MainModel(MainContract.PresenterContract mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void calculateCollectionsSpeed() {
        executorService.execute(() -> {
            arrayListForAddMid = new ArrayList<>(Collections.nCopies(size, (byte) 0));
            linkedListForAddMid = new LinkedList<>(Collections.nCopies(size, (byte) 0));
            copyOnWriteArrayListForAddMid = new CopyOnWriteArrayList<>(Collections.nCopies(size, (byte) 1));
            arrayListForRemoveMid = new ArrayList<>(Collections.nCopies(size, (byte) 0));
            linkedListForRemoveMid = new LinkedList<>(Collections.nCopies(size, (byte) 0));
            copyOnWriteArrayListForRemoveMid = new CopyOnWriteArrayList<>(Collections.nCopies(size, (byte) 1));
            arrayListForSearch = new ArrayList<>(Collections.nCopies(size, (byte) 0));
            arrayListForSearch.add(arrayListForSearch.size() / 2, (byte) 11);
            linkedListForSearch = new LinkedList<>(Collections.nCopies(size, (byte) 0));
            linkedListForSearch.add(linkedListForSearch.size() / 2, (byte) 11);
            copyOnWriteArrayListForSearch = new CopyOnWriteArrayList<>(Collections.nCopies(size, (byte) 1));
            copyOnWriteArrayListForSearch.add(copyOnWriteArrayListForSearch.size() / 2, (byte) 11);

            executorService.execute(() -> {
                long start = System.currentTimeMillis();
                arrayListForAddMid.add(arrayListForAddMid.size() / 2, (byte) 11);
                int duration = (int) (System.currentTimeMillis() - start);
                mainPresenter.sendDuration(MainPresenter.ADD_MID_ARRAY_LIST_TAG, duration);
            });
            executorService.execute(() -> {
                long start = System.currentTimeMillis();
                linkedListForAddMid.add(linkedListForAddMid.size() / 2, (byte) 11);
                int duration = (int) (System.currentTimeMillis() - start);
                mainPresenter.sendDuration(MainPresenter.ADD_MID_LINKED_LIST_TAG, duration);
            });
            executorService.execute(() -> {
                long start = System.currentTimeMillis();
                copyOnWriteArrayListForAddMid.add(copyOnWriteArrayListForAddMid.size() / 2, (byte) 11);
                int duration = (int) (System.currentTimeMillis() - start);
                mainPresenter.sendDuration(MainPresenter.ADD_MID_COPY_ON_WRITE_ARRAY_LIST_TAG, duration);
            });
            executorService.execute(() -> {

                long start = System.currentTimeMillis();
                arrayListForRemoveMid.remove(arrayListForRemoveMid.size() / 2);
                int duration = (int) (System.currentTimeMillis() - start);
                mainPresenter.sendDuration(MainPresenter.REMOVE_MID_ARRAY_LIST_TAG, duration);
            });
            executorService.execute(() -> {

                long start = System.currentTimeMillis();
                linkedListForRemoveMid.remove(linkedListForRemoveMid.size() / 2);
                int duration = (int) (System.currentTimeMillis() - start);
                mainPresenter.sendDuration(MainPresenter.REMOVE_MID_LINKED_LIST_TAG, duration);
            });
            executorService.execute(() -> {

                long start = System.currentTimeMillis();
                copyOnWriteArrayListForRemoveMid.remove(copyOnWriteArrayListForRemoveMid.size() / 2);
                int duration = (int) (System.currentTimeMillis() - start);
                mainPresenter.sendDuration(MainPresenter.REMOVE_MID_COPY_ON_WRITE_ARRAY_LIST_TAG, duration);
            });
            executorService.execute(() -> {
                long start = System.currentTimeMillis();
                arrayListForSearch.indexOf((byte) 11);
                int duration = (int) (System.currentTimeMillis() - start);
                mainPresenter.sendDuration(MainPresenter.SEARCH_ARRAY_LIST_TAG, duration);
            });
            executorService.execute(() -> {
                long start = System.currentTimeMillis();
                linkedListForSearch.indexOf((byte) 11);
                int duration = (int) (System.currentTimeMillis() - start);
                mainPresenter.sendDuration(MainPresenter.SEARCH_LINKED_LIST_TAG, duration);
            });
            executorService.execute(() -> {
                long start = System.currentTimeMillis();
                copyOnWriteArrayListForSearch.indexOf((byte) 11);
                int duration = (int) (System.currentTimeMillis() - start);
                mainPresenter.sendDuration(MainPresenter.SEARCH_COPY_ON_WRITE_ARRAY_LIST_TAG, duration);
            });
            executorService.shutdown();
        });
    }
}