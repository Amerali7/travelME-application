package com.abbas.travelMe.helper;

//REFERENCES
//YouTube. (2018). Listview image with text in android. [online] Available at: https://www.youtube.com/watch?v=_YF6ocdPaBg .
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abbas.travelMe.R;
import com.abbas.travelMe.sql.DatabaseHelper;


/**
 * Created by abbas on 27/03/2018.
 */

public class customlistview extends ArrayAdapter<String> {


    private DatabaseHelper db;

   private String [] Placename;
   private String [] desc;
    private Integer[] imgid;
    private Activity context;
    customButtonListener customListner;



    public customlistview(Activity context, String [] Placename, String [] desc,Integer[] imgid) {
        super(context, R.layout.layoutlists, Placename);


        this.context=context;
        this.Placename=Placename;
        this.desc=desc;
        this.imgid=imgid;



    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    View r=convertView;
    ViewHolder viewHolder=null;
    if(r==null) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        r=layoutInflater.inflate(R.layout.layoutlists,null,true);
        viewHolder=new ViewHolder(r);
        r.setTag(viewHolder);
    }
    else {
    viewHolder= (ViewHolder) r.getTag();


    }

   // viewHolder.imageView.setImageResource(imgid[position]);
    viewHolder.textView.setText(Placename[position]);
    viewHolder.textView2.setText(desc[position]);



        viewHolder.like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position,"like");
                }

            }
        });
        return r;
    }

    public interface customButtonListener {
        public void onButtonClickListner(int position,String value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }


    class ViewHolder {

                TextView textView;
                TextView textView2;
                ImageView imageView;
                Button like;
                ViewHolder (View v) {
                    textView=(TextView) v.findViewById(R.id.textView);
                    textView2=(TextView) v.findViewById(R.id.textView2);
                    imageView= (ImageView) v.findViewById(R.id.imageView2);
                    like = (Button) v.findViewById(R.id.Like);

                }
        }

    }

