package swa.poseidon.form;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BidFormList
{
	List<BidForm> bidFormList;
}
