package Commands;

import java.util.List;

public class MacroCommand<T, E extends Exception> implements iCommand<T, E> {
    List<iCommand<T, E>> commands;

    public MacroCommand(List<iCommand<T, E>> commands) {
        this.commands = commands;
    }

    @Override
    public T execute() throws E {
        for (iCommand<T, E> command : commands) {
            command.execute();
        }
        return null;
    }
}
