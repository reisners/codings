package ${package}.spring;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import ${package}.ui.Receiver;

@Configuration
@ComponentScan(basePackageClasses={${package}.beans.Tag.class})
public class AppSpringConfig {
    public static final String DESTINATION_ALOA = "aloa";
    
    @Bean
    public static ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("vm://embedded?broker.persistent=false");
    }
    
    @Bean
    public static CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(connectionFactory());
    }
    
    @Bean
    public static JmsTemplate jmsPubSubTemplateAloa() {
        JmsTemplate jmsPubSubTemplate = new JmsTemplate();
        jmsPubSubTemplate.setPubSubDomain(true);
        jmsPubSubTemplate.setDefaultDestinationName(DESTINATION_ALOA);
        jmsPubSubTemplate.setConnectionFactory(cachingConnectionFactory());
        return jmsPubSubTemplate;
    }
    
    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Bean
    MessageListenerAdapter adapter(Receiver receiver) {
        MessageListenerAdapter messageListener
                = new MessageListenerAdapter(receiver);
        messageListener.setDefaultListenerMethod("receiveMessage");
        return messageListener;
    }

    @Bean
    SimpleMessageListenerContainer container(MessageListenerAdapter messageListener,
                                             ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageListener(messageListener);
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName(DESTINATION_ALOA);
        container.setPubSubDomain(true);
        return container;
    }

    
}