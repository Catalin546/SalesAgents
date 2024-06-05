package com.example.salesagents.Domain;


public interface IObserver<T> {
    void updateObserver(T data);
}