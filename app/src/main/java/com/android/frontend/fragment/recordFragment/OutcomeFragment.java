package com.android.frontend.fragment.recordFragment;

import androidx.fragment.app.Fragment;
import com.android.frontend.R;
import com.android.frontend.db.DBManager;
import com.android.frontend.db.TypeBean;

import java.util.List;

/**
 * @Description: 支出记录页面
 * @Author: MING
 * @Date: 2024/01/07
 */

public class OutcomeFragment extends BaseRecordFragment {

    // 重写
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> outlist = DBManager.getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.ic_qita_fs);
    }

    @Override
    public void saveAccountToDB() {
        billBean.setKind(0);
        DBManager.insertItemToAccounttb(billBean);
    }
}
