package com.circle.circle.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.circle.circle.Adapter.MyGridAdapter;
import com.circle.circle.Bean.UserImgs;
import com.circle.circle.R;
import com.circle.circle.Utils.JsonUtil;
import com.circle.circle.Utils.UploadUtil;


import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublishActivity extends AppCompatActivity implements UploadUtil.OnUploadProcessListener {
    private List<UserImgs> mUI;
    private String userID;
    private GridView gridView1;              //网格显示缩略图
    public static final String KEY_PHOTO_PATH = "photo_path";
    private Uri imageUri;
    private List<String> imsUrls=new ArrayList<>();
    public static final int TO_SELECT_PHOTO = 3;
    /**获取到的图片路径*/
    private String picPath;
    private static String requestURL = "http://10.0.2.2:8080/fileUpload/file_upload";
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    protected static final int TO_UPLOAD_FILE = 1;
    private MyHandler handler=new MyHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_dynamic);
        iniTitle();
        SharedPreferences preferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        userID=preferences.getString("userID","");
        gridView1 = (GridView) findViewById(R.id.gridView1);
        mUI=new ArrayList<>();
        mUI.add(new UserImgs("drawable://"+R.drawable.gridview_addpic));
        gridView1.setAdapter(new MyGridAdapter(mUI,this));

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                if( mUI.size() == 7) { //第一张为默认图片
                    Toast.makeText(PublishActivity.this, "图片数6张已满", Toast.LENGTH_SHORT).show();
                }
                else if(position == 0) {
                    Intent intent = new Intent(PublishActivity.this,SelectPicActivity.class);
                    startActivityForResult(intent, TO_SELECT_PHOTO);
                    }
                else {
                    dialog(position);
                    //Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片",
                    //		Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    static class ReleaseRunnable implements Runnable {
        private List mlist;
        public ReleaseRunnable(List list){
            mlist=list;
        }
        @Override
        public void run() {
            JSONObject JO = JsonUtil.doPost("user_publishDynamic", mlist);
        }
    }
    private void toUploadFile()
    {
        String fileKey = "img";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(this);  //设置监听器监听上传状态
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", "11111");
        if(imsUrls.size()>0){
            for(int i=0;i<imsUrls.size();i++){
                uploadUtil.uploadFile( imsUrls.get(i),fileKey, requestURL,params);
            }
        }
    }

    @Override
    public void onUploadDone(int responseCode, String message) {
    }

    @Override
    public void onUploadProcess(int uploadSize) {
    }

    @Override
    public void initUpload(int fileSize) {
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            toUploadFile();
        }

    }
    private void iniTitle(){
        TextView back=(TextView)findViewById(R.id.back);
        back.setText("< 返回");
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        TextView title_text=findViewById(R.id.title_text);
        title_text.setText("发布动态");
        TextView title_edit=findViewById(R.id.title_edit);
        title_edit.setText("      发布");
        title_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText=findViewById(R.id.editText);
                String content=editText.getText().toString();
                handler.sendEmptyMessage(TO_UPLOAD_FILE);
                Toast.makeText(PublishActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                String imageUrls="";
                if(imsUrls.size()>0){
                    for(int i=0;i<imsUrls.size();i++){
                        imageUrls=imageUrls+"http://10.0.2.2:8080/fileUpload/upload/"+(new File(imsUrls.get(i))).getName()+";";
                    }
                }
                List list = new ArrayList();
                list.add(new BasicNameValuePair("dynamic.userID", userID));
                list.add(new BasicNameValuePair("dynamic.content", content));
                list.add(new BasicNameValuePair("dynamic.imageUrls",imageUrls ));
                list.add(new BasicNameValuePair("dynamic.likeAmount",0+""));
                Thread thread = new Thread(new ReleaseRunnable(list));
                thread.start();
                Intent intent=new Intent(PublishActivity.this,CircleActivity.class);
                intent.putExtra("content",content);
                intent.putExtra("imageUrls",imageUrls);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
        {
            picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
            String path=picPath;
            imsUrls.add(path);
            mUI.add(new UserImgs("file:///"+picPath.trim()));
            gridView1.setAdapter(new MyGridAdapter(mUI,this));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

/*    //刷新图片
    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(pathImage)){
            Bitmap addbmp= BitmapFactory.decodeFile(pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this,
                    imageItem, R.layout.publish_dynamic,
                    new String[] { "itemImage"}, new int[] { R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if(view instanceof ImageView && data instanceof Bitmap){
                        ImageView i = (ImageView)view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView1.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }
    }*/

    /*
     * Dialog对话框提示用户删除操作
     * position为删除图片位置
     */
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PublishActivity.this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mUI.remove(position);
                imsUrls.remove(position-1);
                gridView1.setAdapter(new MyGridAdapter(mUI,PublishActivity.this));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
