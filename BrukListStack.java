//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BrukListStack<E>  {
    private List<E> data = new ArrayList();

    public BrukListStack() {
    }

    public E push(E obj) {
        this.data.add(obj);
        return obj;
    }

    public E peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return this.data.get(this.data.size() - 1);
        }
    }

    public E pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return this.data.remove(this.data.size() - 1);
        }
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }
}