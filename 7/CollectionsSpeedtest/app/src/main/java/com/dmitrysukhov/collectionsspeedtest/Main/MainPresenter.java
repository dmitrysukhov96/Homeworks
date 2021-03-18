package com.dmitrysukhov.collectionsspeedtest.Main;

public class MainPresenter implements MainContract.PresenterContract {

    public static final String TAG = "myLogTag";
    private final MainContract.ViewContract mainView;
    public static final byte ADD_MID_ARRAY_LIST_TAG = 11;
    public static final byte ADD_MID_LINKED_LIST_TAG = 12;
    public static final byte ADD_MID_COPY_ON_WRITE_ARRAY_LIST_TAG = 13;
    public static final byte REMOVE_MID_ARRAY_LIST_TAG = 21;
    public static final byte REMOVE_MID_LINKED_LIST_TAG = 22;
    public static final byte REMOVE_MID_COPY_ON_WRITE_ARRAY_LIST_TAG = 23;
    public static final byte SEARCH_ARRAY_LIST_TAG = 31;
    public static final byte SEARCH_LINKED_LIST_TAG = 32;
    public static final byte SEARCH_COPY_ON_WRITE_ARRAY_LIST_TAG = 33;

    @Override
    public void onButtonWasClicked() {
        MainContract.ModelContract mainModel = new MainModel(this);
        mainModel.calculateCollectionsSpeed();
    }

    @Override
    public void sendDuration(byte operationTag, int duration) {
        mainView.showTimeInMillis(operationTag, duration);
    }

    public MainPresenter(MainContract.ViewContract mainView) {
        this.mainView = mainView;
    }
}
