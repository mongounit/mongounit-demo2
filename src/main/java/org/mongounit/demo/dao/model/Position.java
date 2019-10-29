package org.mongounit.demo.dao.model;

import java.util.Date;
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
@Document(collection = "positions")
public class Position {

  @Id
  private String id;

  private String positionName;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private Date created;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private Date updated;
}
