package dev.myodan.oxiom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Configuration
@EnableJpaAuditing
@EnableMongoAuditing
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class SpringConfig {
}
