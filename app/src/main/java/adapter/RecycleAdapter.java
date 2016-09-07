package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nganthoi.salai.tabgen.R;

import java.util.Collections;
import java.util.List;

import bean.InformationRecycleView;

/**
 * Created by Developer on 19-09-2015.
 */

//recycler adapter for set data on navigation drawer of activity
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyviewHolder> {


    LayoutInflater inflater;
    Context context;
    List<InformationRecycleView> data = Collections.emptyList();


    public RecycleAdapter(Context context, List<InformationRecycleView> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;

    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);
        MyviewHolder holder = new MyviewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final MyviewHolder viewHolder, final int position) {
        InformationRecycleView current = data.get(position);
        viewHolder.title.setText(current.title);
        viewHolder.icon.setImageResource(current.iconId);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;
        LinearLayout container;

        public MyviewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.itemName);
            icon = (ImageView) itemView.findViewById(R.id.itemIcon);

            container = (LinearLayout) itemView.findViewById(R.id.containerMenu);



        }
    }


}
