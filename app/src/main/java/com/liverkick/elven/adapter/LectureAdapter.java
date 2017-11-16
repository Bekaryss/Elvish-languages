package com.liverkick.elven.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liverkick.elven.R;
import com.liverkick.elven.activity.LectureActivity;
import com.liverkick.elven.activity.LecturesActivity;
import com.liverkick.elven.models.Lecture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bekarys on 13.11.17.
 */

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder>{
    private Context mContext;
    private List<Lecture> lectureList;

    public LectureAdapter(Context _mContext, List<Lecture> _lectureList) {
        this.mContext = _mContext;
        this.lectureList = _lectureList;
    }

    @Override
    public LectureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecture_card, parent, false);
        return new LectureAdapter.ViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(LectureAdapter.ViewHolder holder, int position) {
        holder.setPosition(position);
        holder.title.setText(lectureList.get(position).getTitle());
        holder.body.setText(lectureList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return lectureList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title, body;
        int position;

        public ViewHolder(View view, final Context event) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            body = (TextView) view.findViewById(R.id.body);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(event, LectureActivity.class);
                    intent.putExtra("lecture-item", (Parcelable) lectureList.get(position));
                    event.startActivity(intent);
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
