package tekwin.org.catalog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tekwin.org.model.Flower;

/**
 * Created by adamdebbagh on 3/3/15.
 */
public class FlowerAdapter extends ArrayAdapter<Flower> {

    private Context context;
    private List<Flower> flowerList;


    public FlowerAdapter(Context context, int resource, List<Flower> items) {
        super(context, resource, items);
        this.context = context;
        this.flowerList = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView flowerImage;
        TextView floweTitle;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Flower flower = flowerList.get(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_flower, null);
            holder = new ViewHolder();


            //Display flower name in the textView widget

            holder.floweTitle = (TextView) convertView.findViewById(R.id.floweTitle);
            holder.flowerImage = (ImageView) convertView.findViewById(R.id.flowerImage);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.floweTitle.setText(flower.getName());
            //holder.flowerImage.setImageResource(flower.getPhoto());
            return convertView;
    }
}
