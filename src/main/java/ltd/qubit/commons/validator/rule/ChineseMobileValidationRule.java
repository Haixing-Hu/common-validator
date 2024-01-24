////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator.rule;

import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.annotation.RegEx;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * 中国大陆手机号码验证规则。
 *
 * <ul>
 * <li>手机号码可以以 "0", "86", "17951" 开头</li>
 * <li>手机号码一共11位数字</li>
 * <li>各运营商手机号码开头3位数字为
 *    <ul>
 *     <li>移动：134, 135, 136, 137, 138, 139, 147, 148, 150, 151, 152, 157, 158, 159, 172, 178,
 *            182, 183, 184, 187, 188, 198</li>
 *     <li>联通：130, 131, 132, 145, 146, 155, 156, 166, 171, 175, 176, 185, 186</li>
 *     <li>电信：133, 149, 153, 173, 174, 177, 180, 181, 189, 199</li>
 *     <li>虚拟运营商：170</li>
 *   </ul>
 * </li>
 * </ul>
 *
 * @see <a href="https://blog.csdn.net/fengshi_sh/article/details/12085307">
 * 最新手机号码验证正则表达式</a>
 * @see <a href="http://www.cnblogs.com/zengxiangzhan/p/phone.html">
 * 最新手机号段归属地数据库(2019年2月新春版)</a>
 * @author 胡海星
 */
@Immutable
@ThreadSafe
public class ChineseMobileValidationRule {

  public static final ChineseMobileValidationRule INSTANCE = new ChineseMobileValidationRule();

  @RegEx
  private static final String REGEX =
      "^(0|86|17951)?(13[0-9]|14[5-9]|15[0-35-9]|16[5-6]|17[0-8]|18[0-9]|19[89])[0-9]{8}$";

  private static final Pattern PATTERN = Pattern.compile(REGEX);

  public boolean validate(@Nullable final String mobile) {
    if (mobile == null || mobile.isEmpty()) {
      return false;
    }
    return PATTERN.matcher(mobile).matches();
  }
}
