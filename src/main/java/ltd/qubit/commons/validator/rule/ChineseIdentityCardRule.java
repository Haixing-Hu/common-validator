////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator.rule;

import java.time.LocalDate;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.validator.rule.impl.ChineseIdentityCardUtils;

import static ltd.qubit.commons.validator.rule.impl.ChineseIdentityCardUtils.LAST_CHAR;
import static ltd.qubit.commons.validator.rule.impl.ChineseIdentityCardUtils.NUMBER_LENGTH;
import static ltd.qubit.commons.validator.rule.impl.ChineseIdentityCardUtils.RATIO;

/**
 * 中华人民共和国大陆身份证号码验证规则。
 * <p>
 * 身份证号码第1~6位为地址码，表示登记户口时所在地的行政区划代码（省、市、县）。
 * 其中1-2位省、自治区、直辖市代码；3-4位地级市、盟、自治州代码；5-6位县、县级 市、区代码。
 * 如果行政区划进行了重新划分，同一个地方进行户口登记的可能存在地址码不一致的情况。行政区划代
 * 码按GB/T2260的规定执行。
 * <p>
 * 身份证号码第7~12为日期码，表示该居民的出生年月日。格式为YYYYMMDD，
 * 如19491001。出生日期码是按GB/T 7408的规定执行的。
 * <p>
 * 身份证号码第15~17为顺序码，表示同一地址码区域内，同年、同月、同日生的人所
 * 编订的顺序号，根据自己身份证的顺序码就可以知道：与我们同年同月同日生的同 性至少有多少个，且
 * 在我们之前登记户籍的有多少人。身份证顺序码的奇数分配给男性，偶数分配给女性。因此身份证号码倒
 * 数第2位是奇数则为男性，偶数则为女性。
 * <p>
 * 身份证中第十八位数字的计算方法如下：
 * <ul>
 * <li>将前面的身份证号码17位数分别乘以不同的系数，
 * 从第一位到第十七位的系数分别为：7、9、10、5、8、4、2、1、6、3、7、9、
 * 10、5、8、4、2；</li>
 * <li>将这17位数字和系数相乘的结果相加；</li>
 * <li>用加出来和除以11，看余数是多少；</li>
 * <li>余数只可能有0 、1、 2、 3、 4、 5、 6、 7、 8、 9、 10这11个数字；</li>
 * <li>其分别对应的最后一位身份证的号码为1、0、X、9、8、7、6、5、4、3、2；</li>
 * </ul>
 * <p>
 * 通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。
 * 如果余数是10，身份证的最后一位号码就是2。
 * <p>
 * <b>注意：</b>对于身份证号码中编码的出生日期，此验证器只验证该日期是否存在，
 * 没有验证出生日期的范围是否合法。
 *
 * @author 胡海星
 */
@Immutable
@ThreadSafe
public class ChineseIdentityCardRule implements ValidationRule<String> {

  /**
   * {@link ChineseIdentityCardRule} 的单例实例。
   */
  public static final ChineseIdentityCardRule INSTANCE = new ChineseIdentityCardRule();

  /**
   * 验证身份证号码是否合法。
   *
   * <p>身份证中第十八位数字的计算方法如下：
   * <ul>
   * <li>将前面的身份证号码17位数分别乘以不同的系数，
   * 从第一位到第十七位的系数分别为：7、9、10、5、8、4、2、1、6、3、7、9、
   * 10、5、8、4、2；</li>
   * <li>将这17位数字和系数相乘的结果相加；</li>
   * <li>用加出来和除以11，看余数是多少；</li>
   * <li>余数只可能有0 、1、 2、 3、 4、 5、 6、 7、 8、 9、 10这11个数字。</li>
   * <li>其分别对应的最后一位身份证的号码为1、0、X、9、8、7、6、5、4、3、2；</li>
   * </ul>
   * 通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。
   * 如果余数是10，身份证的最后一位号码就是2。
   *
   * <p><b>注意：</b>对于身份证号码中编码的出生日期，此函数只验证该日期是否存在，
   * 没有验证出生日期的范围是否合法。
   *
   * @param number
   *     待验证的身份证号码。
   * @return 若该身份证号码合法则返回{@code true}，否则返回{@code false}。
   */
  @Override
  public boolean validate(final String number) {
    // 验证长度是否合法
    if (number == null || number.length() != NUMBER_LENGTH) {
      return false;
    }
    // 验证奇偶校验码是否合法
    int sum = 0;
    for (int i = 0; i < NUMBER_LENGTH - 1; ++i) {
      final char ch = number.charAt(i);
      if (ch < '0' || ch > '9') {
        return false;
      }
      sum += (ch - '0') * RATIO[i];
    }
    final char lastChar = LAST_CHAR[sum % LAST_CHAR.length];
    if (Character.toUpperCase(number.charAt(NUMBER_LENGTH - 1)) != lastChar) {
      return false;
    }
    // 验证出生日期是否合法
    return ChineseIdentityCardUtils.isBirthdayValid(number);
    // 验证地址区县是否合法
    // FIXME: 暂时不支持旧地区编码
    //    if (! isAreaValid(number)) {
    //      return false;
    //    }
  }

  /**
   * 从身份证号码提取出生日期。
   * <p>
   * 身份证号码第7~12为日期码，表示该居民的出生年月日。格式为YYYYMMDD，
   * 如19491001。出生日期码是按GB/T 7408的规定执行的。
   *
   * <p>
   * <b>注意：</b>对于身份证号码中编码的出生日期，此函数不仅验证该日期是否存在，也会验证出生
   * 日期是否是个合法的日期，同时也会验证该日期是否超过今天的日期（不应该超过今天的日期）。
   *
   * @param number
   *     身份证号码。
   * @return
   *     若该身份证号码中的出生日期编码合法则返回合法的出生日期，否则返回{@code null}。
   */
  @Nullable
  public LocalDate getBirthday(final String number) {
    return ChineseIdentityCardUtils.getBirthday(number);
  }

  /**
   * 从身份证号码中提取性别。
   *
   * @param number
   *     指定的身份证号码。
   * @return
   *     若该身份证号码中的性别编码合法则返回合法的性别，即字符串"MALE"或"FEMALE"；否则返回
   *     {@code null}。
   */
  @Nullable
  public String getGender(final String number) {
    return ChineseIdentityCardUtils.getGender(number);
  }

  /**
   * 从身份证号码中提取家庭住址所在地区编码。
   * <p>
   * 注意：此函数不验证该编码是否合法。
   *
   * @param number
   *     指定的身份证号码。
   * @return
   *     若该身份证号码中的家庭住址所在地区编码合法则返回家庭住址所在地区编码；否则返回
   *     {@code null}。
   */
  @Nullable
  public String getAreaCode(final String number) {
    return ChineseIdentityCardUtils.getAreaCode(number);
  }
}
