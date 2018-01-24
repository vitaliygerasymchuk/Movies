package gerasymchuk.v.themovies.view_model;

import android.arch.lifecycle.ViewModel;

import gerasymchuk.v.themovies.App;
import gerasymchuk.v.themovies.data.RoomDB;

/**
 * Created by vitaliygerasymchuk on 12/10/17
 * Utech(c)
 */

public abstract class AbsViewModel extends ViewModel {
    protected RoomDB db = App.getInstance().getDB();
}
