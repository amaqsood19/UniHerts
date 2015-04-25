package asmirza.uniherts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsListAdapter extends ArrayAdapter<NewsPost> {
    private List<NewsPost> postList;
    private Context context;


    public NewsListAdapter(List<NewsPost> postList, Context ctx) {
        super(ctx, R.layout.news_list_item, postList);
        this.postList = postList;
        this.context = ctx;
    }
    public int getCount() {
        return postList.size();
    }
    public NewsPost getItem(int position) {
        return postList.get(position);
    }
    public long getItemId(int position) {
        return postList.get(position).hashCode();
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        NewsPostHolder holder = new NewsPostHolder();
        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.news_list_item, null);
            // Now we can fill the layout with the right values

            TextView headlineTv = (TextView) v.findViewById(R.id.news_headline);
            ImageView picIv = (ImageView) v.findViewById(R.id.news_image);
            TextView picNameTv = (TextView) v.findViewById(R.id.news_picture_name);
            TextView picDescTv = (TextView) v.findViewById(R.id.news_picture_description);
            TextView newDateTimeTv = (TextView) v.findViewById(R.id.news_date_time);

            holder.headlineTv = headlineTv;
            holder.picIv = picIv;
            holder.picNameTv = picNameTv;
            holder.picDescTv = picDescTv;
            holder.newDateTimeTv = newDateTimeTv;


            v.setTag(holder);
        }

        holder = (NewsPostHolder) v.getTag();
        NewsPost p = postList.get(position);
        holder.headlineTv.setText(p.getMessage());
        if (p.getPicture() != null) {
            holder.picIv.setImageBitmap(p.getPicture());
        }
        holder.picNameTv.setText(p.getPictureName());
        holder.picDescTv.setText(p.getPictureDescription());
        holder.newDateTimeTv.setText(p.getDateTimeCreated());
        return v;
    }


    /* *********************************
    * We use the holder pattern
    * It makes the view faster and avoid finding the component
    * **********************************/
    private static class NewsPostHolder {
        public TextView headlineTv;
        public ImageView picIv;
        public TextView picNameTv;
        public TextView picDescTv;
        public TextView newDateTimeTv;
    }

}