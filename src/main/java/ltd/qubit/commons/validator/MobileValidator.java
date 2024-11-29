////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import ltd.qubit.commons.validator.annotation.Mobile;
import ltd.qubit.commons.validator.rule.ChineseMobileValidationRule;

/**
 * 电子邮件地址验证器。
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
 * @author 胡海星
 * @see <a href="https://blog.csdn.net/fengshi_sh/article/details/12085307">
 * 最新手机号码验证正则表达式</a>
 * @see <a href="http://www.cnblogs.com/zengxiangzhan/p/phone.html">
 * 最新手机号段归属地数据库(2019年2月新春版)</a>
 */
public class MobileValidator extends BaseValidator<Mobile, String> {

  public boolean validate(final String str) {
    return ChineseMobileValidationRule.INSTANCE.validate(str);
  }
}
