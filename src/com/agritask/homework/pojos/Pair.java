package com.agritask.homework.pojos;

public class Pair<T, S> {
    private T left;
    private S right;

    public Pair(){}
    public Pair(T left, S right){
        setLeft(left);
        setRight(right);
    }
    public T getLeft(){
        return left;
    }

    public void setLeft(T left){
        this.left = left;
    }

    public S getRight() {
        return right;
    }

    public void setRight(S right) {
        this.right = right;
    }
}