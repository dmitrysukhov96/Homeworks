package com.dmitrysukhov.collectionsspeedtest.Main;

public interface MainContract {
    interface ModelContract {
        void calculateCollectionsSpeed();
    }

    interface ViewContract {
        void showTimeInMillis(byte operationTag, int resultInMillis);
    }

    interface PresenterContract {
        void onButtonWasClicked();
        void sendDuration(byte operationTag,int duration);
    }
}
