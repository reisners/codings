/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package shoppinglist.model;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

/**
 * The stream interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the productStream service.
 */
public interface ProductService extends Service {

	/**
	 * Example: curl http://localhost:9000/api/product/Alice
	 */
	ServiceCall<NotUsed, String> product(String id);

	/**
	 * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
	 * "Hi"}' http://localhost:9000/api/product/Alice
	 */
	ServiceCall<Product, NotUsed> create();

	@Override
	default Descriptor descriptor() {
		// @formatter:off
		return named("product")
				.withCalls(pathCall("/api/product/:id", this::product), pathCall("/api/product", this::create))
				.withAutoAcl(true);
		// @formatter:on
	}
}
