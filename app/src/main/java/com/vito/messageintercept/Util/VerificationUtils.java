package com.vito.messageintercept.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 验证手机，邮箱等号码
 * Created by Zhangfeng on 2015/9/11.
 * ModifiedBy: Clowire51
 * ModifiedTime: 2015/9/11 10:03
 * ModifiedNotes:
 * Version
 */
public class VerificationUtils {
    /**
     * 是否为对话号码
     * @description: Created by Zhangfeng on 2015/9/11 17:22
     */
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^\\d{11}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isEmail(String email){
        String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
