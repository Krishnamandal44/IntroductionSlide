package enc.test.introductionslide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroducingActivity extends AppCompatActivity {

    private Button startButton;
    private LinearLayout dotsLayout;
    private ViewPager viewPager;
    private CustomAdapter adapter;
    private int[] images = {R.drawable.screen1,R.drawable.screen2,R.drawable.screen3,R.drawable.screen4,R.drawable.screen5,R.drawable.screen6};;
    private TextView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducing);
        startButton = (Button) findViewById(R.id.start_button_id);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
//        point = (ImageView) findViewById(R.id.point_id);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new CustomAdapter(this);
        viewPager.setAdapter(adapter);
        addBottomDots(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                if (position == 5){
                    startButton.setText("GET STARTED");
//                    point.setImageResource(R.drawable.point6);
                    startButton.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startButton.setBackground(getResources().getDrawable(R.drawable.button_border, null));
                    }else {
                        startButton.setBackground(getResources().getDrawable(R.drawable.button_border));
                    }
                }else {
                    startButton.setText("SKIP");if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        startButton.setTextColor(getResources().getColor(R.color.colorText, null));
                    }else {
                        startButton.setTextColor(getResources().getColor(R.color.colorText));
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startButton.setBackground(getResources().getDrawable(R.drawable.button_border_gray, null));
                    }else {
                        startButton.setBackground(getResources().getDrawable(R.drawable.button_border_gray));
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private class CustomAdapter extends PagerAdapter{

        private Context ctx;
        private LayoutInflater inflater;

        CustomAdapter(Context ctx){
            this.ctx = ctx;
        }
        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view==object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=inflater.inflate(R.layout.introducing_screens,container,false);
            ImageView img =(ImageView)v.findViewById(R.id.imageView);
            img.setImageResource(images[position]);


            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(IntroducingActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            container.refreshDrawableState();
        }
    }
    private void addBottomDots(int currentPage) {
        dots = new TextView[images.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(45);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

}
