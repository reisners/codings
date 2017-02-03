/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package shoppinglist.model;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

/**
 * The module that binds the StreamService so that it can be served.
 */
public class ProductModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    // Bind the ProductService service
    bindServices(serviceBinding(ProductService.class, ProductServiceImpl.class));
  }
}
