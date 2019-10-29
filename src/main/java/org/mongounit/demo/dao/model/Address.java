package org.mongounit.demo.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
@Builder
public class Address {

  @NonNull
  private String street;

  private int zipcode;
}
