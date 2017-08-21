package com.example.fqzhang.myapplication.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fqzhang.myapplication.MainActivity;
import com.example.fqzhang.myapplication.R;
import com.example.fqzhang.myapplication.Util.FragmentExchangeController;
import com.example.fqzhang.myapplication.view.PercentView;
import com.example.fqzhang.myapplication.view.RandomNumView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
  /*  @BindView(R.id.randomNum)
    public RandomNumView randomNumView;*/
    @BindView(R.id.show_dialog_btn)
    public Button btn;
    @BindView(R.id.onclick)
    public TextView tv;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.e("detailFragment","----onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, new FrameLayout(getActivity()), false);
        TextView detail = (TextView) view.findViewById(R.id.show_detail_tv);
/*        Button btn = (Button) view.findViewById(R.id.show_dialog_btn);*/
        detail.setText("position:"+mParam2+":::"+mParam1);
        Log.e("detailFragment","----onCreateView");
        ButterKnife.bind(this,view);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
/*        percentView.setChangeProgresslistener(new PercentView.OnChangeProgressListener() {
            @Override
            public void onChangeProgress(PercentView view) {

            }
        });*/
        return view;
    }
    public void showDialog(){
/*        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Dialog);
        builder.setIcon(android.R.drawable.btn_star_big_on)
                .setTitle("提醒")
                .setMessage("hello world!")
                .setCancelable(true)
                .setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // bindData();
            }
        });
        builder.create().show();*/

        MDialogFragment dialogFragment = MDialogFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(dialogFragment, "mdialogFragment");
        ft.commit();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("detailFragment","----onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("detailFragment","----onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("detailFragment","----onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("detailFragment","----onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("detailFragment","----onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("detailFragment","----onDestroyView");
    }
/*    @OnClick(R.id.downLoad)
    public void load(){

    }*/
    boolean flag = false;
    @OnClick(R.id.onclick)
    public  void onclick() {
        Toast.makeText(getActivity(),"点击了！！",Toast.LENGTH_SHORT).show();
    }
/*    @OnClick(R.id.randomNum)
    public void randomNum(){
        if (flag) {

        } else {

        }
    }*/
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void onBackKeyControl(){
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        Log.e("detailFragment","----onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.e("detailFragment","----onDetach");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
