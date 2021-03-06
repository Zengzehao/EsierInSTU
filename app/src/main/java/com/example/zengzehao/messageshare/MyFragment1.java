package com.example.zengzehao.messageshare;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.zhouwei.library.CustomPopWindow;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.example.zengzehao.messageshare.tools.ConutDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class MyFragment1 extends Fragment {

    /*
    private List<Note> mData = null;
    private Context mContext;
    private NoteAdapter mAdapter = null;
    private ListView list_note;
    */
   // private CustomPopWindow mCustomPopWindow;
   // private CustomPopWindow mListPopWindow;
    private ListView listView;

    private ImageButton top_personinfo;
    private ImageButton top_add;

    private TextView menu1;
    private TextView menu2;

    List<Tab01ListView> list=new ArrayList<Tab01ListView>();
    public MyFragment1() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab01, container, false);

        listView = (ListView)view.findViewById(R.id.tab01_listview);

        /*
        //允许从主线程请求网络服务
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        */
        //final List<Map<String, Object>> list= new MyFragment1.getData().doInBackground();
        //
        // final  List<Tab01ListView> list = new MyFragment1.getData2().doInBackground();
        //System.out.println("size:"+list.size());
        //listView.setAdapter(new Tab01ListViewAdapter(getActivity(), list));
        getData2 getdata = new getData2();
        getdata.execute();
      listView.setAdapter(new Tab01ListViewAdapter2(list,getActivity()));
        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        //txt_content.setText("第一个Fragment");
       // ListView list_note = (ListView)view.findViewById(R.id.list_view);
        /*
        mData = new LinkedList<Note>();
        mData.add(new Note("14zhzeng","10分钟前","我要出一把吉他，价钱200元,联系方式66877.我要出一把吉他，价钱200元,联系方式66877.","阅读量 3",R.drawable.touxiang));
        mData.add(new Note("14zhzeng","2小时前","我要出一把吉他，价钱200元","阅读量 3",R.drawable.touxiang));
        mData.add(new Note("14zhzeng","3小时前","我要出一把吉他，价钱200元.我要出一把吉他，价钱200元,联系方式66877.","阅读量 3",R.drawable.touxiang));
        mData.add(new Note("14zhzeng","11-17 11:30","我要出一把吉他，价钱200元.我要出一把吉他，价钱200元,联系方式66877.","阅读量 3",R.drawable.touxiang));
        mData.add(new Note("14zhzeng","11-17 10:00","我要出一把吉他，价钱200元","aaaaa",R.drawable.touxiang));
        mAdapter = new NoteAdapter(mContext,(LinkedList<Note>) mData);

        list_note.setAdapter(mAdapter);
        */
        //String[] str = {"Hello","Hello2","Hell3"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,str);
        //list_note.setAdapter(adapter);

        top_personinfo = (ImageButton) view.findViewById(R.id.top_personinfo);
        top_add = (ImageButton) view.findViewById(R.id.top_add);
        top_personinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"你点击了个人中心",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),PersonInfoActivity.class);
                startActivity(intent);
            }
        });

        top_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"你点击了发布按钮",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getActivity(),WorkAddActivity.class);

                //startActivity(intent);
                //System.out.println("跳转到发布界面");
               // showPopMenu();
                initPopWindow(view);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView listView = (ListView) adapterView;
                Tab01ListView tab01ListView = (Tab01ListView) listView.getItemAtPosition(i);
                String objectId = tab01ListView.getObjectId();
                String contact = tab01ListView.getContact();
                final String time = tab01ListView.getTime();
                //data.get(i).get("username");
                final AVObject todo = AVObject.createWithoutData("Trade",objectId);

                todo.fetchInBackground(new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject object, AVException e) {
                        int clicks_number = (int)object.get("clicks");
                        System.out.println("clicks"+clicks_number);
                        clicks_number++;
                        todo.put("clicks",clicks_number);
                        todo.saveInBackground();
                    }
                });
                Intent intent = new Intent(getActivity(),Tab01ListViewDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("objectId",objectId);
                bundle.putString("time",time);
                bundle.putString("contact",contact);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getActivity(),"你的id"+objectId,Toast.LENGTH_SHORT).show();
            }
        });
        Log.e("HEHE", "1日狗");
        return view;
    }

    private void initPopWindow(View v) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_menu, null, false);
        menu1 = (TextView) view.findViewById(R.id.menu1);
        menu2 = (TextView) view.findViewById(R.id.menu2);

        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v,-5,25);

        //设置popupWindow里的按钮的事件
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "你点击了嘻嘻~", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),SellAddActivity.class);
                startActivity(intent);

            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "你点击了呵呵~", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),BuyAddAcivity.class);
                startActivity(intent);
                popWindow.dismiss();
            }
        });
    }
    /*
    public class getData extends AsyncTask<Void,Void,List<Map<String,Object>>> {





        @Override
        protected List<Map<String,Object>> doInBackground(Void...voids) {

            List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();

            String cql = "select * from Trade";
            try {
                AVCloudQueryResult result = AVQuery.doCloudQuery(cql);
                System.out.println(result);
                List<AVObject> results = (List<AVObject>) result.getResults();
                for (int i = results.size()-1;i>=0;i--){
                    Map<String, Object> map=new HashMap<String, Object>();

                    map.put("portrait",R.drawable.touxiang);
//                    System.out.println("对象"+results.get(i).getAVUser("userName"));
                    //                   System.out.println(results.get(i).getAVUser("userName").get("username"));



                    map.put("username",results.get(i).get("userName"));
                    Date date = new Date();
                    String time = ConutDate.conutTwoDate(date,(Date) results.get(i).get("createdAt"));
                    map.put("time","发布于 "+time);
                    map.put("type",results.get(i).get("type"));
                    map.put("title",results.get(i).get("title"));
                    map.put("contact",results.get(i).get("contactInfo"));
                    map.put("clicks_number",results.get(i).get("clicks").toString());
                    map.put("btn_text",results.get(i).get("btn_text"));
                    list.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
    }
    */

    public class getData2 extends AsyncTask<Void,Void,List<Tab01ListView> >{



        @Override
        protected List<Tab01ListView> doInBackground(Void...voids) {

            list=new ArrayList<Tab01ListView>();

            String cql = "select * from Trade";
            try {
                AVCloudQueryResult result = AVQuery.doCloudQuery(cql);
                System.out.println(result);
                List<AVObject> results = (List<AVObject>) result.getResults();
                for (int i = results.size()-1;i>=0;i--){
                   // Map<String, Object> map=new HashMap<String, Object>();
                    Tab01ListView tab01ListView = new Tab01ListView();
                    //map.put("portrait",R.drawable.touxiang);
//                    System.out.println("对象"+results.get(i).getAVUser("userName"));
                    //                   System.out.println(results.get(i).getAVUser("userName").get("username"));
                    String username = results.get(i).get("userName").toString();
                    AVQuery<AVObject> query = AVQuery.getQuery("_User");
                    query.whereEqualTo("username", username);
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (e != null) {
                                Log.i("bo", "又失败");
                            } else {
                                Log.i("bo", "成功");
                                List<AVObject> users = list;

                                System.out.println(users.get(0).getAVFile("image").getUrl());
                                tab01ListView.setUrl(users.get(0).getAVFile("image").getUrl());;
                            }
                        }
                    });


                    //map.put("username",results.get(i).get("userName"));
                    Date date = new Date();
                    String time = ConutDate.conutTwoDate(date,(Date) results.get(i).get("createdAt"));
                  //  map.put("time","发布于 "+time);
                  //  map.put("type",results.get(i).get("type"));
                 //   map.put("description",results.get(i).get("description"));
                 //  getActivity() map.put("contact",results.get(i).get("contactInfo"));
                 //   map.put("clicks_number",results.get(i).get("clicks").toString());
                 //   map.put("btn_text",results.get(i).get("btn_text"));
                  //  list.add(map);
                    tab01ListView.setUsername(results.get(i).get("userName").toString());
                    tab01ListView.setTime(time);
                    tab01ListView.setTitle(results.get(i).get("title").toString());
                    tab01ListView.setObjectId(results.get(i).getObjectId());
                    tab01ListView.setType(results.get(i).get("type").toString());
                    tab01ListView.setContact(results.get(i).get("contactInfo").toString());
                    tab01ListView.setClicks_number((int)results.get(i).get("clicks"));
                    /*
                    list.add(new Tab01ListView(results.get(i).get("userName").toString(),time,
                            results.get(i).get("title").toString(),results.get(i).getObjectId(),
                            results.get(i).get("type").toString(),results.get(i).get("contactInfo").toString(),(int)results.get(i).get("clicks")));
                    */
                    list.add(tab01ListView);
                    System.out.println("tabList"+list.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
    }


    /*
    private void showPopMenu(){
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_menu,null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .create()
                .showAsDropDown(top_add,0,20);


    }
    */
    /*
    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    /*
    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.menu1:
                        showContent = "点击 Item菜单1";
                        break;
                    case R.id.menu2:
                        showContent = "点击 Item菜单2";
                        break;

                }
                Toast.makeText(getActivity(),showContent,Toast.LENGTH_SHORT).show();
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
       // contentView.findViewById(R.id.menu3).setOnClickListener(listener);
       // contentView.findViewById(R.id.menu4).setOnClickListener(listener);
       // contentView.findViewById(R.id.menu5).setOnClickListener(listener);
    }
    */

}
