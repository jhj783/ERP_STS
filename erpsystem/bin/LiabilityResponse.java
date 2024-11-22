package bin;

import java.util.List;

import com.erp.erpsystem.db.Liability;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LiabilityResponse {
	private List<Liability> liability; 
}
