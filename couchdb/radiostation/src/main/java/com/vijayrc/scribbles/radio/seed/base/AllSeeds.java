package com.vijayrc.scribbles.radio.seed.base;

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
import static ch.lambdaj.Lambda.sort;
import static ch.lambdaj.group.Groups.by;
import static ch.lambdaj.group.Groups.group;

@Log4j
@Repository
@Scope("singleton")
public class AllSeeds implements BeanPostProcessor {
    private Set<SeedMethod> methods = new TreeSet<SeedMethod>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Seed.class))
                continue;
            Seed annotation = method.getAnnotation(Seed.class);
            methods.add(new SeedMethod(bean, method, annotation.description(), annotation.key(), annotation.order()));
        }
        return bean;
    }

    public void run(String... keys) throws Exception {
        List<String> keyList = Arrays.asList(keys);
        Group<SeedMethod> methodGroup = group(methods, by(on(SeedMethod.class).order()));
        for (Group<SeedMethod> subGroup : methodGroup.subgroups()) {
            for (SeedMethod method : subGroup.findAll()) {
                if (!keyList.contains(method.key()))
                    continue;
                ExecutorService executor = Executors.newFixedThreadPool(methods.size());
                CompletionService completionService = new ExecutorCompletionService(executor);
                completionService.submit(new SeedTask(method));
                AllSeeds.log.info(completionService.take().get());
            }
        }
    }

}
