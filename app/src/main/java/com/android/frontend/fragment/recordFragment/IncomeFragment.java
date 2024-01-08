package com.android.frontend.fragment.recordFragment;


import com.android.frontend.R;
import com.android.frontend.db.TypeBean;
import com.android.frontend.db.DBManager;

import java.util.List;

/**
 * @Description: 收入记录页面
 * @Author: MING
 * @Date: 2024/01/07
 */

public class IncomeFragment extends BaseRecordFragment {

    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> inlist = DBManager.getTypeList(1);
        typeList.addAll(inlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.other);
    }

    @Override
    public void saveAccountToDB() {
        billBean.setKind(1);
        DBManager.insertItemToAccounttb(billBean);
    }
}
