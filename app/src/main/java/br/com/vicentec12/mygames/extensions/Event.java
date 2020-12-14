package br.com.vicentec12.mygames.extensions;

/**
 * Responsável por usar o valor apenas uma vez e limpar a variável LiveData.
 */
public class Event<T> {

    private T mContent;

    private boolean hasBeenHandled = false;

    public Event(T content) {
        if (content == null)
            throw new IllegalArgumentException("null values in Event are not allowed.");
        mContent = content;
    }

    public T getContentIfNotHandled() {
        if (!hasBeenHandled) {
            hasBeenHandled = true;
            return mContent;
        }
        return null;
    }

    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }

}
