package gerasymchuk.v.themovies.view;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    @SuppressWarnings("unused")
    private static final String TAG = AbsAdapter.class.getSimpleName();

    public void refresh(@NonNull List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void showMoreItems(@NonNull List<T> items) {
        final int beforeSize = this.items.size();
        this.items.addAll(items);
        final int afterSize = this.items.size();
        notifyItemRangeInserted(beforeSize, afterSize);
    }

    public void removeItem(T item) {
        final int index = items.indexOf(item);
        if (index >= 0) {
            items.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void addItem(T item) {
        items.add(item);
        final int index = items.indexOf(item);
        if (index >= 0) {
            notifyItemInserted(index);
        }
    }

    public void moveItem(T item, int position) {
        if (items.contains(item)) {
            final int indexBefore = items.indexOf(item);
            items.add(position, items.remove(indexBefore));
            final int indexAfter = items.indexOf(item);
            notifyItemMoved(indexBefore, indexAfter);
        }
    }

    @Nullable
    public T getItem(int adapterPosition) {
        if (adapterPosition == -1) return null;
        if (adapterPosition < items.size()) {
            return items.get(adapterPosition);
        }
        return null;
    }

    @NonNull
    public List<T> getItems() {
        return items;
    }

    protected abstract V createHolder(@NonNull ViewGroup parent, int viewType);

    protected abstract void bind(@NonNull V holder, @NonNull T item);

    @NonNull
    private List<T> items = new ArrayList<>();

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        if (position < items.size() && position != -1) {
            final T item = items.get(position);
            bind(holder, item);
        }
    }

    @NonNull
    protected View inflate(@NonNull ViewGroup parent, @LayoutRes int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
