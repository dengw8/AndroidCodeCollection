package com.example.dropdownmenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dropdownmenu.R;
import com.example.dropdownmenu.RecyclerViewAnimator;
import com.example.dropdownmenu.bean.SecondMenuModel;

import java.util.List;

/**
 * Classic custom adapter.
 */
public class AnimAdapterForRecyclerView extends RecyclerView.Adapter<AnimAdapterForRecyclerView.ViewHolder> {

    private List<SecondMenuModel> itemList;
    private AnimAdapterForRecyclerView.OnItemClickListener click;
    private RecyclerViewAnimator mAnimator;
    /**
     * Classic ViewHolder.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.name);
        }
    }

    /**
     * @param recyclerView We ask it but it's not necessarily needed.
     */
    public AnimAdapterForRecyclerView(RecyclerView recyclerView, List<SecondMenuModel> itemList) {
        mAnimator = new RecyclerViewAnimator(recyclerView);
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_adapter_item, parent, false);
        ViewHolder vh = new ViewHolder(v);

        /*
         * First item's entrance animations.
         */
        mAnimator.onCreateViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextView.setText(itemList.get(position).getName());

        /*
         * Item's entrance animations during scroll are performed here.
         */
        mAnimator.onBindViewHolder(holder.itemView, position);

        if(click != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onItemClick(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener click) {
        this.click = click;
    }
}
