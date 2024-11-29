////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import ltd.qubit.commons.validator.annotation.IdentityCard;

public class IdentityCardBean {

  @IdentityCard
  private String number;

  public IdentityCardBean() {}

  public IdentityCardBean(final String number) {
    this.number = number;
  }

  public final String getNumber() {
    return number;
  }

  public final IdentityCardBean setNumber(final String number) {
    this.number = number;
    return this;
  }
}
