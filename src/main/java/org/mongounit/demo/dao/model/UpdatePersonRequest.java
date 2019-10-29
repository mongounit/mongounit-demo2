package org.mongounit.demo.dao.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdatePersonRequest extends CreatePersonRequest {

  private String id;

  public UpdatePersonRequest(
      String id,
      String positionName,
      String name,
      List<String> favColors,
      Address address) {

    super(positionName, name, favColors, address);
    this.id = id;
  }
}
