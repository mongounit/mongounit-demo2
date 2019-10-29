package org.mongounit.demo.dao.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreatePersonRequest {

  String positionName;

  private String name;

  private List<String> favColors;

  private Address address;
}
