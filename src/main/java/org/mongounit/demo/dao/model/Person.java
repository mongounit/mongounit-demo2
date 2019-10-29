package org.mongounit.demo.dao.model;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "people")
public class Person {

  @Id
  private String id;

  String positionId;

  private String name;

  private List<String> favColors;

  private Address address;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private Date created;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private Date updated;
}
