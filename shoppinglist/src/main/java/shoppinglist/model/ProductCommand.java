package shoppinglist.model;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;

import akka.Done;

public interface ProductCommand extends Jsonable {

	@SuppressWarnings("serial")
	@JsonDeserialize
	public static class CreateProduct implements ProductCommand, PersistentEntity.ReplyType<Done> {
		public final Product product;

		@JsonCreator
		public CreateProduct(Product product) {
			this.product = Preconditions.checkNotNull(product, "product");
		}
	}

	@SuppressWarnings("serial")
	@JsonDeserialize
	public class GetProduct implements ProductCommand, PersistentEntity.ReplyType<GetProductReply> {

		@Override
		public String toString() {
			return this.getClass().getSimpleName()+"{}";
		}
	}

	@SuppressWarnings("serial")
	@JsonDeserialize
	public class GetProductReply implements Jsonable {
		public final Optional<Product> product;

		@JsonCreator
		public GetProductReply(Optional<Product> product) {
			this.product = Preconditions.checkNotNull(product, "product");
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this.getClass().getSimpleName()).add("product", product).toString();
		}
	}
}
