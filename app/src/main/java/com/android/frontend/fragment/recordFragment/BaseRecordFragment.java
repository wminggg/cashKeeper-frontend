package com.android.frontend.fragment.recordFragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.frontend.R;
import com.android.frontend.adapter.TypeBaseAdapter;
import com.android.frontend.db.BillBean;
import com.android.frontend.db.TypeBean;
import com.android.frontend.dialog.NoteDialog;
import com.android.frontend.common.utils.KeyBoardUtils;
import com.android.frontend.dialog.SelectTimeDialog;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 记录页面当中的支出模块
 * @Author: MING
 * @Date: 2024/01/08
 */

public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {

    LinearLayout keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    TextView typeTv, beizhuTv, timeTv;
    GridView typeGv;
    List<TypeBean> typeList;
    TypeBaseAdapter adapter;
    BillBean billBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        billBean = new BillBean();
        billBean.setTypename("其他");
        billBean.setsImageId(R.mipmap.other);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(view);
        setInitTime();
        loadDataToGV();
        setGVListener();
        return view;
    }

    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = sdf.format(date);
        timeTv.setText(time);
        billBean.setTime(time);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        billBean.setYear(year);
        billBean.setMonth(month);
        billBean.setDay(day);
    }

    private void setGVListener() {
        typeGv.setOnItemClickListener((parent, view, position, id) -> {
            adapter.selectPos = position;
            adapter.notifyDataSetInvalidated();
            TypeBean typeBean = typeList.get(position);
            String typename = typeBean.getTypename();
            typeTv.setText(typename);
            billBean.setTypename(typename);
            int simageId = typeBean.getSimageId();
            typeIv.setImageResource(simageId);
            billBean.setsImageId(simageId);
        });
    }

    public void loadDataToGV() {
        typeList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);
    }

    private void initView(View view) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeGv = view.findViewById(R.id.frag_record_gv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        beizhuTv = view.findViewById(R.id.frag_record_tv_beizhu);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        beizhuTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);

        KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView, moneyEt);
        boardUtils.showKeyboard();
        boardUtils.setOnEnsureListener(() -> {
            String moneyStr = moneyEt.getText().toString();
            if (TextUtils.isEmpty(moneyStr) || moneyStr.equals("0")) {
                requireActivity().finish();
                return;
            }
            float money = Float.parseFloat(moneyStr);
            billBean.setMoney(money);
            saveAccountToDB();
            requireActivity().finish();
        });
    }

    public abstract void saveAccountToDB();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;
            case R.id.frag_record_tv_beizhu:
                showBZDialog();
                break;
        }
    }

    private void showTimeDialog() {
        SelectTimeDialog dialog = new SelectTimeDialog(requireContext());
        dialog.show();
        dialog.setOnEnsureListener((time, year, month, day) -> {
            timeTv.setText(time);
            billBean.setTime(time);
            billBean.setYear(year);
            billBean.setMonth(month);
            billBean.setDay(day);
        });
    }

    public void showBZDialog() {
        NoteDialog dialog = new NoteDialog(requireContext());
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(() -> {
            String msg = dialog.getEditText();
            if (!TextUtils.isEmpty(msg)) {
                beizhuTv.setText(msg);
                billBean.setBeizhu(msg);
            }
            dialog.cancel();
        });
    }
}