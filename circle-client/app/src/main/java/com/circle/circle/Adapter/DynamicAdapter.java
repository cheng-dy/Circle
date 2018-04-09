package com.circle.circle.Adapter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.circle.circle.Activity.CircleActivity;
import com.circle.circle.Activity.LoginActivity;
import com.circle.circle.Bean.Comment;
import com.circle.circle.Bean.Dynamic;
import com.circle.circle.Bean.DynamicDetail;
import com.circle.circle.Bean.User;
import com.circle.circle.Bean.UserImgs;
import com.circle.circle.R;
import com.circle.circle.Utils.JsonUtil;
import com.circle.circle.widget.NoScrollGridView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;


import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DynamicAdapter extends BaseAdapter {
    private DynamicAdapter dynamicAdapter=this;
    private List<DynamicDetail> mList;
    private Context mContext;
    private CircleActivity mactivity;
    private PopupWindow popWindow;
    private InputMethodManager imm;
    private MyHandler mHandler;
    private User muser;
    public DynamicAdapter(Context _context,User user,CircleActivity activity) {
        this.mContext = _context;
        this.muser=user;
        this.mactivity=activity;
    }

    public void setData(List<DynamicDetail> _list) {
        this.mList = _list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public DynamicDetail getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DynamicAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new DynamicAdapter.ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.dynamic, parent, false);
            holder.gridView = convertView
                    .findViewById(R.id.gridView);
            holder.comment=convertView.findViewById(R.id.comment);
            convertView.setTag(holder);
        } else
            holder = (DynamicAdapter.ViewHolder) convertView.getTag();

        final DynamicDetail dd = getItem(position);
        final Dynamic dynamic=dd.getDynamic();
        final List<Comment> comments=dd.getComments();
        List<UserImgs> imgs=new ArrayList<>();
        if(dynamic.getImageUrls()!=null&&!dynamic.getImageUrls().equals("")){
            String[] imageUrls=dynamic.getImageUrls().split(";");
            for(int i=0;i<imageUrls.length;i++){
                imgs.add(new UserImgs(imageUrls[i]));
            }
        }
        if (mList != null && mList.size() > 0) {
            ImageView delete=convertView.findViewById(R.id.delete);
            if(dynamic.getUserID()==muser.getUserID()||muser.getCharacters().equals("master")){
                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Thread thread=new Thread(new DelDynamicRunable(dynamic.getDynamicID()+""));
                        thread.start();
                        mList.remove(position);
                        dynamicAdapter.notifyDataSetChanged();
                        Toast.makeText(mContext,"动态删除成功",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else delete.setVisibility(View.INVISIBLE);
            TextView content=convertView.findViewById(R.id.content);
            content.setText(dynamic.getContent());
            TextView userName=convertView.findViewById(R.id.user_name);
            userName.setText(dynamic.getUserName());
            ImageView headImage=convertView.findViewById(R.id.head_image);
            ImageLoader.getInstance().displayImage(dynamic.getHeaderImg(), headImage);
            final ListView commentsList=convertView.findViewById(R.id.comments);
            CommentAdapter commentAdapter=new CommentAdapter(mContext,comments);
            commentsList.setAdapter(commentAdapter);
            setListViewHeightBasedOnChildren(commentsList,commentAdapter);

            holder.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Comment comment=new Comment();
                    comment=showPopup(holder.comment,muser,dynamic,comment);
                    comments.add(comment);
                    CommentAdapter commentAdapter=new CommentAdapter(mContext,comments);
                    commentsList.setAdapter(commentAdapter);
                    popupInputMethodWindow(mContext);
                }
            });
            final ImageView like=convertView.findViewById(R.id.like);
            like.setImageResource(R.drawable.dilike);
            final TextView amount=convertView.findViewById(R.id.amount);
            amount.setText(dynamic.getLikeAmount()+"");
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        like.setImageResource(R.drawable.like);
                        int amounts=dynamic.getLikeAmount()+1;
                        dynamic.setLikeAmount(amounts);
                        amount.setText(amounts+"");
                        List list = new ArrayList();
                        list.add(new BasicNameValuePair("dynamic.dynamicID", dynamic.getDynamicID()+""));
                        Thread thread=new Thread(new PraiseRunnable(list));
                        thread.start();
                }
            });
            holder.gridView.setVisibility(View.VISIBLE);
            holder.gridView.setAdapter(new MyGridAdapter(imgs,
                    mContext));
            holder.gridView
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            //imageBrower(position,bean.urls);
                        }
                    });
        }
        return convertView;
    }
    public void setListViewHeightBasedOnChildren(ListView commentsList,CommentAdapter commentAdapter){
        int totalHeight = 0;
        for(int i=0;i<commentAdapter.getCount();i++){
            View listItem=commentAdapter.getView(i,null,commentsList);
            listItem.measure(0,0);
            totalHeight+=listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params=commentsList.getLayoutParams();
        params.height=totalHeight+(commentsList.getDividerHeight()*(commentAdapter.getCount()-1));
        commentsList.setLayoutParams(params);
    }
    /*public void toLike(){
        ImageView like=(ImageView)findViewById(R.id.like);
        like.setImageResource(R.drawable.dilike);
    }*/
    public class ViewHolder {
        NoScrollGridView gridView;
        ImageView comment;
    }

    private Comment showPopup(View parent, User user, final Dynamic dynamic, final Comment comment){//定义PopupWindow
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.comment,null);
        if (popWindow == null) {
            // 创建一个PopuWidow对象
            popWindow = new PopupWindow(view,LinearLayout.LayoutParams.FILL_PARENT,100,true);
        }
//popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //软键盘不会挡着popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置菜单显示的位置
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //监听菜单的关闭事件
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        //监听触屏事件
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                return false;
            }
        });
        final EditText comment_content=view.findViewById(R.id.comment_content);
        Button comment_release=view.findViewById(R.id.comment_release);
        final User muser=user;
        final Dynamic mdynamic=dynamic;
        comment_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=comment_content.getText().toString();
                comment.setUserID(muser.getUserID());
                comment.setContent(content);
                comment.setUserName(muser.getUsername());
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("content", content);
                data.putString("userID",muser.getUserID()+"");
                data.putString("dynamicID",mdynamic.getDynamicID()+"");
                msg.setData(data);
                msg.what = 1;
                mHandler=new MyHandler(mContext,popWindow);
                mHandler.sendMessage(msg);
                popWindow.dismiss();
            }
        });
        return comment;
    }
    private static class MyHandler extends Handler {
        Context mContext;
        PopupWindow mpopWindow;

        public MyHandler(Context context,PopupWindow popWindow) {
            mContext = context;
            mpopWindow=popWindow;
        }

        public MyHandler(Context context) {
            mContext = context;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle bd = msg.getData();
                    Toast.makeText(mContext, bd.getString("content"), Toast.LENGTH_SHORT).show();
                    List list = new ArrayList();
                    list.add(new BasicNameValuePair("comment.content", bd.getString("content")));
                    list.add(new BasicNameValuePair("comment.userID", bd.getString("userID")));
                    list.add(new BasicNameValuePair("comment.dynamicID", bd.getString("dynamicID")));
                    Thread thread = new Thread(new CommentRunnable(list,mContext));
                    thread.start();
                    break;
            }
        }
    }

    static class CommentRunnable implements Runnable {
        private List mlist;
        private Context mcontext;
        public CommentRunnable(List list,Context context){
            mlist=list;
            mcontext=context;
        }
        @Override
        public void run() {
            JSONObject JO = JsonUtil.doPost("user_publishComment", mlist);
            //发送数据到 handler
            try {
                String json = JO.getString("result");
                if (json=="success") {
                    Toast.makeText(mcontext, "评论成功",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class DelDynamicRunable implements Runnable {
        String dynamicID;
        public DelDynamicRunable(String dynamicID){
            this.dynamicID=dynamicID;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            List list = new ArrayList();
            list.add(new BasicNameValuePair("dynamic.dynamicID", dynamicID));
            JsonUtil.doPost("user_delDynamic", list);
        }
    }
    static class PraiseRunnable implements Runnable {
        private List mlist;
        public PraiseRunnable(List list){
            mlist=list;
        }
        @Override
        public void run() {
            JSONObject JO = JsonUtil.doPost("user_addPraise", mlist);
        }
    }

    private void popupInputMethodWindow(Context context) {
        MyHandler handler=new MyHandler(context);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imm = (InputMethodManager) mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 0);
    }
}
