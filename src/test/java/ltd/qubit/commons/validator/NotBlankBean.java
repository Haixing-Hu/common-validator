////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import jakarta.validation.constraints.NotBlank;

public class NotBlankBean {

  @NotBlank
  private String name;

  public NotBlankBean() {}

  public NotBlankBean(final String name) {
    this.name = name;
  }

  public final String getName() {
    return name;
  }

  public final NotBlankBean setName(final String name) {
    this.name = name;
    return this;
  }

}
