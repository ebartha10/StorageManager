package com.example.tp_tema3_fx.backend.model;

/**
 * Immutable bill class
 * @param id id of the bill
 * @param order_id order id for the bill
 * @param date date the bill was registered.
 */
public record Bill(int id, int order_id, String date) {
}
