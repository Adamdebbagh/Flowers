package tekwin.org.catalog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import tekwin.org.model.Flower;

/**
 * Created by adamdebbagh on 3/3/15.
 */
public class FlowerAdapter extends ArrayAdapter<Flower> {

    private Context context;
    private List<Flower> flowerList;
    ViewHolder holder = null;


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

        Flower flower = flowerList.get(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_flower, null);
            holder = new ViewHolder();


            //Display flower name in the textView widget

            holder.floweTitle = (TextView) convertView.findViewById(R.id.floweTitle);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.floweTitle.setText(flower.getName());

        if (flower.getBitmap() !=null){

            holder.flowerImage = (ImageView) convertView.findViewById(R.id.flowerImage);
            holder.flowerImage.setImageBitmap(flower.getBitmap());

        }else {
            FlowerAndView container = new FlowerAndView();
            container.flower = flower;
            container.view = convertView;

            ImageLoader loader = new ImageLoader();
            loader.execute(container);

        }

            return convertView;
    }

    class FlowerAndView{
        public Flower flower;
        public View view;
        public Bitmap bitmap;
    }
    private class ImageLoader extends AsyncTask<FlowerAndView,Void,FlowerAndView> {


        @Override
        protected FlowerAndView doInBackground(FlowerAndView... params) {
            FlowerAndView container = params[0];
            Flower flower = container.flower;

            try{
                String imageUrl = MainActivity.PHOTOS_BASE_URL + flower.getPhoto();
                InputStream in = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                flower.setBitmap(bitmap);
                in.close();
                container.bitmap = bitmap;
                return container;
            }catch (Exception e){
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(FlowerAndView flowerAndView) {
            super.onPostExecute(flowerAndView);
            holder.flowerImage = (ImageView) flowerAndView.view.findViewById(R.id.flowerImage);
            holder.flowerImage.setImageBitmap(flowerAndView.bitmap);
            flowerAndView.flower.setBitmap(flowerAndView.bitmap);

        }
    }
}
