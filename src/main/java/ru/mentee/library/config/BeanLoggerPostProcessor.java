package ru.mentee.library.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeanLoggerPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(BeanLoggerPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("Bean '{}' типа'{}' готов к инициализации", beanName, bean.getClass().getName());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("Bean '{}' типа '{}' инициализирован", beanName, bean.getClass().getName());
        return bean;
    }
}
