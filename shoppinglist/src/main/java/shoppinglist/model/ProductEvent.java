package shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;

public interface ProductEvent extends AggregateEvent<ProductEvent>, Jsonable {

	@Override
	default public AggregateEventTag<ProductEvent> aggregateTag() {
		return ProductEventTag.INSTANCE;
	}
	
	public class ProductCreated implements ProductEvent {
		public final String name;

		@JsonCreator
		public ProductCreated(String name) {
			this.name = name;
		}

		
	}

}
