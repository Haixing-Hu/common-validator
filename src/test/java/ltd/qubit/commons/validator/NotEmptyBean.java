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

public class NotEmptyBean {

  @NotEmpty("姓名")
  private String name;

  public NotEmptyBean() {}

  public NotEmptyBean(final String name) {
    this.name = name;
  }

  public final String getName() {
    return name;
  }

  public final NotEmptyBean setName(final String name) {
    this.name = name;
    return this;
  }

}
