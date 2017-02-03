package shoppinglist.model;

import java.util.Optional;

import org.immutables.value.Value;
import org.pcollections.PSequence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize(as = ProductState.class)
public interface AbstractProductState extends Jsonable {

	@Value.Parameter
	Optional<Product> getProduct();
	
	default public ProductState addProduct(String productName) {
		if (!getProduct().isPresent()) {
			throw new IllegalStateException("product can't be added before client is created");
		}
		return ProductState.of(Optional.of(Product.of(productName)));
	}
}
