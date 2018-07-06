/*
 * Copyright (c) 2018. AxonIQ
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.axoniq.axonhub.client.boot;


import io.axoniq.axonhub.client.AxonHubConfiguration;
import io.axoniq.axonhub.client.PlatformConnectionManager;
import io.axoniq.axonhub.client.event.axon.AxonHubEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.upcasting.event.EventUpcaster;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures AxonHub as the EventStore provider.
 *
 * @author Marc Gathier
 */
@Configuration
@AutoConfigureAfter(MessagingAutoConfiguration.class)
@ConditionalOnClass(QueryUpdateEmitter.class)
public class EventStoreAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public EventStore eventStore(AxonHubConfiguration axonHubConfiguration,
                                 AxonConfiguration configuration,
                                 PlatformConnectionManager platformConnectionManager,
                                 Serializer snapshotSerializer,
                                 @Qualifier("eventSerializer") Serializer serializer) {
        return new AxonHubEventStore(axonHubConfiguration, platformConnectionManager, snapshotSerializer, serializer, configuration.getComponent(EventUpcaster.class) );
    }
}

