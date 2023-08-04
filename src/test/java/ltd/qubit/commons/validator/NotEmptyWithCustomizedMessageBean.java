////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import ltd.qubit.commons.validator.annotation.NotEmpty;

public class NotEmptyWithCustomizedMessageBean {

  @NotEmpty(message = "name属性为空，违背自定义规则")
  private String name;

  public NotEmptyWithCustomizedMessageBean() {}

  public NotEmptyWithCustomizedMessageBean(final String name) {
    this.name = name;
  }

  public final String getName() {
    return name;
  }

  public final NotEmptyWithCustomizedMessageBean setName(final String name) {
    this.name = name;
    return this;
  }

}
