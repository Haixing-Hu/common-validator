////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator;

import ltd.qubit.commons.validator.annotation.Email;

public class EmailBean {

  @Email
  private String email;

  public EmailBean() {}

  public EmailBean(final String email) {
    this.email = email;
  }

  public final String getEmail() {
    return email;
  }

  public final EmailBean setEmail(final String email) {
    this.email = email;
    return this;
  }
}
