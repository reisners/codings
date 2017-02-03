/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package shoppinglist.model;

import static java.util.concurrent.CompletableFuture.completedFuture;

import javax.inject.Inject;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Source;

/**
 * Implementation of the productString.
 */
public class ProductServiceImpl implements ProductService {

	 @Inject
	 public ProductServiceImpl(PersistentEntityRegistry persistentEntities) {
		 persistentEntities.register(ProductEntity.class);;
	 }

	@Override
	public ServiceCall<Product, NotUsed> create() {
		return request -> {
			return productEntityRef(request.getName()).ask(CreateProduct.of(request)).thenApply(ack -> NotUsed.getInstance());
		};
	}

	@Override
	public ServiceCall<NotUsed, String> product(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
