////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import ltd.qubit.commons.validator.annotation.PersonName;

public class NameBean {

  @PersonName
  private String name;

  public NameBean() {}

  public NameBean(final String name) {
    this.name = name;
  }

  public final String getName() {
    return name;
  }

  public final NameBean setName(final String name) {
    this.name = name;
    return this;
  }

}
