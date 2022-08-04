package edu.sctbc.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Arjen10
 * @date 2022/4/26 11:35
 */
@Slf4j
@Configuration
public class ResultJsonFormatConfig {

    @Bean
    public LocalDateTimeSerializer getLocalDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateUtil.yyyy_MM_dd_HH_mm_ss_FORMATTER);
    }

    @Bean
    public LocalDateTimeDeserializer getLocalDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateUtil.yyyy_MM_dd_HH_mm_ss_FORMATTER);
    }

    @Bean
    public LocalDateSerializer getLocalDateSerializer() {
        return new LocalDateSerializer(DateUtil.yyyy_MM_dd_FORMATTER);
    }

    @Bean
    public LocalDateDeserializer getLocalDateDeserializer() {
        return new LocalDateDeserializer(DateUtil.yyyy_MM_dd_FORMATTER);
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter(LocalDateTimeSerializer ldts,
                                                                            LocalDateTimeDeserializer ldtd,
                                                                            LocalDateDeserializer ldd,
                                                                            LocalDateSerializer lds) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper build = new Jackson2ObjectMapperBuilder()
                .findModulesViaServiceLoader(true)
                .serializerByType(LocalDateTime.class, ldts)
                .deserializerByType(LocalDateTime.class, ldtd)
                .serializerByType(LocalDate.class, lds)
                .deserializerByType(LocalDate.class, ldd)
                .dateFormat(new SimpleDateFormat(DateUtil.yyyy_MM_dd_HH_mm_ss))
                .build();
        build.getSerializerProvider()
                .setNullValueSerializer(new JsonSerializer<Object>() {
                    @Override
                    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                            throws IOException {
                        jsonGenerator.writeString("");
                    }
                });
        build.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        converter.setObjectMapper(build);
        return converter;
    }

}
