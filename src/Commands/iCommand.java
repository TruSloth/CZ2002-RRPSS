package Commands;

public interface iCommand<T, E extends Exception> {
    abstract T execute() throws E;
}
