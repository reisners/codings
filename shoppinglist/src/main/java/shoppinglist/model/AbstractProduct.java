package shoppinglist.model;

import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize(as = Product.class)
public interface AbstractProduct {

	@Value.Parameter
	String getName();
	
}
