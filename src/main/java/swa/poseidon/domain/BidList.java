package swa.poseidon.domain;

import org.springframework.beans.factory.annotation.Required;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields
	@Id
	int bidListId;
}
