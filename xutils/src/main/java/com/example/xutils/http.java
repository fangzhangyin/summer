package com.example.xutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpResponse;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_http)
public class http extends AppCompatActivity {

    @ViewInject(R.id.gp)
    private Button gp;
    @ViewInject(R.id.download)
    private Button download;
    @ViewInject(R.id.upload)
    private Button upload;

    @ViewInject(R.id.text1)
    private TextView textView;

    @ViewInject(R.id.img)
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }
    @Event(value = {R.id.gp,R.id.download,R.id.upload},type = View.OnClickListener.class)
    private void setGp(View view){
        switch (view.getId()){
            case R.id.gp:
                gethttp();
                break;
            case R.id.download:
                ImageOptions options = new ImageOptions.Builder().setFadeIn(true).build(); //淡入效果
                //ImageOptions.Builder()的一些其他属性：

                //.setCircular(true) //设置图片显示为圆形
                //.setSquare(true) //设置图片显示为正方形
                //setCrop(true).setSize(200,200) //设置大小
                //.setAnimation(animation) //设置动画
                //.setFailureDrawable(Drawable failureDrawable) //设置加载失败的动画
                //.setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                //.setLoadingDrawableId(int loadingDrawable) //以资源id设置加载中的动画
                //.setIgnoreGif(false) //忽略Gif图片
                //.setParamsBuilder(ParamsBuilder paramsBuilder) //在网络请求中添加一些参数
                //.setRaduis(int raduis) //设置拐角弧度
                //.setUseMemCache(true) //设置使用MemCache，默认true
                x.image().bind(img,"http://img4.imgtn.bdimg.com/it/u=3182769660,1810895318&fm=23&gp=0.jpg",options);
                break;
            case R.id.upload:
                ImageOptions option = new ImageOptions.Builder().setFadeIn(true).build(); //淡入效果
                break;
        }
    }
    private void gethttp() {
        RequestParams params=new RequestParams("https://www.baidu.com/");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                textView.setText(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                textView.setText(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }



}

