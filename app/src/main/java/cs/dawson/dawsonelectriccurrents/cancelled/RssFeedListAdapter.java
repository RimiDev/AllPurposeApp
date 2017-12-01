package cs.dawson.dawsonelectriccurrents.cancelled;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cs.dawson.dawsonelectriccurrents.R;
import cs.dawson.dawsonelectriccurrents.beans.CancelledClass;

public class RssFeedListAdapter extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder>
{
    private List<CancelledClass> cancelledClasses;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder
    {
        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }
    }

    public RssFeedListAdapter(List<CancelledClass> cancelledClasses)
    {
        this.cancelledClasses = cancelledClasses;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cancelled_class, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, int position) {
        final CancelledClass rssFeedModel = cancelledClasses.get(position);
        ((TextView)holder.rssFeedView.findViewById(R.id.cancelledClassView)).setText(rssFeedModel.getTitle());
    }

    @Override
    public int getItemCount()
    {
        return cancelledClasses.size();
    }
}
