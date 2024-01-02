package com.android.frontend.model.enums;

import com.android.frontend.R;
import lombok.Getter;

@Getter
public enum SvgType {
    SUCCESS("成功", R.raw.success_icon),
    FAILURE("失败", R.raw.failure_icon),
    DEFAULT("默认", R.raw.default_icon);

    private final String displayName;
    private final int svgResourceId;

    SvgType(String displayName, int svgResourceId) {
        this.displayName = displayName;
        this.svgResourceId = svgResourceId;
    }

}
