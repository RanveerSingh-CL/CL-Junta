package com.skeleton.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.fragment.ProfileLaterFragment;
import com.skeleton.model.category.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rishucuber on 7/4/17.
 */
public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerAdapter.MyViewHolder> {
    private List<Category> mSelectedCategories;
    private List<Boolean> isSelected;
    private ProfileLaterFragment fragment;

    private List<Category> mListCategories;
    private int counter = 0;
    private final int maxInterestSelected = 5;


    /**
     * @param listCategories data to inflate
     */
    public ProfileRecyclerAdapter(final List<Category> listCategories) {
        mListCategories = listCategories;
        mSelectedCategories = new ArrayList<>();
        isSelected = new ArrayList<Boolean>(Arrays.asList(new Boolean[mListCategories.size()]));
        Collections.fill(isSelected, Boolean.FALSE);
    }


    /**
     * Constructor
     *
     * @param profileStep2Fragment : context
     * @param listCategories       : list of categories
     */
    public ProfileRecyclerAdapter(final ProfileLaterFragment profileStep2Fragment,
                                  final List<Category> listCategories) {
        fragment = profileStep2Fragment;
        mListCategories = listCategories;
        mSelectedCategories = new ArrayList<>();
        isSelected = new ArrayList<Boolean>(Arrays.asList(new Boolean[mListCategories.size()]));
        Collections.fill(isSelected, Boolean.FALSE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_completeness, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Category current = mListCategories.get(position);
        holder.imageViewSelected.setVisibility(View.INVISIBLE);
        if (current != null) {
            holder.tvGridLabel.setText(current.getName());
            /*Glide.with(holder.imageViewItem.getContext())
                    .load(current.getPicURL().getThumbnail())
                    .crossFade()
                    .into(holder.imageViewItem);*/
            if (isSelected.get(position)) {
                holder.imageViewSelected.setVisibility(View.VISIBLE);
            } else {
                holder.imageViewSelected.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (isSelected.get(position)) {
                        isSelected.set(position, !isSelected.get(position));
                        counter--;
                        mSelectedCategories.remove(mListCategories.get(position));
                        /*Toast.makeText(holder.itemView.getContext(), "removed: " + mListCategories.get(position),
                                Toast.LENGTH_SHORT).show();*/
                        holder.imageViewSelected.setVisibility(View.GONE);
                    } else {
                        if (counter < maxInterestSelected) {
                            isSelected.set(position, !isSelected.get(position));
                            counter++;
                            mSelectedCategories.add(mListCategories.get(position));
                            holder.imageViewSelected.setVisibility(View.VISIBLE);
                            /*Toast.makeText(holder.itemView.getContext(), "added: " + mListCategories.get(position),
                                    Toast.LENGTH_SHORT).show();*/
                        } else {
                            Log.d("rishu", "fail");
                        }
                    }

                    if (fragment != null) {
                        fragment.glowIndicator(counter);
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mListCategories.size();
    }

    /**
     * MyViewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imageViewProfile, imageViewSelected;
        private TextView tvGridLabel;

        /**
         * @param itemView item
         */
        public MyViewHolder(final View itemView) {
            super(itemView);
            imageViewProfile = (CircleImageView) itemView.findViewById(R.id.profile_image);
            imageViewSelected = (CircleImageView) itemView.findViewById(R.id.profile_image_selected);
            tvGridLabel = (TextView) itemView.findViewById(R.id.grid_label);
        }
    }

    /**
     * @return : returns selected categories
     */
    public List<Category> getSelectedCategories() {
        return mSelectedCategories;
    }
}
