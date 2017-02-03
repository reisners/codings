package shoppinglist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;
import shoppinglist.model.ProductCommand.CreateProduct;
import shoppinglist.model.ProductEvent.ProductCreated;

public class ProductEntity extends PersistentEntity<ProductCommand, ProductEvent, ProductState> {

	@Override
	public PersistentEntity<ProductCommand, ProductEvent, ProductState>.Behavior initialBehavior(
			Optional<ProductState> snapshotState) {
		BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(ProductState.of(Optional.empty())));
		
		b.setCommandHandler(CreateProduct.class, (cmd, ctx) -> {
			if (state().getProduct().isPresent()) {
				ctx.invalidCommand("Product "+entityId()+" has already been created");
				return ctx.done();
			} else {
				Product product = cmd.product;
				List<ProductEvent> events = new ArrayList<ProductEvent>();
				events.add(new ProductCreated(product.getName()));
				return ctx.thenPersistAll(events, () -> ctx.reply(Done.getInstance()));
			}
		});
		return b.build();
	}

}
