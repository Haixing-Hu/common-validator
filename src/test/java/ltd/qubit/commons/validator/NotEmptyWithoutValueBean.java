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

public class NotEmptyWithoutValueBean {

  @NotEmpty
  private String name;

  public NotEmptyWithoutValueBean() {}

  public NotEmptyWithoutValueBean(final String name) {
    this.name = name;
  }

  public final String getName() {
    return name;
  }

  public final NotEmptyWithoutValueBean setName(final String name) {
    this.name = name;
    return this;
  }

}
