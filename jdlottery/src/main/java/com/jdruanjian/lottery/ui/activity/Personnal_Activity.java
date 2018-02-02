package com.jdruanjian.lottery.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdruanjian.lottery.BaseActivity;
import com.jdruanjian.lottery.BaseApplication;
import com.jdruanjian.lottery.Constants;
import com.jdruanjian.lottery.MainActivity;
import com.jdruanjian.lottery.R;
import com.jdruanjian.lottery.model.entity.PhotoURL;
import com.jdruanjian.lottery.model.net.BasicResponse;
import com.jdruanjian.lottery.model.net.UsersAPI;
import com.jdruanjian.lottery.utils.NetworkUtils;
import com.jdruanjian.lottery.utils.PeUtils;
import com.jdruanjian.lottery.utils.PrefUtils;
import com.jdruanjian.lottery.view.ImageViewCanvas;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by 龙龙 on 2017/8/31.
 * <p>
 * 个人中心
 */

public class Personnal_Activity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.rl_lookinfo)   //保存信息按钮
            RelativeLayout rLlookinfo;
    @BindView(R.id.iv_headshot)    //头像
            ImageViewCanvas ivHeadshot;
    @BindView(R.id.llperfect_info)    //个人资料修改
            RelativeLayout perfectInfo;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.llperfect_aliaply)
    RelativeLayout llperfectAliaply;
    private Dialog dialog;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private PhotoURL model;
    private String uid;
    private String uids;
    public Bitmap iamgeView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        ButterKnife.bind(this);
        btnBack.setVisibility(View.VISIBLE);
        ivHeadshot = (ImageViewCanvas) findViewById(R.id.iv_headshot);
        setTitlebar("个人中心");
        setBar();
        doRequest();
    }

    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(Personnal_Activity.this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        UsersAPI api = new UsersAPI(Personnal_Activity.this, PrefUtils.getString(getApplication(), "uid", null), new BasicResponse.RequestListener() {

            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    model = (PhotoURL) response.model;
                    if (PrefUtils.getString(getApplication(), "uid", null) != null) {
                        ImageLoader.getInstance().displayImage(model.datas, ivHeadshot, BaseApplication.getInst().getDisplayImageOptions());
                    } else {
                        ImageLoader.getInstance().displayImage(String.valueOf(R.drawable.photo), ivHeadshot, BaseApplication.getInst().getDisplayImageOptions());
                    }

                } else {
                    model = (PhotoURL) response.model;
                    //      ImageLoader.getInstance().displayImage(String.valueOf(R.drawable.photo), ivHeadshot, BaseApplication.getInst().getDisplayImageOptions());
                }
            }
        });
        //   api.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        api.executeRequest(0);
    }


    @OnClick({R.id.rl_lookinfo, R.id.iv_headshot, R.id.llperfect_info, R.id.btn_back,R.id.llperfect_aliaply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_lookinfo:
                if (PrefUtils.getString(getApplication(), "uid", null) != null) {
                    Intent intent_lookinfo = new Intent(this, PersonInfo_Activity.class);
                    startActivity(intent_lookinfo);
                } else {
                    Intent intent_login = new Intent(this, LoginActivity.class);
                    startActivity(intent_login);
                }
                break;
            case R.id.llperfect_aliaply:
                if (PrefUtils.getString(getApplication(), "uid", null) != null) {
                    Intent intent_bindalipaly = new Intent(this, BindAliPlayNum_Acitivity.class);
                    startActivity(intent_bindalipaly);
                } else {
                    Intent intent_login = new Intent(this, LoginActivity.class);
                    startActivity(intent_login);
                }
                break;
            case R.id.iv_headshot:
                if (PrefUtils.getString(getApplication(), "uid", null) != null) {
                    showdialog();
                } else {
                    Intent intent_login = new Intent(this, LoginActivity.class);
                    startActivity(intent_login);
                }
                break;
            case R.id.llperfect_info:
                if (PrefUtils.getString(getApplication(), "uid", null) != null) {
                    Intent intent_info = new Intent(this, BindBankNum_Acitivity.class);
                    startActivity(intent_info);
                } else {
                    Intent intent_login = new Intent(this, LoginActivity.class);
                    startActivity(intent_login);
                }

                break;
            case R.id.btn_back:
                Intent intent = new Intent(this, MainActivity.class);
                setResult(1, intent);
                finish();
                break;
        }
    }


    private void showdialog() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_add_picture, null);
        TextView tv_camera = (TextView) localView.findViewById(R.id.tv_camera);
        TextView tv_gallery = (TextView) localView.findViewById(R.id.tv_gallery);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置全屏
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
           /*     // 拍照
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);*/

                if (ContextCompat.checkSelfPermission(Personnal_Activity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Personnal_Activity.this, new String[]{Manifest.permission.CAMERA}, TAKE_PICTURE);
                } else {
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                    // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    startActivityForResult(openCameraIntent, TAKE_PICTURE);
                }
                }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 从系统相册选取照片
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case CHOOSE_PICTURE:

                    if (data == null) {
                        return;
                    } else {
                        cutImage(data.getData());

                    }
                    break;
                case TAKE_PICTURE:

                    cutImage(tempUri);

                    break;
                case CROP_SMALL_PICTURE:

                    if (data != null) {
                        setImageToView(data);
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void cutImage(Uri uri){
        if(uri == null){
            Log.i("alanjet", "The uri is not exist");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置剪切
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);


        startActivityForResult(intent, CROP_SMALL_PICTURE);

    }
    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = PeUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            ivHeadshot.setImageBitmap(photo);
            //        iamgeView = photo;
            upload(photo);
        }
    }

    /**
     * 上传图片到服务器
     */
    @SuppressLint("StaticFieldLeak")
    private void upload(final Bitmap bitmap) {

        new AsyncTask<Bitmap, Void, String>() {
            ProgressDialog progressDialog;

            protected void onPreExecute() {
                progressDialog = new ProgressDialog(Personnal_Activity.this);
                progressDialog.setCanceledOnTouchOutside(false);
                //            progressDialog.setMessage("正在和服务器通信中……");
                progressDialog.show();
            }

            ;

            @Override
            protected String doInBackground(Bitmap... params) {
                try {
                    //此处没有判断是否有sd卡
                    File dirFile = new File("/mnt/sdcard/android/cache");
                    if (!dirFile.exists()) {
                        dirFile.mkdirs();
                    }
                    File file = new File(dirFile, "photo.png");
                    if (params[0].compress(Bitmap.CompressFormat.PNG, 50, new FileOutputStream(file))) {
                        System.out.println("保存图片成功");
                        HttpClient client = new DefaultHttpClient();

                        HttpPost httpPost = new HttpPost(Constants.IMG_URL);
                        MultipartEntity entity = new MultipartEntity();
                        // 通过RSA加密后的用户名
                        String miUserName = PrefUtils.getString(getApplication(), "uid", null);
                        System.out.println("UIUIUIUIABABAB------" + miUserName);
                        entity.addPart("uid", new StringBody(miUserName));
                        //       entity.addPart("ext", new StringBody("png"));
                        entity.addPart("files", new FileBody(file));
                        httpPost.setEntity(entity);
                        HttpResponse response = client.execute(httpPost);
                        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                            InputStream in = response.getEntity().getContent();
                            byte[] buffer = new byte[1024];
                            int len = 0;
                            StringBuffer stringBuffer = new StringBuffer();
                            while ((len = in.read(buffer)) != -1) {
                                stringBuffer.append(new String(buffer, 0, len,
                                        "utf-8"));
                            }
                            System.out.println("stringBuffer = " + stringBuffer.toString().trim());
                            file.delete();//删除文件
                            return stringBuffer.toString().trim();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(String result) {
                progressDialog.dismiss();
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (null != jsonObject && "1".equals(jsonObject.opt("result"))) {// 上传成功
                            ivHeadshot.setImageBitmap(bitmap);
                            Toast.makeText(Personnal_Activity.this, "上传成功",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Personnal_Activity.this, "上传失败",
                            Toast.LENGTH_SHORT).show();
                }
            }

            ;
        }.execute(bitmap);
    }

}

