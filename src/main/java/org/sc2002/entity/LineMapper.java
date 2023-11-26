package org.sc2002.entity;
/**
 * LineMapper
 */
@FunctionalInterface
public interface LineMapper<T> {
    T mapLine(String[] fields);
}
