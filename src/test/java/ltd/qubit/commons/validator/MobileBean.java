////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import ltd.qubit.commons.validator.annotation.Mobile;

public class MobileBean {

  @Mobile
  private String mobile;

  public MobileBean() {}

  public MobileBean(final String mobile) {
    this.mobile = mobile;
  }

  public final String getMobile() {
    return mobile;
  }

  public final MobileBean setMobile(final String mobile) {
    this.mobile = mobile;
    return this;
  }
}
