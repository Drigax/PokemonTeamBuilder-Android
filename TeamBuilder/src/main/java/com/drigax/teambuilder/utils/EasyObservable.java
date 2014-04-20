package com.drigax.teambuilder.utils;

/**
 * Created by drigax on 3/6/14.
 */
public interface EasyObservable<T> {

    void addListener(OnChangeListener<T> listener);

    void removeListener(OnChangeListener<T> listener);

}
