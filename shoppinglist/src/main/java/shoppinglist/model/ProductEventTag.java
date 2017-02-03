package shoppinglist.model;

import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

public class ProductEventTag {
	public static final AggregateEventTag<ProductEvent> INSTANCE = AggregateEventTag.of(ProductEvent.class);
}
