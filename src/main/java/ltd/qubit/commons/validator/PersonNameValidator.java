////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import ltd.qubit.commons.reflect.AnnotationUtils;
import ltd.qubit.commons.validator.annotation.PersonName;
import ltd.qubit.commons.validator.rule.PersonNameType;
import ltd.qubit.commons.validator.rule.PersonNameValidationRule;

/**
 * 用户姓名验证器。
 *
 * <ul>
 * <li>中文名称允许出现下述 Unicode 字符集
 * <ul>
 * <li>CJK Unified Ideographs (U+4E00..U+9FEF)</li>
 * <li>CJK Unified Ideographs Extension A (U+3400..U+4DBF)</li>
 * <li>CJK Unified Ideographs Extension B (U+20000..U+2A6D6)</li>
 * <li>CJK Unified Ideographs Extension C (U+2A700..U+2B734)</li>
 * <li>CJK Unified Ideographs Extension D (U+2B740..U+2B81D)</li>
 * <li>CJK Unified Ideographs Extension E (U+2B820..U+2CEA1)</li>
 * <li>CJK Unified Ideographs Extension F (U+2CEB0..U+2EBE0)</li>
 * <li>CJK Compatibility Ideographs (U+F900..U+FAD9)</li>
 * <li>CJK Compatibility Ideographs Supplement (U+2F800..U+2FA1F)</li>
 * <li>其他一些中文编码历史原因导致的特殊字符，例如：U+E844, U+E863</li>
 * </ul>
 * 同时允许出现少数民族名中的点，包含[.·]等。最小长度为２个字符，最大为30个字符。名字中间不
 * 允许出现空格。</li>
 * <li>汉语拼音姓名允许大小写英文字母，半角空格，半角句点。最短需要有2个字符，最长不超过60
 * 个字符。</li>
 * <li>英文姓名允许大小写英文字母，半角空格，半角句点。最短需要有2个字符，最长不超过60个字符。</li>
 * </ul>
 *
 * @author 胡海星
 * @see <a href="https://en.wikipedia.org/wiki/CJK_Unified_Ideographs">
 *   CJK Unified Ideographs</a>
 * @see <a href="https://en.wikipedia.org/wiki/CJK_Compatibility_Ideographs">
 *   CJK Compatibility Ideographs</a>
 * @see <a href="https://en.wikipedia.org/wiki/CJK_Compatibility_Ideographs_Supplement">
 *   CJK Compatibility Ideographs Supplement</a>
 * @see <a href="http://www.voidcn.com/article/p-sjoaljqg-bpq.html">字符编码杂谈</a>
 * @see <a href="https://www.zhihu.com/question/25725560">
 *   现在的技术而言，字体可以不用考虑储存空间大小的问题，可是为什么大多数网站仍然不支持生僻字？</a>
 * @see <a href="https://www.zhihu.com/question/19668721">CSS 怎么处理生僻汉字？</a>
 * @see <a href="https://en.wikipedia.org/wiki/GB_18030">GB 18030</a>
 */
public class PersonNameValidator extends BaseValidator<PersonName, String> {

  private final PersonNameValidationRule rule = new PersonNameValidationRule();

  @Override
  public boolean validate(final String str) {
    PersonNameType type = AnnotationUtils.getAttribute(annotation, "value");
    if (type == null) {
      type = PersonNameType.ANY;
    }
    rule.setType(type);
    return rule.validate(str);
  }
}
