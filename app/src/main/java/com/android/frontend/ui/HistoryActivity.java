package com.android.frontend.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.frontend.R;
import com.android.frontend.adapter.BillAdapter;
import com.android.frontend.db.BillBean;
import com.android.frontend.dialog.CalendarDialog;
import com.android.frontend.db.DBManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyLv;
    private TextView timeTv;
    private List<BillBean> billList;
    private BillAdapter adapter;
    private int year, month;
    private int dialogSelPos = -1;
    private int dialogSelMonth = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyLv = findViewById(R.id.history_lv);
        timeTv = findViewById(R.id.history_tv_time);
        billList = new ArrayList<>();
        adapter = new BillAdapter(this, billList);
        historyLv.setAdapter(adapter);
        initTime();
        timeTv.setText(year + "年" + month + "月");
        loadData(year, month);
        setLVClickListener();
    }

    private void setLVClickListener() {
        historyLv.setOnItemLongClickListener((parent, view, position, id) -> {
            BillBean billBean = billList.get(position);
            deleteItem(billBean);
            return false;
        });
    }

    private void deleteItem(final BillBean billBean) {
        final int delId = billBean.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (dialog, which) -> {
                    DBManager.deleteItemFromAccounttbById(delId);
                    billList.remove(billBean);
                    adapter.notifyDataSetChanged(); // 更新适配器
                });
        builder.create().show();
    }

    private void loadData(int year, int month) {
        List<BillBean> list = DBManager.getAccountListOneMonthFromAccounttb(year, month);
        billList.clear();
        billList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.history_iv_back:
                finish();
                break;
            case R.id.history_iv_rili:
                CalendarDialog dialog = new CalendarDialog(this, dialogSelPos, dialogSelMonth);
                dialog.show();
                dialog.setDialogSize();
                dialog.setOnRefreshListener((selPos, year, month) -> {
                    timeTv.setText(year + "年" + month + "月");
                    loadData(year, month);
                    dialogSelPos = selPos;
                    dialogSelMonth = month;
                });
                break;
            case R.id.history_btn_clear:
                showDeleteDialog();
                break;
        }
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除提示")
                .setMessage("您确定要删除所有记录么？\n注意：删除后无法恢复，请慎重选择！")
                .setPositiveButton("取消", null)
                .setNegativeButton("确定", (dialog, which) -> {
                    DBManager.deleteAllAccount();
                    billList.clear();
                    adapter.notifyDataSetChanged(); // 更新适配器
                    Toast.makeText(HistoryActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                });
        builder.create().show();
    }
}