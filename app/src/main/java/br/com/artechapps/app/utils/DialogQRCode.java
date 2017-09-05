package br.com.artechapps.app.utils;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import br.com.artechapps.app.R;

/**
 * Created by acstapassoli on 04/09/17.
 */

public class DialogQRCode extends Dialog implements
        android.view.View.OnClickListener {

    public AppCompatActivity c;
    public Dialog d;
    public ImageView qrcode, close;
    public Bitmap mBitmap;

    public DialogQRCode(AppCompatActivity a, Bitmap bitmap) {
        super(a);
        mBitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_qrcode);
        qrcode = (ImageView) findViewById(R.id.image_qrcode);
        qrcode.setImageBitmap(mBitmap);
        close = (ImageView) findViewById(R.id.image_close);
        qrcode.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_close:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
