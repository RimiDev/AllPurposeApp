package cs.dawson.dawsonelectriccurrents.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cs.dawson.dawsonelectriccurrents.R;

/**
 * Created by maximelacasse on 2017-11-28.
 */

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> countryNames;
    LayoutInflater inflter;

    public SpinnerAdapter(Context applicationContext, ArrayList<String> countryNames) {
        this.context = applicationContext;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.country_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(countryNames.get(i));
        return view;
    }


}
