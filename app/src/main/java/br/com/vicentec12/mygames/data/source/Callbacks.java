package br.com.vicentec12.mygames.data.source;

public interface Callbacks {

    interface OnLocalCallback {

        void onSuccess(String message);

        void onFailure(String message);

    }

}
