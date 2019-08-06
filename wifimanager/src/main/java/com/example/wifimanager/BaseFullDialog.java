package com.example.wifimanager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BaseFullDialog extends Dialog {

    private Context context;
    private int height;
    private int width;
    private View view;
    private boolean cancelTouchout;


    private BaseFullDialog(Builder mBuilder) {
        super(mBuilder.context);
        context = mBuilder.context;
        cancelTouchout = mBuilder.cancelTouchout;
        height = mBuilder.height;
        width = mBuilder.width;
        view = mBuilder.view;
    }

    private BaseFullDialog(Builder mBuilder, int themeResId) {
        super(mBuilder.context, themeResId);
        context = mBuilder.context;
        cancelTouchout = mBuilder.cancelTouchout;
        height = mBuilder.height;
        width = mBuilder.width;
        view = mBuilder.view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setCanceledOnTouchOutside(cancelTouchout);
        initWindow();
    }

    private void initWindow() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) context.getResources()
                .getDimension(R.dimen.dlg_common_width);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }


    public static final class Builder {
        private Context context;
        private int height;
        private int width;
        private int resStyle = -1;
        private View view;
        private boolean cancelTouchout;
        private EditText medtext;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * @param resview 设置dialog的视图
         * @return
         */
        public Builder setlayout(int resview) {
            view = LayoutInflater.from(context).inflate(resview, null);
            return this;
        }

        /**
         * @param height 设置宽高 可不设
         * @return
         */
        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public EditText getEditText(int viewid) {
            return view.findViewById(viewid);
        }

        /**
         * @param viewid
         * @return 获得prossess
         */
        public ProgressBar getProssbar(int viewid) {
            return view.findViewById(viewid);
        }


        /**
         * @param viewid
         * @return 获得textview
         */
        public TextView getTextView(int viewid) {
            return view.findViewById(viewid);
        }

        /**
         * @param viewid 获得imageview
         * @return
         */
        public ImageView getImageView(int viewid) {
            return view.findViewById(viewid);
        }

        public Button getButton(int viewid) {
            return view.findViewById(viewid);
        }



        /**
         * @param viewid
         * @param listener 添加点击时间
         * @return
         */
        public Builder addViewClick(int viewid, View.OnClickListener listener) {
            view.findViewById(viewid).setOnClickListener(listener);
            return this;
        }

        /**
         * @param val 取消点击
         * @return
         */
        public Builder cancelTouchout(boolean val) {
            cancelTouchout = val;
            return this;
        }

        /**
         *
         */
        public Builder setText(int viewid, String word) {
            TextView tv1 = view.findViewById(viewid);
            tv1.setText(word);
            return this;
        }

        /**
         * @param resStyle 指定style
         * @return
         */
        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public BaseFullDialog build() {
            if (resStyle != -1) {
                return new BaseFullDialog(this, resStyle);
            } else {
                return new BaseFullDialog(this);
            }
        }

    }

    /**
     * 调用方法
     */
//    BaseFullDialog.Builder mbuilder = new BaseFullDialog.Builder(MainActivity.this);
//    Dialog dialog = mbuilder.setlayout(R.layout.dialog)
//            .style(R.style.Dialog)
//            .build();
//                dialog.show();
}
