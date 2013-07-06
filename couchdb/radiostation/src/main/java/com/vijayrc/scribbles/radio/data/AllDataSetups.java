package com.vijayrc.scribbles.radio.data;

import ch.lambdaj.group.Group;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.group.Groups.by;
import static ch.lambdaj.group.Groups.group;

@Log4j
@Repository
@Scope("singleton")
public class AllDataSetups implements BeanPostProcessor {
    private List<DataSetupMethod> methods = new ArrayList<DataSetupMethod>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(DataSetup.class))
                continue;
            DataSetup annotation = method.getAnnotation(DataSetup.class);
            methods.add(new DataSetupMethod(bean, method, annotation.description(), annotation.key(), annotation.order()));
        }
        return bean;
    }

    public void run(String key) throws Exception {
        Group<DataSetupMethod> methodGroup = group(methods, by(on(DataSetupMethod.class).order()));
        for (Group<DataSetupMethod> subGroup : methodGroup.subgroups()) {
            for (DataSetupMethod method : subGroup.findAll()) {
                if(!method.keyIs(key)) continue;
                ExecutorService executor = Executors.newFixedThreadPool(methods.size());
                CompletionService completionService = new ExecutorCompletionService(executor);
                completionService.submit(new DataSetupTask(method));
                log.info(completionService.take().get());
            }
        }
    }

}
